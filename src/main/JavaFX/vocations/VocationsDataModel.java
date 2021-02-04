package main.JavaFX.vocations;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.classes.Course;
import main.classes.Vocation;
import main.sqlite.DatabaseCommunicaton;

import java.sql.SQLException;
import java.util.HashMap;

public class VocationsDataModel {
    DatabaseCommunicaton dbcomm = new DatabaseCommunicaton();
    private ObjectProperty<Vocation> currentVocation = new SimpleObjectProperty<>();
    public ObservableList<Vocation> vocationsList = FXCollections.observableArrayList(vocation ->
            new Observable[]{vocation.vocationNameProperty(), vocation.courseRequirementsPropery()});

    public ObservableList<Vocation> getVocationsList() {
        return vocationsList;
    }

    public ObjectProperty<Vocation> currentVocation() {
        return currentVocation;
    }

    public void setCurrentVocation(Vocation currentVocation) {
        this.currentVocation.set(currentVocation);
    }

    public Vocation getCurrentVocation() {
        return this.currentVocation.get();
    }

    public ObservableList<Vocation> loadVocations() throws SQLException {
        dbcomm.createVocationsTable();
        ObservableList<Vocation> vocationObservableList = FXCollections.observableArrayList(dbcomm.queryVocations());
        this.vocationsList = vocationObservableList;
        return vocationObservableList;
    }

    public void refreshVocations() throws SQLException {
        this.vocationsList.clear();
        this.vocationsList.addAll(FXCollections.observableArrayList(dbcomm.queryVocations()));
    }

    public void addVocation(String vocationName, HashMap<Course, Integer> courseRequirements) throws SQLException {
        dbcomm.addVocation(vocationName, courseRequirements);
        refreshVocations();
    }

    public void deleteVocation(Integer id) throws SQLException {
        dbcomm.deleteVocation(id);
        refreshVocations();
    }

}
