package com.esiee.java_avance_lot_1;


import com.esiee.java_avance_lot_1.dao.BibliothequeDao;
import com.esiee.java_avance_lot_1.model.Bibliotheque;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BibliothequeDAOUTest {
    @InjectMocks
    BibliothequeDao bibliothequeDaoMock = new BibliothequeDao();

    @BeforeAll
    void setUp() throws SQLException, IOException {
//        Properties prop = new Properties();
//        ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        InputStream stream = loader.getResourceAsStream("../../../config.properties");
//        prop.load(stream);
        BibliothequeDao.setDatabaseUrl("jdbc:h2:mem:ja_library;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        BibliothequeDao.createDatabaseTable();

    }


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

        bibliothequeDaoMock.insertBook(livres);

    }
}
