package main.JavaFX;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class MainWindow extends Application {

    public static Stage mainStage;
    private final int STAGE_WIDTH = 1000;
    private final int STAGE_HEIGHT = 600;
    private final int STAGE_MIN_WIDTH = 650;
    private final int STAGE_MIN_HEIGHT = 500;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage= primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, STAGE_WIDTH,STAGE_HEIGHT);
        scene.getStylesheets().add(getClass().getResource(
                "style.css").toExternalForm());
        primaryStage.setTitle("Schedule arranger");
        primaryStage.minWidthProperty().set(STAGE_MIN_WIDTH);
        primaryStage.minHeightProperty().set(STAGE_MIN_HEIGHT);

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

    public static void showAlertWindow(String title, String header, String context) {
        // Styling an alert window https://stackoverflow.com/a/28421229/10299831
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(MainWindow.class.getResource("style.css").toExternalForm());
        dialogPane.getScene().setFill(Color.TRANSPARENT);
        alert.initModality(Modality.NONE);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }

    public static void showInputWindow(Parent root, Stage stage, Window window) {
        Scene scene = new Scene(root);
        root.setId("additionalWindow");
        scene.getStylesheets().add(MainWindow.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        // Serves the purpose of the new window being imposed over the other window
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.initModality(Modality.WINDOW_MODAL);

        // The following line makes it so that there will only be a single task icon in the task bar
        // when a new stage is show, which in this case is the input stage
        stage.initOwner(window);

        stage.show();
    }

}