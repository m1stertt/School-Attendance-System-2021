package bll;

import be.Course;
import be.Student;
import dal.StudRegDAO;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

}
