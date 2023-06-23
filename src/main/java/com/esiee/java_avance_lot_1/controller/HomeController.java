package com.esiee.java_avance_lot_1.controller;

import com.esiee.java_avance_lot_1.dao.BibliothequeDao;
import com.esiee.java_avance_lot_1.dao.WordExport;
import com.esiee.java_avance_lot_1.dao.XSDUnmarshaller;
import com.esiee.java_avance_lot_1.model.Bibliotheque;
import com.esiee.java_avance_lot_1.singleton.UserSession;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Contrôleur pour la page d'accueil de l'application.
 *
 * @author pGogniat, tMerlay, dSajous
 */
public class HomeController implements Initializable {
    public static final WordExport wordExport = new WordExport();
    public static boolean testEnabled = false;
    private static File selectedFile;
    @FXML
    public MenuItem exportAsPdfButton;
    @FXML
    public MenuItem exportAsWordButton;
    @FXML
    public CheckBox menuConnecte;
    @FXML
    ImageView imageView;
    @FXML
    private MenuItem menuOpen;
    @FXML
    private MenuItem menuClose;
    @FXML
    private MenuItem menuInfos;
    @FXML
    private MenuItem menuDisconnect;
    @FXML
    private TableView<Bibliotheque.Livre> tableXml;
    @FXML
    private TableColumn<Bibliotheque.Livre, String> title;
    @FXML
    private TableColumn<Bibliotheque.Livre.Auteur, String> author;
    @FXML
    private TableColumn<Bibliotheque.Livre, String> presentation;
    @FXML
    private TableColumn<Bibliotheque.Livre, Integer> parution;
    @FXML
    private TableColumn<Bibliotheque.Livre, Integer> column;
    @FXML
    private TableColumn<Bibliotheque.Livre, Integer> range;
    @FXML
    private TableColumn<Bibliotheque.Livre, String> image;
    @FXML
    private TableColumn<Bibliotheque.Livre, Boolean> etat;
    @FXML
    private TableColumn<Bibliotheque.Livre, Integer> id;
    @FXML
    private TextField idInput;
    @FXML
    private TextField titleInput;
    @FXML
    private TextField authorInput;
    @FXML
    private TextArea presentationInput;
    @FXML
    private TextField parutionInput;
    @FXML
    private TextField columnInput;
    @FXML
    private TextField rangeInput;
    @FXML
    private TextField imageInput;
    @FXML
    private Label usernameField;
    @FXML
    private CheckBox etatInput;
    @FXML
    private AnchorPane formPane;
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
     * Ouvre la fenêtre d'informations.
     */
    private static void openInfos() {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(InfosApplication.class.getResource("infos.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {

            throw new RuntimeException("erreur au niveau de l'ouverture de la page d'infos");
        }
        stage.setTitle("Infos");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Renvoie le fichier sélectionné.
     *
     * @return Le fichier sélectionné.
     */
    public static File getSelectedFile() {
        return selectedFile;
    }

    /**
     * Définit le fichier sélectionné.
     *
     * @param file Le fichier sélectionné.
     */
    public static void setSelectedFile(File file) {
        selectedFile = file;
    }

    public static boolean isTestEnabled() {
        return testEnabled;
    }

    public static void setTestEnabled(boolean testEnabled) {
        HomeController.testEnabled = testEnabled;
    }

    /**
     * Permet de définir des actions/Comportements dès l'instanciation
     * de la classe FormController
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.livreTableMapper();
        // Désactivation du formulaire d'ajout/modification a l'initialisation
        disableForm(true);

        if (!testEnabled)
            usernameField.setText(UserSession.getInstance().getUsername());

        int userRole = UserSession.getInstance().getRole();

        if (userRole == 0) {
            add.setDisable(true);
            delete.setDisable(true);
            saveDefault.setDisable(true);
            saveAs.setDisable(true);
        }

        if (userRole == 1) {
            add.setDisable(false);
            delete.setDisable(false);
            saveDefault.setDisable(false);
            saveAs.setDisable(false);
        }

        menuDisconnect.setOnAction(actionEvent -> disconnect());

        menuOpen.setOnAction(actionEvent -> openMenu());

        add.setOnAction(actionEvent -> addBook());

        delete.setOnAction(actionEvent -> deleteBook());

        validerButton.setOnAction(actionEvent -> validateForm());

        menuClose.setOnAction(actionEvent -> Platform.exit());

        menuInfos.setOnAction(event -> openInfos());

        tableXml.setOnMouseClicked(event -> mapFormToSelectedBook());

        saveDefault.setOnAction(actionEvent -> saveDef());

        saveAs.setOnAction(actionEvent -> saveAsXml());

        menuConnecte.setOnAction(actionEvent -> handleModeConnecte());

        exportAsWordButton.setOnAction(actionEvent -> exportAsWord());

        exportAsPdfButton.setOnAction(actionEvent -> exportAsPDF());
    }


    private void disconnect() {
        UserSession.getInstance().cleanUserSession();
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
        Stage currentStage = (Stage) add.getScene().getWindow();

        currentStage.close();
    }

    /**
     * Exporte les données de la table au format PDF.
     *
     * @throws FileNotFoundException Si le fichier de destination n'est pas trouvé.
     */
    private void exportAsPDF() {
        try {
            wordExport.createPdf(tableXml.getItems());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exporte les données de la table au format Word.
     *
     * @throws FileNotFoundException Si le fichier de destination n'est pas trouvé.
     */
    private void exportAsWord() {
        try {
            wordExport.createWord(tableXml.getItems());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gère l'état du mode connecté.
     */
    private void handleModeConnecte() {
        currentFileName.setText("Base de donnée");
        saveDefault.setVisible(true);
        exportAsPdfButton.setVisible(true);
        exportAsWordButton.setVisible(true);
        if (menuConnecte.isSelected()) {
            id.setVisible(true);
            disableForm(true);
            BibliothequeDao bibliothequeDao = new BibliothequeDao();
            try {
                List<Bibliotheque.Livre> allLivres = bibliothequeDao.selectBook();
                tableXml.setItems(FXCollections.observableList(allLivres));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            tableXml.setItems(null);
            id.setVisible(false);

            disableForm(true);
            currentFileName.setText("");
        }
    }

    /**
     * Mappe une ligne du tableau selectionné vers un Livre
     */
    private void mapFormToSelectedBook() {
        Bibliotheque.Livre livre = tableXml.getSelectionModel().getSelectedItem();
        System.err.println(livre.getId());
        if (!Objects.isNull(livre)) {
            if (UserSession.getInstance().getRole() == 1) {
                disableForm(false);
            }
            livreFormMapper(livre);
        } else {
            disableForm(true);
        }
    }

    /**
     * Supprime une ligne du tableau
     */
    private void deleteBook() {
        if (!Objects.isNull(tableXml.getSelectionModel().getSelectedItem())) {
            tableXml.getItems().remove(tableXml.getSelectionModel().getSelectedIndex());
            if (menuConnecte.isSelected()) {
                BibliothequeDao bibliothequeDao = new BibliothequeDao();
                bibliothequeDao.deleteBook(tableXml.getItems().get(tableXml.getSelectionModel().getSelectedIndex()));
            }

        }
    }

    /**
     * Sauvegarde une bibiothèque par défaut ( soit dans la base de donnée, soit dans  le fichier xml ouvert)
     */
    public void saveDef() {
        Bibliotheque bibiliothequeASauvegarder = new Bibliotheque();
        bibiliothequeASauvegarder.setLivre(tableXml.getItems());
        BibliothequeDao bibliothequeDao = new BibliothequeDao();

        if (menuConnecte.isSelected()) {
            try {
                bibliothequeDao.insertOrUpdateBook(tableXml.getItems());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                XSDUnmarshaller.enregistrerBibliotheque(bibiliothequeASauvegarder, selectedFile);
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Sauvegarde une bibiothèque sous ( soit dans la base de donnée, une bibliothèque est crée/ modifié, soit dans un fichier xml choisi par l'utilisateur)
     */
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
            }
        }
    }

    /**
     * Valide les informations remplies dans le formulaire, et ajoute un nouveau livre dans le tableau
     */
    private void validateForm() {
        List<TextField> textFields = formPane.getChildren().stream()
                .filter(node -> node instanceof TextField)
                .map(node -> (TextField) node)
                .toList();

        List<String> bookFieldValues = textFields.stream()
                .map(TextField::getText)
                .toList();

        Bibliotheque.Livre currentBook = tableXml.getSelectionModel().getSelectedItem();
        Bibliotheque.Livre newBook = newBookMapper(bookFieldValues);

        if (!Objects.isNull(currentBook)) {
            tableXml.getItems().set(tableXml.getSelectionModel().getSelectedIndex(), newBook);
        } else {
            tableXml.getItems().add(newBook);

        }
        tableXml.getSelectionModel().select(newBook);
    }

    /**
     * Ajoute un nouveau livre
     */
    private void addBook() {
        disableForm(false);
        Bibliotheque.Livre livre = new Bibliotheque.Livre();
        livre.setTitre(null);
        livre.setRangee((short) 0);
        livre.setParution(0);
        livre.setPresentation(null);
        livre.setImage(null);
        livre.setAuteur(new Bibliotheque.Livre.Auteur());
        livre.setEtat(false);
        tableXml.getItems().add(livre);
        tableXml.getSelectionModel().select(livre);
        livreFormMapper(livre);
    }

    /**
     * Ouvre un Menu FileChooser qui permet de selectionner un fichier XML
     */
    public void openMenu() {
        menuConnecte.setSelected(false);
        FileChooser fileChooser = new FileChooser();

        if (!testEnabled) {
            fileChooser.setTitle("Sélectionner le fichier XML");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            selectedFile = fileChooser.showOpenDialog(null);
        }


        if (selectedFile != null) {
            try {
                bibliotheque = XSDUnmarshaller.lireBibliotheque(selectedFile);
                ObservableList<Bibliotheque.Livre> bibliothequeList = FXCollections.observableList(bibliotheque.getLivre());
                tableXml.setItems(bibliothequeList);
                currentFileName.setText(selectedFile.getName());
                saveDefault.setVisible(true);
                exportAsPdfButton.setVisible(true);
                exportAsWordButton.setVisible(true);

            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Mappe un nouveau livre
     *
     * @param bookFieldValues les informations du livres remplies par l'utilisateur.
     */
    private Bibliotheque.Livre newBookMapper(List<String> bookFieldValues) {

        Bibliotheque.Livre livre = new Bibliotheque.Livre();

        Bibliotheque.Livre.Auteur auteur = new Bibliotheque.Livre.Auteur(bookFieldValues.get(1).split(" ")[1], bookFieldValues.get(1).split(" ")[0]);
        livre.setTitre(bookFieldValues.get(0));
        livre.setAuteur(auteur);
        livre.setPresentation(presentationInput.getText());
        livre.setParution(Integer.parseInt(bookFieldValues.get(2)));
        livre.setColonne(Short.parseShort(bookFieldValues.get(3)));
        livre.setRangee(Short.parseShort(bookFieldValues.get(4)));
        livre.setImage(bookFieldValues.get(5));
        livre.setEtat(etatInput.isSelected());

        return livre;
    }

    /**
     * Permet de disable un ensemple de champs pour le formulaire
     *
     * @param disable
     */
    private void disableForm(boolean disable) {
        formPane.getChildren().forEach(node -> node.setDisable(disable));
    }

    /**
     * Permet de mapper les champs du formulaire en fonction de la cellule selectionné
     *
     * @param livre
     */
    private void livreFormMapper(Bibliotheque.Livre livre) {
        titleInput.setText(livre.getTitre());
        authorInput.setText(livre.getAuteur().getNomPrenom());
        presentationInput.setText(livre.getPresentation());
        parutionInput.setText(String.valueOf(livre.getParution()));
        columnInput.setText(String.valueOf(livre.getColonne()));
        rangeInput.setText(String.valueOf(livre.getRangee()));
        imageInput.setText(livre.getImage());
        if (!Objects.isNull(imageInput.getText()) && !imageInput.getText().isEmpty()) {
            imageView.setImage(new Image(livre.getImage()));
        } else {
            imageView.setImage(null);
        }
        etatInput.setSelected(livre.isEtat());
    }

    /**
     * Permet de définir quelle attribut de l'objet Livre correspond à quelle
     * Colonne du TableView
     */
    private void livreTableMapper() {
        title.setCellValueFactory(new PropertyValueFactory<>("Titre"));
        author.setCellValueFactory(new PropertyValueFactory<>("Auteur"));
        presentation.setCellValueFactory(new PropertyValueFactory<>("Presentation"));
        parution.setCellValueFactory(new PropertyValueFactory<>("parution"));
        column.setCellValueFactory(new PropertyValueFactory<>("colonne"));
        range.setCellValueFactory(new PropertyValueFactory<>("rangee"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
    }
}
