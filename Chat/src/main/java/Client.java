import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    public static void runServer(int listenPort) {
        Runnable t = new Runnable() {
            public void run() {
                ServerSocket providerSocket;
                try

                {
                    providerSocket = new ServerSocket(listenPort, 10);


                    while (true) {
                        try {
                            Socket connection = providerSocket.accept();

                            Runnable task = new Runnable() {
                                public void run() {

                                    Socket conn = connection;
                                    try {
                                        ObjectOutputStream out = new ObjectOutputStream(conn.getOutputStream());
                                        out.flush();
                                        ObjectInputStream in = new ObjectInputStream(conn.getInputStream());
                                        out.writeObject("!ack");
                                        out.flush();


                                        System.out.println("server started");

                                        receiveMessages(in, conn);
                                        sendMessages(out, conn);
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            };
                            new Thread(task).start();
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

    public static void sendMessages(ObjectOutputStream out,Socket conn) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    out.writeObject("!hello");
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scanner inConsole = new Scanner(System.in);
                String message="";
                while(!message.equals("!bye")){

                    try {
                        //System.out.print("Enter a message: ");
                        message = inConsole.nextLine();
                        out.writeObject(message);
                        out.flush();
                        if(message.equals("!byebye")){
                            System.exit(0);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                closeConnection(conn);

            }
        });
        t.start();


    }

    public static void receiveMessages(ObjectInputStream in,Socket conn) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner inConsole = new Scanner(System.in);
                String message="";
                while(!message.equals("!bye")){

                    try {
                        message = (String) in.readObject();
                        System.out.println(">" + message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                closeConnection(conn);
            }
        });
        t.start();
    }

    public static void closeConnection(Socket conn) {
        try {
            conn.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}