package gui.controllers;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
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

    private Main main;




    @FXML
    void attendanceLogin() {
        String username = usernameField.getText().toLowerCase();

        if (username.equals("teacher")) {
            try {
                FXMLLoader loader = new FXMLLoader((getClass().getResource("/gui/views/TeacherView.fxml")));
                Parent root = loader.load();
                handleStageGeneral(root, "Teacher Attendance Page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader((getClass().getResource("/gui/views/StudentView.fxml")));
                Parent root = loader.load();
                handleStageGeneral(root, "Student Attendance Page");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    void onShowPassword(){
        passwordField.setVisible(false);
        passwordTextField.setVisible(true);
        showPassword.setVisible(false);
        hidePassword.setVisible(true);
        passwordTextField.setText(passwordField.getText());
    }

    @FXML
    void onHidePassword(){
        passwordTextField.setVisible(false);
        passwordField.setVisible(true);
        showPassword.setVisible(true);
        hidePassword.setVisible(false);
        passwordField.setText(passwordTextField.getText());
    }


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

    private void handleStageGeneral(Parent root, String title) {

        Scene scene = new Scene(root);
        scene.setFill(null);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(title);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();

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
