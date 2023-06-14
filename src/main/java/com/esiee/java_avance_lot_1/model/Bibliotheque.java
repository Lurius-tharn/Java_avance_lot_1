//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source.
// Généré le : 2023.06.11 à 10:21:38 PM CEST
//


package com.esiee.java_avance_lot_1.model;

import jakarta.xml.bind.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>Classe Java pour anonymous complex type.
 *
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
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
 *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *                   &lt;element name="presentation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="parution" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *                   &lt;element name="colonne" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *                   &lt;element name="rangee" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *                   &lt;element name="image" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="etat" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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

    public void setLivre(List<com.esiee.java_avance_lot_1.model.Bibliotheque.Livre> livres) {
        livre = livres;

    }

    /**
     * <p>Classe Java pour anonymous complex type.
     *
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
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
     *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
     *         &lt;element name="presentation" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="parution" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
     *         &lt;element name="colonne" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
     *         &lt;element name="rangee" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
     *         &lt;element name="image" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="etat" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
            "id",
            "presentation",
            "parution",
            "colonne",
            "rangee",
            "image",
            "etat"
    })
    @EqualsAndHashCode
    @Getter
    @Setter
    public static class Livre {


        @XmlElement(required = true)
        protected String titre;
        @XmlElement(required = true)
        protected Bibliotheque.Livre.Auteur auteur;
        @EqualsAndHashCode.Exclude
        @XmlSchemaType(name = "unsignedInt")
        protected int id;
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
        @XmlElement(required = true)
        protected boolean etat;

        public Livre() {
            // Constructeur vide
        }

        public Livre(int id, String titre, com.esiee.java_avance_lot_1.model.Bibliotheque.Livre.Auteur auteur, String presentation, int parution, short colonne, short rangee, String image, boolean etat) {
            this.id = id;
            this.titre = titre;
            this.auteur = auteur;
            this.presentation = presentation;
            this.parution = parution;
            this.colonne = colonne;
            this.rangee = rangee;
            this.image = image;
            this.etat = etat;
        }

        /**
         * Obtient la valeur de la propriété titre.
         *
         * @return possible object is
         * {@link String }
         */
        public String getTitre() {
            return titre;
        }

        /**
         * Définit la valeur de la propriété titre.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setTitre(String value) {
            this.titre = value;
        }

        /**
         * Obtient la valeur de la propriété auteur.
         *
         * @return possible object is
         * {@link Bibliotheque.Livre.Auteur }
         */
        public Bibliotheque.Livre.Auteur getAuteur() {
            return auteur;
        }

        /**
         * Définit la valeur de la propriété auteur.
         *
         * @param value allowed object is
         *              {@link Bibliotheque.Livre.Auteur }
         */
        public void setAuteur(Bibliotheque.Livre.Auteur value) {
            this.auteur = value;
        }

        /**
         * Obtient la valeur de la propriété id.
         */
        public int getId() {
            return id;
        }

        /**
         * Définit la valeur de la propriété id.
         */
        public void setId(int value) {
            this.id = value;
        }

        /**
         * Obtient la valeur de la propriété presentation.
         *
         * @return possible object is
         * {@link String }
         */
        public String getPresentation() {
            return presentation;
        }

        /**
         * Définit la valeur de la propriété presentation.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setPresentation(String value) {
            this.presentation = value;
        }

        /**
         * Obtient la valeur de la propriété parution.
         */
        public int getParution() {
            return parution;
        }

        /**
         * Définit la valeur de la propriété parution.
         */
        public void setParution(int value) {
            this.parution = value;
        }

        /**
         * Obtient la valeur de la propriété colonne.
         */
        public short getColonne() {
            return colonne;
        }

        /**
         * Définit la valeur de la propriété colonne.
         */
        public void setColonne(short value) {
            this.colonne = value;
        }

        /**
         * Obtient la valeur de la propriété rangee.
         */
        public short getRangee() {
            return rangee;
        }

        /**
         * Définit la valeur de la propriété rangee.
         */
        public void setRangee(short value) {
            this.rangee = value;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String value) {
            this.image = value;
        }

        /**
         * Obtient la valeur de la propriété etat.
         */
        public boolean isEtat() {
            return etat;
        }

        /**
         * Définit la valeur de la propriété etat.
         */
        public void setEtat(boolean value) {
            this.etat = value;
        }


        /**
         * <p>Classe Java pour anonymous complex type.
         *
         * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
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
             * Obtient la valeur de la propriété nom.
             *
             * @return possible object is
             * {@link String }
             */
            public String getNom() {
                return nom;
            }

            /**
             * Définit la valeur de la propriété nom.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setNom(String value) {
                this.nom = value;
            }

            /**
             * Obtient la valeur de la propriété prenom.
             *
             * @return possible object is
             * {@link String }
             */
            public String getPrenom() {
                return prenom;
            }

            /**
             * Définit la valeur de la propriété prenom.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setPrenom(String value) {
                this.prenom = value;
            }

            public String getNomPrenom() {
                return prenom + " " + nom;
            }

            @Override
            public String toString() {
                if (!Objects.isNull(nom) && !Objects.isNull(prenom))
                    return getNomPrenom();
                return "";
            }
        }

    }

}
