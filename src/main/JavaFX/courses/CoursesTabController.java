package main.JavaFX.courses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    private VBox coursesVBox;
    @FXML
    private TableView<Course> coursesTableView;
    @FXML
    private Button createNewCourseButton;
    @FXML
    private Button deleteLCourseButton;
    private MainWindowController mainWindowController;

    public void initialize() throws SQLException {

        setupUiControls();

        createNewCourseButton.setOnAction(actionEvent -> {
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

            Window window = ((Node)actionEvent.getSource()).getScene().getWindow();
            MainWindow.showInputWindow(root,stage, window);

        });

        deleteLCourseButton.setOnAction(actionEvent -> {
            Course course = coursesTableView.getSelectionModel().getSelectedItem();
            try {
                coursesDataModel.deleteCourse(course.getId());
            } catch (SQLException e) {
                MainWindow.showAlertWindow("Notification", "This course is currently " +
                        "associated with a vocation and cannot be deleted because of that.", e.getLocalizedMessage());
                e.printStackTrace();
            }
        });

    };

    private void setupUiControls() {
        //vocationsTabHBox.setPadding(new Insets(0, 0, 0, 0));
        coursesVBox.setAlignment(Pos.CENTER);
        coursesVBox.spacingProperty().bind(coursesVBox.widthProperty().multiply(0.15));

        deleteLCourseButton.setMinWidth(130.0);
        deleteLCourseButton.setPrefWidth(130.0);
        deleteLCourseButton.setMaxWidth(130.0);
        createNewCourseButton.setMinWidth(130.0);
        createNewCourseButton.setPrefWidth(130.0);
        createNewCourseButton.setMaxWidth(130.0);
    }

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
