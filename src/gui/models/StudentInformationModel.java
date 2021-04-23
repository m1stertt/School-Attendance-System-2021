package gui.models;

import be.Attendance;
import be.CourseDay;
import be.Student;
import bll.StudRegManager;
import dal.StudRegDAO;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.util.List;

public class StudentInformationModel {

    private StudRegManager studRegManager = StudRegManager.createStudRegManager();

    public XYChart.Series createWeekDaySeries(int studentId) {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Student Attendance");
        series.getData().add(new XYChart.Data<>("Monday", lessonDaysUntilNow - attendanceDays.get("MONDAY")));
        series.getData().add(new XYChart.Data<>("Tuesday", lessonDaysUntilNow - attendanceDays.get("TUESDAY")));
        series.getData().add(new XYChart.Data<>("Wednesday", lessonDaysUntilNow - attendanceDays.get("WEDNESDAY")));
        series.getData().add(new XYChart.Data<>("Thursday", lessonDaysUntilNow - attendanceDays.get("THURSDAY")));
        series.getData().add(new XYChart.Data<>("Friday", lessonDaysUntilNow - attendanceDays.get("FRIDAY")));
        return studRegManager.createWeekDaySeries(studentId);
    }

    public double getAbsenceData(int studentID) {
        return studRegManager.getAbsenceData(studentID);
    }

    public List<String> getCoursesStringForDay(Student student, LocalDate localDate) {
        return studRegManager.getCoursesStringForDay(student,localDate);
    }
    public List<CourseDay> getCourseDays(Student student, LocalDate localDate){
        return studRegManager.getCourseDays(student,localDate);
    }

    public void registerAttendance(Student student,int courseID){
        studRegManager.registerAttendance(student,courseID);
    }

    public void removeAttendance(Attendance attendance){
        studRegManager.removeAttendance(attendance);
    }

    public List<Attendance> getAttendanceList(int studentID) {
        return studRegManager.getAttendanceList(studentID);
    }


}
