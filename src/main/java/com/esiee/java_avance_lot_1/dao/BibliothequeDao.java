package com.esiee.java_avance_lot_1.dao;

import com.esiee.java_avance_lot_1.model.Bibliotheque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BibliothequeDao {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/ja_library";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "dada2323";

    private static final String INSERT_QUERY = "INSERT INTO ja_library.livre (idLivre, titre, auteur, presentation, parution, colonne, rangee, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public void insertBook(List<Bibliotheque.Livre> livres) throws SQLException {

        livres.stream().forEach(livre -> {

            try (Connection connection = DriverManager
                    .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {

                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);

                preparedStatement.setInt(1, 0);
                preparedStatement.setString(2, livre.getTitre());
                preparedStatement.setString(3, livre.getAuteur().getNomPrenom());
                preparedStatement.setString(4, livre.getPresentation());
                preparedStatement.setInt(5, livre.getParution());
                preparedStatement.setInt(6, livre.getColonne());
                preparedStatement.setInt(7, livre.getRangee());
                preparedStatement.setString(8, livre.getImage());


                System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                printSQLException(e);
            }
        });


    }

}
