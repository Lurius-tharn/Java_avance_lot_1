package com.esiee.java_avance_lot_1.jaxbe;

import jakarta.xml.bind.*;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import javafx.scene.control.cell.PropertyValueFactory;
import org.xml.sax.SAXException;

import java.io.File;
import java.util.List;

public class XSDUnmarshaller {
    public static void main(String[] args) throws JAXBException {
        File xsdFile = new File("src/main/resources/com/esiee/java_avance_lot_1/xml/Biblio.xsd");

        JAXBContext jc = JAXBContext.newInstance("com.esiee.java_avance_lot_1.jaxbe");

        Unmarshaller unmarshaller = jc.createUnmarshaller();

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        try {
            Schema schema = schemaFactory.newSchema(xsdFile);
            unmarshaller.setSchema(schema);
            Bibliotheque biblio = (Bibliotheque) unmarshaller.unmarshal(new File("src/main/resources/com/esiee/java_avance_lot_1/xml/Biblio.xml"));

            List livres = biblio.getLivre();

            for (Object objLivre : livres) {
                Bibliotheque.Livre livre = (Bibliotheque.Livre) objLivre;
                System.out.println("Livre ");
                System.out.println("Titre   : " + livre.getTitre());
                System.out.println("Auteur  : " + livre.getAuteur().getNomPrenom());
                System.out.println("Parution : " + livre.getParution());
                System.out.println();
            }
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    public static Bibliotheque lireBibliotheque(File selectedFile) throws JAXBException {
        File xsdFile = new File("src/main/resources/com/esiee/java_avance_lot_1/xml/Biblio.xsd");

        JAXBContext jc = JAXBContext.newInstance("com.esiee.java_avance_lot_1.jaxbe");

        Unmarshaller unmarshaller = jc.createUnmarshaller();

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        try {
            Schema schema = schemaFactory.newSchema(xsdFile);
            unmarshaller.setSchema(schema);
            Bibliotheque biblio = (Bibliotheque) unmarshaller.unmarshal(selectedFile);

            List livres = biblio.getLivre();

            for (Object objLivre : livres) {
                Bibliotheque.Livre livre = (Bibliotheque.Livre) objLivre;
                System.out.println("Livre ");
                System.out.println("Titre   : " + livre.getTitre());
                System.out.println("Auteur  : " + livre.getAuteur().getNomPrenom());
                System.out.println("Parution : " + livre.getParution());
                System.out.println();
            }
            return biblio;
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }


}
