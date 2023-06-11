package com.esiee.java_avance_lot_1.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class SaveController implements Initializable {

    @FXML
    private Button saveNoButton;

    @FXML
    private Button saveYesButton;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveNoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveNoButton.getScene().getWindow().hide();

            }
        });

        saveYesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }
}
