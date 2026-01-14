import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAO {
    private Connection connection;

    public EmpruntDAO(){
        this.connection = DBConnexion.getConnection();
    }

    public void AjouterEmprunt(Emprunts emprunts){
        String sql = "INSERT INTO Emprunt(dateEmprunt, dateRetour, dateRetourEffective, id_livre, id_membre) VALUES (? , ?, ?, ?, ?)";

        try(PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setDate(1, new java.sql.Date(emprunts.getDateEmprunt().getTime()));
            pst.setDate(2, new java.sql.Date(emprunts.getDateRetour().getTime()));


            if (emprunts.getDateRetourEffective() != null) {
                pst.setDate(3, new java.sql.Date(emprunts.getDateRetourEffective().getTime()));
            } else {
                pst.setNull(3, java.sql.Types.DATE);
            }

            pst.setInt(4, emprunts.getLivresId().getId_livre());
            pst.setInt(5, emprunts.getMembresId().getId_lecteur()); // Vérifiez le nom de votre méthode getter

            pst.executeUpdate();
            System.out.println("L'emprunt a été enregistré avec succès");

        } catch (Exception e) {
            throw new RuntimeException("Impossible d'enregistrer avec succès " + e.getMessage());
        }
    }

    public  List<Emprunts> afficherEmprunt(){
        List<Emprunts> emprunts = new ArrayList<>();
        String sql = "SELECT * FROM Emprunt";

        try(PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()) {
            while (rs.next()){

                Livres livres = new Livres(rs.getInt("id_livre"));
                Membres membres = new Membres(rs.getInt("id_membre"));
                Emprunts emprunts1 = new Emprunts(
                        rs.getInt("id"),
                        rs.getDate("dateEmprunt"),
                        rs.getDate("dateRetour"),
                        rs.getDate("dateRetourEffective"),
                        membres,
                        livres
                );

                emprunts.add(emprunts1);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return emprunts;
    }
}
