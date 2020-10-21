/**
 * Edit by EC
 */

package main.java.boundary;

import main.java.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.java.utils.JavaFx;

import java.io.IOException;

public class UserProfileBoundary {

    private User userLogged;

    @FXML
    private Label nicknameField;

    @FXML
    private Label nameField;

    @FXML
    private Label surnameField;

    @FXML
    private Label emailField;

    @FXML
    private Label genderField;

    public void initData(User userLogged) {
        this.userLogged = userLogged;
        nicknameField.setText(userLogged.getNickname());
        nameField.setText(userLogged.getName());
        surnameField.setText(userLogged.getSurname());
        emailField.setText(userLogged.getEmail());
        genderField.setText(userLogged.getGender().toString());
    }

    public void initialize() {}

    public void changeApartmentResearch() {
        try {
            Stage stage = (Stage) nicknameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(JavaFx.class.getResource("/standAlone/apartmentResearch.fxml"));
            Parent root = loader.load();
            ApartmentResearchBoundary apartmentResearchBoundary = loader.getController();
            apartmentResearchBoundary.initData(userLogged);
            stage.setTitle("ApartmentAnnounce Research");
            stage.setScene(new Scene(root, 1000, 650));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeRoomResearch() {
        try {
            Stage stage = (Stage) nicknameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(JavaFx.class.getResource("/standAlone/roomResearch.fxml"));
            Parent root = loader.load();
            RoomResearchBoundary roomResearchBoundary = loader.getController();
            roomResearchBoundary.initData(userLogged);
            stage.setTitle("RoomAnnounce Research");
            stage.setScene(new Scene(root, 1000, 650));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeFavorite() {
        try {
            Stage stage = (Stage) nicknameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(JavaFx.class.getResource("/standAlone/favorite.fxml"));
            Parent root = loader.load();
            FavoriteBoundary favoriteBoundary = loader.getController();
            favoriteBoundary.initData(userLogged);
            stage.setTitle("Favorite");
            stage.setScene(new Scene(root, 1000, 650));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeRecent() {
        try {
            Stage stage = (Stage) nicknameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(JavaFx.class.getResource("/standAlone/recent.fxml"));
            Parent root = loader.load();
            RecentBoundary recentBoundary = loader.getController();
            recentBoundary.initData(userLogged);
            stage.setTitle("Recent");
            stage.setScene(new Scene(root, 1000, 650));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LogOut(){
        try{
            Stage stage = (Stage) nicknameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(JavaFx.class.getResource("/standAlone/login.fxml"));
            Parent root = loader.load();
            LoginBoundary loginBoundary = loader.getController();
            stage.setTitle("Log In");
            stage.setScene(new Scene(root, 1000, 650));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
