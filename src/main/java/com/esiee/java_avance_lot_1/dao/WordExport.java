package com.esiee.java_avance_lot_1.dao;

import com.esiee.java_avance_lot_1.model.Bibliotheque;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.apache.poi.util.Units;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class WordExport {

    private static void addCustomHeadingStyle(XWPFDocument docxDocument, String strStyleId, int headingLevel) {

        CTStyle ctStyle = CTStyle.Factory.newInstance();
        ctStyle.setStyleId(strStyleId);

        CTString styleName = CTString.Factory.newInstance();
        styleName.setVal(strStyleId);
        ctStyle.setName(styleName);

        CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
        indentNumber.setVal(BigInteger.valueOf(headingLevel));

        ctStyle.setUiPriority(indentNumber);

        CTOnOff onoffnull = CTOnOff.Factory.newInstance();
        ctStyle.setUnhideWhenUsed(onoffnull);

        ctStyle.setQFormat(onoffnull);

        CTPPr ppr = CTPPr.Factory.newInstance();
        ppr.setOutlineLvl(indentNumber);
        ctStyle.setPPr(ppr);

        XWPFStyle style = new XWPFStyle(ctStyle);

        XWPFStyles styles = docxDocument.createStyles();

        style.setType(STStyleType.PARAGRAPH);
        styles.addStyle(style);

    }

    // TODO : solution temporaire, a revoir avec PdfConverter

    public void createPdf(List<Bibliotheque.Livre> livres) throws IOException {

        try(PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDFont font = new PDType1Font(PDType1Font.TIMES_ROMAN.getCOSObject());

            PDPageContentStream contents = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, false);

            contents.setFont(font, 12);

            contents.beginText();
            contents.newLineAtOffset(100, 700);
            contents.setLeading(14.5f);

            livres.forEach(livre -> {

                try {
                    contents.showText(livre.getTitre());
                    contents.newLineAtOffset(0, -15);
                    contents.showText(livre.getPresentation());
                    contents.newLineAtOffset(0, -15);
                    contents.showText(livre.getAuteur().getNomPrenom());
                    contents.newLineAtOffset(0, -15);
                    contents.showText(Integer.toString(livre.getParution()));
                    doc.addPage(new PDPage());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            contents.endText();
            contents.close();

            FileChooser fileChooser = new FileChooser();

            //Set extension filter for text files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichier PDF", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                FileOutputStream fos = new FileOutputStream(file);
                doc.save(fos);
                fos.close();
            }
        }
//
//            PDDocument pdfDocument = new PDDocument();
//            PDPageTree pages = pdfDocument.getPages();
//            XWPFDocument document = createWordContent(livres);
//            FileChooser fileChooser = new FileChooser();
//
//            PDFont font = PDType1Font.TIMES_ROMAN;
//
//            //Set extension filter for text files
//            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichier pdf", "*.pdf");
//            fileChooser.getExtensionFilters().add(extFilter);
//
//
//            File file = fileChooser.showSaveDialog(null);
//            for (XWPFParagraph paragraph : document.getParagraphs()) {
//                PDPage page = new PDPage();
//                pages.add(page);
//
//                PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);
//                contentStream.setFont(font, 12);
//
//                float margin = 50;
//                float yStart = page.getMediaBox().getHeight() - margin;
//                float yPosition = yStart;
//                float lineHeight = 14;
//                List<String> lines = List.of(paragraph.getText().split("\n"));
//
//                for (String line : lines) {
//                    if (yPosition < margin) {
//                        contentStream.endText();
//                        contentStream.close();
//                        contentStream = new PDPageContentStream(pdfDocument, page);
//                        contentStream.setFont(font, 12);
//                        yPosition = yStart;
//                    }
//
//                    contentStream.beginText();
//                    contentStream.newLineAtOffset(margin, yPosition);
//                    contentStream.showText(line);
//                    contentStream.endText();
//
//                    yPosition -= lineHeight;
//                }
//
//                contentStream.close();
//            }
//
//            if (file != null) {
//                FileOutputStream fos = new FileOutputStream(file);
//                document.write(fos);
//                pdfDocument.save(fos);
//                document.close();
//                pdfDocument.close();
//                fos.close();
//            }
    }

    public void createWord(List<Bibliotheque.Livre> livres) throws IOException {

        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichier Word", "*.docx");
        fileChooser.getExtensionFilters().add(extFilter);

        XWPFDocument doc = createWordContent(livres);


        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            FileOutputStream fos = new FileOutputStream(file);
            doc.write(fos);
            fos.close();
        }
    }

    private XWPFDocument createWordContent(List<Bibliotheque.Livre> livres) {
        XWPFDocument doc = new XWPFDocument();
        String recapEmp = "Récapitulatifs emprunts";
        String pageGarde = "Page de garde";
        String presLivres = "Présentation des livres";

        doc.createTOC();
        addCustomHeadingStyle(doc, pageGarde, 1);
        addCustomHeadingStyle(doc, presLivres, 1);
        livres.forEach(livre -> addCustomHeadingStyle(doc, livre.getTitre(), 2));
        addCustomHeadingStyle(doc, recapEmp, 1);


        AtomicReference<XWPFParagraph> paragraph = new AtomicReference<>(doc.createParagraph());

        CTP ctP = paragraph.get().getCTP();
        CTSimpleField toc = ctP.addNewFldSimple();
        toc.setInstr("TOC \\h");
        toc.setDirty(STOnOff.TRUE);

        // Partie Page de garde
        AtomicReference<XWPFRun> pageGardeRun = new AtomicReference<>(paragraph.get().createRun());
        paragraph.set(doc.createParagraph());
        pageGardeRun.set(paragraph.get().createRun());
        pageGardeRun.get().setText(pageGarde);
        paragraph.get().setStyle(pageGarde);


        AtomicReference<XWPFRun> run = new AtomicReference<>(paragraph.get().createRun());
        paragraph.set(doc.createParagraph());
        run.set(paragraph.get().createRun());
        run.get().setText(presLivres);
        paragraph.get().setStyle(presLivres);

        livres.forEach(livre -> createBookContent(doc, livre));

        AtomicReference<XWPFRun> recapRun = new AtomicReference<>(paragraph.get().createRun());
        paragraph.set(doc.createParagraph());
        recapRun.set(paragraph.get().createRun());
        recapRun.get().setText(recapEmp);
        paragraph.get().setStyle(recapEmp);

        // Création du tableau
        XWPFTable table = doc.createTable(livres.size() + 1, 4);
        table.setWidth("100%");

        // Style de la première ligne (en-tête du tableau)
        XWPFTableRow headerRow = table.getRow(0);
        headerRow.getCell(0).setText("Titre");
        headerRow.getCell(1).setText("Auteur");
        headerRow.getCell(2).setText("Date de parution");
        headerRow.getCell(3).setText("Présentation");

        // Remplissage des données des livres dans le tableau
        livres.stream().filter(livre -> livre.isEtat()).forEach((livre) -> {
            int index = livres.indexOf(livre);

            XWPFTableRow row = table.getRow(index + 1);
            row.getCell(0).setText(livre.getTitre());
            row.getCell(1).setText(livre.getAuteur().getNomPrenom());
            row.getCell(2).setText(Integer.toString(livre.getParution()));
            row.getCell(3).setText(livre.getPresentation());
        });


        // Créer un en-tête pour chaque page
        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc, sectPr);
        XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
        XWPFParagraph headerParagraph = header.createParagraph();
        XWPFRun headerRun = headerParagraph.createRun();
        headerRun.setText(LocalDate.now() + "                                                           ESIEE-IT");
        return doc;
    }

    private void createBookContent(XWPFDocument doc, Bibliotheque.Livre livre) {
        XWPFParagraph livreParagraph = doc.createParagraph();
        XWPFRun livreRun = livreParagraph.createRun();
        livreRun.addBreak(BreakType.PAGE);

        XWPFParagraph titreParagraph = doc.createParagraph();
        XWPFRun titreRun = titreParagraph.createRun();
        titreRun.setText("Titre : ");
        titreRun.setBold(true);

        XWPFParagraph titreTextParagraph = doc.createParagraph();
        XWPFRun titreTextRun = titreTextParagraph.createRun();
        titreTextRun.setText(livre.getTitre());

        livreRun.setText(livre.getTitre());
        livreParagraph.setStyle(livre.getTitre());
        // Auteur
        XWPFParagraph auteurParagraph = doc.createParagraph();
        XWPFRun auteurDetailRun = auteurParagraph.createRun();
        auteurDetailRun.setText("Auteur : ");
        auteurDetailRun.setBold(true);

        XWPFParagraph auteurTextParagraph = doc.createParagraph();
        XWPFRun auteurTextRun = auteurTextParagraph.createRun();
        auteurTextRun.setText(livre.getAuteur().getNomPrenom());
        // Présentation
        XWPFParagraph presentationDetailParagraph = doc.createParagraph();
        XWPFRun presentationDetailRun = presentationDetailParagraph.createRun();
        presentationDetailRun.setText("Présentation: ");
        presentationDetailRun.setBold(true);

        XWPFParagraph presentationTextParagraph = doc.createParagraph();
        XWPFRun presentationTextRun = presentationTextParagraph.createRun();
        presentationTextRun.setText(livre.getPresentation());

        // Date de parution
        XWPFParagraph dateParutionParagraph = doc.createParagraph();
        XWPFRun dateParutionRun = dateParutionParagraph.createRun();
        dateParutionRun.setText("Date de parution: ");
        dateParutionRun.setBold(true);

        XWPFParagraph dateParutionTextParagraph = doc.createParagraph();
        XWPFRun dateParutionTextRun = dateParutionTextParagraph.createRun();
        dateParutionTextRun.setText(Integer.toString(livre.getParution()));

        // Rangée
        XWPFParagraph rangeeParagraph = doc.createParagraph();
        XWPFRun rangeeRun = rangeeParagraph.createRun();
        rangeeRun.setText("Rangée: ");
        rangeeRun.setBold(true);

        XWPFParagraph rangeeTextParagraph = doc.createParagraph();
        XWPFRun rangeeTextRun = rangeeTextParagraph.createRun();
        rangeeTextRun.setText(Integer.toString(livre.getRangee()));

        // Colonne
        XWPFParagraph colonneParagraph = doc.createParagraph();
        XWPFRun colonneRun = colonneParagraph.createRun();
        colonneRun.setText("Colonne: ");
        colonneRun.setBold(true);

        XWPFParagraph colonneTextParagraph = doc.createParagraph();
        XWPFRun colonneTextRun = colonneTextParagraph.createRun();
        colonneTextRun.setText(Integer.toString(livre.getColonne()));

        // Image
        XWPFParagraph imageParagraph = doc.createParagraph();
        XWPFRun imageRun = imageParagraph.createRun();
        imageRun.addBreak();

        try {
            int format = XWPFDocument.PICTURE_TYPE_PNG;
            String imageName = "Couverture";
            String imageURL = livre.getImage();
            if(!imageURL.isEmpty()){
                URL url = new URL(imageURL);
                BufferedImage bufferedImage = ImageIO.read(url);
                File tempFile = File.createTempFile("temp", ".png");
                ImageIO.write(bufferedImage, "png", tempFile);
                try (FileInputStream fis = new FileInputStream(tempFile)) {
                    imageRun.addPicture(fis, format, imageName, Units.toEMU(200), Units.toEMU(300));
                }
            }

        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }


}
