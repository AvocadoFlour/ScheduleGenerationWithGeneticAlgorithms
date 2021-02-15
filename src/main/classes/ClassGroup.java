package main.classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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

    public StringProperty identificationProperty() {
        StringProperty identificationSP = new SimpleStringProperty();
        identificationSP.set(this.classIdentification);
        return identificationSP;
    }

    public StringProperty vocationNameProperty() {
        StringProperty vocationNameSP = new SimpleStringProperty();
        vocationNameSP.set(this.vocation.getName());
        return vocationNameSP;
    }

    public IntegerProperty numberOfStudentsProperty() {
        IntegerProperty numberOfStudentsProperty = new SimpleIntegerProperty();
        numberOfStudentsProperty.set(this.numberOfStudents);
        return numberOfStudentsProperty;
    }

}
