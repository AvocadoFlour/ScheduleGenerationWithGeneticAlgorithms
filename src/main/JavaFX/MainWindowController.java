package main.JavaFX;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.JavaFX.classGroups.ClassGroupDataModel;
import main.JavaFX.classGroups.ClassGroupsTabController;
import main.JavaFX.courses.CoursesDataModel;
import main.JavaFX.courses.CoursesTabController;
import main.JavaFX.lectureHalls.LectureHallsDataModel;
import main.JavaFX.lectureHalls.LectureHallsTabController;
import main.JavaFX.lecturers.LecturersDataModel;
import main.JavaFX.lecturers.LecturersTabController;
import main.JavaFX.vocations.VocationsDataModel;
import main.JavaFX.vocations.VocationsTabController;

import java.io.IOException;
import java.sql.SQLException;

public class MainWindowController {

    @FXML
    MenuItem produceScheduleMenuItem;
    @FXML
    private VocationsTabController vocationsTabController;
    @FXML
    private ClassGroupsTabController classGroupsTabController;
    @FXML
    private CoursesTabController coursesTabController;
    @FXML
    private LecturersTabController lecturersTabController;
    @FXML
    private LectureHallsTabController lectureHallsTabController;

    private VocationsDataModel vocationsDataModel;
    private ClassGroupDataModel classGroupDataModel;
    private CoursesDataModel coursesDataModel;
    private LectureHallsDataModel lectureHallsDataModel;
    private LecturersDataModel lecturersDataModel;

    public void initialize() throws SQLException, IOException {

        this.vocationsDataModel = new VocationsDataModel();
        this.classGroupDataModel = new ClassGroupDataModel();
        this.coursesDataModel = new CoursesDataModel();
        this.lectureHallsDataModel = new LectureHallsDataModel();
        this.lecturersDataModel = new LecturersDataModel();

        this.vocationsTabController.injectMainWindowController(this);
        this.classGroupsTabController.injectMainWindowController(this);
        this.coursesTabController.injectMainWindowController(this);
        this.lecturersTabController.injectMainWindowController(this);
        this.lectureHallsTabController.injectMainWindowController(this);

    }

    public VocationsDataModel getVocationsDataModel() {
        return vocationsDataModel;
    }

    public ClassGroupDataModel getClassGroupDataModel() {
        return classGroupDataModel;
    }

    public CoursesDataModel getCoursesDataModel() {
        return coursesDataModel;
    }

    public LectureHallsDataModel getLectureHallsDataModel() {
        return lectureHallsDataModel;
    }

    public LecturersDataModel getLecturersDataModel() {
        return lecturersDataModel;
    }

}