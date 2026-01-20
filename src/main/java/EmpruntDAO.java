import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAO {
    private Connection connection;

    public EmpruntDAO(){
        this.connection = DBConnexion.getConnection();
    }

    public void AjouterEmprunt(Emprunts emprunts){

        int idLivre = emprunts.getLivresId().getId_livre();
        int idMembre = emprunts.getMembresId().getId_lecteur();
        int stock = 0;

        String livreExiste = "SELECT id FROM Livres WHERE id = ?";
        String membreExiste = "SELECT id FROM Membres WHERE id = ?";
        String nbreExemplaire = "SELECT nbre_exemplaire FROM Livres WHERE  id = ?";

        try {
            try(PreparedStatement pst = connection.prepareStatement(nbreExemplaire)) {
                pst.setInt(1, idLivre);
                try(ResultSet rs = pst.executeQuery()) {
                    if (rs.next()){
                        stock = rs.getInt("nbre_exemplaire");
                    }else {
                        System.out.println("cet");
                        return;
                    }
                }
                if (stock <= 0) {
                    System.out.println("Désolé, plus d'exemplaires disponibles pour ce livre.");
                    return;
                }
            }catch (SQLException e) {
                throw new RuntimeException("Erreur lors de la vérification du stock : " + e.getMessage());
            }

            try(PreparedStatement pst = connection.prepareStatement(livreExiste)) {
                pst.setInt(1, idLivre);

                if (!pst.executeQuery().next()){
                    System.out.println("Ce livre n'existe pas dans la bibliothque");
                    return;
                }

            }

            try (PreparedStatement pst = connection.prepareStatement(membreExiste)) {
                pst.setInt(1, idMembre);

                if (!pst.executeQuery().next()){
                    System.out.println("Ce membre ne figure pas parmis les membres de la bibliotheque");
                    return;
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur d'enregistrement " + e.getMessage());
        }



        String ajout = "INSERT INTO Emprunt(dateEmprunt, dateRetour, dateRetourEffective, id_livre, id_membre) VALUES (? , ?, ?, ?, ?)";

        try(PreparedStatement pst = connection.prepareStatement(ajout)) {

            pst.setDate(1, new java.sql.Date(emprunts.getDateEmprunt().getTime()));
            pst.setDate(2, new java.sql.Date(emprunts.getDateRetour().getTime()));


            if (emprunts.getDateRetourEffective() != null) {
                pst.setDate(3, new java.sql.Date(emprunts.getDateRetourEffective().getTime()));
            } else {
                pst.setNull(3, java.sql.Types.DATE);
            }

            pst.setInt(4, idLivre);
            pst.setInt(5, idMembre);

            pst.executeUpdate();
            System.out.println("L'emprunt a été enregistré avec succès");

            String actualiseLivre = "UPDATE Livres SET nbre_exemplaire = nbre_exemplaire - 1 WHERE id = ?";
            try (PreparedStatement pstUpdate = connection.prepareStatement(actualiseLivre)) {
                pstUpdate.setInt(1, idLivre);
                pstUpdate.executeUpdate();
               // System.out.println("L'emprunt a été enregistré et le stock a été mis à jour (-1).");
            }

        } catch (Exception e) {
            throw new RuntimeException("Impossible d'enregistrer l'emprunt " + e.getMessage());
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
                        livres,
                        rs.getInt("amande_paye")
                );

                emprunts.add(emprunts1);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return emprunts;
    }

    public void retounerLivre(int emprunId) {
        String sqlSelect = "SELECT id_livre, dateEmprunt, dateRetour, dateRetourEffective, amande_paye FROM Emprunt WHERE id = ?";

        // Variables temporaires pour le calcul
        int idLivre = -1;
        double amendeCalculee = 0;

        try (PreparedStatement pst = connection.prepareStatement(sqlSelect)) {
            pst.setInt(1, emprunId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    if (rs.getDate("dateRetourEffective") != null) {
                        System.out.println("Ce livre a déjà été retourné");
                        return;
                    }

                    idLivre = rs.getInt("id_livre");

                    // --- CALCUL DE L'AMENDE ---
                    // On crée un objet temporaire avec les dates de la BD pour utiliser vos méthodes
                    Emprunts temp = new Emprunts(
                            rs.getDate("dateEmprunt"),
                            rs.getDate("dateRetour"),
                            new java.util.Date(), // Date effective = aujourd'hui
                            null, null,
                            rs.getDouble("amande_paye")
                    );

                    amendeCalculee = temp.calculerAmende(100); // 100 FCFA par jour
                } else {
                    System.out.println("Aucun emprunt n'existe avec cet ID");
                    return;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur: " + e.getMessage());
        }

        // --- MISE À JOUR DE LA BASE DE DONNÉES ---
        String updateEmprunt = "UPDATE Emprunt SET dateRetourEffective = ?, amande_paye = ? WHERE id = ?";
        String updateStock = "UPDATE Livres SET nbre_exemplaire = nbre_exemplaire + 1 WHERE id = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement pstE = connection.prepareStatement(updateEmprunt)) {
                pstE.setDate(1, new java.sql.Date(System.currentTimeMillis()));
                pstE.setDouble(2, amendeCalculee); // Enregistre l'amende calculée
                pstE.setInt(3, emprunId);
                pstE.executeUpdate();
            }

            try (PreparedStatement pstS = connection.prepareStatement(updateStock)) {
                pstS.setInt(1, idLivre);
                pstS.executeUpdate();
            }

            connection.commit();
            System.out.println("Livre retourné avec succès. Amende enregistrée : " + amendeCalculee + " FCFA");

        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ex) {}
            throw new RuntimeException("Erreur lors du retour : " + e.getMessage());
        }
    }

    public List<Emprunts> liveRetarder(){
        List<Emprunts> empruntsList = new ArrayList<>();

        String sql = "SELECT * FROM Emprunt WHERE (dateRetourEffective > dateRetour) OR (dateRetourEffective IS NULL AND dateRetour < CURRENT_DATE)";

        try(PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()) {

            while (rs.next()){
                Livres livres = new Livres(rs.getInt("id_livre"));
                Membres membres = new Membres(rs.getInt("id_membre"));
                Emprunts emprunts = new Emprunts(
                        rs.getInt("id"),
                        rs.getDate("dateEmprunt"),
                        rs.getDate("dateRetour"),
                        rs.getDate("dateRetourEffective"),
                        membres,
                        livres,
                        rs.getInt("amande_paye"));

                empruntsList.add(emprunts);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur: " + e.getMessage());
        }

        return empruntsList;
    }


}
