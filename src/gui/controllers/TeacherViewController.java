package gui.controllers;

import be.Course;
import be.Student;
import bll.StudRegManager;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TeacherViewController implements Initializable {

    @FXML
    private PieChart attendancePieChart;
    @FXML
    private AreaChart studentAttendanceChart;
    @FXML
    private TableView<Student> studentsTableView;
    @FXML
    private TableColumn<Student, String> studentFirstName;
    @FXML
    private TableColumn<Student, String> studentLastName;
    @FXML
    public TableColumn<Student, String> summarizedAttendance;
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
        initializeCourses();
        courseComboCheckBox.getSelectionModel().selectFirst();
        drawPieChartData();
        drawAreaChartData();
        timeDisplayed();
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
        DecimalFormat currency = new DecimalFormat(" 0.0%");

        studentFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        studentLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        summarizedAttendance.setCellValueFactory(cellData -> {
            String formattedCost = currency.format(cellData.getValue().getAbsence());
            return new SimpleStringProperty(formattedCost);
        });
        studentFirstName.setMaxWidth(90);
        studentLastName.setMaxWidth(90);
        summarizedAttendance.setMaxWidth(85);
        initializeStudents();
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
        drawPieChartData();
    }

    public void onStudentSelected(MouseEvent mouseEvent) {
        studentAttendanceChart.getData().clear();
        studentAttendanceChart.getData().add(studRegManager.getSummarizedStudentWeekDayData());
    }
}
