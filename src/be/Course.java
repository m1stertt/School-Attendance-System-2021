package be;

public class Course {
    private String courseName;
    private double presence;
    private double absence;

    public double getPresence() {
        return presence;
    }

    public void setPresence(double presence) {
        this.presence = presence;
    }

    public double getAbsence() {
        return absence;
    }

    public void setAbsence(double absence) {
        this.absence = absence;
    }


    public String getClassName() {
        return courseName;
    }

    public void setClassName(String courseName) {
        this.courseName = courseName;
    }

    public Course(String courseName, double presence, double absence) {
        this.courseName = courseName;
        this.presence = presence;
        this.absence = absence;
    }

    @Override
    public String toString() {
        return courseName;
    }
}
