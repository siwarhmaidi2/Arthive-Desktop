package edu.esprit.services;

import edu.esprit.entities.Commentaire;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceCommentaire implements IServiceCommentaire<Commentaire> {
    Connection cnx = DataSource.getInstance().getCnx();
    private ServiceUser serviceUser = new ServiceUser();
    private ServicePublication servicePublication = new ServicePublication();

    @Override
    public void add(Commentaire commentaire) {
        String req = "INSERT INTO commentaires (contenu_commentaire, d_ajout_commentaire, id_user, id_publication) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, commentaire.getContenuCommentaire());
            ps.setTimestamp(2, commentaire.getDateAjoutCommentaire());
            ps.setInt(3, commentaire.getUser().getId_user());
            ps.setInt(4, commentaire.getPublication().getId_publication());

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                commentaire.setIdCommentaire(generatedKeys.getInt(1));
            }

            System.out.println("Commentaire added!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Commentaire commentaire) {
        String req = "UPDATE commentaires SET contenu_commentaire = ?, d_ajout_commentaire = ?, id_user = ?, id_publication = ? WHERE id_commentaire = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, commentaire.getContenuCommentaire());
            ps.setTimestamp(2, commentaire.getDateAjoutCommentaire());
            ps.setInt(3, commentaire.getUser().getId_user());
            ps.setInt(4, commentaire.getPublication().getId_publication());
            ps.setInt(5, commentaire.getIdCommentaire());
            ps.executeUpdate();
            System.out.println("Commentaire updated!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM commentaires WHERE id_commentaire = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Commentaire deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Commentaire getOneByID(int id) {
        String req = "SELECT * FROM commentaires WHERE id_commentaire = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int idCommentaire = resultSet.getInt("id_commentaire");
                String contenuCommentaire = resultSet.getString("contenu_commentaire");
                Timestamp dateAjoutCommentaire = resultSet.getTimestamp("d_ajout_commentaire");
                int idUser = resultSet.getInt("id_user");
                int idPublication = resultSet.getInt("id_publication");

                Commentaire commentaire = new Commentaire(idCommentaire, contenuCommentaire, dateAjoutCommentaire,
                        serviceUser.getOneByID(idUser), servicePublication.getOneByID(idPublication));

                return commentaire;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Set<Commentaire> getAll() {
        Set<Commentaire> commentaires = new HashSet<>();

        String query = "SELECT * FROM commentaires";
        try (
                Statement statement = cnx.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int idCommentaire = resultSet.getInt("id_commentaire");
                String contenuCommentaire = resultSet.getString("contenu_commentaire");
                Timestamp dateAjoutCommentaire = resultSet.getTimestamp("d_ajout_commentaire");
                int idUser = resultSet.getInt("id_user");
                int idPublication = resultSet.getInt("id_publication");

                Commentaire commentaire = new Commentaire(idCommentaire, contenuCommentaire, dateAjoutCommentaire,
                        serviceUser.getOneByID(idUser), servicePublication.getOneByID(idPublication));

                commentaires.add(commentaire);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return commentaires;
    }

    public int getCommentsCountForPublication(int publicationId) {
        String query = "SELECT COUNT(*) FROM commentaires WHERE id_publication = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, publicationId);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0; // Return 0 in case of an error
    }
}
