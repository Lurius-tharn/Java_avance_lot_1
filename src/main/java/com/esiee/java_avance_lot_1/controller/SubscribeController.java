package com.esiee.java_avance_lot_1.controller;

import com.esiee.java_avance_lot_1.dao.BibliothequeDao;
import com.esiee.java_avance_lot_1.singleton.Popup;
import com.esiee.java_avance_lot_1.vue.InfosApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SubscribeController implements Initializable {

    @FXML
    private TextField mailInput;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private PasswordField passwordConfirmInput;
    @FXML
    private Button subscribeButton;
    @FXML
    private Button backToLoginPageButton;
    @FXML
    private ChoiceBox<String> roleSelector;
    @FXML
    private Text errorText;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        roleSelector.getItems().addAll("Utilisateur", "Gérant");

        subscribeButton.setOnAction(ActionEvent -> checkSubscribe());
        mailInput.focusedProperty().addListener((observable, oldValue, newValue) -> errorText.setVisible(false));
        usernameInput.focusedProperty().addListener((observable, oldValue, newValue) -> errorText.setVisible(false));
        backToLoginPageButton.setOnAction(ActionEvent -> backToLoginPage());
    }

    private void backToLoginPage() {
        mailInput.clear();
        usernameInput.clear();
        passwordInput.clear();
        passwordConfirmInput.clear();

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
        Stage currentStage = (Stage) subscribeButton.getScene().getWindow();
        currentStage.close();
    }

    private void checkSubscribe() {
        if(checkEmailValidity(mailInput.getText())) {
            if(usernameInput.getText().length() >= 4 && passwordInput.getText().length() >= 8) {
                if(passwordInput.getText().equals(passwordConfirmInput.getText())) {
                    int role = 0;
                    BibliothequeDao bibliothequeDao = new BibliothequeDao();
                    if(roleSelector.getValue().equals("Gérant")) {
                        role = 1;
                    }
                    try {
                        int res = bibliothequeDao.createUser(usernameInput.getText(), passwordInput.getText(), role, mailInput.getText());
                        System.out.println(res);
                        if(res == 0) {
                            errorText.setText("Nom d'utilisateur déjà utilisé");
                            errorText.setVisible(true);
                        } else {
                            goToConfirmPage();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    errorText.setText("Champs de MDP différents");
                    errorText.setVisible(true);
                }
            } else {
                errorText.setText("Nom d'utilisateur trop court ou MDP trop court");
                errorText.setVisible(true);
            }
        } else {
            errorText.setText("Mail invalide");
            errorText.setVisible(true);
        }

    }

    private void goToConfirmPage() {
        if (Popup.getPopup() != null) {
            Popup.getPopup().clearPopupValue();
        }
        Popup.getPopup("Compte créé avec succés");
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(InfosApplication.class.getResource("confirm_subscribe.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Information");
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) subscribeButton.getScene().getWindow();
        currentStage.close();
    }

    private boolean checkEmailValidity(String mail) {
        return Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")
                .matcher(mail)
                .matches();
    }
}
