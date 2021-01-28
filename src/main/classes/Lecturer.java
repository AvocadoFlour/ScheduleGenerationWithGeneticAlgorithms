package main.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.stream.IntStream;

public class Lecturer {

    private int id;
    private String name;
    private String lastName;
    private int[] qualifications;

    public Lecturer(int id, String name, String lastName, int[] qualifications) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.qualifications = qualifications;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int[] getQualifications() {
        return qualifications;
    }

    public void setQualifications(int[] qualifications) {
        this.qualifications = qualifications;
    }

    public StringProperty nameProperty() {
        StringProperty nameSP = new SimpleStringProperty();
        nameSP.set(this.name);
        return nameSP;
    }

    public StringProperty lastNameProperty() {
        StringProperty lastNameSP = new SimpleStringProperty();
        lastNameSP.set(this.lastName);
        return lastNameSP;
    }

    private Integer qualificationsSimpleInteger() {
        StringBuilder qs = new StringBuilder();
        for (Integer i : this.qualifications) {
            qs.append(i);
        }
        return Integer.parseInt(qs.toString());
    }

    private String qualificationsSeparatedString() {
        StringBuilder qs = new StringBuilder();
        for (Integer i : this.qualifications) {
            qs.append(i + " | ");
        }
        return qs.toString();
    }

    public StringProperty qualificationsProperty() {
        StringProperty qualificationsSP = new SimpleStringProperty();
        qualificationsSP.set(this.qualificationsSeparatedString());
        return qualificationsSP;
    }

    public static int[] convertToIntegerArray(Integer i) {
        String s = i.toString();
        IntStream is = s.chars();
        int[] ia = is.toArray();
        return ia;
    }

}
