package main.java.boundary;

import main.java.control.SingleAnnounceController;
import main.java.entity.Announce;
import main.java.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.java.utils.JavaFx;

import java.awt.*;
import java.io.IOException;

public class SingleAnnounceBoundary {

    public javafx.scene.control.Label CityField;
    public Label IDField;
    public Label DateField;
    public Label AddressField;
    public Label PriceField;
    public Label DescriptionField;
    public Label AvailableField;
    public Button UP2;


    private User loggedUser;
    private int selectedAnnounce;
    private Announce announce;


    public void initialize() {}


    public void initData(int selectedAnnounce , User loggedUser , boolean RoomOrApart) {
        this.selectedAnnounce = selectedAnnounce;
        this.loggedUser = loggedUser;
        if(RoomOrApart) {
            announce = SingleAnnounceController.ShowSelectedAAnnounce(selectedAnnounce);
        }
        else {
            announce = SingleAnnounceController.ShowSelectedRAnnounce(selectedAnnounce);
        }
        IDField.setText(String.valueOf(announce.getID()));
        CityField.setText(String.valueOf(announce.getCity()));
        AddressField.setText(String.valueOf(announce.getAddress()));
        PriceField.setText(String.valueOf(announce.getprice()));
        DescriptionField.setText(String.valueOf(announce.getDescription()));
        AvailableField.setText(String.valueOf(announce.getavailable()));
        DateField.setText(String.valueOf(announce.getDate()));
    }

    @FXML

    public void OnClickBackUser(){

        Stage stage = (Stage) UP2.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(JavaFx.class.getResource("/standAlone/userProfile.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserProfileBoundary userProfileBoundary = loader.getController();
        userProfileBoundary.initData(loggedUser);
        stage.setTitle("User Profile");
        stage.setScene(new Scene(root, 1000, 650));
        stage.setResizable(false);
        stage.show();
    }


}
