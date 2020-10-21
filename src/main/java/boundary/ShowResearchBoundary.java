package main.java.boundary;

import main.java.bean.ResearchBean;
import main.java.control.ShowResearchController;
import main.java.entity.*;
import main.java.exception.EntityNotExistException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import main.java.utils.JavaFx;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowResearchBoundary {

    private ResearchBean researchBean;
    private User loggedUser;
    private int selectedAnnounce;
    boolean RoomOrApart;

    private ShowResearchController showResearchController = new ShowResearchController();

    @FXML
    private ListView announceList;

    @FXML
    private Button UP;

    public void initialize(){}

    public void initData(ResearchBean researchBean , User loggedUser) throws EntityNotExistException {

        this.researchBean = researchBean;
        this.loggedUser = loggedUser;

        List<Announce> announces = showResearchController.showCurrentResearch(researchBean, loggedUser);
        if (announces.isEmpty()) {
            throw new EntityNotExistException();
        }
        List<String> list = new ArrayList<>();
        for (Announce r : announces) {
            if (r.getClass().equals(ApartmentAnnounce.class)) {
                list.add("ApartmentAnnounce ID: " + r.getID());
                RoomOrApart = true;
            }
            else {
                list.add("RoomAnnounce ID: " + r.getID());
                RoomOrApart = false;
            }
        }
        announceList.setItems(FXCollections.observableArrayList(list));

        announceList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                selectedAnnounce = announces.get(newValue.intValue()).getID();
            }
        });

    }

    @FXML
    public void OnClick(){
        try {
            Stage stage = (Stage) announceList.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(JavaFx.class.getResource("/standAlone/showSingleAnnounce.fxml"));
            Parent root = loader.load();
            SingleAnnounceBoundary singleAnnounceBoundary = loader.getController();
            singleAnnounceBoundary.initData(selectedAnnounce , loggedUser , RoomOrApart);
            stage.setTitle("Announce shown");
            stage.setScene(new Scene(root, 1000, 650));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void OnClickBackUser(){

        Stage stage = (Stage) announceList.getScene().getWindow();
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
