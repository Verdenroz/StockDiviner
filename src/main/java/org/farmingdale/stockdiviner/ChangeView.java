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
            stage.show();

    }
}
