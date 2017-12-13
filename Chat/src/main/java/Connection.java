import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Connection {

    public static Socket initializeConnection(int port) {

        Scanner inConsole = new Scanner(System.in);

        Socket requestSocket=null;
        ObjectOutputStream out;
        ObjectInputStream in;

        try {
            requestSocket = new Socket("localhost", port);
            System.out.println("client started");
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());


            if (!((String) in.readObject()).equals("!ack"))
                Client.closeConnection(requestSocket);
            else {
                Client.receiveMessages(in, requestSocket);
                Client.sendMessages(out, requestSocket);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    return requestSocket;
}

}
