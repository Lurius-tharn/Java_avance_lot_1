package com.esiee.java_avance_lot_1.dao;

import com.esiee.java_avance_lot_1.model.Bibliotheque;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class XSDUnmarshaller {
    public static void main(String[] args) throws JAXBException {
        File xsdFile = new File("src/main/resources/com/esiee/java_avance_lot_1/xml/Biblio.xsd");

        JAXBContext jc = JAXBContext.newInstance("com.esiee.java_avance_lot_1.model");

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

        JAXBContext jc = JAXBContext.newInstance("com.esiee.java_avance_lot_1.model");

        Unmarshaller unmarshaller = jc.createUnmarshaller();

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        try {
            Schema schema = schemaFactory.newSchema(xsdFile);
            unmarshaller.setSchema(schema);
            Bibliotheque biblio = (Bibliotheque) unmarshaller.unmarshal(selectedFile);

            return biblio;
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void enregistrerBibliotheque(Bibliotheque bibliotheque, File selectedFile) throws JAXBException, FileNotFoundException {
        JAXBContext contextObj = JAXBContext.newInstance(Bibliotheque.class);

        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);


        marshallerObj.marshal(bibliotheque, selectedFile);

    }
}


