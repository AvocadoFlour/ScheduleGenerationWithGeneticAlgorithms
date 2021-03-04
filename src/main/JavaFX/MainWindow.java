package main.JavaFX;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainWindow extends Application {

    public static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage= primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 1200,700);
        scene.getStylesheets().add(getClass().getResource(
                "style.css").toExternalForm());
        primaryStage.setTitle("Schedule arranger");
        primaryStage.minWidthProperty().set(800);
        primaryStage.minHeightProperty().set(600);

        // Both of the following lines are needed for nice rounded edges
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);

        ResizeHelper.addResizeListener(primaryStage);

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