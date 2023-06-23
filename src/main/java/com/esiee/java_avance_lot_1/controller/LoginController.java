package com.esiee.java_avance_lot_1.controller;

import com.esiee.java_avance_lot_1.dao.BibliothequeDao;
import com.esiee.java_avance_lot_1.singleton.UserSession;
import com.esiee.java_avance_lot_1.vue.HomeApplication;
import com.esiee.java_avance_lot_1.vue.InfosApplication;
import com.esiee.java_avance_lot_1.vue.ResetPasswordApplication;
import com.esiee.java_avance_lot_1.vue.SubscribeApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Button connecterButton;
    @FXML
    private Button subscribeButton;
    @FXML
    private Text errorText;
    @FXML
    private Button resetPasswordButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connecterButton.setOnAction(ActionEvent -> checkLogin());
        usernameInput.focusedProperty().addListener((observable, oldValue, newValue) -> errorText.setVisible(false));
        subscribeButton.setOnAction(ActionEvent -> goToSubscribePage());
        resetPasswordButton.setOnAction(ActionEvent -> goToResetPasswordPage());
    }

    private void goToResetPasswordPage() {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ResetPasswordApplication.class.getResource("reset_password.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Réinitialisation");
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) connecterButton.getScene().getWindow();
        currentStage.close();
    }

    private void goToSubscribePage() {
        usernameInput.clear();
        passwordInput.clear();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(SubscribeApplication.class.getResource("subscribe.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Inscription");
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) connecterButton.getScene().getWindow();
        currentStage.close();
    }

    private void checkLogin() {
        BibliothequeDao bibliothequeDao = new BibliothequeDao();
        boolean isUserValid = false;
        int userRole = 0;

        try{
            isUserValid = bibliothequeDao.checkUserCreds(usernameInput.getText(), passwordInput.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(!usernameInput.getText().isEmpty() && !passwordInput.getText().isEmpty()) {
            if(isUserValid) {
                try {
                    userRole = bibliothequeDao.getUserRole(usernameInput.getText());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                UserSession.getInstance(usernameInput.getText(), userRole);
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource("home.fxml"));
                Scene scene;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setTitle("Bibliothèque virtuelle");
                stage.setScene(scene);
                stage.show();
                Stage currentStage = (Stage) connecterButton.getScene().getWindow();
                currentStage.close();
                System.out.println(UserSession.getInstance().toString());
            } else {
                errorText.setText("Mot de passe ou nom d'utilisateur eronné");
                errorText.setVisible(true);
                usernameInput.clear();
                passwordInput.clear();
            }
        } else {
            errorText.setText("Veuillez remplir les champs de saisies");
            errorText.setVisible(true);
            usernameInput.clear();
            passwordInput.clear();
        }

    }
}
