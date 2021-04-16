package gui.models;

import be.Attendance;
import bll.StudRegManager;
import javafx.scene.chart.XYChart;

import java.util.List;

public class StudentInformationModel {

    private StudRegManager studRegManager = new StudRegManager();

    public XYChart.Series createWeekDaySeries(int studentId) {
        return studRegManager.createWeekDaySeries(studentId);
    }

    public double getAbsenceData(int studentID) {
        return studRegManager.getAbsenceData(studentID);
    }


    public List<Attendance> getAttendanceList(int studentID) {
        return studRegManager.getAttendanceList(studentID);
    }


}
