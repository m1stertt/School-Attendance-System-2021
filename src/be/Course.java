package be;

import java.util.Date;

public class Course {
    private int id;
    private String courseName;
    private Date startDate;
    private Date endDate;

    public int getId() {
        return id;
    }


    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }


    public String getCourseName() {
        return courseName;
    }

    public Course(int id, String courseName, Date startDate, Date endDate) {
        this.id = id;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return courseName;
    }
}
