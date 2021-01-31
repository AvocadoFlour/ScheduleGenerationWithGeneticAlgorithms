package main.classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {

    private Long id;
    private String courseName;
    private int teacherQualification;

    public Course(Long id, String courseName, int teacherQualification) {
        this.id = id;
        this.courseName = courseName;
        this.teacherQualification = teacherQualification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getTeacherQualification() {
        return teacherQualification;
    }

    public void setTeacherQualification(int teacherQualification) {
        this.teacherQualification = teacherQualification;
    }

    public StringProperty courseNameProperty() {
        StringProperty courseNameSP = new SimpleStringProperty();
        courseNameSP.set(this.courseName);
        return courseNameSP;
    }

    public IntegerProperty teacherQualificationProperty() {
        IntegerProperty teacherQualificationProperty = new SimpleIntegerProperty();
        teacherQualificationProperty.set(this.teacherQualification);
        return teacherQualificationProperty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        if (teacherQualification != course.teacherQualification) return false;
        if (!id.equals(course.id)) return false;
        return courseName.equals(course.courseName);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + courseName.hashCode();
        result = 31 * result + teacherQualification;
        return result;
    }
}
