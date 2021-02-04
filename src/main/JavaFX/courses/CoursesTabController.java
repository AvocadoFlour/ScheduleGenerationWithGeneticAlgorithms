package main.JavaFX.courses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.JavaFX.lecturers.LecturersInputControler;
import main.classes.Course;
import main.classes.Lecturer;

import java.io.IOException;
import java.sql.SQLException;

public class CoursesTabController {

    private CoursesDataModel coursesDataModel;
    @FXML
    private TableView<Course> coursesTableView;
    @FXML
    private Button addNewCourseButton;
    @FXML
    private Button deleteLCourseButton;
    private TableColumn<Course, String> courseNameColumn;
    private TableColumn<Course, Integer> courseQualificationColumn;

    public void initialize() throws SQLException {
        initModel();
        coursesDataModel.dbcomm.createCoursesTable();

        addNewCourseButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("coursesInput.fxml"));
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = loader.load();

            } catch (IOException e) {
                e.printStackTrace();
            }

            CoursesInputController coursesInputController = loader.getController();
            coursesInputController.initModel(coursesDataModel);

            stage.setTitle("Course Input");
            if (root!=null) {
                stage.setScene(new Scene(root));
                stage.setResizable(false);
            } else {throw new NullPointerException();}

            // Serves the purpose of the new window being imposed over the other window
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
        });

        deleteLCourseButton.setOnAction(actionEvent -> {
            Course course = coursesTableView.getSelectionModel().getSelectedItem();
            try {
                coursesDataModel.deleteCourse(course.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    };

    public void openCoursesInputWindow(ActionEvent actionEvent) throws SQLException {
    }

    private void initModel() throws SQLException {
        if (this.coursesDataModel != null) {
            throw new IllegalStateException("Courses model can only be initialized once");
        }
        this.coursesDataModel = new CoursesDataModel();
        courseNameColumn = new TableColumn<>("Name");
        courseQualificationColumn = new TableColumn<>("Teacher qualification");
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseQualificationColumn.setCellValueFactory(new PropertyValueFactory<>("teacherQualification"));
        coursesTableView.getColumns().add(0, courseNameColumn);
        coursesTableView.getColumns().add(1, courseQualificationColumn);
        coursesTableView.getItems().clear();
        coursesDataModel.loadCourses();
        coursesTableView.setItems(coursesDataModel.getCoursesList());
    }

}
