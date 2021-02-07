package gui.controllers;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


public class LoginViewController {

    @FXML
    private ImageView EASV;

    @FXML
    void loginStudent(ActionEvent event) {

    }

    @FXML
    void loginTeacher(ActionEvent event) {

    }


    private Main main;


    public void setMainApp(Main mainApp){
        this.main = mainApp;
    }
}
