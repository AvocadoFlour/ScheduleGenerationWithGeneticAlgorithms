package main.JavaFX.lecturers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LecturersInputController {

    private LecturersDataModel lecturersDataModel;
    @FXML private CheckBox literatureCheck;
    @FXML private CheckBox scienceCheck;
    @FXML private TextField nameField;
    @FXML private TextField lastNameField;
    @FXML private Button confirmButton;
    @FXML private Button cancelButton;
    @FXML private Text warningText;

    public void initialize() {

        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!literatureCheck.isSelected()&&!scienceCheck.isSelected()) {
                    warningText.setFill(Color.color( 0.6f, 0.2f, 0.2f));
                    warningText.setText("Select at least one qualification!");
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
                //lecturersDataModel.addLecturer(name, lastName, );
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            }
        });

    }

    public void initModel(LecturersDataModel lecturersDataModel) {
        this.lecturersDataModel = lecturersDataModel;
    }

}
