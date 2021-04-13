package gui.models;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentTimeClock {

    private static CurrentTimeClock instance;

    public static CurrentTimeClock getInstance(){
        if(instance==null){
            instance = new CurrentTimeClock();
        }
        return instance;
    }

    public void initClock(javafx.scene.control.Label currentTimeOfLogin) {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            currentTimeOfLogin.setText("Current Time: "+ LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

}
