package gui.controllers;

import be.Course;
import be.Student;
import bll.LoginSession;
import com.jfoenix.controls.JFXComboBox;
import gui.models.CurrentTimeClock;
import gui.models.TeacherViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TeacherViewController implements Initializable {

    @FXML
    private PieChart attendancePieChart;
    @FXML
    private TableView<Student> studentsTableView;
    @FXML
    private TableColumn<Student, String> studentFirstName;
    @FXML
    private TableColumn<Student, String> studentLastName;
    @FXML
    public TableColumn<Student, Double> summarizedAttendance;
    @FXML
    private JFXComboBox<Course> courseComboCheckBox;
    @FXML
    private Label currentTimeOfLogin;
    @FXML
    private ImageView EASV;

    private ScreenController screenController;
    private TeacherViewModel teacherViewModel = new TeacherViewModel();
    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addImage();
        screenController = ScreenController.getInstance();

        initializeStudentViewDisplay();
        initializeCourses();
        initializeStudents();
        displayClock();
        runGetStudentsThread();
        runDrawPieChartDataThread();
    }


    public void runDrawPieChartDataThread() {
        Task<ObservableList<PieChart.Data>> task = new Task<>() {
            @Override
            public ObservableList<PieChart.Data> call() throws Exception {
                HashMap<String, Double> courseAbsenceData = teacherViewModel.getCourseAbsenceDate(courseComboCheckBox.getSelectionModel().getSelectedItem().getId());
                ObservableList<PieChart.Data> attendancePieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Presence", courseAbsenceData.get("classAverageStudentAttendance")),
                        new PieChart.Data("Absence", courseAbsenceData.get("allLessonsInCourse")-courseAbsenceData.get("classAverageStudentAttendance"))
                );
                return attendancePieChartData;
            }
        };
        task.setOnFailed(e -> {
            task.getException().printStackTrace();
        });
        task.setOnSucceeded(e -> {
            attendancePieChart.setData(task.getValue());
        });
        executorService.submit(task);
    }


    public void initializeStudents() {
        runGetStudentsThread();
        courseComboCheckBox.getSelectionModel().selectFirst();
        summarizedAttendance.setSortType(TableColumn.SortType.DESCENDING);
        studentsTableView.getSortOrder().add(summarizedAttendance);
        studentsTableView.sort();
    }

    public void initializeCourses() {
        courseComboCheckBox.getItems().addAll(teacherViewModel.getAllCourses());
    }

    public void initializeStudentViewDisplay() {
        //Set student columns.
        studentFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        studentLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        summarizedAttendance.setCellValueFactory(new PropertyValueFactory<>("absence"));
        summarizedAttendance.setCellFactory(tc -> new TableCell<Student, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                if (empty) {
                    setText("");
                } else {
                    setText(String.format("%.2f%%", price.doubleValue()));
                }
            }
        });
        studentFirstName.setMaxWidth(90);
        studentLastName.setMaxWidth(90);
        summarizedAttendance.setMaxWidth(85);
    }

    private void displayClock() {
        CurrentTimeClock.getInstance().initClock(currentTimeOfLogin);
    }

    public void addImage() {
        Image logo = new Image("gui/images/EASV_v2.png");
        EASV.setImage(logo);
    }

    @FXML
    void logoutTeacher() {
        screenController.setLoginInView();
        LoginSession.setIsLoggedIn(false);
    }

    public void onComboboxSelect(ActionEvent actionEvent) {
        runGetStudentsThread();
        runDrawPieChartDataThread();
    }

    public void runGetStudentsThread() {
        Task<ObservableList<Student>> task = new Task<>() {
            @Override
            public ObservableList<Student> call() throws Exception {
                return teacherViewModel.getAllStudents(courseComboCheckBox.getSelectionModel().getSelectedItem().getId());
            }
        };
        task.setOnFailed(e -> {
            task.getException().printStackTrace();
        });
        task.setOnSucceeded(e -> {
            studentsTableView.setItems(task.getValue());
            summarizedAttendance.setSortType(TableColumn.SortType.ASCENDING);
            studentsTableView.getSortOrder().add(summarizedAttendance);
            studentsTableView.sort();
        });
        executorService.submit(task);
    }

    public void getsStudentInfo() throws IOException {
        Student selectedStudent = studentsTableView.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/StudentInformationView.fxml"));
        Parent root = loader.load();
        StudentInformationController controller = loader.getController();
        controller.attendanceEdit(selectedStudent);
        handleStageGeneral(root);
    }

    private void handleStageGeneral(Parent root) {
        Scene scene = new Scene(root);
        scene.setFill(null);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }


}
