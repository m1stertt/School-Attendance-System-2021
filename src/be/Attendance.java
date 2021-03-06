package be;

import bll.StudRegManager;
import dal.StudRegDAO;

import java.util.Date;

public class Attendance {
    private int studentID;
    private int courseID;
    private Date registerTime;
    private int status;
    private String name;
    private StudRegManager studRegManager = new StudRegManager(new StudRegDAO());
    public Attendance(int studentID,int courseID, Date registerTime,int status){
        this.studentID=studentID;
        this.courseID=courseID;
        this.registerTime=registerTime;
        this.status=status;
        this.name=studRegManager.getCourseName(courseID);
    }

    public String getName() {
        return name;
    }

    public Date getTime(){
        return registerTime;
    }

    public int getStudentID() {
        return studentID;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public int getCourseID() {
        return courseID;
    }

    @Override
    public String toString() {
        return getName()+"\nDate & Time:\n"+getTime().toString();
    }
}
