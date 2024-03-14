package org.farmingdale.stockdiviner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloController {

    private Stage stage;
    private Scene scene;
    private Parent root;



    @FXML
    protected void onHelloButtonClick( ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Search.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);

        // Get the stage from the event that triggered the method call
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
}