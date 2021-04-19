package gui.controllers;

import be.Attendance;
import be.CourseDay;
import be.Student;
import com.jfoenix.controls.JFXButton;
import gui.models.StudentInformationModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class StudentInformationController implements Initializable {

    @FXML
    private ListView listView;
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
        datePicker.setValue(LocalDate.now()); // Set initial value
        datePicker.valueProperty().addListener((ov, oldValue, newValue) -> { // Listen for changes in the datePicker
            createCoursesView(studentInformationModel.getCourseDays(selectedStudent,datePicker.getValue())); // Update list of courses to reflect new date.
        });
    }

    public void createCoursesView(List<CourseDay> courses) {
        ObservableList<CourseDay> course=FXCollections.observableList(courses);
        listView.setItems(course);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    CourseDay cd = (CourseDay) listView.getSelectionModel().getSelectedItem();
                    studentInformationModel.registerAttendance(selectedStudent,cd.getCourseID());
                }
                //@todo update view etc.
            }
        });
    }

    public void createAttendanceView(Student student) {
        //Get courses
        List<Attendance> courses= studentInformationModel.getAttendanceList(student.getId());
        //Create view
        attendanceList.getChildren().clear();
        int viewHeight=60; //Pixels per element in the list vertically.
        for (int i = 0; i < courses.size(); i++) {
            Group group = new Group();
            group.relocate(108, -1 + viewHeight * i);

            Button button1 = new Button("REMOVE");
            button1.relocate(25, 7);
            button1.setStyle("-fx-background-color:red");
            button1.setUserData(courses.get(i));
            button1.setOnAction(e->removeAttendance(e));
            group.getChildren().add(button1);

            Label label = new Label("Course: "+courses.get(i).toString());
            label.setStyle("-fx-text-fill:white;-fx-font-size:12px;");
            label.relocate(-100, 0);
            group.getChildren().add(label);

            Line line = new Line(-119, 0, 85, 0);
            line.relocate(-100, 54);
            group.getChildren().add(line);

            attendanceList.getChildren().add(group);
        }
        attendanceList.setMinSize(184, viewHeight * courses.size()); //This makes sure scroll appears if necessary, calculating the proper height for the pane, so the scrollPane will react.
    }

    public void removeAttendance(ActionEvent e){
        studentInformationModel.removeAttendance((Attendance)((Button)e.getSource()).getUserData());
        createAttendanceView(selectedStudent);
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
