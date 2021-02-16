package main.JavaFX.scheduleDisplay;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import main.classes.ClassGroup;
import main.classes.Lecture;
import main.geneticAlgoritmClasses.DynamicSchedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ScheduleDisplayController {

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

    public void initialize() {
        setUpHoursPane();
        // generate();
    }

    private void generate() {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.color( 0.6f, 0.2f, 0.2f));
        rectangle.setHeight(20);
        rectangle.setWidth(20);
        mondayPane.getChildren().add(rectangle);
        hoursPane.getChildren().add(rectangle);
        tuesdayPane.getChildren().add(rectangle);
    }

    private void setUpHoursPane() {
        for (int i = 0; i<11; i++) {
            Label hourLabel = new Label();
            hourLabel.setText(String.valueOf(i+8));
            hourLabel.setLayoutX(12);
            hourLabel.setLayoutY(((RATIO*i)*HEIGHT_OF_RECTANGLE_INPUT_AREA)+50);
            hoursPane.getChildren().add(hourLabel);
        }
    }

    public void setDynamicScheduleItem(DynamicSchedule dynamicSchedule) {
        HashMap<ClassGroup, ArrayList<Lecture>> classGroupsHashMap = new HashMap<>();

        for (Lecture l : dynamicSchedule.getLectures()) {
            classGroupsHashMap.putIfAbsent(l.getClassGroup(), new ArrayList<>());
            classGroupsHashMap.get(l.getClassGroup()).add(l);
        }

        int couunter = 0;
        for(Map.Entry<ClassGroup, ArrayList<Lecture>> e : classGroupsHashMap.entrySet()) {
            if (couunter > 0 ) {
                break;
            }
            couunter += 1;
            String classIdentification = e.getKey().getClassIdentification();
            for (Lecture l : e.getValue()) {
                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.color( 0.6f, 0.2f, 0.2f));
                String course = l.getCourse().getCourseName();
                int fromH = l.getFromH();
                int toH = l.getToH();
                int lectureDuration = toH-fromH;
                double rectangleHeight = RATIO * lectureDuration * HEIGHT_OF_RECTANGLE_INPUT_AREA;
                rectangle.setHeight(rectangleHeight-4);
                rectangle.widthProperty().set(180);
                rectangle.setStrokeType(StrokeType.CENTERED);
                rectangle.setStroke(Color.BLACK);
                rectangle.setX(10);
                Label courseNameLabel = new Label();
                courseNameLabel.setLayoutX(12);
                courseNameLabel.setText(course);
                Label fromToLabel = new Label();
                fromToLabel.setLayoutX(12);
                Label classIdentificationLabel = new Label();
                classIdentificationLabel.setLayoutX(12);
                classIdentificationLabel.setText(classIdentification);


                // dailyStartH variable:
                // Calculating the starting time of the lecture for any day. E.g. the genetic algorithm
                // could provide the starting value of 12h, which needs to be converted into 4 because
                // that is the time that the lecture will start on the next day, with the condition
                // that the day has 8 hour slots available for scheduling lectures
                int dailyStartH = fromH % 8;
                rectangle.setY(((RATIO*dailyStartH)*HEIGHT_OF_RECTANGLE_INPUT_AREA)+52);
                courseNameLabel.setLayoutY(((RATIO*dailyStartH)*HEIGHT_OF_RECTANGLE_INPUT_AREA)+50);
                int fromToLabelStartH = dailyStartH + 8;
                fromToLabel.setText(fromToLabelStartH + " - " + (fromToLabelStartH+lectureDuration));
                fromToLabel.setLayoutY(((RATIO*dailyStartH)*HEIGHT_OF_RECTANGLE_INPUT_AREA)+61);
                classIdentificationLabel.setLayoutY(((RATIO*dailyStartH)*HEIGHT_OF_RECTANGLE_INPUT_AREA)+72);

                int dayOfWeek = fromH/8;
                switch (dayOfWeek) {
                    case 0 -> {
                        mondayPane.getChildren().add(rectangle);
                        mondayPane.getChildren().add(courseNameLabel);
                        mondayPane.getChildren().add(fromToLabel);
                        mondayPane.getChildren().add(classIdentificationLabel);
                    }
                    case 1 -> {
                        tuesdayPane.getChildren().add(rectangle);
                        tuesdayPane.getChildren().add(courseNameLabel);
                        tuesdayPane.getChildren().add(fromToLabel);
                        tuesdayPane.getChildren().add(classIdentificationLabel);
                    }
                    case 2 -> {
                        wednesdayPane.getChildren().add(rectangle);
                        wednesdayPane.getChildren().add(courseNameLabel);
                        wednesdayPane.getChildren().add(fromToLabel);
                        wednesdayPane.getChildren().add(classIdentificationLabel);
                    }
                    case 3 -> {
                        thursdayPane.getChildren().add(rectangle);
                        thursdayPane.getChildren().add(courseNameLabel);
                        thursdayPane.getChildren().add(fromToLabel);
                        thursdayPane.getChildren().add(classIdentificationLabel);
                    }
                    case 4 -> {
                        fridayPane.getChildren().add(rectangle);
                        fridayPane.getChildren().add(courseNameLabel);
                        fridayPane.getChildren().add(fromToLabel);
                        fridayPane.getChildren().add(classIdentificationLabel);
                    }
                }
            }
        }
    }

}
