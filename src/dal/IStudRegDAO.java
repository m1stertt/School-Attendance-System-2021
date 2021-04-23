package dal;

import be.Attendance;
import be.Course;
import be.CourseDay;
import be.Student;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IStudRegDAO {
    List<Student> getAllStudents(int courseId);

    List<Course> getAllCourses();

    List<Course> getAllStudentCourses(int studentID);

    void registerAttendance(int courseId);

    void registerAttendance(Student student, int courseId);

    void removeAttendance(Attendance attendance);

    String getCourseName(int courseID);

    List<Timestamp> getALlStudentAttendanceDates(int studentId);

    Integer getStudentAttendanceDaysInSemesterCourse(int courseId, int studentId);

    List<Attendance> getStudentAttendanceDays(int studentId);

    List<String> getCoursesStringForDay(Integer day);

    List<String> getCoursesStringForDay(Student student, Integer day);

    List<CourseDay> getCourseDays(Student student, Integer day);

    HashMap<String, ArrayList<LocalTime>> getCourseTime(Integer day);

    Integer getCourseDaysInSemesterCourseUntilNow(int courseId);

    boolean checkLogin(String username, String password, String Role);
}
