package gui.controllers;

import be.Student;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
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

    public RadioButton presentSelect;
    public RadioButton absentSelect;
    public ToggleGroup attendance;
    public LineChart absenceDisplay;
    public JFXButton closesWindow;
    public Label studentNameDisplay;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawStudentAttendenceChart();
    }

    public void savesAndCloses(ActionEvent actionEvent) {

        //todo add code to save and close the window

        Stage window = (Stage) closesWindow.getScene().getWindow();
        window.close();

    }

    public void setsAttendance(ActionEvent actionEvent) {
    }

    public void attendanceEdit(Student student){

        //todo add stuff that allows editing of attendance

    }

    public void drawStudentAttendenceChart(){
        Axis<String> xAxis = absenceDisplay.getXAxis();
        xAxis.setTickLabelRotation(1);
        xAxis.setLabel("Days of the Week");
        Axis<String> yAxis = absenceDisplay.getYAxis();
        yAxis.setLabel("Percentage Absence");

        //todo add information from database on students abscene
    }


}
