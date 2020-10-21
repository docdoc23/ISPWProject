package main.java.boundary;

import main.java.control.LoginController;
import main.java.entity.User;
import main.java.exception.EntityNotExistException;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.utils.JavaFx;

public class LoginBoundary {

    private LoginController loginController = new LoginController();

    @FXML
    private TextField nicknameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        BooleanBinding booleanBind = nicknameField.textProperty().isEmpty().or(passwordField.textProperty().isEmpty());
        loginButton.disableProperty().bind(booleanBind);
    }

    @FXML
    public void login() {
        try {
            User u = loginController.login(nicknameField.getText(), passwordField.getText());
            if (u == null) {
                JavaFx.newAlert(Alert.AlertType.ERROR, "Error!", "Wrong password!");
                passwordField.clear();
            }
            Stage stage = (Stage) nicknameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(JavaFx.class.getResource("/standAlone/userProfile.fxml"));
            Parent root = loader.load();
            UserProfileBoundary userProfileBoundary = loader.getController();
            userProfileBoundary.initData(u);
            stage.setTitle("User Profile");
            stage.setScene(new Scene(root, 1000, 650));
            stage.setResizable(false);
            stage.show();

        } catch (EntityNotExistException e) {
            JavaFx.newAlert(Alert.AlertType.ERROR, "Error!", "Nickname not exists!");
            nicknameField.clear();
            passwordField.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void changeSignup() {
        try {
            JavaFx.loadNewScene((Stage) nicknameField.getScene().getWindow(), "/standAlone/signup.fxml",
                    "Signup");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
