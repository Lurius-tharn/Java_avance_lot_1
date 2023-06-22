package com.esiee.java_avance_lot_1;

import com.esiee.java_avance_lot_1.controller.HomeController;
import com.esiee.java_avance_lot_1.dao.WordExport;
import com.esiee.java_avance_lot_1.model.Bibliotheque;
import com.esiee.java_avance_lot_1.vue.HomeApplication;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.NodeQueryUtils;
import org.testfx.util.WaitForAsyncUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.esiee.java_avance_lot_1.fixture.LivreTestBuilder.unLivre;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
class HomeControllerITest {


    @Mock
    WordExport wordExport;

    Stage stage;
    //TODO: clean ca, merge ca, verifier coverage,

    @Start
    public void start(Stage stage) throws Exception {
        HomeController.setTestEnabled(true);
        WordExport.setTestEnabled(true);
        this.stage = stage;
        HomeApplication application = new HomeApplication();
        application.start(stage);
    }

    @Test
    @Order(1)
    void should_contain_button_with_text(FxRobot robot) {
        FxAssert.verifyThat("#validerButton", LabeledMatchers.hasText("Valider"));
    }

    @Test
    @Order(2)
    void should_open_xml_and_add_book_to_table(FxRobot robot) throws TimeoutException {
        // Vérifiez que selectedFile est initialisé à null avant le clic sur le menu "Open"
        robot.clickOn("#menuFile");
        // Simulez la sélection d'un fichier dans le FileChooser
        File fileToSelect = new File("src/main/resources/com/esiee/java_avance_lot_1/xml/test.xml");

        // Appelez la méthode setSelectedFile de votre contrôleur avec le fichier sélectionné
        HomeController.setSelectedFile(fileToSelect);
        WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, () -> {
            return robot.lookup("#menuOpen").match(NodeQueryUtils.isVisible()).tryQuery().isPresent();
        });
        // Cliquez sur le menu "Open"
        robot.clickOn("#menuOpen");

