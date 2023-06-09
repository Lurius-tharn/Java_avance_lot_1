package com.esiee.java_avance_lot_1.dao;

import com.esiee.java_avance_lot_1.model.Bibliotheque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BibliothequeDao {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/ja_library";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "dada2323";

    private static final String INSERT_QUERY = "INSERT INTO ja_library.livre (idLivre, titre, auteur, presentation, parution, colonne, rangee, image) VALUES (?, ?, ?, ?, ?, ?, ?, null)";

    public void insertBook(Bibliotheque.Livre livre) throws SQLException {

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, livre.getTitre());

        }
    }

}
