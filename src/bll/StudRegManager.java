package bll;

import be.Course;
import be.Student;
import dal.StudRegDAO;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Simple BLL pass-through layer.
 */
public class StudRegManager {

    private StudRegDAO studRegDAO = new StudRegDAO();


    public ObservableList<Student> getAllStudents(){
        return studRegDAO.getAllStudents();
    }

    public List<Course> getAllCourses(){
        return studRegDAO.getAllCourses();
    }

    public List<String> getCoursesStringForDay(LocalDate localDate){
        Integer day=localDate.getDayOfWeek().getValue();
        return studRegDAO.getCoursesStringForDay(day);
    }

    public XYChart.Series getSummarizedStudentWeekDayData(){
        return studRegDAO.getSummarizedStudentWeekDayData();
    }

    public HashMap<String, ArrayList<LocalTime>> getCourseTime(LocalDate localDate) {
        Integer day=localDate.getDayOfWeek().getValue();
        return studRegDAO.getCourseTime(day);
    }

    public int getCourseDaysInPeriod(String s) {
        return studRegDAO.getCourseDaysInSemesterCourse(s);
    }
}
