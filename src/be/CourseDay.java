package be;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CourseDay {
    int courseID;
    String courseName;
    int weekDay;
    Date startTime;
    Date endTime;

    public CourseDay(int courseID,String courseName,int weekDay, Date startTime, Date endTime){
        this.courseID=courseID;
        this.weekDay=weekDay;
        this.startTime=startTime;
        this.endTime=endTime;
        this.courseName=courseName;

    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("H:mm");
        return this.courseName+" - " + df.format(startTime) +"-" + df.format(endTime);
    }

    public int getCourseID() {
        return courseID;
    }
}
