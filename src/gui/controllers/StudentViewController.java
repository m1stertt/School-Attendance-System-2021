package gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class StudentViewController implements Initializable {

    @FXML
    private Label currentTime;
    @FXML
    private ImageView EASV;
    @FXML
    private PieChart attendancePieChart;
    @FXML
    private DatePicker datePicker;

    private ScreenController screenController;
    //private List<String> CoursesPerDay; //@todo figure out, need dal stuff.


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addImage();
        screenController = ScreenController.getInstance();
        timeDisplayed();
        ObservableList<PieChart.Data> attendancePieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Present", 75), // @todo actually calculate
                new PieChart.Data("Absent", 25) // same as above. Needs to be calcualted
        );
        attendancePieChart.setData(attendancePieChartData);
        datePicker.setValue(LocalDate.now());
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
    void closesStudent() {
        screenController.setLoginInView();
    }
}
