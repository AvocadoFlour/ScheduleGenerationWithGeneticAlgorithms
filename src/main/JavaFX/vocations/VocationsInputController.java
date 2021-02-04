package main.JavaFX.vocations;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import main.classes.Course;

public class VocationsInputController {

    @FXML
    private TableView<Course> coursesTableView;
    @FXML
    private TableView<Course> addedCourseRequirementsTableView;
    @FXML
    private Button removeCourseRequirementButton;
    @FXML
    private Button addCourseRequirementButton;
    @FXML
    private RadioButton radioButton1h;
    @FXML
    private RadioButton radioButton3h;
    @FXML
    private RadioButton radioButton2h;
    @FXML
    private Button createButton;
    @FXML
    private Button cancelButton;

    public void initialize() {

    }

}
