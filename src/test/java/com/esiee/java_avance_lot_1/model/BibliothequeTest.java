package com.esiee.java_avance_lot_1.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.esiee.java_avance_lot_1.fixture.LivreTestBuilder.unLivre;

class BibliothequeTest extends Bibliotheque {

    private Livre livre;

    @BeforeEach
    void setUp() {
        livre = unLivre().build();
    }


    @Test
    void testGetTitre() {

        livre.setTitre("test1");
        Assertions.assertEquals("test1", livre.getTitre());
    }

    @Test
    void testGetAuteur_Nok() {
        Assertions.assertNotEquals(new Livre.Auteur("Bourreau", "Alice"), livre.getAuteur());
    }

    @Test
    void testGetAuteur() {

        livre.setAuteur(new Livre.Auteur("nom1", "prenom1"));
        Assertions.assertEquals("nom1", livre.getAuteur().getNom());
        Assertions.assertEquals("prenom1", livre.getAuteur().getPrenom());

    }

    @Test
    void testGetPresentation() {

        livre.setPresentation("description1");
        Assertions.assertEquals("description1", livre.getPresentation());
    }

    @Test
    void testGetParution() {

        livre.setParution(2012);
        Assertions.assertEquals(2012, livre.getParution());

//        livre.setParution(Integer.parseInt("test"));
//        Assertions.assertThNumberFormatException);
    }

    @Test
    void testGetImage() {

        livre.setImage("t");
        Assertions.assertEquals("t", livre.getImage());
    }

    @Test
    void testGetEtat() {

        livre.setEtat(false);
        Assertions.assertEquals(false, livre.isEtat());
    }

    //fff
    @Test
    void testGetRangee() {

        livre.setRangee(Short.parseShort("5"));
        Assertions.assertEquals(5, livre.getRangee());
    }

    @Test
    void testGetColonne() {

        livre.setColonne(Short.parseShort("5"));
        Assertions.assertEquals(5, livre.getColonne());
    }

    @Test
    void testSetLivre1() {
    }

}
