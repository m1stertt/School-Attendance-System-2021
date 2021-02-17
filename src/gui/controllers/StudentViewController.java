package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class StudentViewController implements Initializable {

    @FXML
    private Label currentTime;
    @FXML
    private ImageView EASV;

    private ScreenController screenController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addImage();
        screenController = ScreenController.getInstance();
        timeDisplayed();
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
