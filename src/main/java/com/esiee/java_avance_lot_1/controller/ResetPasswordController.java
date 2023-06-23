package com.esiee.java_avance_lot_1.controller;

import com.esiee.java_avance_lot_1.dao.BibliothequeDao;
import com.esiee.java_avance_lot_1.singleton.Popup;
import com.esiee.java_avance_lot_1.vue.ConfirmSubApplication;
import com.esiee.java_avance_lot_1.vue.LoginApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ResetPasswordController implements Initializable {
    @FXML
    private TextField mailInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private PasswordField passwordConfirmInput;
    @FXML
    private Button confirmResetButton;
    @FXML
    private Button backToLoginPageButton;
    @FXML
    private Text errorText;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirmResetButton.setOnAction(ActionEvent -> resetPassword());
        backToLoginPageButton.setOnAction(ActionEvent -> goToLoginPage());
    }

    private void goToLoginPage() {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) confirmResetButton.getScene().getWindow();
        currentStage.close();
    }

    private void resetPassword() {
        BibliothequeDao bibliothequeDao = new BibliothequeDao();
        int res = 0;

        if(checkEmailValidity(mailInput.getText())) {
            if(!passwordInput.getText().isEmpty() && passwordInput.getText().equals(passwordConfirmInput.getText())) {
                try {
                    res = bibliothequeDao.updateMail(mailInput.getText(), passwordInput.getText());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if(res==0){
                    errorText.setText("Mail non existant");
                    errorText.setVisible(true);
                } else {
                    if (Popup.getPopup() != null) {
                        Popup.getPopup().clearPopupValue();
                    }
                    Popup.getPopup("Mot de passe réinitialisé avec succés");
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(ConfirmSubApplication.class.getResource("confirm_subscribe.fxml"));
                    Scene scene;
                    try {
                        scene = new Scene(fxmlLoader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage.setTitle("Confirmation");
                    stage.setScene(scene);
                    stage.show();
                    Stage currentStage = (Stage) confirmResetButton.getScene().getWindow();
                    currentStage.close();
                }
            } else {
                errorText.setText("Champs de MDP différents");
                errorText.setVisible(true);
            }
        } else {
            errorText.setText("Mail invalide");
            errorText.setVisible(true);
        }

    }

    private boolean checkEmailValidity(String mail) {
        return Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
                .matcher(mail)
                .matches();
    }
}
