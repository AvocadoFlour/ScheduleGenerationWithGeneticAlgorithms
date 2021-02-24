package main.JavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Schedule arranger");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();        

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void alertWindow(String title, String header, String context) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }

}