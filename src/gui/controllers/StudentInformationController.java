package gui.controllers;

import be.Student;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class StudentInformationController {

    protected boolean editMode = false;
    public RadioButton presentSelect;
    public RadioButton absentSelect;
    public ToggleGroup attendance;
    public LineChart absenceDisplay;
    public JFXButton closesWindow;

    public void savesAndCloses(ActionEvent actionEvent) {
    }

    public void setsAttendance(ActionEvent actionEvent) {
    }

    public void attendanceEdit(Student student){
        editMode = true;

        //todo add stuff that allows editing of attendance

    }
}
