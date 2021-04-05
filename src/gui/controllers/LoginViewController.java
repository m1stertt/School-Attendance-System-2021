package gui.controllers;

import com.jfoenix.controls.JFXComboBox;
import gui.models.StudRegModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private JFXComboBox<String> roleComboBox;


    @FXML
    private ImageView EASV;

    private ScreenController screenController;

    private StudRegModel studRegModel = new StudRegModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addImage();
        screenController = ScreenController.getInstance();
        roleComboBox.getSelectionModel().selectFirst();

    }

    @FXML
    public void attendanceLogin() {
        try {
            String userName = usernameField.getText();
            String password = passwordField.getText();
            String role = roleComboBox.getSelectionModel().getSelectedItem();
            if(studRegModel.checkLogin(userName, password, role) && role.equalsIgnoreCase("student")){
                screenController.setStudentView();
            }
            else if ((studRegModel.checkLogin(userName, password, role) && role.equalsIgnoreCase("teacher"))){
                screenController.setTeacherView();
            }
            else throw new Exception();

    } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Failed");
            alert.setContentText("Please enter correct Username and Password and/or check your login Role.");
            alert.showAndWait();
        }
    }

        @FXML
    public void onShowPassword() {
        passwordField.setVisible(false);
        passwordTextField.setVisible(true);
        showPassword.setVisible(false);
        hidePassword.setVisible(true);
        passwordTextField.setText(passwordField.getText());
    }

    @FXML
    public void onHidePassword() {
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
