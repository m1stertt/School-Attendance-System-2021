package gui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;


/**
 * ScreenController class for controlling the settings for views
 * inside the Background.fxml view.
 */
public class ScreenController {

    private static ScreenController screenControllerInstance = new ScreenController();

    private Stage primaryStage;
    private BorderPane backgroundLayout;

    public static ScreenController getInstance() {
        return screenControllerInstance;
    }

    private ScreenController() {
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public void initBackgroundLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ScreenController.class.getResource("/gui/views/Background.fxml"));
            backgroundLayout = (BorderPane) loader.load();

            Scene scene = new Scene(backgroundLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLoginInView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ScreenController.class.getResource("/gui/views/LoginView.fxml"));
            AnchorPane userLogin = (AnchorPane) loader.load();
            setStageOptions(366, 468, userLogin);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTeacherView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ScreenController.class.getResource("/gui/views/TeacherView.fxml"));
            AnchorPane teacherView = (AnchorPane) loader.load();
            setStageOptions(650, 475, teacherView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setStudentView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ScreenController.class.getResource("/gui/views/StudentView.fxml"));
            AnchorPane studentView = (AnchorPane) loader.load();
            setStageOptions(650, 475, studentView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStageOptions(double width, double height, AnchorPane view) {
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        backgroundLayout.setCenter(view);
        primaryStage.centerOnScreen();
    }

}
