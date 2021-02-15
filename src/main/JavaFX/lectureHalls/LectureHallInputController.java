package main.JavaFX.lectureHalls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LectureHallInputController {

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField hallCodeField;
    @FXML
    private TextField hallCapacityField;

    private LectureHallsDataModel lectureHallsDataModel;

    public void initialize() {

        /**
         * Cancel button functionality
         */
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            }
        });

        /**
         * Making sure that only numbers are inputted in the hall capacity field
         */
        hallCapacityField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    hallCapacityField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String hallCode = hallCodeField.getText();
                int hallCapacity = Integer.parseInt(hallCapacityField.getText());
                try {
                    lectureHallsDataModel.addLectureHall(hallCode, hallCapacity);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();
            }
        });
    }

    /**
     * Load up the data of the lecture hally which is displayed in the lectureHallsTab stage
     * @param model - the data class
     */
    public void initModel(LectureHallsDataModel model) {
        this.lectureHallsDataModel = model;
    }

}
