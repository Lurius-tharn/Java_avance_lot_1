package com.esiee.java_avance_lot_1.dao;

import com.esiee.java_avance_lot_1.model.Bibliotheque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe gère les opérations de base de données liées à la table "livre" dans la base de données "ja_library".
 * Elle permet d'insérer, mettre à jour et récupérer des livres, ainsi que de vérifier l'existence de bibliothèques et de livres.
 *
 * @author pGogniat, tMerlay, dSajous
 */
public class BibliothequeDao {

    private static final String DATABASE_USERNAME = "ja_Library_admin";
    private static final String DATABASE_PASSWORD = "ja_library";

    private static final String INSERT_QUERY = "INSERT INTO ja_library.livre (idLivre, titre, auteur, presentation, parution, colonne, rangee, image, etat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_QUERY = "UPDATE ja_library.livre SET titre=?, auteur=?, presentation=?, parution=?, colonne=?, rangee=?, image=?, etat=? WHERE idLivre=?";
    private static final String SELECT_QUERY = "SELECT * FROM ja_library.livre";

    private static final String BIBLIO_CHECK_QUERY = "SELECT nomBibliotheque from bibliothéque where nomBibliotheque = ?";

    private static final String BIBLIO_CREATE_QUERY = "INSERT INTO ja_library.bibliothéque(idBibliothéque, nomBibliothéque) VALUES (?, ?)";

    private static final String LIVRE_CHECK_QUERY = "SELECT * FROM ja_library.livre WHERE idLivre = ?";

    private static final String LIVRE_CREATE_SCHEMA = "CREATE schema ja_library";


    private static final String LIVRE_CREATE_TABLE = "create table IF NOT EXISTS ja_library.livre (idLivre int GENERATED BY DEFAULT AS IDENTITY primary key, titre MEDIUMTEXT null, auteur MEDIUMTEXT null, presentation MEDIUMTEXT null, parution int null, colonne varchar(45) null, rangee varchar(45) null, image MEDIUMTEXT null, etat varchar(10) default 'false')";
    private static String DATABASE_URL = "jdbc:mysql://localhost:3306/ja_library";

    /**
     * Méthode utilitaire pour définir l'URL de la base de données ( cas test H2).
     *
     * @param url URL de la base de données
     */
    public static void setDatabaseUrl(String url) {
        DATABASE_URL = url;
    }

    /**
     * Crée le schéma "ja_library" dans la base de données et la table "livre" s'il n'existe pas déjà.
     * Cette méthode doit être appelée avant d'utiliser d'autres méthodes de la classe. Méthode d'initialisation dans le cas d'un test
     *
     * @throws SQLException si une erreur survient lors de l'exécution de la requête SQL
     */
    public static void createDatabaseTable() throws SQLException {

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(LIVRE_CREATE_SCHEMA);

            stmt.executeUpdate(LIVRE_CREATE_TABLE);
            stmt.close();


        }
    }

    /**
     * Affiche les détails d'une exception SQLException.
     *
     * @param ex l'exception SQLException à afficher
     */
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

    /**
     * Sélectionne tous les livres de la table "livre" dans la base de données.
     *
     * @return une liste contenant tous les livres sélectionnés
     * @throws SQLException si une erreur survient lors de l'exécution de la requête SQL
     */
    public List<Bibliotheque.Livre> selectBook() throws SQLException {

        List<Bibliotheque.Livre> allLivres = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {

            preparedStatement = connection.prepareStatement(SELECT_QUERY);
            System.out.println(preparedStatement);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
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
            if (preparedStatement != null && rs != null) {
                try {
                    preparedStatement.close();
                    rs.close();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }

        return allLivres;
    }

    /**
     * Insère ou met à jour une liste de livres dans la base de données.
     * Si un livre existe déjà dans la base de données, il sera mis à jour, sinon il sera inséré.
     *
     * @param livres la liste des livres à insérer ou mettre à jour
     * @throws SQLException si une erreur survient lors de l'exécution de la requête SQL
     */
    public void insertOrUpdateBook(List<Bibliotheque.Livre> livres) throws SQLException {

        livres.forEach(livre -> {

            try (Connection connection = DriverManager
                    .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {

                if (checkIfLivreExists(livre)) {
                    PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
                    preparedStatement.setString(1, livre.getTitre());

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


    /**
     * Vérifie si un livre existe dans la base de données.
     *
     * @param livre le livre à vérifier
     * @return true si le livre existe, false sinon
     * @throws SQLException si une erreur survient lors de l'exécution de la requête SQL
     */
    public boolean checkIfLivreExists(Bibliotheque.Livre livre) throws SQLException {
        Integer idLivreBase = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {

            preparedStatement = connection.prepareStatement(LIVRE_CHECK_QUERY);

            preparedStatement.setInt(1, livre.getId());
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                idLivreBase = rs.getInt(1);
            }

        } catch (SQLException e) {
            printSQLException(e);
        } finally {
            if (preparedStatement != null && rs != null) {
                try {
                    preparedStatement.close();
                    rs.close();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }

        return idLivreBase != null;
    }
}
