package main.mockData;

import main.classes.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MockData {

    // Two lecturers (professors)
    //First lecturer who can hold lectures for the first and the third courses
    public Lecturer lecturer1 = new Lecturer(0, "Mark", "Twain", new int[]{1, 3});
    //Second lecturer who can hold lectures for the first and the second courses
    public Lecturer lecturer2 = new Lecturer(1, "Edgar Allan", "Poe", new int[]{1, 2});
    public List<Lecturer> lecturers = new ArrayList<>(Arrays.asList(lecturer1, lecturer2));

    // Three courses

    public Course course1 = new Course(0L, "Java", 1);
    public Course course2 = new Course(1L, "Unorthodox calculus methods", 2);
    public Course course3 = new Course(2L, "Python", 3);
    public List<Course> courses = new ArrayList<>(Arrays.asList(course1, course2, course3));

    // Two lecture halls; One with 25, the other with 35 seats of seating capacity.

    public LectureHall lectureHall1 = new LectureHall(0,"E_150", 25);
    public LectureHall lectureHall2 = new LectureHall(1, "IT1", 35);
    public List<LectureHall> lectureHalls = new ArrayList<>(Arrays.asList(lectureHall1, lectureHall2));

    // Two vocations, defining how many hours of each course a class of a certain vocation needs to attend.

    public Vocation vocation1 = new Vocation(0L, "Experimental programming", new HashMap<>() {{
        put(course1, 2);
        put(course2, 1);
        put(course3, 3);
    }});

    public Vocation vocation2 = new Vocation(1L, "Functional applications", new HashMap<>() {{
        put(course1, 3);
        put(course2, 2);
        put(course3, 1);
    }});

    // Two classes (student groups); One with 25, the other with 35 students.

    public ClassGroup classGroup1 = new ClassGroup(0L, 25, "1A", vocation1);
    public ClassGroup classGroup2 = new ClassGroup(1L, 35, "2A", vocation2);
    public ArrayList<ClassGroup> classGroups = new ArrayList<>(Arrays.asList(classGroup1, classGroup2));

}
