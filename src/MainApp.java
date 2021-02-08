import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane backgroundLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("School Attendance");

        initBackgroundLayout();

        loginInPage();
    }

    private void initBackgroundLayout()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/gui/views/Background.fxml"));
            backgroundLayout = (BorderPane) loader.load();

            Scene scene = new Scene(backgroundLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loginInPage()
    {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/gui/views/LoginView.fxml"));
            AnchorPane userLogin = (AnchorPane) loader.load();

            backgroundLayout.setCenter(userLogin);

        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }






}