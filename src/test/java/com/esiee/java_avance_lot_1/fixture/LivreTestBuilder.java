package com.esiee.java_avance_lot_1.fixture;

import com.esiee.java_avance_lot_1.model.Bibliotheque;

public class LivreTestBuilder {

    private String titre = "TitreTEst";
    private Bibliotheque.Livre.Auteur auteur = new Bibliotheque.Livre.Auteur("Robillard", "Anne");
    private int id = 0;
    private String presentation = "Presenbtation fictive pour test";
    private int parution = 0;
    private short colonne = '0';
    private short rangee = '0';
    private String image = "url image";
    private boolean etat = false;

    public static LivreTestBuilder unLivre() {
        return new LivreTestBuilder();
    }


    public LivreTestBuilder avecTitre(String titre) {
        this.titre = titre;
        return this;
    }

    public LivreTestBuilder avecId(int id) {
        this.id = id;
        return this;
    }

    public Bibliotheque.Livre build() {
        return Bibliotheque.Livre
                .builder()
                .id(id)
                .titre(titre)
                .auteur(auteur)
                .etat(etat)
                .image(image)
                .rangee(rangee)
                .presentation(presentation)
                .parution(parution)
                .build();
    }
}
