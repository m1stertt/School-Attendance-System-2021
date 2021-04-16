package gui.controllers;

import be.Attendance;
import be.Course;
import be.Student;
import bll.LoginSession;
import bll.StudRegManager;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentInformationController implements Initializable {

    @FXML
    private AnchorPane attendanceList;
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
    @FXML
    private PieChart attendancePieChart;

    private Student selectedStudent;

    private StudRegManager studRegManager = new StudRegManager();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void createAttendanceView(Student student) {
        //LoginSession.getUserName();
        //Get courses
        List<Attendance> courses=studRegManager.getAttendanceList(student.getId());
        //Create view
        attendanceList.getChildren().clear();
        for (int i = 0; i < courses.size(); i++) {
            //Create buttons
            Group group = new Group();
            group.relocate(108, -1 + 50 * i);
            //The 50*i is the important part^ to make the list work, it defines how far down on the list this grouping will be and places it accordingly.

            Button button1 = new Button("REMOVE");
            button1.relocate(25, 7);
            button1.setStyle("-fx-background-color:red");
            group.getChildren().add(button1);

            Label label = new Label("Course: "+courses.get(i).getName()+"\nDate: "+courses.get(i).getTime().toString());
            label.setStyle("-fx-text-fill:white;-fx-font-size:12px;");
            label.relocate(-100, 0);
            group.getChildren().add(label);

            Line line = new Line(-119, 0, 85, 0);
            line.relocate(-100, 39);
            group.getChildren().add(line);

            attendanceList.getChildren().add(group);
        }
        attendanceList.setMinSize(184, 50 * courses.size()); //This makes sure scroll appears if necessary, calculating the proper height for the pane, so the scrollPane will react.
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
        drawPieChartData();
        drawStudentAttendenceChart();
        createAttendanceView(student);
    }

    public void drawStudentAttendenceChart() {
        Axis<String> xAxis = absenceDisplay.getXAxis();
        xAxis.setTickLabelRotation(10);
        Axis<String> yAxis = absenceDisplay.getYAxis();
        yAxis.setLabel("Absence shown as lessons");
        absenceDisplay.getData().add(studRegManager.createWeekDaySeries(selectedStudent.getId()));
    }

    public void drawPieChartData() {
        double d=studRegManager.getAbsenceData(selectedStudent.getId());
        ObservableList<PieChart.Data> attendancePieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Present", d),
                new PieChart.Data("Absent", 1-d)
        );
        attendancePieChart.setData(attendancePieChartData);
    }


}
