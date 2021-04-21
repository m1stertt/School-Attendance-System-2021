package bll;

import be.Attendance;
import be.Course;
import be.CourseDay;
import be.Student;
import dal.StudRegDAO;
import javafx.scene.chart.XYChart;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudRegManager {

    private StudRegDAO studRegDAO = new StudRegDAO();

    public List<Student> getAllStudentsCalculatedAbsence(int id) {
        int courseDaysInSemester = studRegDAO.getCourseDaysInSemesterCourseUntilNow(id);
        List<Student> allStudents = studRegDAO.getAllStudents(id);
        allStudents.forEach(student -> student.setAbsence(student.getAbsence() / courseDaysInSemester * 100));
        return allStudents;
    }

    public List<Course> getAllCourses() {
        return studRegDAO.getAllCourses();
    }

    public List<String> getCoursesStringForDay(LocalDate localDate) {
        Integer day = localDate.getDayOfWeek().getValue();
        return studRegDAO.getCoursesStringForDay(day);
    }
    public List<String> getCoursesStringForDay(Student student,LocalDate localDate) {
        Integer day = localDate.getDayOfWeek().getValue();
        return studRegDAO.getCoursesStringForDay(student,day);
    }

    public List<CourseDay> getCourseDays(Student student, LocalDate localDate){
        Integer day = localDate.getDayOfWeek().getValue();
        return studRegDAO.getCourseDays(student,day);
    }

    public HashMap<String, ArrayList<LocalTime>> getCourseTime(LocalDate localDate) {
        Integer day = localDate.getDayOfWeek().getValue();
        return studRegDAO.getCourseTime(day);
    }

    public void registerAttendance(Student student,int courseID){
        studRegDAO.registerAttendance(student,courseID);
    }

    public void removeAttendance(Attendance attendance){
        studRegDAO.removeAttendance(attendance);
    }

    public List<Attendance> getAttendanceList(int studentID) {
        return studRegDAO.getStudentAttendanceDays(studentID);
    }

    public boolean checkLogin(String userName, String password, String role) {
        return studRegDAO.checkLogin(userName, password, role);
    }

    public void attendanceRegister(Course course) {
        studRegDAO.registerAttendance(course.getId());
    }

    public String getCourseName(int courseID) {
        return studRegDAO.getCourseName(courseID);
    }

    public HashMap<String, Double> getCourseAbsenceDate(int courseId) {
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

    public HashMap<String, Integer> getWeekdayAttendanceData(int studentId) {
        HashMap<String, Integer> attendanceDays = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            attendanceDays.put(DayOfWeek.of(i).toString(), 0);
        }
        studRegDAO.getALlStudentAttendanceDates(studentId).forEach(timestamp -> {
            LocalDateTime attendanceDay = timestamp.toLocalDateTime();
            switch (attendanceDay.getDayOfWeek()) {
                case MONDAY -> {
                    Integer count = attendanceDays.get("MONDAY");
                    attendanceDays.put("MONDAY", count + 1);
                }
                case TUESDAY -> {
                    Integer count = attendanceDays.get("TUESDAY");
                    attendanceDays.put("TUESDAY", count + 1);
                }
                case WEDNESDAY -> {
                    Integer count = attendanceDays.get("WEDNESDAY");
                    attendanceDays.put("WEDNESDAY", count + 1);
                }
                case THURSDAY -> {
                    Integer count = attendanceDays.get("THURSDAY");
                    attendanceDays.put("THURSDAY", count + 1);
                }
                case FRIDAY -> {
                    Integer count = attendanceDays.get("FRIDAY");
                    attendanceDays.put("FRIDAY", count + 1);
                }
            }
        });
        return attendanceDays;
    }

    public XYChart.Series createWeekDaySeries(int studentId) {
        HashMap<String, Integer> attendanceDays = getWeekdayAttendanceData(studentId);
        int lessonDaysUntilNow = 0;
        List<Course> allCourses = studRegDAO.getAllCourses();
        for (Course c : allCourses) {
            lessonDaysUntilNow += studRegDAO.getCourseDaysInSemesterCourseUntilNow(c.getId());
        }
        lessonDaysUntilNow = lessonDaysUntilNow / 5;
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Student Attendance");
        series.getData().add(new XYChart.Data<>("Monday", lessonDaysUntilNow - attendanceDays.get("MONDAY")));
        series.getData().add(new XYChart.Data<>("Tuesday", lessonDaysUntilNow - attendanceDays.get("TUESDAY")));
        series.getData().add(new XYChart.Data<>("Wednesday", lessonDaysUntilNow - attendanceDays.get("WEDNESDAY")));
        series.getData().add(new XYChart.Data<>("Thursday", lessonDaysUntilNow - attendanceDays.get("THURSDAY")));
        series.getData().add(new XYChart.Data<>("Friday", lessonDaysUntilNow - attendanceDays.get("FRIDAY")));
        return series;
    }

    public double getAbsenceData(int studentID) { //Returns a double representing the percentage of how present the student is. 0.24=24% attendance etc.
        List<Course> courses = studRegDAO.getAllStudentCourses(studentID);
        double sum = 0;
        for (Course course : courses) {
            sum += (double) studRegDAO.getStudentAttendanceDaysInSemesterCourse(course.getId(), studentID) / studRegDAO.getCourseDaysInSemesterCourseUntilNow(course.getId());
        }
        double d = sum / courses.size();
        return d;
    }

}
