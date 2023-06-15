package com.esiee.java_avance_lot_1.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class InfosController implements Initializable {

    @FXML
    private Button infosClose;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        infosClose.setOnAction(actionEvent -> infosClose.getScene().getWindow().hide());
    }
}
