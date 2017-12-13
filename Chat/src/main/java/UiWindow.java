import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.Socket;
import java.util.Scanner;

public class UiWindow  extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        Scanner inConsole = new Scanner(System.in);
        System.out.print("Enter a listen port: ");
        Integer portIn = Integer.parseInt(inConsole.nextLine());

        Client.runServer(portIn);



        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Label produs = new Label("listen port: "+portIn);
        grid.add(produs, 0, 0);

        TextField portField = new TextField();
        grid.add(portField, 0, 1);
        portField.setPromptText("Enter a port");

        Button btn = new Button("Initialize new connection ");
        HBox hbBtn = new HBox(10);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                Socket socket=Connection.initializeConnection(Integer.parseInt(portField.getText()));
                if(socket==null){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Connection failed");

                    alert.showAndWait();


                }
                else {
                    ChatWindow window = new ChatWindow();
                    window.setSocket(socket);
                    Stage stage = new Stage();
                    try {
                        window.start(stage);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                portField.setText("");
                portField.setPromptText("Enter a port");
            }
        });

        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 3);


        Scene scene = new Scene(grid, 500, 275);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Chat");
        primaryStage.show();


    }

}
