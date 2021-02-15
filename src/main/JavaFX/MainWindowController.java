package main.JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
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
import main.classes.ClassGroup;
import main.classes.Course;
import main.classes.LectureHall;
import main.classes.Lecturer;
import main.geneticAlgoritmClasses.DynamicEvolveAndSolve;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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

        produceScheduleMenuItem.setOnAction(actionEvent -> {
            try {
                DynamicEvolveAndSolve.execute(classGroupDataModel.getClassGroupsArrayList(),
                        coursesDataModel.getCoursesArrayList(), lecturersDataModel.getLecturersArrayList(),
                        lectureHallsDataModel.getClassGroupsArrayList());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

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