package main.JavaFX.lecturers;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.classes.Lecturer;
import main.sqlite.DatabaseCommunicaton;

import java.sql.SQLException;

public class LecturersDataModel {
    DatabaseCommunicaton dbcomm = new DatabaseCommunicaton();

    public ObservableList<Lecturer> getLecturersList() {
        return LecturerList;
    }

    public ObservableList<Lecturer> LecturerList = FXCollections.observableArrayList(Lecturer ->
            new Observable[]{Lecturer.nameProperty(), Lecturer.lastNameProperty(), Lecturer.qualificationsProperty()});
    private ObjectProperty<Lecturer> currentLecturer = new SimpleObjectProperty<>();


    public ObjectProperty<Lecturer> currentLecturer() {
        return currentLecturer;
    }

    public void setCurrentLecturer(Lecturer currentLecturer) {
        this.currentLecturer.set(currentLecturer);
    }

    public Lecturer getCurrentLecturer() {
        return this.currentLecturer.get();
    }

    public ObservableList<Lecturer> loadLecturers() throws SQLException {
        dbcomm.createLecturersTable();
        ObservableList<Lecturer> LecturerObservableList = FXCollections.observableArrayList(dbcomm.queryLecturers());
        this.LecturerList = LecturerObservableList;
        return LecturerObservableList;
    }

    public void refreshLecturers() throws SQLException {
        this.LecturerList.clear();
        this.LecturerList.addAll(FXCollections.observableArrayList(dbcomm.queryLecturers()));
    }

    public void addLecturer(String name, String  lastName, Integer qualifications) throws SQLException {
        dbcomm.addLecturer(name, lastName, qualifications);
        refreshLecturers();
    }

    /*public void deleteLecturer(Integer id) throws SQLException {
        dbcomm.deleteLecturer(id);
        refreshLecturers();
    }*/

}