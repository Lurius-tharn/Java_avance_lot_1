package com.esiee.java_avance_lot_1.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BibliothequeTest extends Bibliotheque {

    private  Livre livre;
    @Test
    void testGetLivre() {

    }

    @Test
    void testSetLivre() {
    }

    @BeforeEach
    void setUp() {
         livre = new Livre(1, "test", new Livre.Auteur("nom", "prenom"), "description", 2011, (short) '1', (short) '1', "",true );
    }


    @Test
    void testGetTitre() {
        Assertions.assertEquals("test", livre.getTitre());

        livre.setTitre("test1");
        Assertions.assertEquals("test1", livre.getTitre());
    }

    @Test
    void testGetAuteur_Nok() {
        Assertions.assertNotEquals(new Livre.Auteur("Bourreau", "Alice"), livre.getAuteur());
    }
    @Test
    void testGetAuteur(){
        Assertions.assertEquals("nom", livre.getAuteur().getNom());
        Assertions.assertEquals("prenom", livre.getAuteur().getPrenom());

        livre.setAuteur(new Livre.Auteur("nom1", "prenom1"));
        Assertions.assertEquals("nom1", livre.getAuteur().getNom());
        Assertions.assertEquals("prenom1", livre.getAuteur().getPrenom());

    }

    @Test
    void testGetPresentation() {
        Assertions.assertEquals("description", livre.getPresentation());

        livre.setPresentation("description1");
        Assertions.assertEquals("description1", livre.getPresentation());
    }

    @Test
    void testGetParution() {
        Assertions.assertEquals(2011 , livre.getParution());

        livre.setParution(2012);
        Assertions.assertEquals(2012 , livre.getParution());

//        livre.setParution(Integer.parseInt("test"));
//        Assertions.assertThNumberFormatException);
    }

    @Test
    void testGetImage() {
        Assertions.assertEquals("" , livre.getImage());

        livre.setImage("t");
        Assertions.assertEquals("t" , livre.getImage());
    }

    @Test
    void testGetEtat() {
        Assertions.assertEquals(true , livre.isEtat());

        livre.setEtat(false);
        Assertions.assertEquals(false , livre.isEtat());
    }
//fff
    @Test
    void testGetRangee() {
        Assertions.assertEquals(1 , livre.getRangee());

        livre.setRangee(Short.parseShort("5"));
        Assertions.assertEquals(5 , livre.getRangee());
    }

    @Test
    void testGetColonne() {
        Assertions.assertEquals(1 , livre.getColonne());

        livre.setColonne(Short.parseShort("5"));
        Assertions.assertEquals(5 , livre.getColonne());
    }
    @Test
    void testSetLivre1() {
    }

}