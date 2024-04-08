package org.farmingdale.stockdiviner;

import com.google.firebase.auth.UserRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.farmingdale.stockdiviner.model.firebase.FirebaseAuthentication;

import java.io.IOException;
import java.util.Objects;

public class RegistrationScreenController {
    public TextField usernameField;
    public TextField passwordField;
    public TextField reEnterPasswordField;
    public Button registerButton;
    public TextField emailTextField;
    public Label NotificationText;

    @FXML
    protected void onRegisterButtonClick(ActionEvent event) throws IOException {
        FirebaseAuthentication auth = FirebaseAuthentication.getInstance();
        if(!Objects.equals(passwordField.getText(), reEnterPasswordField.getText())) {
            NotificationText.setText("Passwords do not match.");
        }
        else if(passwordField.getText().length() < 8) {
            NotificationText.setText("Password must be 8 characters or longer.");
        }
        else {
            UserRecord user = auth.createUser((emailTextField.getText()), usernameField.getText(), passwordField.getText());
            if(user == null) {
                NotificationText.setText("Email is already in use.");
                return;
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("warning-screen.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}
