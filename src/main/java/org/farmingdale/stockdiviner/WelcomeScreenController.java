package org.farmingdale.stockdiviner;

import com.google.firebase.auth.UserRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.farmingdale.stockdiviner.model.firebase.FirebaseAuthentication;

import java.io.IOException;

public class WelcomeScreenController {
    public Hyperlink signUpLink;
    public Button loginButton;
    public TextField welcomePwd;
    public TextField welcomeUName;
    public Label loginText;
    public ProgressBar welcomeProgress;
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
        welcomeProgress.setProgress(0.25);
        FirebaseAuthentication auth = FirebaseAuthentication.getInstance();
        UserRecord user = auth.authenticateUser(welcomeUName.getText(), welcomePwd.getText());
        welcomeProgress.setProgress(0.5);
        if(user == null) {
            loginText.setText("User not found.");
            welcomeProgress.setProgress(0);
        }
        else {
            welcomeProgress.setProgress(0.75);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-successful.fxml"));
            root = fxmlLoader.load();
            scene = new Scene(root);

            welcomeProgress.setProgress(1);

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