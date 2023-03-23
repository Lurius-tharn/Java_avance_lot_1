package com.esiee.java_avance_lot_1.controller;

import com.esiee.java_avance_lot_1.jaxbe.Bibliotheque;
import com.esiee.java_avance_lot_1.jaxbe.XSDUnmarshaller;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HoeController implements Initializable {

    @FXML
    private MenuItem menuOpen;
    @FXML
    private TableView<Bibliotheque.Livre> tableXml;
    @FXML
    private TableColumn<Bibliotheque.Livre, String> title;
    @FXML
    private TableColumn<Bibliotheque.Livre, String> author;
    @FXML
    private TableColumn<Bibliotheque.Livre, String> presentation;
    @FXML
    private TableColumn<Bibliotheque.Livre, String> parution;
    @FXML
    private TableColumn<Bibliotheque.Livre, String> column;
    @FXML
    private TableColumn<Bibliotheque.Livre, String> range;
    @FXML
    private TableColumn<Bibliotheque.Livre, String> image;

    /**
     * Permet de définir des actions/Comportements dès l'instanciation
     * de la classe FormController
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.LivreTableMapper();
        menuOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Sélectionner le fichier XML");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
                File selectedFile = fileChooser.showOpenDialog(null);
                if (selectedFile != null) {
                    try {
                        Bibliotheque bibliotheque = XSDUnmarshaller.lireBibliotheque(selectedFile);
                        ObservableList<Bibliotheque.Livre> bibliothequeList = FXCollections.observableList(bibliotheque.getLivre());

                        tableXml.setItems(bibliothequeList);

                    } catch (JAXBException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        tableXml.setOnMouseClicked(event -> {
            // Récupération de la ligne sélectionnée
            Bibliotheque.Livre livre = tableXml.getSelectionModel().getSelectedItem();
            System.out.println(livre);

        });
    }


    /**
     * Permet de définir quelle attribut de l'objet Person correspond à quelle
     * Colonne du TableView
     */
    private void LivreTableMapper() {
        title.setCellValueFactory(new PropertyValueFactory<>("titre"));
        author.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        presentation.setCellValueFactory(new PropertyValueFactory<>("presentation"));
        parution.setCellValueFactory(new PropertyValueFactory<>("parution"));
        column.setCellValueFactory(new PropertyValueFactory<>("colonne"));
        range.setCellValueFactory(new PropertyValueFactory<>("rangee"));

    }
}
