import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {
    private Connection connection;

    public LivreDAO() {
        this.connection = DBConnexion.getConnection();
    }

    public void ajouterLivre(Livres livre){
        String sql = "INSERT INTO Livres (titre, auteur, cathegories, nbre_exemplaire) VALUES (? , ? , ? , ?)";

        try(PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, livre.getTitre());
            pst.setString(2, livre.getAuteur());
            pst.setString(3, livre.getCathegories());
            pst.setInt(4, livre.getNbreExemplaire());

            pst.executeUpdate();

            System.out.println("Livre ajouté avec succès");

        }catch (SQLException e){
            System.out.println("Impossible d'ajouter le livre: " + e.getMessage());
        }
    }

    public  List<Livres>  afficherTousLesLivres(){
        List<Livres> livres = new ArrayList<>();
        String sql = "SELECT * FROM Livres";

        try(PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()) {
            while (rs.next()){
                Livres livre = new Livres(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getString("cathegories"),
                        rs.getInt("nbre_exemplaire")
                );
                livres.add(livre);

            }

        } catch (SQLException e) {
            throw new RuntimeException("Impossible d'afficher les livres: " + e.getMessage());
        }

        return livres;
    }

    public  void  supprimerLivreParTitre(String titre) {
        String sql = "DELETE FROM Livres WHERE titre = ?";

        try(PreparedStatement pst = connection.prepareStatement(sql)) {
                pst.setString(1, titre);

                int ligne = pst.executeUpdate();

            System.out.println("le livre " + titre + " a  été supprimé avec succès");
        } catch (SQLException e) {
            throw new RuntimeException("Impossible de supprimer ce livre " + e.getMessage());
        }
    }

    public void rechercheLivre(String nom){
        String sql = "SELECT * FROM Livres WHERE titre = ? OR cathegories = ?";

        try(PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, nom);
            pst.setString(2, nom);

            try (ResultSet rs = pst.executeQuery()){

                boolean trouve = false;
                System.out.println("---Livre(s) trouvé(s)---");
            while (rs.next()){
                trouve = true;
                Livres livre = new Livres(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getString("cathegories"),
                        rs.getInt("nbre_exemplaire")
                );
                livre.afficherInfos();

            }
                if (!trouve){
                    System.out.println("Aucun livre trouvé pour " + nom);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Impossible de trouver ce livre " + e.getMessage());
        }

    }
}
