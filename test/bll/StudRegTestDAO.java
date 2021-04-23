package bll;

import be.Attendance;
import be.Course;
import be.CourseDay;
import be.Student;
import dal.IStudRegDAO;
import dal.StudRegDAO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class StudRegTestDAO implements IStudRegDAO {

    @Override
    public List<Student> getAllStudents(int courseId) {
        List<Student> allStudents = new ArrayList<>();
        allStudents.add(new Student(1, "Thomas", "Truelsen", 10));
        allStudents.add(new Student(2, "Peter", "Hansen", 10));
        allStudents.add(new Student(3, "Lars", "Jakobsen", 10));
        return allStudents;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> allCourses = new ArrayList<>();
        Date time = Calendar.getInstance().getTime();
            allCourses.add(new Course(1, "DBOS2.AB.21", time, time));
            allCourses.add(new Course(2, "ITO2.AB.21", time, time));
            allCourses.add(new Course(3, "SCO2.B.21", time, time));
            allCourses.add(new Course(4, "SDE2.B.21", time, time));


        return allCourses;
    }

    public List<Course> getAllStudentCourses(int studentID) {
        List<Course> allCourses = new ArrayList<>();
        Date time = Calendar.getInstance().getTime();
        if(studentID == 2) {
            allCourses.add(new Course(1, "DBOS2.AB.21", time, time));
            allCourses.add(new Course(2, "ITO2.AB.21", time, time));
            allCourses.add(new Course(3, "SCO2.B.21", time, time));
            allCourses.add(new Course(4, "SDE2.B.21", time, time));
        }

        return allCourses;
    }

    @Override
    public void registerAttendance(int courseId) {

    }

    @Override
    public void registerAttendance(Student student, int courseId) {

    }

    @Override
    public void removeAttendance(Attendance attendance) {

    }

    @Override
    public String getCourseName(int courseID) {
        if(courseID == 1) return "DBOS2.AB.21";
        if(courseID == 2) return "ITO2.AB.21";
        if(courseID == 3) return "SCO2.B.21";
        if(courseID == 4) return "SDE2.B.21";
        return null;
    }

    @Override
    public List<Timestamp> getALlStudentAttendanceDates(int studentId) {
        List<Timestamp> allAttendanceDays = new ArrayList();
        allAttendanceDays.add(new Timestamp(System.currentTimeMillis()));
        allAttendanceDays.add(new Timestamp(System.currentTimeMillis()));
        allAttendanceDays.add(new Timestamp(System.currentTimeMillis()));
        allAttendanceDays.add(new Timestamp(System.currentTimeMillis()));
        return allAttendanceDays;
    }

    @Override
    public Integer getStudentAttendanceDaysInSemesterCourse(int courseId, int studentId) {
        return 10;
    }

    @Override
    public List<Attendance> getStudentAttendanceDays(int studentId) {
        return null;
    }

    @Override
    public List<String> getCoursesStringForDay(Integer day) {
        List<String> allCoursesForDay = new ArrayList<>();
        allCoursesForDay.add("DBOS2.AB.21");
        allCoursesForDay.add("ITO2.AB.21");
        allCoursesForDay.add("SCO2.B.21");
        allCoursesForDay.add("SDE2.B.21");
        return allCoursesForDay;
    }

    @Override
    public List<String> getCoursesStringForDay(Student student, Integer day) {
        List<String> allCoursesForDay = new ArrayList<>();
        allCoursesForDay.add("DBOS2.AB.21");
        allCoursesForDay.add("ITO2.AB.21");
        allCoursesForDay.add("SCO2.B.21");
        allCoursesForDay.add("SDE2.B.21");
        return allCoursesForDay;
    }

    @Override
    public List<CourseDay> getCourseDays(Student student, Integer day) {
        return null;
    }

    @Override
    public HashMap<String, ArrayList<LocalTime>> getCourseTime(Integer day) {
        return null;
    }

    @Override
    public Integer getCourseDaysInSemesterCourseUntilNow(int courseId) {
        return 20;
    }

    @Override
    public boolean checkLogin(String username, String password, String Role) {
        return false;
    }


}
