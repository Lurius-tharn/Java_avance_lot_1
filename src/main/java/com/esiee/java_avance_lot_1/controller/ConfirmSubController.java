package com.esiee.java_avance_lot_1.controller;

import com.esiee.java_avance_lot_1.singleton.Popup;
import com.esiee.java_avance_lot_1.vue.InfosApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmSubController implements Initializable {
    @FXML
    private Button confirmSubButton;
    @FXML
    private Text popupText;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        popupText.setText(Popup.getPopup().getTextValue());
        confirmSubButton.setOnAction(ActionEvent -> confirmSubAndGoToLogin());
    }

    private void confirmSubAndGoToLogin() {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(InfosApplication.class.getResource("login.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) confirmSubButton.getScene().getWindow();
        currentStage.close();
    }
}
