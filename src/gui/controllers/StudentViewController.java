package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentViewController implements Initializable {

    @FXML
    private ImageView EASV;
    @FXML
    private Button closeStudent;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addImage();
    }

    public void addImage(){
        Image logo =  new Image("gui/images/EASV_v2.png");
        EASV.setImage(logo);
    }

    @FXML
    void closesStudent() {
        Stage window = (Stage) closeStudent.getScene().getWindow();
        window.close();
    }
}
