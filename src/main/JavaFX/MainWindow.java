package main.JavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.JavaFX.vocations.VocationsTabController;
import main.geneticAlgoritmClasses.EvolveAndSolve;

public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Schedule arranger");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        /*EvolveAndSolve e = new EvolveAndSolve();
        e.execute();*/

    }

    public static void main(String[] args) {
        launch(args);
    }
}