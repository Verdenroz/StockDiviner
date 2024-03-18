package org.farmingdale.stockdiviner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class WarningScreenController {
    @FXML
    public Button warnCloseButton;

    @FXML
    public void onWarnCloseButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("warning-screen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
    }
}
