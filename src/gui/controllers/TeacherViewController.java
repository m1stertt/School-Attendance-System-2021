package gui.controllers;

import be.Course;
import be.Student;
import bll.StudRegManager;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TeacherViewController implements Initializable {

    @FXML
    private PieChart attendancePieChart;

    @FXML
    private TableView<Student> studentsTableView;
    @FXML
    private TableColumn studentName;
    @FXML
    private TableColumn summarizedAttendance;
    @FXML
    private TableColumn summarizedAttendanceWeekday;
    @FXML
    private JFXComboBox<Course> courseComboCheckBox;
    @FXML
    private Label currentTime;
    @FXML
    private ImageView EASV;

    private ScreenController screenController;
    private StudRegManager studRegManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addImage();
        screenController = ScreenController.getInstance();
        studRegManager = new StudRegManager();
        initializeStudentViewDisplay();
        addStudents();
        addClasses();
        courseComboCheckBox.getSelectionModel().selectFirst();
        drawData();
        timeDisplayed();
    }

    public void drawData() {
        ObservableList<PieChart.Data> attendancePieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Present", courseComboCheckBox.getSelectionModel().getSelectedItem().getPresence()),
                new PieChart.Data("Absent", courseComboCheckBox.getSelectionModel().getSelectedItem().getAbsence())
            );
        attendancePieChart.setData(attendancePieChartData);
    }

    public void addStudents() {
        studentsTableView.setItems(studRegManager.getAllStudents());
    }

    public void addClasses() {
        courseComboCheckBox.getItems().addAll(studRegManager.getAllCourses());
    }

    public void initializeStudentViewDisplay() {
        //Set student columns.

        studentName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        summarizedAttendance.setCellValueFactory(new PropertyValueFactory<Student, Double>("absence"));
        //  summarizedAttendanceWeekday.setCellValueFactory(new PropertyValueFactory<>(""));
        studentName.setMaxWidth(75);
        studentName.setMaxWidth(75);
        summarizedAttendance.setMaxWidth(75);
        summarizedAttendance.setMaxWidth(75);
        summarizedAttendanceWeekday.setMaxWidth(155);
        summarizedAttendanceWeekday.setMaxWidth(170);
    }

    public void timeDisplayed(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        currentTime.setText("Current Time: "+String.valueOf(dtf.format(now)));
    }

    public void addImage() {
        Image logo = new Image("gui/images/EASV_v2.png");
        EASV.setImage(logo);
    }

    @FXML
    void closesTeacher() {
        screenController.setLoginInView();
    }

    public void onComboboxSelect(ActionEvent actionEvent) {
        drawData();
    }
}
