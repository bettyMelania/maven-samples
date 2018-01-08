import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConnectionManager extends Application {
    //we have 3 peoples: port: 2000,3000,4000
    private volatile Map<Socket,List<Object>> streamsForSockets;
    private volatile Map<ObjectInputStream,ReceiveMessagesTask> receiverThreads;
    private ThreadPoolExecutor executor;
    @Override
    public void start(Stage primaryStage){
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        streamsForSockets=new HashMap<>();
        receiverThreads=new HashMap<>();

        StartWindow c1 = new StartWindow();
        c1.setManager(2000, this);
        StartWindow c2 = new StartWindow();
        c2.setManager(3000, this);
        StartWindow c3 = new StartWindow();
        c3.setManager(4000, this);
        try {

            c1.start(new Stage());
            runServer(2000);
            c2.start(new Stage());
            runServer(3000);
            c3.start(new Stage());
            runServer(4000);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void runServer(int listenPort) {
        ServerTask task=new ServerTask(listenPort);
        executor.execute(task);
    }
    public Socket initializeConnection(int port,StartWindow s) {
        Socket requestSocket=null;
            try{
            requestSocket = new Socket("localhost", port);
            if(requestSocket==null){
                s.showError("This port isn't available");
            }
            else {

                synchronized (streamsForSockets) {
                    List<Object> streams = new ArrayList<>();
                    ObjectInputStream in = new ObjectInputStream(requestSocket.getInputStream());
                    streams.add(new ObjectOutputStream(requestSocket.getOutputStream()));
                    streams.add(in);
                    streamsForSockets.put(requestSocket, streams);
                }
                System.out.println("init");
                ChatWindow window = new ChatWindow();
                window.setManagerSocket(this,requestSocket);
                Stage stage = new Stage();

                try {
                    window.setManagerSocket(this,requestSocket);
                    window.start(stage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                //ReceiveMessagesTask thread=receiveMessage(in,window);
                //receiverThreads.put(in,thread);
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

    private ReceiveMessagesTask receiveMessage(ObjectInputStream in,ChatWindow window){
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
    }

    public void sendManager(ChatWindow window,Socket connection) {

        window.setManagerSocket(this,connection);
    }

     class ReceiveMessagesTask implements Runnable {

        private ObjectInputStream in;
        private ChatWindow window;
        private String name="ReceiveMessages Task";

        public ReceiveMessagesTask(ObjectInputStream in,ChatWindow window) {
            this.in=in;
            this.window=window;
        }
        @Override
        public void run() {
            System.out.println(name + " on "+window.getPort()+" : Running");
            String message = "";
            while (!message.equals("!bye")) {
                try {
                    message = (String) in.readObject();
                    System.out.println("received > " + message);
                    window.showMessage(message);

                } catch (IOException e) {
                    window.showException(e);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(name + " : Done");
        }
        @Override
        public String toString() {
            return name;
        }
    }

    class ServerTask implements Runnable {

        private int listenPort;
        private String name="ServerTask Task ";

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
                        System.out.println("accepted");
                        synchronized (streamsForSockets) {
                            List<Object> streams = new ArrayList<>();
                            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
                            streams.add(new ObjectOutputStream(connection.getOutputStream()));
                            streams.add(in);
                            streamsForSockets.put(connection, streams);
                        }
                        ChatWindow window = new ChatWindow();
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
                        //ReceiveMessagesTask t=receiveMessage(in,window);
                        //receiverThreads.put(in,t);
                        send("!ack",connection);
                        System.out.println("connection received");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(name + " : Done");
        }
        @Override
        public String toString() {
            return name;
        }
    }

}
