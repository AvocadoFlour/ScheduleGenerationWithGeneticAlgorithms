package main.JavaFX.classGroups;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.classes.ClassGroup;
import main.classes.Course;
import main.classes.Vocation;
import main.sqlite.DatabaseCommunicaton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ClassGroupDataModel {
    DatabaseCommunicaton dbcomm = new DatabaseCommunicaton();
    private ObjectProperty<ClassGroup> currentClassGroup = new SimpleObjectProperty<>();
    public ObservableList<ClassGroup> classGroupsList = FXCollections.observableArrayList(classGroup ->
            new Observable[]{classGroup.identificationProperty(), classGroup.vocationNameProperty(), classGroup.numberOfStudentsProperty()});

    public ObservableList<ClassGroup> getClassGroupsList() {
        return classGroupsList;
    }

    public ObjectProperty<ClassGroup> currentClassGroup() {
        return currentClassGroup;
    }

    public void setCurrentClassGroup(ClassGroup currentClassGroup) {
        this.currentClassGroup.set(currentClassGroup);
    }

    public ClassGroup getCurrentClassGroup() {
        return this.currentClassGroup.get();
    }

    private ArrayList<ClassGroup> getClassGroupsArrayList() throws SQLException {
        return dbcomm.queryClassGroups();
    }

    public ObservableList<ClassGroup> loadClassGroups() throws SQLException {
        dbcomm.createClassGroupsTable();
        ObservableList<ClassGroup> classGroupsObservableList = FXCollections.observableArrayList(dbcomm.queryClassGroups());
        this.classGroupsList = classGroupsObservableList;
        return classGroupsObservableList;
    }

    public void refreshClassGroups() throws SQLException {
        this.classGroupsList.clear();
        this.classGroupsList.addAll(FXCollections.observableArrayList(dbcomm.queryClassGroups()));
    }

    public void addClassGroup(String classGroupIdentification, int vocationId, int numberOfStudents) throws SQLException {
        dbcomm.addClassGroup(classGroupIdentification, vocationId, numberOfStudents);
        refreshClassGroups();
    }

    public void deleteClassGroup(Integer id) throws SQLException {
        dbcomm.deleteClassGroup(id);
        refreshClassGroups();
    }

}