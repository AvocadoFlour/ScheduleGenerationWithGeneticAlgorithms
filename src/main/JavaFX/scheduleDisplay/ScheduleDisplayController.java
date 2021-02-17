package main.JavaFX.scheduleDisplay;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import main.classes.ClassGroup;
import main.classes.Lecture;
import main.geneticAlgoritmClasses.DynamicSchedule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScheduleDisplayController {

    private Stage thisStage;
    @FXML
    private Menu changeClassGroupMenu;
    @FXML
    private Pane hoursPane;
    @FXML
    private Pane mondayPane;
    @FXML
    private Pane tuesdayPane;
    @FXML
    private Pane wednesdayPane;
    @FXML
    private Pane thursdayPane;
    @FXML
    private Pane fridayPane;

    private final int HEIGHT_OF_RECTANGLE_INPUT_AREA = 550;
    private final double RATIO = ((double) 1/11);
    HashMap<ClassGroup, ArrayList<Lecture>> classGroupsHashMap = new HashMap<>();

    public void initialize() {
        setUpHoursPane();
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage=thisStage;
    }

    private void setUpHoursPane() {
        for (int i = 0; i<11; i++) {
            Label hourLabel = new Label();
            hourLabel.setText(String.valueOf(i+8));
            hourLabel.setLayoutX(12);
            hourLabel.setLayoutY(((RATIO*i)*HEIGHT_OF_RECTANGLE_INPUT_AREA)+50);
            hoursPane.getChildren().add(hourLabel);
            Line line = new Line();
            line.setStartX(50);
            line.setStartY(((RATIO*i)*HEIGHT_OF_RECTANGLE_INPUT_AREA)+50);
            line.setEndX(1000);
            line.setEndY(((RATIO*i)*HEIGHT_OF_RECTANGLE_INPUT_AREA)+50);
            hoursPane.getChildren().add(line);
        }
    }

    private void addMenuItems(HashMap<ClassGroup, ArrayList<Lecture>> classGroupsHashMap) {
        for (Map.Entry<ClassGroup, ArrayList<Lecture>> e : classGroupsHashMap.entrySet()) {
            MenuItem menuItem = new MenuItem();
            menuItem.setText(e.getKey().getClassIdentification());
            menuItem.setUserData(e.getKey());
            menuItem.setOnAction(actionEvent -> {
                clearSchedule();
                setChangeClassGroup((ClassGroup) menuItem.getUserData());
            });
            changeClassGroupMenu.getItems().add(menuItem);
        }
    }

    private void setChangeClassGroup(ClassGroup classGroup) {
        setStageTitle(classGroup.getClassIdentification());
        drawSchedule(classGroupsHashMap.get(classGroup));
    }

    private void setStageTitle(String string) {
        thisStage.setTitle("Class group: " + string);
    }

    public void setDynamicScheduleItem(DynamicSchedule dynamicSchedule) {

        for (Lecture l : dynamicSchedule.getLectures()) {
            classGroupsHashMap.putIfAbsent(l.getClassGroup(), new ArrayList<>());
            classGroupsHashMap.get(l.getClassGroup()).add(l);
        }

        addMenuItems(classGroupsHashMap);

        int counter = 0;
        for(Map.Entry<ClassGroup, ArrayList<Lecture>> e : classGroupsHashMap.entrySet()) {
            if (counter > 0) {
                break;
            }
            counter += 1;
            drawSchedule(e.getValue());
        }
    }

    private void clearSchedule() {
        mondayPane.getChildren().removeIf(r -> r instanceof Rectangle);
        tuesdayPane.getChildren().removeIf(r -> r instanceof Rectangle);
        wednesdayPane.getChildren().removeIf(r -> r instanceof Rectangle);
        thursdayPane.getChildren().removeIf(r -> r instanceof Rectangle);
        fridayPane.getChildren().removeIf(r -> r instanceof Rectangle);
    }


    public void drawSchedule(ArrayList<Lecture> e) {
        setStageTitle(e.get(0).getClassGroup().getClassIdentification());
        for (Lecture l : e) {
            Color textColor = Color.valueOf("#fffaf0");
            Rectangle rectangle = new Rectangle();
            rectangle.setFill(Color.color( 0.6f, 0.2f, 0.2f));
            String course = l.getCourse().getCourseName();
            int fromH = l.getFromH();
            int toH = l.getToH();
            int lectureDuration = toH-fromH;
            double rectangleHeight = RATIO * lectureDuration * HEIGHT_OF_RECTANGLE_INPUT_AREA;
            rectangle.setHeight(rectangleHeight-4);
            rectangle.widthProperty().set(170);
            rectangle.setStrokeType(StrokeType.CENTERED);
            rectangle.setStroke(Color.BLACK);
            rectangle.setX(10);
            Label courseNameLabel = new Label();
            courseNameLabel.setTextFill(textColor);
            courseNameLabel.setLayoutX(12);
            courseNameLabel.setText("Course: " + course);
            Label fromToLabel = new Label();
            fromToLabel.setLayoutX(12);
            fromToLabel.setTextFill(textColor);
            Label lecturerLabel = new Label();
            lecturerLabel.setLayoutX(12);
            lecturerLabel.setTextFill(textColor);
            lecturerLabel.setText("Lecturer: " + l.getLecturer().getLastName() + " " + l.getLecturer().getName());
            Label lectureHallLabel = new Label();
            lectureHallLabel.setLayoutX(12);
            lectureHallLabel.setTextFill(textColor);
            lectureHallLabel.setText("Hall: " + l.getLectureHall().getHallCode());


            // dailyStartH variable:
            // Calculating the starting time of the lecture for any day. E.g. the genetic algorithm
            // could provide the starting value of 12h, which needs to be converted into 4 because
            // that is the time that the lecture will start on the next day, with the condition
            // that the day has 8 hour slots available for scheduling lectures
            int dailyStartH = fromH % 8;
            rectangle.setY(((RATIO*dailyStartH)*HEIGHT_OF_RECTANGLE_INPUT_AREA)+52);
            courseNameLabel.setLayoutY(((RATIO*dailyStartH)*HEIGHT_OF_RECTANGLE_INPUT_AREA)+50);
            int fromToLabelStartH = dailyStartH + 8;
            fromToLabel.setText("Time: " +  fromToLabelStartH + " - " + (fromToLabelStartH+lectureDuration));
            fromToLabel.setLayoutY(((RATIO*dailyStartH)*HEIGHT_OF_RECTANGLE_INPUT_AREA)+61);
            lecturerLabel.setLayoutY(((RATIO*dailyStartH)*HEIGHT_OF_RECTANGLE_INPUT_AREA)+72);
            lectureHallLabel.setLayoutY(((RATIO*dailyStartH)*HEIGHT_OF_RECTANGLE_INPUT_AREA)+83);

            int dayOfWeek = fromH/8;
            switch (dayOfWeek) {
                case 0 -> {
                    mondayPane.getChildren().add(rectangle);
                    mondayPane.getChildren().add(courseNameLabel);
                    mondayPane.getChildren().add(fromToLabel);
                    mondayPane.getChildren().add(lecturerLabel);
                    mondayPane.getChildren().add(lectureHallLabel);
                }
                case 1 -> {
                    tuesdayPane.getChildren().add(rectangle);
                    tuesdayPane.getChildren().add(courseNameLabel);
                    tuesdayPane.getChildren().add(fromToLabel);
                    tuesdayPane.getChildren().add(lecturerLabel);
                    tuesdayPane.getChildren().add(lectureHallLabel);
                }
                case 2 -> {
                    wednesdayPane.getChildren().add(rectangle);
                    wednesdayPane.getChildren().add(courseNameLabel);
                    wednesdayPane.getChildren().add(fromToLabel);
                    wednesdayPane.getChildren().add(lecturerLabel);
                    wednesdayPane.getChildren().add(lectureHallLabel);
                }
                case 3 -> {
                    thursdayPane.getChildren().add(rectangle);
                    thursdayPane.getChildren().add(courseNameLabel);
                    thursdayPane.getChildren().add(fromToLabel);
                    thursdayPane.getChildren().add(lecturerLabel);
                    thursdayPane.getChildren().add(lectureHallLabel);
                }
                case 4 -> {
                    fridayPane.getChildren().add(rectangle);
                    fridayPane.getChildren().add(courseNameLabel);
                    fridayPane.getChildren().add(fromToLabel);
                    fridayPane.getChildren().add(lecturerLabel);
                    fridayPane.getChildren().add(lectureHallLabel);
                }
            }
        }
    }

}
