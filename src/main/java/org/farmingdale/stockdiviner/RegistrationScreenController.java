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
import java.util.regex.Pattern;

public class RegistrationScreenController {
    public TextField usernameField;
    public TextField passwordField;
    public TextField reEnterPasswordField;
    public Button registerButton;
    public TextField emailTextField;
    public Label NotificationText;
    public Label emailNotificationText;
    public Label usernameNotificationText;

    public static boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@"+
                "(?:[a-zA-Z0-9-]+\\.)+[a-z"+
                "A-Z]{2,7}$";
        Pattern emailPattern = Pattern.compile(regex);
        if(email == null)
            return false;
        return emailPattern.matcher(email).matches();
    }

    @FXML
    protected void onRegisterButtonClick(ActionEvent event) throws IOException {
        FirebaseAuthentication auth = FirebaseAuthentication.getInstance();
        if(!Objects.equals(passwordField.getText(), reEnterPasswordField.getText())) {
            NotificationText.setText("Passwords do not match.");
        }
        else if(passwordField.getText().length() < 8) {
            NotificationText.setText("Password must be 8 characters or longer.");
        }
        else if((!emailTextField.getText().contains("@")) || (!emailTextField.getText().contains("."))) {
            emailNotificationText.setText("Email must contain a '@' sign and a '.' sign.");
        }
        else if(!validateEmail(emailTextField.getText()))  {
            emailNotificationText.setText("Invalid email address.");
        }
        else {
            UserRecord user = auth.createUser((emailTextField.getText()), usernameField.getText(), passwordField.getText());
            if(user == null) {
                NotificationText.setText("Email is already in use.");
                return;
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registration-successful.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}

// Email validation referenced from https://steemit.com/utopian-io/@creon/learn-java-fxml-part-1-creating-scenes-with-email-validation-and-scene-switch
