import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager extends Application {
    //we have 3 peoples: port: 2000,3000,4000
    private volatile Map<Socket,List<Object>> streamsForSockets;
    @Override
    public void start(Stage primaryStage){
        streamsForSockets=new HashMap<>();


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
        Runnable t = new Runnable() {
            public void run() {
                ServerSocket providerSocket;
                try

                {
                    providerSocket = new ServerSocket(listenPort, 10);


                    while (true) {
                        try {
                            Socket connection = providerSocket.accept();
                            int port=connection.getPort();
                            List<Object> streams=new ArrayList<>();
                            streams.add(new ObjectOutputStream(connection.getOutputStream()));
                            streams.add(new ObjectInputStream(connection.getInputStream()));
                            streamsForSockets.put(connection,streams);


                            ChatWindow window = new ChatWindow();
                            Platform.runLater(new Runnable(){
                                public void run(){
                                    Stage stage = new Stage();
                                    try {
                                        window.start(stage);
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                    send("!ack",connection);
                                    sendManager(window,connection);
                                }
                            });

                            System.out.println("connection received");


                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (
                        IOException e)

                {
                    e.printStackTrace();
                }

            }
        };
        new Thread(t).start();

    }
    public Socket initializeConnection(int port,StartWindow s) {


        Socket requestSocket=null;


        try {
            requestSocket = new Socket("localhost", port);
            if(requestSocket==null){
                s.showError("This port isn't available");
            }
            else {

                ChatWindow window = new ChatWindow();
                window.setManagerSocket(this,requestSocket);

                Stage stage = new Stage();
                try {
                    window.start(stage);
                    window.setManagerSocket(this,requestSocket);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                //send("!Hello",requestSocket);
                System.out.println("connection started");
            }

        }catch (ConnectException ex){
            System.out.println(ex.getMessage());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return requestSocket;
    }
    /*
    private void receiveMessage(ObjectInputStream in){
        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String message="";
                while(in!=null && !message.equals("!bye") && runningB){

                    try {
                        message = (String) in.readObject();
                        System.out.println(">" + message);
                        showMessage(message);
                    } catch (IOException e) {
                        showException(e);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        receiveThread.start();
    }
    */
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
}
