package org.farmingdale.stockdiviner;

import com.google.firebase.auth.UserRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.farmingdale.stockdiviner.model.firebase.FirebaseAuthentication;

import java.io.IOException;

public class WelcomeScreenController {
    public Hyperlink signUpLink;
    public Button loginButton;
    public TextField welcomePwd;
    public TextField welcomeUName;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label welcomeText;

    /*@FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }*/

    @FXML
    protected void onLoginButtonClick(ActionEvent event) throws IOException {
        FirebaseAuthentication auth = FirebaseAuthentication.getInstance();
        UserRecord user = auth.authenticateUser(welcomeUName.getText(), welcomePwd.getText());
        if(user == null) {
            System.out.println("User not found.");
        }
        else {
            SharedService sharedService = SharedService.getInstance();
            sharedService.setUser(user);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("warning-screen.fxml"));
            root = fxmlLoader.load();
            scene = new Scene(root);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    protected void onSignUpLinkClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registration-screen.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}