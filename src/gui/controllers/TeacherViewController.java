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
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherViewController implements Initializable {

    @FXML
    private PieChart attendancePieChart;
    @FXML
    private AreaChart studentAttendanceChart;
    @FXML
    private TableView<Student> studentsTableView;
    @FXML
    private TableColumn studentFirstName;
    @FXML
    private TableColumn studentLastName;
    @FXML
    private TableColumn summarizedAttendance;
    @FXML
    private JFXComboBox<Course> courseComboCheckBox;
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
        initializeCourses();
        courseComboCheckBox.getSelectionModel().selectFirst();
        drawPieChartData();
        drawAreaChartData();
    }

    public void drawPieChartData() {
        ObservableList<PieChart.Data> attendancePieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Present", courseComboCheckBox.getSelectionModel().getSelectedItem().getPresence()),
                new PieChart.Data("Absent", courseComboCheckBox.getSelectionModel().getSelectedItem().getAbsence())
        );
        attendancePieChart.setData(attendancePieChartData);
    }

    public void drawAreaChartData() {
        Axis<String> xAxis = studentAttendanceChart.getXAxis();
        xAxis.setTickLabelRotation(10);
        Axis<String> yAxis = studentAttendanceChart.getYAxis();
        yAxis.setLabel("Absence shown as lessons");

        studentAttendanceChart.getData().add(studRegManager.getSummarizedStudentWeekDayData());
    }

    public void initializeStudents() {
        studentsTableView.setItems(studRegManager.getAllStudents());
        summarizedAttendance.setSortType(TableColumn.SortType.DESCENDING);
        studentsTableView.getSortOrder().setAll(summarizedAttendance);
    }

    public void initializeCourses() {
        courseComboCheckBox.getItems().addAll(studRegManager.getAllCourses());

    }

    public void initializeStudentViewDisplay() {
        //Set student columns.
        studentFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        studentLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        summarizedAttendance.setCellValueFactory(new PropertyValueFactory<Student, Double>("absence"));
        studentFirstName.setMaxWidth(75);
        studentLastName.setMaxWidth(75);
        summarizedAttendance.setMaxWidth(115);
        initializeStudents();
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
        drawPieChartData();
    }

    public void onStudentSelected(MouseEvent mouseEvent) {
        studentAttendanceChart.getData().clear();
        studentAttendanceChart.getData().add(studRegManager.getSummarizedStudentWeekDayData());
    }
}
