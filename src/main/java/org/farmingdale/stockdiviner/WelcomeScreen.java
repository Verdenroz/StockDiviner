package org.farmingdale.stockdiviner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class WelcomeScreen extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WelcomeScreen.class.getResource("Splash_Screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Stock Diviner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}