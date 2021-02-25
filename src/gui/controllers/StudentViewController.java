package gui.controllers;

import bll.StudRegManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class StudentViewController implements Initializable {

    @FXML
    private PieChart attendancePieChart;
    @FXML
    private Label currentTime;
    @FXML
    private ImageView EASV;
    @FXML
    private DatePicker datePicker;
    @FXML private AnchorPane anchorPane;

    private ScreenController screenController;
    private StudRegManager studRegManager;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addImage();
        screenController = ScreenController.getInstance();
        timeDisplayed();
        drawPieChartData();
        handleDatePicker();

        studRegManager = new StudRegManager();
        createCoursesView(studRegManager.getCoursesStringForDay(datePicker.getValue()));
    }

    public void handleDatePicker(){
        datePicker.setValue(LocalDate.now()); //Set initial value
        datePicker.valueProperty().addListener((ov, oldValue, newValue) -> { //Listen for changes in the datePicker
            createCoursesView(studRegManager.getCoursesStringForDay(datePicker.getValue())); //Update list of courses to reflect new date.
        });
    }

    public void createCoursesView(List<String> courses){
        anchorPane.getChildren().clear();
        for(int i=0;i<courses.size();i++){
            //Create buttons
            Group group=new Group();
            group.relocate(108,-1+40*i);

            Button button1=new Button("ABSENT");
            button1.relocate(31,7);
            button1.setStyle("-fx-background-color:#ff0000");
            group.getChildren().add(button1);

            Button button2=new Button("PRESENT");
            button2.relocate(116,7);
            button2.setStyle("-fx-background-color:green");
            group.getChildren().add(button2);

            Label label=new Label(courses.get(i));
            label.setStyle("-fx-text-fill:black;-fx-font-size:16px;");
            label.relocate(-96,7);
            group.getChildren().add(label);

            Line line=new Line(-119,0,196,0); //@todo positioning needs to be better. off atm.
            line.relocate(12,39);
            group.getChildren().add(line);
            setButtonEvents(button1,button2);
            setButtonEvents(button2,button1);
            anchorPane.getChildren().add(group);
        }
        anchorPane.setMinSize(320,40*courses.size()); //This makes sure scroll appears, calculating the proper height for the pane, so the scrollpane will react.
    }

    public void setButtonEvents(Button button1,Button button2){
        button1.setOnAction(e->{
            button1.setDisable(true);
            button2.setDisable(false);
        });
    }

    public void drawPieChartData() {
        ObservableList<PieChart.Data> attendancePieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Present", 85),
                new PieChart.Data("Absent", 15)
        );
        attendancePieChart.setData(attendancePieChartData);
    }

    public void timeDisplayed(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        currentTime.setText("Current Time: "+dtf.format(now));
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
