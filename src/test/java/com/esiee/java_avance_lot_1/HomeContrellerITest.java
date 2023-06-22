package com.esiee.java_avance_lot_1;

import com.esiee.java_avance_lot_1.controller.HomeController;
import com.esiee.java_avance_lot_1.model.Bibliotheque;
import com.esiee.java_avance_lot_1.vue.HomeApplication;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(ApplicationExtension.class)
class HomeContrellerITest {


    Stage stage;
    //TODO: clean ca, merge ca, verifier coverage,

    @Start
    public void start(Stage stage) throws Exception {
        HomeController.testEnabled = true;
        this.stage = stage;
        HomeApplication application = new HomeApplication();
        application.start(stage);
    }

    @Test
    void should_contain_button_with_text(FxRobot robot) {
        FxAssert.verifyThat("#validerButton", LabeledMatchers.hasText("Valider"));
    }

    @DisplayName("Devrait ouvrir un fichier xml, et a partir de ses données, chargé le tableau")
    @Test
    void should_open_xml_and_add_book_to_table(FxRobot robot) throws TimeoutException {
        // Vérifiez que selectedFile est initialisé à null avant le clic sur le menu "Open"
        robot.clickOn("#menuFile");
        assertNull(HomeController.getSelectedFile());
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
    @DisplayName("Devrait Afficher un formuylaire quand on clique sur une ligne du tableau")
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
    @DisplayName("evve")
    void name(FxRobot robot) throws TimeoutException {

        // TODO A REFACTO
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
    @DisplayName("modification")
    void modification(FxRobot robot) throws TimeoutException {

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
    void cas_1(FxRobot robot) throws TimeoutException, IOException {
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

}