        // Vérifiez que selectedFile est maintenant égal au fichier sélectionné
        assertEquals(fileToSelect, HomeController.getSelectedFile());
        TableView<?> tableView = robot.lookup("#tableXml").queryTableView();
        System.err.println("items" + tableView.getItems().toString());
    }

    @Test
    @Order(3)
    void should_edit_a_row(FxRobot robot) throws TimeoutException {

        robot.clickOn("#menuFile");
        // Simulez la sélection d'un fichier dans le FileChooser
        File fileToSelect = new File("src/main/resources/com/esiee/java_avance_lot_1/xml/test.xml");

        // Appelez la méthode setSelectedFile de votre contrôleur avec le fichier sélectionné
        HomeController.setSelectedFile(fileToSelect);
        WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, () -> {
            return robot.lookup("#menuOpen").match(NodeQueryUtils.isVisible()).tryQuery().isPresent();
        });
        // Cliquez sur le menu "Open"
        robot.clickOn("#menuOpen");

        // Vérifiez que selectedFile est maintenant égal au fichier sélectionné
        assertEquals(fileToSelect, HomeController.getSelectedFile());
        TableView<?> tableView = robot.lookup("#tableXml").queryTableView();

        Assertions.assertTrue(tableView.getItems().size() > 0);


        TableView.TableViewSelectionModel<?> selectionModel = tableView.getSelectionModel();

        int rowIndex = 0;
        selectionModel.select(rowIndex);

        Node node = robot.lookup("#tableXml").nth(0).query();
        robot.clickOn(node);

        FxAssert.verifyThat("#formPane", Node::isVisible);

    }

    @Test
    @Order(4)
    void name(FxRobot robot) throws TimeoutException {

        robot.clickOn("#menuFile");
        // Simulez la sélection d'un fichier dans le FileChooser
        File fileToSelect = new File("src/main/resources/com/esiee/java_avance_lot_1/xml/test.xml");

        // Appelez la méthode setSelectedFile de votre contrôleur avec le fichier sélectionné
        HomeController.setSelectedFile(fileToSelect);
        WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, () -> {
            return robot.lookup("#menuOpen").match(NodeQueryUtils.isVisible()).tryQuery().isPresent();
        });
        // Cliquez sur le menu "Open"
        robot.clickOn("#menuOpen");

        // Vérifiez que selectedFile est maintenant égal au fichier sélectionné
        assertEquals(fileToSelect, HomeController.getSelectedFile());
        TableView<?> tableView = robot.lookup("#tableXml").queryTableView();

        Assertions.assertTrue(tableView.getItems().size() > 0);


        TableView.TableViewSelectionModel<?> selectionModel = tableView.getSelectionModel();

        int rowIndex = 0;
        selectionModel.select(rowIndex);

        Node node = robot.lookup("#tableXml").nth(0).query();
        robot.clickOn(node);

        Bibliotheque.Livre livre = unLivre().build();

        TextField titleInput = robot.lookup("#titleInput").query();
        TextField authorInput = robot.lookup("#authorInput").query();
        TextArea presentationInput = robot.lookup("#presentationInput").query();
        TextField parutionInput = robot.lookup("#parutionInput").query();
        TextField columnInput = robot.lookup("#columnInput").query();
        TextField rangeInput = robot.lookup("#rangeInput").query();
        TextField imageInput = robot.lookup("#imageInput").query();
        CheckBox etatInput = robot.lookup("#etatInput").query();
        int avantajout = tableView.getItems().size();


        titleInput.setText(livre.getTitre());
        authorInput.setText(livre.getAuteur().getNomPrenom());
        presentationInput.setText(livre.getPresentation());
        parutionInput.setText(Integer.toString(livre.getParution()));
        columnInput.setText(Integer.toString(livre.getColonne()));
        rangeInput.setText(Integer.toString(livre.getRangee()));
        imageInput.setText(livre.getImage());
        etatInput.setSelected(livre.isEtat());

        etatInput.setSelected(true);


        Assertions.assertTrue(Objects.equals(titleInput.getText(), "TitreTest"));

        // quand clique sur bouton + ajoute dans lma table view

        robot.clickOn("#add");
        robot.clickOn("#validerButton");

        Assertions.assertTrue(tableView.getItems().size() != avantajout);

    }

    @Test
    @Order(5)
    void ajout_et_suppression(FxRobot robot) throws TimeoutException {

        robot.clickOn("#add");

        robot.clickOn("#titleInput");
        robot.eraseText(10);
        robot.write("test");

        robot.clickOn("#authorInput");
        robot.eraseText(10);
        robot.write("auteur auteur");

        robot.clickOn("#presentationInput");
        robot.eraseText(5);
        robot.write("presentation");

        robot.clickOn("#parutionInput");
        robot.eraseText(1);
        robot.write("2015");

        robot.clickOn("#columnInput");
        robot.eraseText(1);
        robot.write("1");

        robot.clickOn("#rangeInput");
        robot.eraseText(1);
        robot.write("1");

        robot.clickOn("#imageInput");
        robot.eraseText(1);
        robot.write("url");

        CheckBox etatInput = robot.lookup("#etatInput").query();
        robot.clickOn("#etatInput");

        robot.clickOn("#validerButton");

        TextField titleInput = robot.lookup("#titleInput").query();
        TextField authorInput = robot.lookup("#authorInput").query();
        TextArea presentationInput = robot.lookup("#presentationInput").query();
        TextField parutionInput = robot.lookup("#parutionInput").query();
        TextField columnInput = robot.lookup("#columnInput").query();
        TextField rangeInput = robot.lookup("#rangeInput").query();
        TextField imageInput = robot.lookup("#imageInput").query();
        CheckBox etatInpute = robot.lookup("#etatInput").query();

        assertEquals("test", titleInput.getText());
        assertEquals("auteur auteur", authorInput.getText());
        assertEquals("presentation", presentationInput.getText());
        assertEquals("2015", parutionInput.getText());
        assertEquals("1", columnInput.getText());
        assertEquals("1", rangeInput.getText());
        assertEquals("url", imageInput.getText());

        robot.clickOn("#delete");

        TableView<?> tableView = robot.lookup("#tableXml").queryTableView();
        Assertions.assertTrue(tableView.getItems().size() == 0);

    }

    @Test
    @Order(6)
    void sauvegarde_default(FxRobot robot) throws TimeoutException, IOException {

        robot.clickOn("#menuFile");
        // Simulez la sélection d'un fichier dans le FileChooser
        File fileToSelect = new File("src/main/resources/com/esiee/java_avance_lot_1/xml/test.xml");

        // Appelez la méthode setSelectedFile de votre contrôleur avec le fichier sélectionné
        HomeController.setSelectedFile(fileToSelect);
        WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, () -> {
            return robot.lookup("#menuOpen").match(NodeQueryUtils.isVisible()).tryQuery().isPresent();
        });
        // Cliquez sur le menu "Open"
        robot.clickOn("#menuOpen");

        TableView<?> tableView = robot.lookup("#tableXml").queryTableView();


        int rowCount = tableView.getItems().size();

        // Select the desired row index
        int rowIndex = 0;
        TableView.TableViewSelectionModel<?> selectionModel = tableView.getSelectionModel();
        selectionModel.select(rowIndex);

        // Get the node representing the selected row
        Node node = robot.lookup("#tableXml").nth(rowIndex).query();

        // Click on the node
        robot.clickOn(node);

        robot.clickOn("#add");

        robot.clickOn("#titleInput");
        robot.eraseText(10);
        robot.write("test");

        robot.clickOn("#authorInput");
        robot.eraseText(10);
        robot.write("auteur auteur");

        robot.clickOn("#presentationInput");
        robot.eraseText(15);
        robot.write("presentation");

        robot.clickOn("#parutionInput");
        robot.eraseText(10);
        robot.write("2015");

        robot.clickOn("#columnInput");
        robot.eraseText(10);
        robot.write("1");

        robot.clickOn("#rangeInput");
        robot.eraseText(10);
        robot.write("1");

        TextField image =
                robot.lookup("#imageInput").query();
        image.setText("");
        robot.clickOn("#imageInput");
        robot.write("https://i.pinimg.com/originals/1d/f1/26/1df126025aaeb5816a6e97664919ff1c.jpg");

        CheckBox etatInput = robot.lookup("#etatInput").query();
        robot.clickOn("#etatInput");

        robot.clickOn("#validerButton");

        robot.clickOn("#menuEdition");
        WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, () -> {
            return robot.lookup("#saveDefault").match(NodeQueryUtils.isVisible()).tryQuery().isPresent();
        });
        robot.clickOn("#saveDefault");


    }


    @Test
    @Order(7)
    void should_open_infos_application(FxRobot robot) throws TimeoutException, IOException {
        robot.clickOn("#menuAbout");
        // Simulez la sélection d'un fichier dans le FileChooser

        // Appelez la méthode setSelectedFile de votre contrôleur avec le fichier sélectionné
        WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, () -> {
            return robot.lookup("#menuInfos").match(NodeQueryUtils.isVisible()).tryQuery().isPresent();
        });
        // Cliquez sur le menu "Open"
        robot.clickOn("#menuInfos");

        FxAssert.verifyThat("#pgId", LabeledMatchers.hasText("Pierre Gogniat"));

    }

    @Test
    void should_export_as_pdf(FxRobot robot) throws IOException, TimeoutException {

        robot.clickOn("#menuFile");
        // Simulez la sélection d'un fichier dans le FileChooser
        File fileToSelect = new File("src/main/resources/com/esiee/java_avance_lot_1/xml/test.xml");

        // Appelez la méthode setSelectedFile de votre contrôleur avec le fichier sélectionné
        HomeController.setSelectedFile(fileToSelect);
        WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, () -> {
            return robot.lookup("#menuOpen").match(NodeQueryUtils.isVisible()).tryQuery().isPresent();
        });
        // Cliquez sur le menu "Open"
        robot.clickOn("#menuOpen");

        // Vérifiez que selectedFile est maintenant égal au fichier sélectionné
        assertEquals(fileToSelect, HomeController.getSelectedFile());


        robot.clickOn("#menuExport");

        File pdfToExport = new File("src/main/resources/com/esiee/java_avance_lot_1/xml/test.pdf");

        WordExport.setTestFile(pdfToExport);

        WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, () -> robot.lookup("#exportAsPdfButton").match(NodeQueryUtils.isVisible()).tryQuery().isPresent());
        // Cliquez sur le menu "Open"
        robot.clickOn("#exportAsPdfButton");

        assertNotNull(pdfToExport);

    }

    @Test
    void should_export_as_word(FxRobot robot) throws IOException, TimeoutException {

        robot.clickOn("#menuFile");
        // Simulez la sélection d'un fichier dans le FileChooser
        File fileToSelect = new File("src/main/resources/com/esiee/java_avance_lot_1/xml/test.xml");

        // Appelez la méthode setSelectedFile de votre contrôleur avec le fichier sélectionné
        HomeController.setSelectedFile(fileToSelect);
        WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, () -> {
            return robot.lookup("#menuOpen").match(NodeQueryUtils.isVisible()).tryQuery().isPresent();
        });
        // Cliquez sur le menu "Open"
        robot.clickOn("#menuOpen");

        // Vérifiez que selectedFile est maintenant égal au fichier sélectionné
        assertEquals(fileToSelect, HomeController.getSelectedFile());


        robot.clickOn("#menuExport");

        File pdfToExport = new File("src/main/resources/com/esiee/java_avance_lot_1/xml/test.docx");

        WordExport.setTestFile(pdfToExport);

        WaitForAsyncUtils.waitFor(2, TimeUnit.SECONDS, () -> robot.lookup("#exportAsWordButton").match(NodeQueryUtils.isVisible()).tryQuery().isPresent());
        // Cliquez sur le menu "Open"
        robot.clickOn("#exportAsWordButton");

        assertNotNull(pdfToExport);
    }

}
