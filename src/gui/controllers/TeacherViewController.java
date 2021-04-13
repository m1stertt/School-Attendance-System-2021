package gui.controllers;

import be.Course;
import be.Student;
import be.Teacher;
import bll.LoginSession;
import bll.StudRegManager;
import com.jfoenix.controls.JFXComboBox;
import gui.models.CurrentTimeClock;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TeacherViewController implements Initializable {

    @FXML
    private Label displayTeacherName;
    @FXML
    private Button moreStudentInfo;
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
    private Label currentTimeOfLogin;
    @FXML
    private ImageView EASV;

    private ScreenController screenController;
    private StudRegManager studRegManager;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addImage();
        screenController = ScreenController.getInstance();
        studRegManager = new StudRegManager();
        initializeStudentViewDisplay();
        initializeCourses();
        courseComboCheckBox.getSelectionModel().selectFirst();
        initializeStudents();
        displayClock();
    }


//    public void drawPieChartData() {
//        ObservableList<PieChart.Data> attendancePieChartData = FXCollections.observableArrayList(
//                new PieChart.Data("Present", courseComboCheckBox.getSelectionModel().getSelectedItem().getPresence()),
//                new PieChart.Data("Absent", courseComboCheckBox.getSelectionModel().getSelectedItem().getAbsence())
//        );
//        attendancePieChart.setData(attendancePieChartData);
//    }

//    public void drawAreaChartData() {
//        Axis<String> xAxis = studentAttendanceChart.getXAxis();
//        xAxis.setTickLabelRotation(10);
//        Axis<String> yAxis = studentAttendanceChart.getYAxis();
//        yAxis.setLabel("Absence shown as lessons");
//
//        studentAttendanceChart.getData().add(studRegManager.getSummarizedStudentWeekDayData());
//    }

    public void initializeStudents() {
        studentsTableView.setItems(studRegManager.getAllStudents(courseComboCheckBox.getSelectionModel().getSelectedItem().getCourseName()));
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
        Task<ObservableList<Student>> task = new Task<>() {
            @Override
            public ObservableList<Student> call() throws Exception {
                return studRegManager.getAllStudents(courseComboCheckBox.getSelectionModel().getSelectedItem().getCourseName());
            }
        };
        task.setOnFailed(e -> {
            task.getException().printStackTrace();
        });
        task.setOnSucceeded(e ->
                studentsTableView.setItems(task.getValue()));

        executorService.submit(task);
//        studentsTableView.setItems(studRegManager.getAllStudents(courseComboCheckBox.getSelectionModel().getSelectedItem().getCourseName()));
    }





    public void onStudentSelected(MouseEvent mouseEvent) {
        //studentAttendanceChart.getData().clear();
        //studentAttendanceChart.getData().add(studRegManager.getSummarizedStudentWeekDayData());
    }

    public void getsStudentInfo() throws IOException {
        Student selectedStudent = studentsTableView.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/StudentInformationView.fxml"));
        Parent root = loader.load();
        StudentInformationController controller = loader.getController();
        controller.attendanceEdit(selectedStudent);
        controller.studentName(selectedStudent);
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
