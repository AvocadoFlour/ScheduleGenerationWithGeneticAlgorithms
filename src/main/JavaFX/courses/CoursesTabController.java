package main.JavaFX.courses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.JavaFX.MainWindow;
import main.JavaFX.MainWindowController;
import main.classes.Course;

import java.io.IOException;
import java.sql.SQLException;

public class CoursesTabController {

    private CoursesDataModel coursesDataModel;
    @FXML
    private SplitPane coursesSplitPane;
    @FXML
    private TableView<Course> coursesTableView;
    @FXML
    private Button addNewCourseButton;
    @FXML
    private Button deleteLCourseButton;
    private MainWindowController mainWindowController;

    public void initialize() throws SQLException {

        addNewCourseButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("courseInput.fxml"));
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = loader.load();

            } catch (IOException e) {
                e.printStackTrace();
            }

            CourseInputController courseInputController = loader.getController();
            courseInputController.initModel(coursesDataModel);

            stage.setTitle("Course Input");
            if (root!=null) {
                stage.setScene(new Scene(root));
                stage.setResizable(false);
            } else {throw new NullPointerException();}

            // Serves the purpose of the new window being imposed over the other window
            stage.initModality(Modality.APPLICATION_MODAL);

            // The following line makes it so that there will only be a single task icon in the task bar
            // when a new stage is show, which in this case is the input stage
            stage.initOwner( ((Node)actionEvent.getSource()).getScene().getWindow() );

            stage.show();
        });

        deleteLCourseButton.setOnAction(actionEvent -> {
            Course course = coursesTableView.getSelectionModel().getSelectedItem();
            try {
                coursesDataModel.deleteCourse(course.getId());
            } catch (SQLException e) {
                MainWindow.alertWindow("Notification", "This course is currently " +
                        "associated with a vocation and cannot be deleted because of that.", e.getLocalizedMessage());
                e.printStackTrace();
            }
        });

    };

    public void openCoursesInputWindow(ActionEvent actionEvent) throws SQLException {
    }

    private void initModel() throws SQLException {
        TableColumn<Course, String> courseNameColumn = new TableColumn<>("Name");

        // https://stackoverflow.com/a/22732723/10299831
        courseNameColumn.setCellFactory(tc -> {
            TableCell<Course, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(courseNameColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });

        TableColumn<Course, Integer> courseQualificationColumn = new TableColumn<>("Teacher qualification");
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseNameColumn.prefWidthProperty().bind(coursesTableView.widthProperty().multiply(0.5));
        courseNameColumn.setResizable(false);
        courseQualificationColumn.setCellValueFactory(new PropertyValueFactory<>("teacherQualification"));
        courseQualificationColumn.prefWidthProperty().bind(coursesTableView.widthProperty().multiply(0.5));
        courseQualificationColumn.setResizable(false);
        coursesTableView.getColumns().add(0, courseNameColumn);
        coursesTableView.getColumns().add(1, courseQualificationColumn);
        coursesTableView.getItems().clear();
        coursesDataModel.loadCourses();
        coursesTableView.setItems(coursesDataModel.getCoursesList());
    }

    public void injectMainWindowController(MainWindowController mainWindowController) throws SQLException {
        this.mainWindowController = mainWindowController;
        this.coursesDataModel = mainWindowController.getCoursesDataModel();
        initModel();
    };

}
