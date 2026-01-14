import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MembreDAO {
    private Connection connection;

    public MembreDAO(){
        this.connection = DBConnexion.getConnection();
    }

    public  void ajouterUnMembre(Membres membres){
        String sql = "INSERT INTO Membres (nom, prenom, email, dateAdhesion) VALUES (?, ?, ?, ?)";

        try(PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, membres.getNom());
            pst.setString(2, membres.getPrenom());
            pst.setString(3, membres.getEmail());
            pst.setDate(4, new java.sql.Date(membres.getDateAdhesion().getTime()));

            pst.executeUpdate();

            System.out.println("Membre ajouté avec succès");

        } catch (Exception e) {
            throw new RuntimeException("Impossible d'ajouter cette personne " + e.getMessage());
        }
    }

    public List<Membres> listeMembres(){
        List<Membres> membres = new ArrayList<>();

        String sql = "SELECT * FROM Membres";

        try(PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs= pst.executeQuery()) {
            System.out.println("===== LISTE DE TOUS LES MEMBRES");
            while (rs.next()){
                Membres membres1= new Membres(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getDate("dateAdhesion")
                );
                membres.add(membres1);
            }

        } catch (Exception e) {
            throw new RuntimeException("Impossible d'afficher les membres " + e.getMessage());
        }
        return membres;

    }
}
