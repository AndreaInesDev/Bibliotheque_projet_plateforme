import java.time.LocalDate;
import java.util.Date;

public class Membres {
    private int id_lecteur;
    private  String nom;
    private String prenom;
    private String email;
    private Date dateAdhesion;

    public Membres(int id_lecteur, String nom, String prenom, String email, Date dateAdhesion) {
        this.id_lecteur = id_lecteur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateAdhesion = dateAdhesion;
    }

    public Membres(String nom, String prenom, String email, Date dateAdhesion) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateAdhesion = dateAdhesion;
    }

    public Membres(int id_lecteur) {
        this.id_lecteur = id_lecteur;
    }

    public int getId_lecteur() {
        return id_lecteur;
    }

    public void setId_lecteur(int id_lecteur) {
        this.id_lecteur = id_lecteur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateAdhesion() {
        return dateAdhesion;
    }

    public void setDateAdhesion(Date dateAdhesion) {
        this.dateAdhesion = dateAdhesion;
    }

    public void afficherInfos(){
        System.out.println("Membres{" +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", dateAdhesion=" + dateAdhesion +
                '}');
    }
}
