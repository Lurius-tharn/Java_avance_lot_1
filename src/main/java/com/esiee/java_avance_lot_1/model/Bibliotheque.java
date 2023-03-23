//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source.
// G�n�r� le : 2023.03.23 � 10:22:50 AM CET
//


package com.esiee.java_avance_lot_1.model;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Classe Java pour anonymous complex type.
 *
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="livre" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="titre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="auteur">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="prenom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="presentation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="parution" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *                   &lt;element name="colonne" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *                   &lt;element name="rangee" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *                   &lt;element name="image" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "livre"
})
@XmlRootElement(name = "bibliotheque")
public class Bibliotheque {

    @XmlElement(required = true)
    protected List<Bibliotheque.Livre> livre;

    /**
     * Gets the value of the livre property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the livre property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLivre().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Bibliotheque.Livre }
     */
    public List<Bibliotheque.Livre> getLivre() {
        if (livre == null) {
            livre = new ArrayList<Bibliotheque.Livre>();
        }
        return this.livre;
    }

    public void setLivre(List<Bibliotheque.Livre> livres) {
        livre = livres;

    }

    /**
     * <p>Classe Java pour anonymous complex type.
     *
     * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="titre" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="auteur">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="prenom" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="presentation" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="parution" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
     *         &lt;element name="colonne" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
     *         &lt;element name="rangee" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
     *         &lt;element name="image" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "titre",
            "auteur",
            "presentation",
            "parution",
            "colonne",
            "rangee",
            "image"
    })
    public static class Livre {

        @XmlElement(required = true)
        protected String titre;
        @XmlElement(required = true)
        protected Bibliotheque.Livre.Auteur auteur;
        @XmlElement(required = true)
        protected String presentation;
        @XmlSchemaType(name = "unsignedShort")
        protected int parution;
        @XmlSchemaType(name = "unsignedByte")
        protected short colonne;
        @XmlSchemaType(name = "unsignedByte")
        protected short rangee;
        @XmlElement(required = true)
        protected String image;

        public Livre() {
            // Constructeur vide
        }

        public Livre(String titre, Auteur auteur, String presentation, int parution, short colonne, short rangee, String image) {
            this.titre = titre;
            this.auteur = auteur;
            this.presentation = presentation;
            this.parution = parution;
            this.colonne = colonne;
            this.rangee = rangee;
            this.image = image;
        }

        /**
         * Obtient la valeur de la propri�t� titre.
         *
         * @return possible object is
         * {@link String }
         */
        public String getTitre() {
            return titre;
        }

        /**
         * D�finit la valeur de la propri�t� titre.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setTitre(String value) {
            this.titre = value;
        }

        /**
         * Obtient la valeur de la propri�t� auteur.
         *
         * @return possible object is
         * {@link Bibliotheque.Livre.Auteur }
         */
        public Bibliotheque.Livre.Auteur getAuteur() {
            return auteur;
        }

        /**
         * D�finit la valeur de la propri�t� auteur.
         *
         * @param value allowed object is
         *              {@link Bibliotheque.Livre.Auteur }
         */
        public void setAuteur(Bibliotheque.Livre.Auteur value) {
            this.auteur = value;
        }

        /**
         * Obtient la valeur de la propri�t� presentation.
         *
         * @return possible object is
         * {@link String }
         */
        public String getPresentation() {
            return presentation;
        }

        /**
         * D�finit la valeur de la propri�t� presentation.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setPresentation(String value) {
            this.presentation = value;
        }

        /**
         * Obtient la valeur de la propri�t� parution.
         */
        public int getParution() {
            return parution;
        }

        /**
         * D�finit la valeur de la propri�t� parution.
         */
        public void setParution(int value) {
            this.parution = value;
        }

        /**
         * Obtient la valeur de la propri�t� colonne.
         */
        public short getColonne() {
            return colonne;
        }

        /**
         * D�finit la valeur de la propri�t� colonne.
         */
        public void setColonne(short value) {
            this.colonne = value;
        }

        /**
         * Obtient la valeur de la propri�t� rangee.
         */
        public short getRangee() {
            return rangee;
        }

        /**
         * D�finit la valeur de la propri�t� rangee.
         */
        public void setRangee(short value) {
            this.rangee = value;
        }

        /**
         * Obtient la valeur de la propri�t� image.
         *
         * @return possible object is
         * {@link String }
         */
        public String getImage() {
            return image;
        }

        /**
         * D�finit la valeur de la propri�t� image.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setImage(String value) {
            this.image = value;
        }


        /**
         * <p>Classe Java pour anonymous complex type.
         *
         * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="prenom" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "nom",
                "prenom"
        })
        public static class Auteur {


            @XmlElement(required = true)
            protected String nom;
            @XmlElement(required = true)
            protected String prenom;

            public Auteur() {

            }

            public Auteur(String nom, String prenom) {
                this.nom = nom;
                this.prenom = prenom;
            }

            /**
             * Obtient la valeur de la propri�t� nom.
             *
             * @return possible object is
             * {@link String }
             */
            public String getNom() {
                return nom;
            }

            /**
             * D�finit la valeur de la propri�t� nom.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setNom(String value) {
                this.nom = value;
            }

            /**
             * Obtient la valeur de la propri�t� prenom.
             *
             * @return possible object is
             * {@link String }
             */
            public String getPrenom() {
                return prenom;
            }

            /**
             * D�finit la valeur de la propri�t� prenom.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setPrenom(String value) {
                this.prenom = value;
            }

            /**
             * Obtient la valeur des prppriétés nom et prenom
             *
             * @return possible object is
             * {@link String }
             */
            public String getNomPrenom() {
                return prenom + " " + nom;
            }
        }

    }

}
