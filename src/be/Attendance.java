package be;

import bll.StudRegManager;

import java.util.Date;

public class Attendance {
    private int studentID;
    private int courseID;
    private Date registerTime;
    private int status;
    private String name;
    private StudRegManager studRegManager = new StudRegManager();
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
}
