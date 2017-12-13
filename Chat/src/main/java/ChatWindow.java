import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.Socket;

public class ChatWindow  extends Application {
    private Socket socket;
    TextArea messages;

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label produs = new Label("connected with: "+socket);
        grid.add(produs, 0, 0);

        messages= new TextArea();
        grid.add(messages, 0, 1);


        TextField messageField = new TextField();
        grid.add(messageField, 0, 2);
        messageField.setPromptText("Enter a message");

        Button btnSend = new Button("Send ");
        HBox hbBtnSend = new HBox(10);

        btnSend.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                //send
                messages.appendText("Me >"+messageField.getText()+"\n");

                messageField.setText("");
                messageField.setPromptText("Enter a message");
            }
        });

        hbBtnSend.getChildren().add(btnSend);
        grid.add(hbBtnSend, 0, 3);



        Scene scene = new Scene(grid, 500, 275);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Chat");
        primaryStage.show();


    }

    public void setSocket(Socket socket){
        this.socket=socket;
    }


    public void receiveMessage(String message){
        messages.appendText("> "+message+"\n");
    }
}
