package com.esiee.java_avance_lot_1.dao;

import com.esiee.java_avance_lot_1.model.Bibliotheque;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.esiee.java_avance_lot_1.fixture.LivreTestBuilder.unLivre;

class WordExportTest {
    @Mock
    private WordExport wordExport;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        wordExport = new WordExport();
        wordExport.setTestEnabled(true);

    }


    @Test
    public void createWord_shouldCreateWordFile() throws IOException {
        // Given
        List<Bibliotheque.Livre> livres = List.of(unLivre().build(), unLivre().avecTitre("Autre titre").build());

        wordExport.setTestFile(new File("src/main/resources/com/esiee/java_avance_lot_1/xml/bib.docx"));

        // When
        wordExport.createWord(livres);

        // Then
        File expectedFile = new File("src/main/resources/com/esiee/java_avance_lot_1/xml/bib.docx");
        Assertions.assertTrue(expectedFile.exists());
    }

    @Test
    @DisplayName(" devrait creer un fichier word avec un sommaire")
    public void createWordContent_cas_1() {
        // Given
        List<Bibliotheque.Livre> livres = List.of(unLivre().build(), unLivre().avecTitre("Autre titre").build());
        wordExport.setTestFile(new File("src/main/resources/com/esiee/java_avance_lot_1/xml/bib.docx"));

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
        wordExport.setTestFile(new File("src/main/resources/com/esiee/java_avance_lot_1/xml/bib.docx"));

        // When
        XWPFDocument document = wordExport.createWordContent(livres);

        // Then
        Assertions.assertNotNull(document.getStyles().getStyle("Récapitulatifs emprunts"));
    }

}
