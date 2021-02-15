package main.JavaFX.lecturers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LecturerInputControler {

    private LecturersDataModel lecturersDataModel;
    @FXML private CheckBox literatureCheck;
    @FXML private CheckBox scienceCheck;
    @FXML private TextField nameField;
    @FXML private TextField lastNameField;
    @FXML private Button confirmButton;
    @FXML private Button cancelButton;
    @FXML private Label warningText;

    public void initialize() {

        confirmButton.setOnAction(actionEvent -> {
            if(!literatureCheck.isSelected()&&!scienceCheck.isSelected()) {
                warningText.setTextFill(Color.color( 0.6f, 0.2f, 0.2f));
                warningText.setText("Select at least one qualification!");
                return;
            }
            String name = nameField.getText();
            String lastName = lastNameField.getText();
            StringBuilder qualificationsStringBuilder = new StringBuilder();
            if (literatureCheck.isSelected()) {
                qualificationsStringBuilder.append(1);
            }
            if (scienceCheck.isSelected()) {
                qualificationsStringBuilder.append(2);
            }
            try {
                lecturersDataModel.addLecturer(name, lastName, Integer.parseInt(qualificationsStringBuilder.toString()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Node source = (Node)  actionEvent.getSource();
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.close();
        });

        cancelButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });

    }

    public void initModel(LecturersDataModel lecturersDataModel) {
        this.lecturersDataModel = lecturersDataModel;
    }

}
