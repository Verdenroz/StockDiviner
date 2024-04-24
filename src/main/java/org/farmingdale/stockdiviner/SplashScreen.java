package org.farmingdale.stockdiviner;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
public class SplashScreen {
    @FXML
    private ImageView StockDivinerLogo;

    @FXML
    private ProgressBar loadingBar;




    public void initialize()  {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), StockDivinerLogo);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        fadeTransition.setOnFinished(e-> {

            try {
                Progress();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        fadeTransition.play();



    }

    public void Progress() throws IOException {
        final int maxTime = 4;
        Timeline timeline = new Timeline();

        for (int i = 0; i <= maxTime; i++) {
            final int time  = i;
            timeline.getKeyFrames().add(
                    new KeyFrame(
                            Duration.seconds(time),
                            event ->
                                loadingBar.setProgress((double) time / maxTime)

                    )
            );

        }

        timeline.setOnFinished(event -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(0.25));
            pause.setOnFinished(e -> {
                try {
                    Stage stage = (Stage) StockDivinerLogo.getScene().getWindow();
                    ChangeView.getInstance().changeViewUsingStageTo("welcome-screen", stage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            pause.play();


        });


        timeline.play();


    }

}


