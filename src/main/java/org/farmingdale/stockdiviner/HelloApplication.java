package org.farmingdale.stockdiviner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Load properties and set system property
        Properties properties = new Properties();
        try (InputStream input = HelloApplication.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Unable to find config.properties");
            }
            properties.load(input);
            String apiKey = properties.getProperty("alphaVantage.apiKey");
            System.setProperty("alphaVantage.apiKey", apiKey);
            System.out.println("System property alphaVantage.apiKey set to: " + System.getProperty("alphaVantage.apiKey"));
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        launch(args);
    }
}