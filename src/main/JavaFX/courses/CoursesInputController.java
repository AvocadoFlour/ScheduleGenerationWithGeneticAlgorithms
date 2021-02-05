package main.JavaFX.courses;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CoursesInputController {

    private CoursesDataModel coursesDataModel;
    @FXML
    private TextField courseNameTextField;
    @FXML
    private Button createCourseButton;
    @FXML
    private Button cancelButton;
    @FXML
    private RadioButton literatureRadioButton;
    @FXML
    private RadioButton scienceRadioButton;
    @FXML
    private Label warningText;
    private ToggleGroup toggleGroup = new ToggleGroup();

    public void initialize() {

        literatureRadioButton.setUserData(1);
        scienceRadioButton.setUserData(2);
        literatureRadioButton.setToggleGroup(toggleGroup);
        scienceRadioButton.setToggleGroup(toggleGroup);

        createCourseButton.setOnAction(actionEvent -> {
            if (courseNameTextField.getText().length()<5) {
                warningText.setTextFill(Color.color( 0.6f, 0.2f, 0.2f));
                warningText.setText("Course name must have at least 5 letters.");
                return;
            }
            if (toggleGroup.getSelectedToggle()==null) {
                warningText.setTextFill(Color.color( 0.6f, 0.2f, 0.2f));
                warningText.setText("A qualification requirement is necessary.");
            }

            String courseName = courseNameTextField.getText();
            int qualificationRequirement = (int) toggleGroup.getSelectedToggle().getUserData();
            try {
                coursesDataModel.addCourse(courseName, qualificationRequirement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close();
        });

        cancelButton.setOnAction(actionEvent -> {
            close();
        });

    }

    private void close() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void initModel(CoursesDataModel coursesDataModel) {
        this.coursesDataModel = coursesDataModel;
    }

}
