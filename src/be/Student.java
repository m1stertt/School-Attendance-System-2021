package be;

public class Student {

    private String firstName;
    private String lastName;
    private double absence;

    public double getAbsence() {
        return absence;
    }

    public void setAbsence(double absence) {
        this.absence = absence;
    }




    public Student(String firstName, double absence) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.absence = absence;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }





}
