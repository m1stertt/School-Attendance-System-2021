package bll;

import be.Course;
import be.Student;
import dal.StudRegDAO;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Simple BLL pass-through layer.
 */
public class StudRegManager {

    private StudRegDAO studRegDAO = new StudRegDAO();


    public ObservableList<Student> getAllStudents(String courseName) {
            double allLessonsInCourse = studRegDAO.getCourseDaysInSemesterCourse(courseName);
            ObservableList<Student> allStudents = studRegDAO.getAllStudents();
            allStudents.forEach(student -> {
                double studentAbsence = studRegDAO.getStudentAttendanceDaysInSemesterCourse(courseName);
                student.setAbsence(studentAbsence / allLessonsInCourse);
            });
        return allStudents;
        }



    public List<Course> getAllCourses() {
        return studRegDAO.getAllCourses();
    }

    public List<String> getCoursesStringForDay(LocalDate localDate) {
        Integer day = localDate.getDayOfWeek().getValue();
        return studRegDAO.getCoursesStringForDay(day);
    }

    public XYChart.Series getSummarizedStudentWeekDayData() {
        return studRegDAO.getSummarizedStudentWeekDayData();
    }

    public HashMap<String, ArrayList<LocalTime>> getCourseTime(LocalDate localDate) {
        Integer day = localDate.getDayOfWeek().getValue();
        return studRegDAO.getCourseTime(day);
    }

    public int getCourseDaysInPeriod(String s) {
        return studRegDAO.getCourseDaysInSemesterCourse(s);
    }

    public boolean checkLogin(String userName, String password, String role) {
        return studRegDAO.checkLogin(userName, password, role);
    }

    public void attendanceRegister(Course course) {
        studRegDAO.registerAttendance(course.getCourseName());
    }
}
