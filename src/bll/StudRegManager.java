package bll;

import be.Course;
import be.Student;
import dal.StudRegDAO;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

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

    public XYChart.Series getSummarizedStudentWeekDayData(){
        return studRegDAO.getSummarizedStudentWeekDayData();
    }

}
