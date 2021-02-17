package gui.controllers;

import dal.StudRegDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginViewController implements Initializable {

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField usernameField;
    @FXML
    private Button showPassword;
    @FXML
    private Button hidePassword;


    @FXML
    private ImageView EASV;

    private ScreenController screenController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addImage();
        screenController = ScreenController.getInstance();
    }

    @FXML
    void attendanceLogin() {
        String username = usernameField.getText().toLowerCase();

        if (username.equals("teacher")) {
            screenController.setTeacherView();
        } else if(username.equals("student")){
            screenController.setStudentView();
        } else {
            return;
        }
    }

    @FXML
    void onShowPassword() {
        passwordField.setVisible(false);
        passwordTextField.setVisible(true);
        showPassword.setVisible(false);
        hidePassword.setVisible(true);
        passwordTextField.setText(passwordField.getText());
    }

    @FXML
    void onHidePassword() {
        passwordTextField.setVisible(false);
        passwordField.setVisible(true);
        showPassword.setVisible(true);
        hidePassword.setVisible(false);
        passwordField.setText(passwordTextField.getText());
    }

    public void addImage() {
        Image logo = new Image("gui/images/EASV_v2.png");
        EASV.setImage(logo);
    }

}
