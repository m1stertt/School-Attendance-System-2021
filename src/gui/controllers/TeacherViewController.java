package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherViewController implements Initializable {

    @FXML
    private ImageView EASV;

    private ScreenController screenController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addImage();
        screenController = ScreenController.getInstance();
    }

    public void addImage() {
        Image logo = new Image("gui/images/EASV_v2.png");
        EASV.setImage(logo);
    }

    @FXML
    void closesTeacher() {
        screenController.loginInPage();
    }

}
