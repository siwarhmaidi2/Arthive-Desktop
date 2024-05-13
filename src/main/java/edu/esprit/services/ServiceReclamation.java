package edu.esprit.services;


import edu.esprit.entities.Groupe;
import edu.esprit.entities.Reclamation;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceReclamation implements IService<Reclamation>{

    Connection cnx = DataSource.getInstance().getCnx();

    public void ajouter2(Reclamation reclamation, int idGroupe) {
        String req = "INSERT INTO `reclamationgroupe`(`id_groupe`, `descReclamation`) VALUES (?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idGroupe); // Utilisez setInt() pour l'ID du groupe
            ps.setString(2, reclamation.getDesc_Reclamation());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    public void supprimer(int id) {
        String req = "DELETE FROM `reclamationgroupe` WHERE `id_reclamation`=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Reclamation with ID " + id + " deleted !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




ServiceGroupe SG = new ServiceGroupe();
    @Override
    public Set<Reclamation> getAll() {
        Set<Reclamation> reclamations = new HashSet<>();

        String req = "SELECT * FROM `reclamationgroupe`";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id = rs.getInt("id_reclamation");
                int groupeId = rs.getInt("nom_groupe");
                String description = rs.getString("descReclamation");
                Groupe groupe =  SG.getOneByID(groupeId);
                        Reclamation reclamation = new Reclamation();
                reclamation.setId_reclamation(id);
                //reclamation.setRec_groupe(String.valueOf(groupe));
                reclamation.setDesc_Reclamation(description);
                reclamations.add(reclamation);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reclamations;
    }

    @Override
    public Reclamation getOneByID(int id) {
        return null;
    }


    @Override
    public void add(Reclamation reclamation) {

    }

    @Override
    public void update(Reclamation reclamation) {

    }

    @Override
    public void delete(int id) {

    }
}
