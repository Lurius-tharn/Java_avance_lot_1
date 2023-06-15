package com.esiee.java_avance_lot_1;


import com.esiee.java_avance_lot_1.dao.BibliothequeDao;
import com.esiee.java_avance_lot_1.model.Bibliotheque;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BibliothequeDAOUTest {
    @InjectMocks
    BibliothequeDao bibliothequeDaoMock;

    @Test
    @DisplayName("devrait inserer une liste de livres")
    void should_insert_multiples_books() throws SQLException {

        List<Bibliotheque.Livre> livres = List.of(Bibliotheque.Livre
                .builder()
                .titre("titre")
                .etat(true)
                .auteur(Bibliotheque.Livre.Auteur
                        .builder()
                        .nom("Robillard")
                        .prenom("Anne")
                        .build())
                .build());


    }
}
