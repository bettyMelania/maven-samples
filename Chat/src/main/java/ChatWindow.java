import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatWindow  extends Application {
    private Socket connection;
    private TextArea messages;
    private ConnectionManager manager;


    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label produs = new Label("connected with: "+connection);
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
                manager.send(messageField.getText(),connection);


                messages.appendText("Me >"+messageField.getText()+"\n");

                messageField.setText("");
                messageField.setPromptText("Enter a message");
                //if(messageField.getText().equals("!bye"))
                    //stopReceiving();
            }
        });

        hbBtnSend.getChildren().add(btnSend);
        grid.add(hbBtnSend, 0, 3);



        Scene scene = new Scene(grid, 500, 275);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Chat");
        primaryStage.show();


    }



    public void showException(Exception ex){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
        System.out.println(ex);
    }
    public void showMessage(String message){
        Platform.runLater(new Runnable() {
            public void run() {
                messages.appendText("> " + message + "\n");
            }
        });
    }



    public void setManagerSocket(ConnectionManager manager, Socket connection) {
        this.manager=manager;
        this.connection=connection;
    }

    public int getPort() {
        return connection.getLocalPort();
    }
}
