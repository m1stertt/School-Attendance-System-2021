package gui.controllers;

import bll.LoginSession;
import gui.models.CurrentTimeClock;
import gui.models.StudentViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class StudentViewController implements Initializable {

    @FXML
    private PieChart attendancePieChart;
    @FXML
    private Label currentTimeOfLogin;
    @FXML
    private ImageView EASV;
    @FXML
    private DatePicker datePicker;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label displayStudentName;

    private ScreenController screenController;
    private StudentViewModel studentViewModel = new StudentViewModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addImage();
        screenController = ScreenController.getInstance();
        displayClock();
        drawPieChartData();
        handleDatePicker();
        displayStudentName.setText(LoginSession.getUserName());
        //todo display student name and not login  name


        createCoursesView(studentViewModel.getCoursesStringForDay(datePicker.getValue()));
    }


    public void handleDatePicker() {
        datePicker.setValue(LocalDate.now()); //Set initial value
        datePicker.valueProperty().addListener((ov, oldValue, newValue) -> { //Listen for changes in the datePicker
            createCoursesView(studentViewModel.getCoursesStringForDay(datePicker.getValue())); //Update list of courses to reflect new date.
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
            button1.relocate(116, 7);
            button1.setStyle("-fx-background-color:green");
            group.getChildren().add(button1);

            Label label = new Label(courses.get(i));
            label.setStyle("-fx-text-fill:black;-fx-font-size:14px;");
            label.relocate(-100, 10);
            group.getChildren().add(label);

            Line line = new Line(-119, 0, 185, 0); //@todo positioning needs to be better. off atm.
            line.relocate(-100, 39);
            group.getChildren().add(line);

            setButtonEvents(button1, label); //Set events for "blurring" buttons on click and clearing the opposite button of any blur.
            anchorPane.getChildren().add(group);
        }
        anchorPane.setMinSize(320, 40 * courses.size()); //This makes sure scroll appears if necessary, calculating the proper height for the pane, so the scrollPane will react.
    }

    public void setButtonEvents(Button button1, Label label1) {
        button1.setOnAction(e -> {
            HashMap<String, ArrayList<LocalTime>> courseTimes = studentViewModel.getCourseTime(datePicker.getValue());
            courseTimes.forEach((s, dates) -> {
                if ((label1.getText().contains(s)) && isWithinRange(dates.get(0), dates.get(1))) {
                    button1.setDisable(true);
                    studentViewModel.getAllCourses().forEach(course -> {
                        if (label1.getText().contains(course.getCourseName())) {
                            studentViewModel.attendanceRegister(course);
                        }
                    });
                }
            });
        });
    }

    public boolean isWithinRange(LocalTime startTime, LocalTime endTime) {
        LocalTime now = LocalTime.now();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime currentDate = LocalDateTime.now();
        if (now.isAfter(startTime) && now.isBefore((endTime)) && dtf.format(currentDate).equals(String.valueOf(datePicker.getValue()))) {
            return true;
        } else {
            return false;
        }
    }

    public void drawPieChartData() {
        double d= studentViewModel.getAbsenceData(LoginSession.getUserId());
        ObservableList<PieChart.Data> attendancePieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Present", d),
                new PieChart.Data("Absent", 1-d)
        );
        attendancePieChart.setData(attendancePieChartData);
    }

    private void displayClock() {
        CurrentTimeClock.getInstance().initClock(currentTimeOfLogin);
    }

    public void addImage() {
        Image logo = new Image("gui/images/EASV_v2.png");
        EASV.setImage(logo);
    }

    @FXML
    void logoutStudent() {
        screenController.setLoginInView();
        LoginSession.setIsLoggedIn(false);
    }
}
