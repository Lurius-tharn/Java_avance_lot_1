package com.esiee.java_avance_lot_1.dao;


import com.esiee.java_avance_lot_1.model.Bibliotheque;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.esiee.java_avance_lot_1.fixture.LivreTestBuilder.unLivre;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BibliothequeDAOUTest {
    @InjectMocks
    BibliothequeDao bibliothequeDaoMock = new BibliothequeDao();

    @BeforeAll
    void setUp() throws SQLException, IOException {
//        Properties prop = new Properties();
//        ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        InputStream stream = loader.getResourceAsStream("../../../config.properties");
//        prop.load(stream);
        BibliothequeDao.setDatabaseUrl("jdbc:h2:mem:ja_library;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;DATABASE_TO_UPPER=false");
        BibliothequeDao.createDatabaseTable();

    }

    @Test
    @DisplayName("devrait inserer une liste de livres non existant en bdd")
    void should_insert_multiples_books() throws SQLException {

        List<Bibliotheque.Livre> livres = List.of(unLivre().avecTitre("titre A").avecId(0).build());


        bibliothequeDaoMock.insertOrUpdateBook(livres);


        List<Bibliotheque.Livre> livresDatabase = bibliothequeDaoMock.selectBook();

        Assertions.assertEquals(1, livresDatabase.size());


    }

    @Test
    @DisplayName("devrait mettre a jour des livres")
    void should_update_book() throws SQLException {

        List<Bibliotheque.Livre> livresDatabase = bibliothequeDaoMock.selectBook();
        String old = livresDatabase.get(0).getTitre();

        livresDatabase.get(0).setTitre("Nouveau titre");
        bibliothequeDaoMock.insertOrUpdateBook(livresDatabase);

        List<Bibliotheque.Livre> livresDatabaseUp = bibliothequeDaoMock.selectBook();


        Assertions.assertNotEquals(livresDatabaseUp.get(0).getTitre(), old);


    }

}
