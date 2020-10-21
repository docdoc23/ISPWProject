/**
 * Edit by EC
 */

package main.java.boundary;

import main.java.control.SignupController;
import main.java.entity.User;
import main.java.exception.EntityAlreadyExistException;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.utils.JavaFx;

public class SignupBoundary {

    private SignupController signupController = new SignupController();

    @FXML
    private TextField nicknameField;

    @FXML
    private TextField emailField;

    @FXML
    private ChoiceBox<String> genderField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField surnameField;

    @FXML
    private Button submitButton;

    public void initialize() {
        ObservableList<String> genders =
                FXCollections.observableArrayList("Male", "Female");
        genderField.setItems(genders);
        // Disable button if one textfield is empty
        BooleanBinding booleanBind = nicknameField.textProperty().isEmpty().or(nameField.textProperty().isEmpty())
                .or(surnameField.textProperty().isEmpty()).or(emailField.textProperty().isEmpty())
                .or(passwordField.textProperty().isEmpty()).or(genderField.valueProperty().isNull());
        submitButton.disableProperty().bind(booleanBind);

    }

    @FXML
    public void signup() {
        try {
            User u = signupController.signup(nicknameField.getText(), nameField.getText(), surnameField.getText(),
                    emailField.getText(), passwordField.getText(), genderField.getValue().toCharArray()[0]);
            Stage stage = (Stage) nicknameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(JavaFx.class.getResource("/standAlone/userProfile.fxml"));
            Parent root = loader.load();
            UserProfileBoundary userProfileBoundary = loader.getController();
            userProfileBoundary.initData(u);
            stage.setTitle("User Profile");
            stage.setScene(new Scene(root, 1000, 650));
            stage.setResizable(false);
            stage.show();

        } catch (EntityAlreadyExistException e) {
            JavaFx.newAlert(Alert.AlertType.ERROR, "Error!", "Nickname already exists!");
            nicknameField.clear();
            passwordField.clear();
            nameField.clear();
            surnameField.clear();
            emailField.clear();
        } catch (Exception e) {
            e.printStackTrace();
            JavaFx.newAlert(Alert.AlertType.ERROR, "Error!", "Error during registration!");
            nicknameField.clear();
            passwordField.clear();
            nameField.clear();
            surnameField.clear();
            emailField.clear();
        }
    }
}
