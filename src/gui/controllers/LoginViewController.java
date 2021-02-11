package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginViewController implements Initializable {

    @FXML
    PasswordField passwordField;
    @FXML
    TextField passwordTextField;
    @FXML
    TextField usernameField;
    @FXML
    Button showPassword;
    @FXML
    Button hidePassword;

    @FXML
    private ImageView EASV;

    private ScreenController screenController;


    @FXML
    void attendanceLogin() {
        String username = usernameField.getText().toLowerCase();

        if (username.equals("teacher")) {
            screenController.teacherPage();
        } else {
            screenController.studentPage();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addImage();
        screenController = ScreenController.getInstance();

    }

    public void addImage() {
        Image logo = new Image("gui/images/EASV_v2.png");
        EASV.setImage(logo);
    }


}

/*
    @FXML
    void loginStudent()
    {
        try {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/gui/views/StudentView.fxml")));
            Parent root = loader.load();
            handleStageGeneral(root, "Student Attendance Page");
        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void loginTeacher() {
        try {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/gui/views/TeacherView.fxml")));
            Parent root = loader.load();
            handleStageGeneral(root, "Teacher Attendance Page");
        }catch(IOException e) {
            e.printStackTrace();
        }

    }*/
