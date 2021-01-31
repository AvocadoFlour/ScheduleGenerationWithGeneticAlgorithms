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

    /**
     * Returns a single integer comprised of all the teacher's qualifications.
     * E.G. if the teacher's qualifications array holds the values [1,2],
     * then the return will return the integer "12".
     * @return
     */
    private Integer qualificationsSimpleInteger() {
        StringBuilder qs = new StringBuilder();
        for (Integer i : this.qualifications) {
            qs.append(i);
        }
        return Integer.parseInt(qs.toString());
    }

    /**
     * Returns a single string comprised of all the teacher's qualifications.
     * E.G. if the teacher's qualifications array holds the values [1,2],
     * then the return will return the string "12".
     * @return returns the teacher qualifications as a single combined string
     */
    private String qualificationsSeparatedString() {
        StringBuilder qs = new StringBuilder();
        int counter = 0;
        for (Integer i : this.qualifications) {
            if (!(counter==0)) {
                qs.append(" | ");
            }
            counter+=1;
            qs.append(i);
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
        int[] ia = new int[s.length()];
        for (int j = 0; j < s.length(); j++) {
            ia[j] = Integer.parseInt(String.valueOf(s.charAt(j)));
        }
        return ia;
    }

}
