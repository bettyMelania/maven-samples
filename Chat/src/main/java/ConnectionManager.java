import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConnectionManager extends Application {
    //we have 3 peoples: port: 2000,4000,5000
    private volatile Map<Socket,List<Object>> streamsForSockets;
    private volatile Map<ObjectInputStream,ReceiveMessagesTask> receiverThreads;
    private ThreadPoolExecutor executor;
    @Override
    public void start(Stage primaryStage){
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        streamsForSockets=new HashMap<>();
        receiverThreads=new HashMap<>();

        StartWindow c1 = new StartWindow();
        c1.setManager(2000, this);
        StartWindow c2 = new StartWindow();
        c2.setManager(4000, this);
        StartWindow c3 = new StartWindow();
        c3.setManager(5000, this);
        try {

            c1.start(new Stage());
            runServer(2000);
            c2.start(new Stage());
            runServer(4000);
            c3.start(new Stage());
            runServer(5000);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void runServer(int listenPort) {
        ServerTask task=new ServerTask(listenPort);
        executor.execute(task);
    }
    public Socket initializeConnection(int port,StartWindow s,int localPort) {

        Socket requestSocket=null;
        try{

            requestSocket = new Socket("localhost", port);

            if(requestSocket==null){
                s.showError("This port isn't available");
            }
            else {
                ChatWindow window = new ChatWindow();
                    List<Object> streams = new ArrayList<>();
                    ObjectInputStream in = new ObjectInputStream(requestSocket.getInputStream());
                    ObjectOutputStream out=new ObjectOutputStream(requestSocket.getOutputStream());
                    streams.add(out);
                    streams.add(in);
                    streams.add(window.getMessages());
                synchronized (streamsForSockets) {
                    streamsForSockets.put(requestSocket, streams);
                }

                window.setManagerSocket(this,requestSocket);
                Stage stage = new Stage();
                window.setManagerSocket(this,requestSocket);
                try {
                    window.start(stage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                ReceiveMessagesTask task=receiveMessage(in,window);
                receiverThreads.put(in,task);
                send("!Hello",requestSocket);
                System.out.println("connection started");
            }
        }catch (ConnectException ex){
            System.out.println(ex.getMessage());
            s.showError(ex.getMessage());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return requestSocket;
    }

    private synchronized ReceiveMessagesTask receiveMessage(ObjectInputStream in,ChatWindow window){
        ReceiveMessagesTask task=new ReceiveMessagesTask(in,window);
        executor.execute(task);
        return task;

    }

    public void send(String message,Socket socket){
        ObjectOutputStream out=(ObjectOutputStream) streamsForSockets.get(socket).get(0);
        try {
            out.writeObject(message);
            out.flush();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        if(message.equals("!bye")) {
            ObjectInputStream in=(ObjectInputStream)streamsForSockets.get(socket).get(1);
             receiverThreads.get(in).interrupt();
             receiverThreads.remove(in);
            ((Stage)((TextArea)streamsForSockets.get(socket).get(2)).getScene().getWindow()).close();
            streamsForSockets.remove(socket);
            closeConection(socket.getPort(), socket.getLocalPort());

        }
        else if(message.equals("!bye bye")){
            closeAllMyConnections(socket.getLocalPort());
        }
    }

    private void closeAllMyConnections(int localPort) {
        Iterator it = streamsForSockets.entrySet().iterator();
        List<Socket> toRemove=new ArrayList<>();
       for(Map.Entry<Socket,List<Object>> pair:streamsForSockets.entrySet()){
            Socket socket=pair.getKey();
            if(socket.getLocalPort()==localPort || socket.getPort()==localPort){
                ObjectInputStream in=(ObjectInputStream)(pair.getValue()).get(1);
                ((Stage)((TextArea)(pair.getValue()).get(2)).getScene().getWindow()).close();
                receiverThreads.get(in).interrupt();
                receiverThreads.remove(in);

                toRemove.add(socket);
            }
        }
        for(Socket socket:toRemove)
            streamsForSockets.remove(socket);
    }

    private void closeConection(int port, int localPort) {
        Iterator it = streamsForSockets.entrySet().iterator();
        Socket toDelete=null;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Socket socket=(Socket) pair.getKey();
            if(socket.getLocalPort()==port && socket.getPort()==localPort){
                ObjectInputStream in=(ObjectInputStream)((List<Object>)pair.getValue()).get(1);
                ((Stage)((TextArea)((List<Object>)pair.getValue()).get(2)).getScene().getWindow()).close();
                receiverThreads.get(in).interrupt();
                receiverThreads.remove(in);
                toDelete=socket;
                break;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        if(toDelete!=null)
            streamsForSockets.remove(toDelete);
    }

    public void sendManager(ChatWindow window,Socket connection) {

        window.setManagerSocket(this,connection);
    }

    class ReceiveMessagesTask implements Runnable {

        private ObjectInputStream in;
        private ChatWindow window;
        private String name="ReceiveMessages Task";
        String message = "";

        public ReceiveMessagesTask(ObjectInputStream in,ChatWindow window) {
            this.in=in;
            this.window=window;
        }
        @Override
        public void run() {
            System.out.println(name + " from "+window.getPort()+" : Running");

            while (!message.equals("!bye") && !message.equals("!bye bye")) {
                try {
                    message = (String) in.readObject();
                    //System.out.println("received > " + message);
                    window.showMessage(message);
                } catch (IOException e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            window.showException(e);
                        }
                    });
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(name + " from "+window.getPort()+" : Done");
        }
        @Override
        public String toString() {
            return name;
        }

        public void interrupt() {
            message="!bye";
        }
    }

    class ServerTask implements Runnable {

        private int listenPort;
        private String name="ServerTask ";

        public ServerTask(int port) {
            this.listenPort=port;
        }
        @Override
        public void run() {
            System.out.println(name + " port: "+listenPort+" : Running");
            ServerSocket providerSocket;
            try {
                providerSocket = new ServerSocket(listenPort, 10);
                while (true) {
                    try {
                        Socket connection = providerSocket.accept();
                        ChatWindow window = new ChatWindow();
                        List<Object> streams = new ArrayList<>();
                        ObjectOutputStream out=new ObjectOutputStream(connection.getOutputStream());
                        out.flush();
                        ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
                        streams.add(out);
                        streams.add(in);
                        streams.add(window.getMessages());
                        synchronized (streamsForSockets) {
                            streamsForSockets.put(connection, streams);
                        }

                        sendManager(window,connection);
                        Platform.runLater(new Runnable(){
                            public void run(){
                                Stage stage = new Stage();
                                try {
                                    window.start(stage);
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });
                        ReceiveMessagesTask t=receiveMessage(in,window);
                        receiverThreads.put(in,t);
                        send("!ack",connection);
                        System.out.println("connection received");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(name + " port: "+listenPort+" : Done");
        }
        @Override
        public String toString() {
            return name;
        }
    }


}