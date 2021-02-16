package main.JavaFX;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.JavaFX.classGroups.ClassGroupDataModel;
import main.JavaFX.classGroups.ClassGroupsTabController;
import main.JavaFX.courses.CoursesDataModel;
import main.JavaFX.courses.CoursesTabController;
import main.JavaFX.lectureHalls.LectureHallInputController;
import main.JavaFX.lectureHalls.LectureHallsDataModel;
import main.JavaFX.lectureHalls.LectureHallsTabController;
import main.JavaFX.lecturers.LecturersDataModel;
import main.JavaFX.lecturers.LecturersTabController;
import main.JavaFX.scheduleDisplay.ScheduleDisplayController;
import main.JavaFX.vocations.VocationsDataModel;
import main.JavaFX.vocations.VocationsTabController;
import main.classes.ClassGroup;
import main.classes.Course;
import main.classes.LectureHall;
import main.classes.Lecturer;
import main.geneticAlgoritmClasses.DynamicEvolveAndSolve;
import main.geneticAlgoritmClasses.DynamicSchedule;

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
    @FXML
    MenuItem testiranjeCrtanjaRasporeda;

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
                DynamicSchedule generatedSchedule = DynamicEvolveAndSolve.execute(classGroupDataModel.getClassGroupsArrayList(),
                        coursesDataModel.getCoursesArrayList(), lecturersDataModel.getLecturersArrayList(),
                        lectureHallsDataModel.getClassGroupsArrayList());
                openScheduleDisplay(generatedSchedule);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        testiranjeCrtanjaRasporeda.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scheduleDisplay/scheduleDisplay.fxml"));
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Generated schedule");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            stage.show();
        });

    }

    private void openScheduleDisplay(DynamicSchedule dynamicSchedule) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scheduleDisplay/scheduleDisplay.fxml"));
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ScheduleDisplayController scheduleDisplayController = loader.getController();
        scheduleDisplayController.setDynamicScheduleItem(dynamicSchedule);
        stage.setTitle("Generated schedule");
        stage.setScene(new Scene(root));
        stage.setResizable(false);

        stage.show();
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