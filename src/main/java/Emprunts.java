import java.util.Date;

public class Emprunts {
    private int id_emprunt;
    private Date dateEmprunt;
    private Date dateRetour;
    private Date dateRetourEffective;
    private Membres membresId;
    private Livres livresId;

    public Emprunts(int id_emprunt, Date dateEmprunt, Date dateRetour, Date dateRetourEffective, Membres membresId, Livres livresId) {
        this.id_emprunt = id_emprunt;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.dateRetourEffective = dateRetourEffective;
        this.membresId = membresId;
        this.livresId = livresId;
    }

    public Emprunts(Date dateEmprunt, Date dateRetour, Date dateRetourEffective, Membres membresId, Livres livresId) {
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.dateRetourEffective = dateRetourEffective;
        this.membresId = membresId;
        this.livresId = livresId;
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

    public void afficherInfos() {
        System.out.println(
         "Emprunts{"+
                " dateEmprunt=" + dateEmprunt +
                ", dateRetour=" + dateRetour +
                ", dateRetourEffective=" + dateRetourEffective +
                ", membresId=" + membresId.getId_lecteur() +
                ", livresId=" + livresId.getId_livre() +
                '}'
        );
    }
}
