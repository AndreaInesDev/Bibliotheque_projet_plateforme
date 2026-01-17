import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Emprunts {
    private int id_emprunt;
    private Date dateEmprunt;
    private Date dateRetour;
    private Date dateRetourEffective;
    private Membres membresId;
    private Livres livresId;
    private double amande_paye;

    public Emprunts(int id_emprunt, Date dateEmprunt, Date dateRetour, Date dateRetourEffective, Membres membresId, Livres livresId, double amande_paye) {
        this.id_emprunt = id_emprunt;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.dateRetourEffective = dateRetourEffective;
        this.membresId = membresId;
        this.livresId = livresId;
        this.amande_paye = amande_paye;
    }

    public Emprunts(Date dateEmprunt, Date dateRetour, Date dateRetourEffective, Membres membresId, Livres livresId, double amande_paye) {
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.dateRetourEffective = dateRetourEffective;
        this.membresId = membresId;
        this.livresId = livresId;
        this.amande_paye = amande_paye;
    }

    public int getId_emprunt() {
        return id_emprunt;
    }

    public void setId_emprunt(int id_emprunt) {
        this.id_emprunt = id_emprunt;
    }

    public Date getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(Date dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }

    public Date getDateRetourEffective() {
        return dateRetourEffective;
    }

    public void setDateRetourEffective(Date dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    public Membres getMembresId() {
        return membresId;
    }

    public void setMembresId(Membres membresId) {
        this.membresId = membresId;
    }

    public Livres getLivresId() {
        return livresId;
    }

    public void setLivresId(Livres livresId) {
        this.livresId = livresId;
    }

    public double getAmande_paye() {
        return amande_paye;
    }

    public void setAmande_paye(double amande_paye) {
        this.amande_paye = amande_paye;
    }

    public void afficherInfos() {
        String statut = (dateRetourEffective == null) ? "EN COURS" : "RENDU";
        System.out.println(
                "ID: " + id_emprunt +
                        " | Livre ID: " + livresId.getId_livre() +
                        " | Membre ID: " + membresId.getId_lecteur() +
                        " | Statut: " + statut +
                        " | Amende payée: " + amande_paye + " FCFA"
        );
    }

    public  long calculerJoursRetard(){

        // On convertit la date de retour prévue en LocalDate
        LocalDate echeance = new java.sql.Date(this.dateRetour.getTime()).toLocalDate();
        LocalDate fin;

        if (this.dateRetourEffective != null) {
            // Le livre est déjà revenu : retard fixe
            fin = new java.sql.Date(this.dateRetourEffective.getTime()).toLocalDate();
        } else {
            // Le livre n'est pas encore revenu : retard par rapport à aujourd'hui
            fin = LocalDate.now();
        }

        long jours = ChronoUnit.DAYS.between(echeance, fin);
        return Math.max(0, jours); // Retourne 0 si on est en avance
    }

    public double calculerAmende(double tarifParJour) {
        return calculerJoursRetard() * tarifParJour;
    }
}
