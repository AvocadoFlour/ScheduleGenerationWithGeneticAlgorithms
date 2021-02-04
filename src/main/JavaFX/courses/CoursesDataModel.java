package main.JavaFX.courses;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.classes.Course;
import main.sqlite.DatabaseCommunicaton;

import java.sql.SQLException;

public class CoursesDataModel {
    DatabaseCommunicaton dbcomm = new DatabaseCommunicaton();
    private ObjectProperty<Course> currentCourse = new SimpleObjectProperty<>();
    public ObservableList<Course> coursesList = FXCollections.observableArrayList(Course ->
            new Observable[]{Course.courseNameProperty(), Course.teacherQualificationProperty()});

    public ObservableList<Course> getCoursesList() {
        return coursesList;
    }

    public ObjectProperty<Course> currentCourse() {
        return currentCourse;
    }

    public void setCurrentCourse(Course currentCourse) {
        this.currentCourse.set(currentCourse);
    }

    public Course getCurrentCourse() {
        return this.currentCourse.get();
    }

    public ObservableList<Course> loadCourses() throws SQLException {
        dbcomm.createCoursesTable();
        ObservableList<Course> coursesObservableList = FXCollections.observableArrayList(dbcomm.queryCourses());
        this.coursesList = coursesObservableList;
        return coursesObservableList;
    }

    public void refreshCourses() throws SQLException {
        this.coursesList.clear();
        this.coursesList.addAll(FXCollections.observableArrayList(dbcomm.queryCourses()));
    }

    public void addCourse(String name,int qualification) throws SQLException {
        dbcomm.addCourse(name, qualification);
        refreshCourses();
    }

    public void deleteCourse(Long id) throws SQLException {
        dbcomm.deleteCourse(id);
        refreshCourses();
    }

}