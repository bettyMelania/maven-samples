import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    ServerSocket providerSocket;
    Scanner inConsole;
    volatile String closeMessage;
    int portIn;
    int portOut;

    Client(int portIn,int portOut) {
        this.portIn = portIn;
        this.portOut = portOut;
        inConsole = new Scanner(System.in);
        closeMessage = "";
    }

    void runServer() {
        try {
            providerSocket = new ServerSocket(portIn, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!closeMessage.equals("!byebye")) {
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

                            receiveMessages(in,conn);
                            sendMessages(out,conn);
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
    }

    void runClient() {
            
                Runnable task = new Runnable() {
                    public void run() {
                        //while (!closeMessage.equals("close")) {
                            Socket requestSocket;
                            ObjectOutputStream out;
                            ObjectInputStream in;

                            System.out.print("You can initialize connection: ");
                            String messageConsole = inConsole.nextLine();
                            if (messageConsole.equals("!hello")) {
                                try {
                                    requestSocket = new Socket("localhost", portOut);
                                    System.out.println("client started");
                                    out = new ObjectOutputStream(requestSocket.getOutputStream());
                                    out.flush();
                                    in = new ObjectInputStream(requestSocket.getInputStream());

                                    out.writeObject("!hello");
                                    out.flush();

                                    if (!((String) in.readObject()).equals("!ack"))
                                        closeConnection(requestSocket);
                                    else {
                                        receiveMessages(in, requestSocket);
                                        sendMessages(out, requestSocket);
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    //}
                };
                new Thread(task).start();
    }

    private void sendMessages(ObjectOutputStream out,Socket conn) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String message="";
                while(!message.equals("!bye") && !closeMessage.equals("close")){

                    try {
                        //System.out.print("Enter a message: ");
                        message = inConsole.nextLine();
                        out.writeObject(message);
                        out.flush();
                        if(message.equals("!byebye")){
                            closeMessage="close";
                            System.exit(0);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                closeMessage="close";
                closeConnection(conn);

            }
        });
        t.start();


    }

    private void receiveMessages(ObjectInputStream in,Socket conn) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String message="";
                while(!message.equals("!bye") && !closeMessage.equals("close")){

                    try {
                        message = (String) in.readObject();
                        System.out.println(">" + message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }


                }

                closeMessage="close";
                closeConnection(conn);
            }
        });
        t.start();
    }

    private void closeConnection(Socket conn) {
        try {
            conn.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}