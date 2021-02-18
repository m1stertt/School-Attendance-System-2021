package be;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {

    private StringProperty firstName;
    private StringProperty lastName;
    private DoubleProperty absence;

    public Student(String firstName, String lastName, double absence) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.absence = new SimpleDoubleProperty(absence);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public double getAbsence() {
        return absence.get();
    }

    public void setAbsence(double absence) {
        this.absence.set(absence);
    }

}
