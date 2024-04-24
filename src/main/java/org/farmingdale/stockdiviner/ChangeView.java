package org.farmingdale.stockdiviner;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeView {
    FXMLLoader fxmlLoader;
    Parent root;

    Scene scene;
    Stage stage;

    public static ChangeView getInstance() {
        return new ChangeView();
    }

    public void changeViewTo(String fileName, ActionEvent event) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource(fileName + ".fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

        // Maximize the window instead of setting it to full screen
       // stage.setMaximized(true);

        // The window will be resizable by default, but you can explicitly set it if you want
        stage.setResizable(true);

        stage.show();
    }

     public void logout(ActionEvent event) throws IOException {
        changeViewTo("welcome-screen", event);
}
public void changeViewUsingStageTo(String fileName, Stage stage) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource(fileName + ".fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

}
}
