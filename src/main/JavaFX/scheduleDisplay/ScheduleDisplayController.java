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

    public void initialize() {
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
                double ratio = ((double) 1/17);
                double rectangleHeight = ratio * lectureDuration * 950;
                rectangle.setHeight(rectangleHeight-6);
                rectangle.widthProperty().set(180);
                rectangle.setStrokeType(StrokeType.CENTERED);
                rectangle.setStroke(Color.BLACK);
                rectangle.setX(10);
                Label courseNameLabel = new Label();
                courseNameLabel.setText(course);
                courseNameLabel.setLayoutX(10);

                // dailyStartH variable:
                // Calculating the starting time of the lecture for any day. E.g. the genetic algorithm
                // could provide the starting value of 12h, which needs to be converted into 4 because
                // that is the time that the lecture will start on the next day, with the condition
                // that the day has 8 hour slots available for scheduling lectures
                int dailyStartH = fromH % 8;
                rectangle.setY(((ratio*dailyStartH)*950)+50);
                courseNameLabel.setLayoutY(((ratio*dailyStartH)*950)+50);

                int dayOfWeek = fromH/8;
                switch (dayOfWeek) {
                    case 0:
                        mondayPane.getChildren().add(rectangle);
                        mondayPane.getChildren().add(courseNameLabel);
                        break;
                    case 1:
                        tuesdayPane.getChildren().add(rectangle);
                        tuesdayPane.getChildren().add(courseNameLabel);
                        break;
                    case 2:
                        wednesdayPane.getChildren().add(rectangle);
                        wednesdayPane.getChildren().add(courseNameLabel);
                        break;
                    case 3:
                        thursdayPane.getChildren().add(rectangle);
                        thursdayPane.getChildren().add(courseNameLabel);
                        break;
                    case 4:
                        fridayPane.getChildren().add(rectangle);
                        fridayPane.getChildren().add(courseNameLabel);
                        break;
                    case 5:
                        fridayPane.getChildren().add(rectangle);
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Pozvan poseban uvijet");
                        alert.setHeaderText("Dan tjedna u switch-case je ispao 5");
                        alert.setContentText("Treba bolje srediti ovo ");
                        alert.showAndWait();
                        break;
                    }
               System.out.println(l.getCourse().getCourseName() + " od " + fromH + " do " + toH + " Traje: " + lectureDuration);
            }
        }
    }

}
