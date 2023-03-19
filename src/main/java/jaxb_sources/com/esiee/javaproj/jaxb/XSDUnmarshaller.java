package jaxb_sources.com.esiee.javaproj.jaxb;

import jakarta.xml.bind.*;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

import java.io.File;
import java.util.List;

public class XSDUnmarshaller {
    public static void main(String[] args) throws JAXBException {
        File xsdFile = new File("src/main/resources/Biblio.xsd");

        JAXBContext jc = JAXBContext.newInstance("jaxb_sources.com.esiee.javaproj.jaxb");

        Unmarshaller unmarshaller = jc.createUnmarshaller();

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        try {
            Schema schema = schemaFactory.newSchema(xsdFile);
            unmarshaller.setSchema(schema);
            Bibliotheque biblio = (Bibliotheque) unmarshaller.unmarshal(new File("src/main/resources/Biblio.xml"));

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
}
