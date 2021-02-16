package main.JavaFX.scheduleDisplay;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
                double ratio = ((double) 1/12);
                double rectangleHeight = ratio * lectureDuration * 950;
                rectangle.setHeight(rectangleHeight);
                rectangle.widthProperty().set(180);
                rectangle.
                rectangle.setX(10);
                rectangle.setY((ratio*fromH)+50);
                int dayOfWeek = fromH/8;
                switch (dayOfWeek) {
                    case 0:
                        mondayPane.getChildren().add(rectangle);
                        break;
                    case 1:
                        tuesdayPane.getChildren().add(rectangle);
                        break;
                    case 2:
                        wednesdayPane.getChildren().add(rectangle);
                        break;
                    case 3:
                        thursdayPane.getChildren().add(rectangle);
                        break;
                    case 4:
                        fridayPane.getChildren().add(rectangle);
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
