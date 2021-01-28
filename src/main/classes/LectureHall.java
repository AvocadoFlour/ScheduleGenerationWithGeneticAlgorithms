package main.classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LectureHall {

    private String hallCode;
    private int capacity;
    private int id;

    public LectureHall(int id, String hallCode, int capacity) {
        this.id = id;
        this.hallCode = hallCode;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHallCode() {
        return hallCode;
    }

    public void setHallCode(String hallCode) {
        this.hallCode = hallCode;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public StringProperty codeProperty() {
        StringProperty hallCodeSP = new SimpleStringProperty();
        hallCodeSP.set(this.hallCode);
        return hallCodeSP;
    }

    public IntegerProperty capacityProperty() {
        IntegerProperty hallCapacitySP = new SimpleIntegerProperty();
        hallCapacitySP.set(this.capacity);
        return hallCapacitySP;
    }

}
