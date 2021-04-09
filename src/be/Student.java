package be;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Student extends User {

    private DoubleProperty absence;

    public Student(int id,String firstName, String lastName, double absence) {
        super(id,firstName,lastName);
        this.absence = new SimpleDoubleProperty(absence);
    }


    public double getAbsence() {
        return absence.get();
    }

    public void setAbsence(double absence) {
        this.absence.set(absence);
    }

}
