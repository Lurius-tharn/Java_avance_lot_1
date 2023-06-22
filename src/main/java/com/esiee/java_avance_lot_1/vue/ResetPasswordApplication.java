package com.esiee.java_avance_lot_1.vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ResetPasswordApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InfosApplication.class.getResource("reset_password.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Réinitialisation");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
