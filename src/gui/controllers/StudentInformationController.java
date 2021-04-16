package gui.controllers;

import be.Attendance;
import be.Student;
import com.jfoenix.controls.JFXButton;
import gui.models.StudentInformationModel;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class StudentInformationController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane attendanceList;
    @FXML
    private LineChart absenceDisplay;
    @FXML
    private JFXButton closesWindow;
    @FXML
    private Label studentNameDisplay;
    @FXML
    private PieChart attendancePieChart;
    @FXML
    private DatePicker datePicker;

    private Student selectedStudent;
    private StudentInformationModel studentInformationModel = new StudentInformationModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleDatePicker() {
        datePicker.setValue(LocalDate.now()); //Set initial value
        datePicker.valueProperty().addListener((ov, oldValue, newValue) -> { //Listen for changes in the datePicker
            createCoursesView(studentInformationModel.getCoursesStringForDay(selectedStudent,datePicker.getValue())); //Update list of courses to reflect new date.
        });
    }

    public void createCoursesView(List<String> courses) {
        anchorPane.getChildren().clear();
        for (int i = 0; i < courses.size(); i++) {
            //Create buttons
            Group group = new Group();
            group.relocate(108, -1 + 40 * i);
            //The 40*i is the important part^ to make the list work, it defines how far down on the list this grouping will be and places it accordingly.

            Button button1 = new Button("PRESENT");
            button1.relocate(25, 7);
            button1.setStyle("-fx-background-color:green");
            group.getChildren().add(button1);

            Label label = new Label(courses.get(i));
            label.setStyle("-fx-text-fill:white;-fx-font-size:12px;");
            label.relocate(-100, 10);
            group.getChildren().add(label);

            Line line = new Line(-119, 0, 185, 0); //@todo positioning needs to be better. off atm.
            line.relocate(-100, 39);
            group.getChildren().add(line);

            //setButtonEvents(button1, label); //Set events for "blurring" buttons on click and clearing the opposite button of any blur.
            anchorPane.getChildren().add(group);
        }
        anchorPane.setMinSize(320, 40 * courses.size()); //This makes sure scroll appears if necessary, calculating the proper height for the pane, so the scrollPane will react.
    }

    public void createAttendanceView(Student student) {
        //LoginSession.getUserName();
        //Get courses
        List<Attendance> courses= studentInformationModel.getAttendanceList(student.getId());
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


    public void attendanceEdit(Student student) {
        this.selectedStudent = student;
        studentNameDisplay.setText(selectedStudent.getFirstName() + " " + selectedStudent.getLastName());
        drawPieChartData();
        drawStudentAttendenceChart();
        createAttendanceView(student);
        handleDatePicker();
    }

    public void drawStudentAttendenceChart() {
        Axis<String> xAxis = absenceDisplay.getXAxis();
        xAxis.setTickLabelRotation(10);
        Axis<String> yAxis = absenceDisplay.getYAxis();
        yAxis.setLabel("Absence shown as lessons");
        absenceDisplay.getData().add(studentInformationModel.createWeekDaySeries(selectedStudent.getId()));
    }

    public void drawPieChartData() {
        double d= studentInformationModel.getAbsenceData(selectedStudent.getId());
        ObservableList<PieChart.Data> attendancePieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Present", d),
                new PieChart.Data("Absent", 1-d)
        );
        attendancePieChart.setData(attendancePieChartData);
    }


}
