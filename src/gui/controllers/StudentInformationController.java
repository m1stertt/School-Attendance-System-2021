package gui.controllers;

import be.Student;
import bll.StudRegManager;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentInformationController implements Initializable {

    @FXML
    private RadioButton presentSelect;
    @FXML
    private RadioButton absentSelect;
    @FXML
    private ToggleGroup attendance;
    @FXML
    private LineChart absenceDisplay;
    @FXML
    private JFXButton closesWindow;
    @FXML
    private Label studentNameDisplay;

    private Student selectedStudent;

    private StudRegManager studRegManager = new StudRegManager();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawStudentAttendenceChart();

    }


    public void savesAndCloses(ActionEvent actionEvent) {
        Stage window = (Stage) closesWindow.getScene().getWindow();
        window.close();

    }

    public void setsAttendance(ActionEvent actionEvent) {
    }

    public void attendanceEdit(Student student) {
        this.selectedStudent = student;
        studentNameDisplay.setText(selectedStudent.getFirstName() + " " + selectedStudent.getLastName());
    }

    public void drawStudentAttendenceChart() {
        Axis<String> xAxis = absenceDisplay.getXAxis();
        xAxis.setTickLabelRotation(10);
        Axis<String> yAxis = absenceDisplay.getYAxis();
        yAxis.setLabel("Absence shown as lessons");

        absenceDisplay.getData().add(studRegManager.getSummarizedStudentWeekDayData());

        //todo add information from database on students abscene
    }


}
