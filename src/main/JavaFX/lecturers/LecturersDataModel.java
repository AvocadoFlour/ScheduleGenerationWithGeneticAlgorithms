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
    private ObjectProperty<Lecturer> currentLecturer = new SimpleObjectProperty<>();
    public ObservableList<Lecturer> lecturersList = FXCollections.observableArrayList(Lecturer ->
            new Observable[]{Lecturer.nameProperty(), Lecturer.lastNameProperty(), Lecturer.qualificationsProperty()});

    public ObservableList<Lecturer> getLecturersList() {
        return lecturersList;
    }

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
        ObservableList<Lecturer> lecturerObservableList = FXCollections.observableArrayList(dbcomm.queryLecturers());
        this.lecturersList = lecturerObservableList;
        return lecturerObservableList;
    }

    public void refreshLecturers() throws SQLException {
        this.lecturersList.clear();
        this.lecturersList.addAll(FXCollections.observableArrayList(dbcomm.queryLecturers()));
    }

    public void addLecturer(String name, String  lastName, Integer qualifications) throws SQLException {
        dbcomm.addLecturer(name, lastName, qualifications);
        refreshLecturers();
    }

    public void deleteLecturer(Integer id) throws SQLException {
        dbcomm.deleteLecturer(id);
        refreshLecturers();
    }

}