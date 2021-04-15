package bll;

import be.Attendance;
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


public class StudRegManager {

    private StudRegDAO studRegDAO = new StudRegDAO();

    public List<Student> getAllStudents(int id) {
        return studRegDAO.getAllStudents(id);
    }

    public List<Student> getAllStudentsCalculatedAbsence(int id) {
        int courseDaysInSemester = studRegDAO.getCourseDaysInSemesterCourseUntilNow(id);
        List<Student> allStudents = studRegDAO.getAllStudents(id);
        allStudents.forEach(student -> student.setAbsence(student.getAbsence()/courseDaysInSemester*100));
        return allStudents;
    }

    public List<Course> getAllCourses() {
        return studRegDAO.getAllCourses();
    }
    public List<Course> getAllCoursesForStudent(Student student) {
        return studRegDAO.getAllStudentCourses(student.getId());
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

    public HashMap<String, Double> getCourseAbsenceDate(int courseId){
        HashMap<String, Double> courseAbsenceData = new HashMap<>();
        List<Student> allStudents = studRegDAO.getAllStudents(courseId);
        int studentCounter = 0;
        double totalStudentAttendanceDays = 0;
        for (Student s : allStudents) {
            studentCounter++;
            totalStudentAttendanceDays += s.getAbsence();
        }
        double classAverageStudentAttendance = totalStudentAttendanceDays / studentCounter;
        double allLessonsInCourse = studRegDAO.getCourseDaysInSemesterCourseUntilNow(courseId);
        courseAbsenceData.put("classAverageStudentAttendance", classAverageStudentAttendance);
        courseAbsenceData.put("allLessonsInCourse", allLessonsInCourse);
        return courseAbsenceData;
    }

    public int getCourseDaysInSemesterCourseUntilNow(int id) {
        return studRegDAO.getCourseDaysInSemesterCourseUntilNow(id);
    }
    //Returns a double representing the percentage of how present the student is. 0.24=24% attendance etc.
    public double getAbsenceData(int studentID){
        List<Course> courses=studRegDAO.getAllStudentCourses(studentID);
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

    public boolean checkLogin(String userName, String password, String role) {
        return studRegDAO.checkLogin(userName, password, role);
    }

    public void attendanceRegister(Course course) {
        studRegDAO.registerAttendance(course.getId());
    }

    public String getCourseName(int courseID){
        return studRegDAO.getCourseName(courseID);
    }
}
