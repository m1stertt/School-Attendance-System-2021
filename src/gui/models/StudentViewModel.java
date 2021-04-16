package gui.models;

import be.Course;
import bll.StudRegManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentViewModel {

    private StudRegManager studRegManager = new StudRegManager();


    public List<String> getCoursesStringForDay(LocalDate localDate) {
        return studRegManager.getCoursesStringForDay(localDate);
    }


    public HashMap<String, ArrayList<LocalTime>> getCourseTime(LocalDate localDate) {
        return studRegManager.getCourseTime(localDate);
    }

    public List<Course> getAllCourses() {
        return studRegManager.getAllCourses();
    }

    public double getAbsenceData(int studentID) {
        return studRegManager.getAbsenceData(studentID);
    }

    public void attendanceRegister(Course course) {
        studRegManager.attendanceRegister(course);
    }


}
