package main.classes;

public class Lecture {

    private Long id;
    private Lecturer lecturer;
    private Course course;
    private ClassGroup classGroup;
    private LectureHall lectureHall;
    private int fromH;
    private int toH;

    public Lecture(Long id, Lecturer lecturer, Course course, LectureHall lectureHall, ClassGroup classGroup) {
        this.id = id;
        this.lecturer = lecturer;
        this.course = course;
        this.lectureHall = lectureHall;
        this.classGroup = classGroup;
    }

    public Lecture(Long id, Lecturer lecturer, Course course, ClassGroup classGroup, LectureHall lectureHall, int fromH, int toH) {
        this.id = id;
        this.lecturer = lecturer;
        this.course = course;
        this.lectureHall = lectureHall;
        this.classGroup = classGroup;
        this.fromH = fromH;
        this.toH = toH;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LectureHall getLectureHall() {
        return lectureHall;
    }

    public void setLectureHall(LectureHall lectureHall) {
        this.lectureHall = lectureHall;
    }

    public ClassGroup getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
    }

    public int getFromH() {
        return fromH;
    }

    public void setFromH(int fromH) {
        this.fromH = fromH;
    }

    public int getToH() {
        return toH;
    }

    public void setToH(int toH) {
        this.toH = toH;
    }
}
