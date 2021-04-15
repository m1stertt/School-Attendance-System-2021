package bll;

import be.Attendance;
import be.Course;
import be.Student;
import dal.StudRegDAO;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Simple BLL pass-through layer.
 */
public class StudRegManager {

    private StudRegDAO studRegDAO = new StudRegDAO();


    public ObservableList<Student> getAllStudents(int id) {
        return studRegDAO.getAllStudents(id);
    }



    public List<Course> getAllCourses() {
        return studRegDAO.getAllCourses();
    }
    public List<Course> getAllCoursesForStudent(Student student) {
        return studRegDAO.getAllCourses(student.getId());
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

    //Returns a double representing the percentage of how present the student is. 0.24=24% attendance etc.
    public double getAbsenceData(int studentID){
        List<Course> courses=studRegDAO.getAllCourses(studentID);
        double sum=0;
        for(Course course:courses){
            sum+=(double)studRegDAO.getStudentAttendanceDaysInSemesterCourse(course.getId(),studentID)/studRegDAO.getCourseDaysInSemesterCourseUntilNow(course.getId());
        }
        double d=sum/courses.size();
        return d;
    }

    public List<Attendance> getAttendanceList(int studentID){
        return studRegDAO.getStudentAttendanceDays(studentID);
    }

    public int getCourseDaysInPeriod(int id) {
        return studRegDAO.getCourseDaysInSemesterCourse(id);
    }

    public boolean checkLogin(String userName, String password, String role) {
        return studRegDAO.checkLogin(userName, password, role);
    }

    public void attendanceRegister(Course course) {
        studRegDAO.registerAttendance(course.getCourseName());
    }

    public String getCourseName(int courseID){
        return studRegDAO.getCourseName(courseID);
    }
}
