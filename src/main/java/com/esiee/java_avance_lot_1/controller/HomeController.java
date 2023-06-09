package com.esiee.java_avance_lot_1.controller;

import com.dlsc.formsfx.model.structure.DateField;
import com.esiee.java_avance_lot_1.dao.BibliothequeDao;
import com.esiee.java_avance_lot_1.dao.XSDUnmarshaller;
import com.esiee.java_avance_lot_1.model.Bibliotheque;
import com.esiee.java_avance_lot_1.vue.InfosApplication;
import jakarta.xml.bind.JAXBException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class HomeController implements Initializable {

    public static File selectedFile;
    @FXML
    ImageView imageView;
    @FXML
    private MenuItem menuOpen;
    @FXML
    private MenuItem menuClose;
    @FXML
    private MenuItem menuInfos;
    @FXML
    private TableView<Bibliotheque.Livre> tableXml;
    @FXML
    private TableColumn<Bibliotheque.Livre, String> title;
    @FXML
    private TableColumn<Bibliotheque.Livre.Auteur, String> author;
    @FXML
    private TableColumn<Bibliotheque.Livre, String> presentation;
    @FXML
    private TableColumn<Bibliotheque.Livre, XMLGregorianCalendar> parution;
    @FXML
    private TableColumn<Bibliotheque.Livre, Integer> column;
    @FXML
    private TableColumn<Bibliotheque.Livre, Integer> range;
    @FXML
    private TableColumn<Bibliotheque.Livre, String> image;
    @FXML
    private TextField titleInput;
    @FXML
    private TextField authorInput;
    @FXML
    private TextArea presentationInput;
    @FXML
    private DatePicker parutionInput;
    @FXML
    private TextField columnInput;
    @FXML
    private TextField rangeInput;
    @FXML
    private TextField imageInput;
    @FXML
    private AnchorPane FormPane;
    private ObservableList<Bibliotheque.Livre> bibliothequeList;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button validerButton;
    @FXML
    private MenuItem saveDefault;
    @FXML
    private MenuItem saveAs;
    @FXML
    private Label currentFileName;
    private Bibliotheque bibliotheque;

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
        disableForm(true);
        menuOpen.setOnAction(actionEvent -> {
            openMenu();
        });
        add.setOnAction(actionEvent -> {
            addBook();
        });

        delete.setOnAction(actionEvent -> {
            if (!Objects.isNull(tableXml.getSelectionModel().getSelectedItem()))
                tableXml.getItems().remove(tableXml.getSelectionModel().getSelectedIndex());
        });

        validerButton.setOnAction(actionEvent -> {
            ValidateForm();
        });
        menuClose.setOnAction(actionEvent -> Platform.exit());

        menuInfos.setOnAction(event -> {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(InfosApplication.class.getResource("infos.fxml"));
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setTitle("Infos");
            stage.setScene(scene);
            stage.show();
        });

        tableXml.setOnMouseClicked(event -> {
            Bibliotheque.Livre livre = tableXml.getSelectionModel().getSelectedItem();
            if (!Objects.isNull(livre)) {
                disableForm(false);
                LivreFormMapper(livre);
            } else {
                disableForm(true);

            }


        });

        saveDefault.setOnAction(actionEvent -> {
            bibliotheque.setLivre(tableXml.getItems());
            BibliothequeDao bibliothequeDao = new BibliothequeDao();
            try {
                bibliothequeDao.insertBook(tableXml.getItems());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                XSDUnmarshaller.enregistrerBibliotheque(bibliotheque, selectedFile);
            } catch (JAXBException | FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        saveAs.setOnAction(actionEvent -> {
            saveAsXml();

        });
    }

    private void saveAsXml() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        selectedFile = fileChooser.showSaveDialog(null);
        if (!Objects.isNull(selectedFile)) {
            if (Objects.isNull(bibliotheque)) {
                bibliotheque = new Bibliotheque();
                if (Objects.isNull(bibliotheque.getLivre()))
                    bibliotheque.setLivre(new ArrayList<>());
            }

            bibliotheque.setLivre(tableXml.getItems());


            try {
                XSDUnmarshaller.enregistrerBibliotheque(bibliotheque, selectedFile);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void ValidateForm() {
        List<TextField> textFields = FormPane.getChildren().stream()
                .filter(node -> node instanceof TextField)
                .map(node -> (TextField) node)
                .collect(Collectors.toList());

        List<String> bookFieldValues = textFields.stream()
                .map(TextField::getText)
                .collect(Collectors.toList());

        Bibliotheque.Livre currentBook = tableXml.getSelectionModel().getSelectedItem();
        Bibliotheque.Livre newBook = newBookMapper(bookFieldValues);

        if (!Objects.isNull(currentBook)) {
            tableXml.getItems().set(tableXml.getSelectionModel().getSelectedIndex(), newBook);
        } else {
            tableXml.getItems().add(newBook);

        }
        tableXml.getSelectionModel().select(newBook);
    }

    private void addBook() {
        disableForm(false);
        Bibliotheque.Livre livre = new Bibliotheque.Livre();
        livre.setTitre(null);
        livre.setRangee((short) 0);
        livre.setParution(null);
        livre.setPresentation(null);
        livre.setImage(null);
        livre.setAuteur(new Bibliotheque.Livre.Auteur());
        tableXml.getItems().add(livre);
        tableXml.getSelectionModel().select(livre);
        LivreFormMapper(livre);
    }

    private void openMenu() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner le fichier XML");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                bibliotheque = XSDUnmarshaller.lireBibliotheque(selectedFile);
                bibliothequeList = FXCollections.observableList(bibliotheque.getLivre());
                tableXml.setItems(bibliothequeList);
                currentFileName.setText(selectedFile.getName());
                saveDefault.setVisible(true);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }

    private Bibliotheque.Livre newBookMapper(List<String> bookFieldValues) {

        Bibliotheque.Livre livre = new Bibliotheque.Livre();

        Bibliotheque.Livre.Auteur auteur = new Bibliotheque.Livre.Auteur(bookFieldValues.get(1).split(" ")[0], bookFieldValues.get(1).split(" ")[1]);
        livre.setTitre(bookFieldValues.get(0));
        livre.setAuteur(auteur);
        livre.setPresentation(presentationInput.getText());
        try {
            livre.setParution(DatatypeFactory.newInstance().newXMLGregorianCalendar(parutionInput.getValue().toString()));
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        livre.setColonne(Short.parseShort(bookFieldValues.get(3)));
        livre.setRangee(Short.parseShort(bookFieldValues.get(4)));
        livre.setImage(bookFieldValues.get(5));

        return livre;
    }


    /**
     * Permet de disable un ensemple de champs pour le formulaire
     *
     * @param disable
     */
    private void disableForm(boolean disable) {
        FormPane.getChildren().stream().forEach(node -> node.setDisable(disable));
    }

    /**
     * Permet de mapper les champs du formulaire en fonction de la cellule selectionné
     *
     * @param livre
     */
    private void LivreFormMapper(Bibliotheque.Livre livre) {
        titleInput.setText(livre.getTitre());
        authorInput.setText(livre.getAuteur().toString());
        presentationInput.setText(livre.getPresentation());
        LocalDate parutionLocalDate = LocalDate.of(livre.getParution().getYear(), livre.getParution().getMonth(), livre.getParution().getDay());
        parutionInput.setValue(parutionLocalDate);
        columnInput.setText(String.valueOf(livre.getColonne()));
        rangeInput.setText(String.valueOf(livre.getRangee()));
        imageInput.setText(livre.getImage());
        if (!Objects.isNull(imageInput.getText())) {
            imageView.setImage(new Image(livre.getImage()));
        } else {
            imageView.setImage(null);
        }
    }


    /**
     * Permet de définir quelle attribut de l'objet Livre correspond à quelle
     * Colonne du TableView
     */
    private void LivreTableMapper() {
        title.setCellValueFactory(new PropertyValueFactory<>("Titre"));
        author.setCellValueFactory(new PropertyValueFactory<>("Auteur"));
        presentation.setCellValueFactory(new PropertyValueFactory<>("Presentation"));
        parution.setCellValueFactory(new PropertyValueFactory<>("parution"));
        column.setCellValueFactory(new PropertyValueFactory<>("colonne"));
        range.setCellValueFactory(new PropertyValueFactory<>("rangee"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));

    }
}
