package main.classes;

public class ClassGroup {

    private long id;
    private int numberOfStudents;
    private String classIdentification;
    private Vocation vocation;

    public ClassGroup(long id, int numberOfStudents, String classIdentification, Vocation vocation) {
        this.id = id;
        this.numberOfStudents = numberOfStudents;
        this.classIdentification = classIdentification;
        this.vocation = vocation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public String getClassIdentification() {
        return classIdentification;
    }

    public void setClassIdentification(String classIdentification) {
        this.classIdentification = classIdentification;
    }

    public Vocation getVocation() {
        return vocation;
    }

    public void setVocation(Vocation vocation) {
        this.vocation = vocation;
    }
}
