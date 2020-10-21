package main.java.utils;

import main.java.exception.InvalidInputException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class JavaFx {

    public static void newAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public static FXMLLoader loadNewScene(Stage stage, String file, String title) throws Exception {
        FXMLLoader loader = new FXMLLoader(JavaFx.class.getResource(file));
        Parent root = loader.load();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1000, 650));
        stage.setResizable(false);
        stage.show();
        return loader;
    }

    public static Double doubleTextFieldCheck(TextField textField, Label errorLabel, String errorMessage)
            throws InvalidInputException {
        try {
            if (textField.getText().trim().isEmpty())
                return null;
            Double result = Double.parseDouble(textField.getText());
            return result;
        } catch (Exception e) {
            errorLabel.setText(errorMessage);
            textField.clear();
            throw new InvalidInputException();
        }
    }

    public static Integer integerTextFieldCheck(TextField textField, Label errorLabel, String errorMessage)
            throws InvalidInputException {
        try {
            if (textField.getText().trim().isEmpty())
                return null;
            Integer result = Integer.parseInt(textField.getText());
            return result;
        } catch (Exception e) {
            errorLabel.setText(errorMessage);
            textField.clear();
            throw new InvalidInputException();
        }
    }
}
