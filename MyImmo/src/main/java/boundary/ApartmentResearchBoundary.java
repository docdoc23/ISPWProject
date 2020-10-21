/**
 * Edit by EC
 */

package main.java.boundary;

import main.java.bean.ApartmentResearchBean;
import main.java.control.ApartmentResearchController;
import main.java.entity.Sorting;
import main.java.entity.User;
import main.java.exception.CreationFailedException;
import main.java.exception.EntityNotExistException;
import main.java.exception.InvalidInputException;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.java.utils.JavaFx;

import java.io.IOException;
import java.util.*;

public class ApartmentResearchBoundary {

    private User userLogged;
    private ApartmentResearchController apartmentResearchController = new ApartmentResearchController();

    @FXML private TextField cityField, sizeField, priceMinField, priceMaxField, localsMinField, localsMaxField,
            bathroomNumberField, bedsNumberMinField, bedsNumberMaxField;

    @FXML private ChoiceBox<Sorting> sortingField;

    @FXML private CheckBox favoriteCheck, furnishedCheck;

    @FXML private Button buttonResearch, buttonUserProfile;

    @FXML private Label errorField;

    public void initialize() {
        Sorting[] array = Sorting.values();
        List<Sorting> list = Arrays.asList(array);
        ObservableList<Sorting> sortings = FXCollections.observableArrayList(list);
        sortingField.setItems(sortings);
        // Disable button if necessary field is empty
        BooleanBinding booleanBind = cityField.textProperty().isEmpty().or(priceMinField.textProperty().isEmpty())
                .or(priceMaxField.textProperty().isEmpty()).or(sizeField.textProperty().isEmpty())
                .or(sortingField.valueProperty().isNull());
        buttonResearch.disableProperty().bind(booleanBind);
    }

    public void initData(User userLogged) {
        this.userLogged = userLogged;
    }

    public void clearAll() {
        cityField.clear();
        sizeField.clear();
        priceMinField.clear();
        priceMaxField.clear();
        localsMinField.clear();
        localsMaxField.clear();
        bathroomNumberField.clear();
        bedsNumberMinField.clear();
        bedsNumberMaxField.clear();
        sortingField.getSelectionModel().clearSelection();
        favoriteCheck.setSelected(false);
        furnishedCheck.setSelected(false);
    }

    public void newApartmentResearch() {
       try {
            Double size = JavaFx.doubleTextFieldCheck(sizeField, errorField, "Error! Size must be a number");
            Double priceMin = JavaFx.doubleTextFieldCheck(priceMinField, errorField, "Error! Price Min must be a number");
            Double priceMax = JavaFx.doubleTextFieldCheck(priceMaxField, errorField, "Error! Price Max must be a number");
            Integer localsMin = JavaFx.integerTextFieldCheck(localsMinField, errorField, "Error! Locals Min must be a integer " +
                    "number");
            Integer localsMax = JavaFx.integerTextFieldCheck(localsMaxField, errorField, "Error! Locals Max must be a integer " +
                    "number");
            Integer bathroomNumber = JavaFx.integerTextFieldCheck(bathroomNumberField, errorField, "Error! Bathroom Number must be a " +
                    "integer number");
            Integer bedsNumberMin = JavaFx.integerTextFieldCheck(bedsNumberMinField, errorField, "Error! Beds Min Number must be a " +
                    "integer number");
            Integer bedsNumberMax = JavaFx.integerTextFieldCheck(bedsNumberMaxField, errorField, "Error! Beds Max Number must be a " +
                    "integer number");

            if (priceMin > priceMax) {
                errorField.setText("Price Max must be greater then Price Min!");
                priceMinField.clear();
                priceMaxField.clear();
                throw new InvalidInputException();
            }
            if (localsMin != null && localsMax != null) {
                if (localsMin > localsMax) {
                    errorField.setText("Locals Max must be greater then Locals Min!");
                    localsMinField.clear();
                    localsMaxField.clear();
                    throw new InvalidInputException();
                }
            }
            if (bedsNumberMin != null && bedsNumberMax != null) {
                if (bedsNumberMin > bedsNumberMax) {
                    errorField.setText("Beds Number Max must be greater then Beds Number Min!");
                    bedsNumberMinField.clear();
                    bedsNumberMaxField.clear();
                    throw new InvalidInputException();
                }
            }

            ApartmentResearchBean apartmentResearchBean = new ApartmentResearchBean(cityField.getText(), priceMin,
                   priceMax, size, favoriteCheck.isSelected(),sortingField.getValue(), localsMin, localsMax,
                   furnishedCheck.isSelected(), bathroomNumber, bedsNumberMin, bedsNumberMax);

            apartmentResearchController.newApartmentResearch(apartmentResearchBean, userLogged);

            Stage stage = (Stage) cityField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(JavaFx.class.getResource("/standAlone/showResearch.fxml"));
            Parent root = null;
            root = loader.load();
            ShowResearchBoundary showResearchBoundary = loader.getController();
            showResearchBoundary.initData(apartmentResearchBean, userLogged);
            stage.setTitle("Show Research");
            stage.setScene(new Scene(root, 1000, 650));
            stage.setResizable(false);
            stage.show();
        } catch (InvalidInputException iie) {
            return;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EntityNotExistException e) {
           JavaFx.newAlert(Alert.AlertType.INFORMATION, "Result", "No Announce Founded!");
           clearAll();
           return;
       } catch (CreationFailedException e) {
           JavaFx.newAlert(Alert.AlertType.ERROR, "Creation Failed",
                   "Error during creation of new research!");
           clearAll();
           return;
       }

    }

    public void changeUserProfile() {
        try {
            Stage stage = (Stage) cityField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(JavaFx.class.getResource("/standAlone/userProfile.fxml"));
            Parent root = loader.load();
            UserProfileBoundary userProfileBoundary = loader.getController();
            userProfileBoundary.initData(userLogged);
            stage.setTitle("User Profile");
            stage.setScene(new Scene(root, 1000, 650));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
