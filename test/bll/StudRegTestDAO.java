package bll;

import be.Course;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StudRegTestDAO {

    public List<Course> getAllStudentCourses() {
        List<Course> allCourses = new ArrayList<>();
        Date time = Calendar.getInstance().getTime();
        allCourses.add(new Course(1, "DBOS2.AB.21", time, time));
        allCourses.add(new Course(2, "ITO2.AB.21", time, time));
        allCourses.add(new Course(3, "SCO2.B.21", time, time));
        allCourses.add(new Course(4, "SDE2.B.21", time, time));

        return allCourses;
    }

    public Integer getStudentAttendanceDaysInSemesterCourse() {
        return 10;
    }

    public double getCourseDaysInSemesterCourseUntilNow() {
        return 20;
    }
}
