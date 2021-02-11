import gui.controllers.ScreenController;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    private ScreenController screenController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        screenController = ScreenController.getInstance();
        screenController.setPrimaryStage(primaryStage);
        screenController.getPrimaryStage().setTitle("School Attendance");
        screenController.initBackgroundLayout();
        screenController.loginInPage();

    }


}