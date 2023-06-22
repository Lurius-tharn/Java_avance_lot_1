package com.esiee.java_avance_lot_1.dao;

import com.esiee.java_avance_lot_1.model.Bibliotheque;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.esiee.java_avance_lot_1.fixture.LivreTestBuilder.unLivre;

class WordExportTest {
    @Mock
    private WordExport wordExport;

    @BeforeEach
    void setUp() {
        wordExport = new WordExport();
        wordExport.setTestEnabled(true);

    }


    @Test
    void createWord_shouldCreateWordFile() throws IOException {
        // Given
        List<Bibliotheque.Livre> livres = List.of(unLivre().build(), unLivre().avecTitre("Autre titre").build());

        wordExport.setTestFile(new File("src/main/resources/com/esiee/java_avance_lot_1/xml/test.docx"));

        // When
        wordExport.createWord(livres);

        // Then
        File expectedFile = new File("src/main/resources/com/esiee/java_avance_lot_1/xml/test.docx");
        Assertions.assertTrue(expectedFile.exists());
    }

    @Test
    @DisplayName(" devrait creer un fichier word avec un sommaire")
    void createWordContent_cas_1() {
        // Given
        List<Bibliotheque.Livre> livres = List.of(unLivre().build(), unLivre().avecTitre("Autre titre").build());
        wordExport.setTestFile(new File("src/main/resources/com/esiee/java_avance_lot_1/xml/test.docx"));

        // When
        XWPFDocument document = wordExport.createWordContent(livres);

        // Then
        Assertions.assertNotNull(document);
        Assertions.assertNotNull(document.getStyles().getStyle("Page de garde"));
        Assertions.assertNotNull(document.getStyles().getStyle("Présentation des livres"));
        Assertions.assertNotNull(document.getStyles().getStyle("TitreTest"));
        Assertions.assertNotNull(document.getStyles().getStyle("Autre titre"));
        Assertions.assertNotNull(document.getStyles().getStyle("Récapitulatifs emprunts"));

    }

    @Test
    @DisplayName("devrait creer un fichier word avec des livres empruntés")
    void createWordContent_cas_2() {
        List<Bibliotheque.Livre> livres = List.of(unLivre().avecEtat(true).build(), unLivre().avecTitre("Autre titre").avecEtat(true).build());
        wordExport.setTestFile(new File("src/main/resources/com/esiee/java_avance_lot_1/xml/test.docx"));

        // When
        try (XWPFDocument document = wordExport.createWordContent(livres)) {

            // Then
            Assertions.assertNotNull(document.getStyles().getStyle("Récapitulatifs emprunts"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
