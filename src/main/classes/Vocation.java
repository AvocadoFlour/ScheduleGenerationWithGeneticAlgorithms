package main.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

public class Vocation {

    private long id;
    private String name;
    private HashMap<Course, Integer> courseRequirements;

    public Vocation(long id, String name, HashMap<Course, Integer> courseRequirements) {
        this.id = id;
        this.name = name;
        this.courseRequirements = courseRequirements;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Course, Integer> getCourseRequirements() {
        return courseRequirements;
    }

    public void setCourseRequirements(HashMap<Course, Integer> courseRequirements) {
        this.courseRequirements = courseRequirements;
    }

    public StringProperty vocationNameProperty() {
        StringProperty vocationNameSP = new SimpleStringProperty();
        vocationNameSP.set(this.name);
        return vocationNameSP;
    }

    public StringProperty courseRequirementsProperty() {
        StringProperty courseRequirementsSP = new SimpleStringProperty();
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Course, Integer> m : this.courseRequirements.entrySet()) {
            sb.append(m.getKey().getCourseName() + " " + m.getValue());
        }
        courseRequirementsSP.set(sb.toString());
        return courseRequirementsSP;
    }

}