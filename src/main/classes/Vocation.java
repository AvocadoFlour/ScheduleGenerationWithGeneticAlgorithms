package main.classes;

import java.util.HashMap;

public class Vocation {

    private long id;
    private String title;
    private HashMap<Course, Integer> courseRequirements;

    public Vocation(long id, String title, HashMap<Course, Integer> courseRequirements) {
        this.id = id;
        this.title = title;
        this.courseRequirements = courseRequirements;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HashMap<Course, Integer> getCourseRequirements() {
        return courseRequirements;
    }

    public void setCourseRequirements(HashMap<Course, Integer> courseRequirements) {
        this.courseRequirements = courseRequirements;
    }
}