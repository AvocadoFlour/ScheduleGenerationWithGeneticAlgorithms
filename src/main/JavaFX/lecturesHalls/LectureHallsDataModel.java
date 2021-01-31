package main.JavaFX.lecturesHalls;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.classes.LectureHall;
import main.sqlite.DatabaseCommunicaton;

import java.sql.SQLException;

public class LectureHallsDataModel {
    DatabaseCommunicaton dbcomm = new DatabaseCommunicaton();
    private ObjectProperty<LectureHall> currentLectureHall = new SimpleObjectProperty<>();
    public ObservableList<LectureHall> lectureHallList = FXCollections.observableArrayList(lectureHall ->
            new Observable[]{lectureHall.codeProperty(), lectureHall.capacityProperty()});

    public ObservableList<LectureHall> getLectureHallList() {
        return lectureHallList;
    }

    public ObjectProperty<LectureHall> currentLectureHall() {
        return currentLectureHall;
    }

    public void setCurrentLectureHall(LectureHall currentLectureHall) {
        this.currentLectureHall.set(currentLectureHall);
    }

    public LectureHall getCurrentLectureHall() {
        return this.currentLectureHall.get();
    }

    public ObservableList<LectureHall> loadLectureHalls() throws SQLException {
        dbcomm.createLectureHallsTable();
        ObservableList<LectureHall> lectureHallObservableList = FXCollections.observableArrayList(dbcomm.queryLectureHalls());
        this.lectureHallList = lectureHallObservableList;
        return lectureHallObservableList;
    }

    public void refreshLectureHalls() throws SQLException {
        this.lectureHallList.clear();
        this.lectureHallList.addAll(FXCollections.observableArrayList(dbcomm.queryLectureHalls()));
    }

    public void addLectureHall(String hallCode, int hallCapacity) throws SQLException {
        dbcomm.addLectureHall(hallCode, hallCapacity);
        refreshLectureHalls();
    }

    public void deleteLectureHall(Integer id) throws SQLException {
        dbcomm.deleteLectureHall(id);
        refreshLectureHalls();
    }

}