package gui.controllers;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginViewController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addImage();
    }

    public void addImage(){
        Image logo =  new Image("gui/images/EASV_v2.png");
        EASV.setImage(logo);
    }
}
