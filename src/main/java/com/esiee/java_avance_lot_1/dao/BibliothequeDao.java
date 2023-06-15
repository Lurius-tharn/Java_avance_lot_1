package com.esiee.java_avance_lot_1.dao;

import com.esiee.java_avance_lot_1.model.Bibliotheque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BibliothequeDao {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/ja_library";
    private static final String DATABASE_USERNAME = "ja_Library_admin";
    private static final String DATABASE_PASSWORD = "ja_library";

    private static final String INSERT_QUERY = "INSERT INTO ja_library.livre (idLivre, titre, auteur, presentation, parution, colonne, rangee, image, etat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_QUERY = "UPDATE ja_library.livre SET titre=?, auteur=?, presentation=?, parution=?, colonne=?, rangee=?, image=?, etat=? WHERE idLivre=?";
    private static final String SELECT_QUERY = "SELECT * FROM livre";

    private static final String BIBLIO_CHECK_QUERY = "SELECT nomBibliotheque from bibliothéque where nomBibliotheque = ?";

    private static final String BIBLIO_CREATE_QUERY = "INSERT INTO ja_library.bibliothéque(idBibliothéque, nomBibliothéque) VALUES (?, ?)";

    private static final String LIVRE_CHECK_QUERY = "SELECT * FROM ja_library.livre WHERE idLivre = ?";

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

    public List<Bibliotheque.Livre> selectBook() throws SQLException {

        List<Bibliotheque.Livre> allLivres = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {

            preparedStatement = connection.prepareStatement(SELECT_QUERY);
            System.out.println(preparedStatement);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("idLivre");
                String titre = rs.getString("titre");
                String rsAuteur = rs.getString("auteur");
                Bibliotheque.Livre.Auteur auteur = new Bibliotheque.Livre.Auteur(rsAuteur.split(" ")[0], rsAuteur.split(" ")[1]);
                String presentation = rs.getString("presentation");
                int parution = rs.getInt("parution");
                short colonne = rs.getShort("colonne");
                short rangee = rs.getShort("rangee");
                String image = rs.getString("image");
                boolean etat = Boolean.parseBoolean(rs.getString("etat"));

                Bibliotheque.Livre rowLivre = Bibliotheque
                        .Livre
                        .builder()
                        .titre(titre)
                        .etat(etat)
                        .auteur(auteur)
                        .colonne(colonne)
                        .id(id)
                        .image(image)
                        .parution(parution)
                        .presentation(presentation)
                        .rangee(rangee)
                        .build();


                allLivres.add(rowLivre);
            }
        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            preparedStatement.close();
            rs.close();
        }

        return allLivres;

    }

    public void insertBook(List<Bibliotheque.Livre> livres) throws SQLException {

        livres.stream().forEach(livre -> {

            try (Connection connection = DriverManager
                    .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {

                if(checkIfLivreExists(livre)){
                    PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);

                    preparedStatement.setString(1, livre.getTitre());
                    preparedStatement.setString(2, livre.getAuteur().getNomPrenom());
                    preparedStatement.setString(3, livre.getPresentation());
                    preparedStatement.setInt(4, livre.getParution());
                    preparedStatement.setInt(5, livre.getColonne());
                    preparedStatement.setInt(6, livre.getRangee());
                    preparedStatement.setString(7, livre.getImage());
                    preparedStatement.setString(8, Boolean.toString(livre.isEtat()));
                    preparedStatement.setInt(9, livre.getId());

                    System.out.println(preparedStatement);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                } else {
                    PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);

                    preparedStatement.setInt(1, 0);
                    preparedStatement.setString(2, livre.getTitre());
                    preparedStatement.setString(3, livre.getAuteur().getNomPrenom());
                    preparedStatement.setString(4, livre.getPresentation());
                    preparedStatement.setInt(5, livre.getParution());
                    preparedStatement.setInt(6, livre.getColonne());
                    preparedStatement.setInt(7, livre.getRangee());
                    preparedStatement.setString(8, livre.getImage());
                    preparedStatement.setString(9, Boolean.toString(livre.isEtat()));

                    System.out.println(preparedStatement);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                }

            } catch (SQLException e) {
                printSQLException(e);
            }
        });
    }
    public void insertBiblio(String nomBiblio) throws SQLException {
        PreparedStatement preparedStatement = null;

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {

            preparedStatement = connection.prepareStatement(BIBLIO_CREATE_QUERY);

            preparedStatement.setString(1, nomBiblio);
            preparedStatement.setInt(2, 0);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            preparedStatement.close();
        }
    }
    public boolean checkBiblio(String nomBiblio) throws SQLException {
        String nomBiblioBase = "";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {

            preparedStatement = connection.prepareStatement(BIBLIO_CHECK_QUERY);

            preparedStatement.setString(1, nomBiblio);
            rs = preparedStatement.executeQuery();

            nomBiblioBase = rs.getString(1);


        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            preparedStatement.close();
            rs.close();
        }

        return nomBiblioBase != null;
    }

    public boolean checkIfLivreExists(Bibliotheque.Livre livre) throws SQLException {
        Integer idLivreBase = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {

            preparedStatement = connection.prepareStatement(LIVRE_CHECK_QUERY);

            preparedStatement.setInt(1, livre.getId());
            rs = preparedStatement.executeQuery();

            if(rs.next()){
                idLivreBase = rs.getInt(1);
            }

        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            preparedStatement.close();
            rs.close();
        }

        return idLivreBase != null;
    }
}
