public class Livres {
    private int id_livre, nbreExemplaire;
    private String titre;
    private String auteur;
    private String cathegories;




    public Livres(String titre,String auteur, String cathegories, int nbreExemplaire ) {
        this.nbreExemplaire = nbreExemplaire;
        this.titre = titre;
        this.auteur = auteur;
        this.cathegories = cathegories;
    }

    public Livres(int id_livre , String titre, String auteur, String cathegories , int nbreExemplaire) {
        this.id_livre = id_livre;
        this.nbreExemplaire = nbreExemplaire;
        this.titre = titre;
        this.auteur = auteur;
        this.cathegories = cathegories;
    }

    public Livres(int id_livre) {
        this.id_livre = id_livre;
    }

    public int getId_livre() {
        return id_livre;
    }

    public void setId_livre(int id_livre) {
        this.id_livre = id_livre;
    }

    public int getNbreExemplaire() {
        return nbreExemplaire;
    }

    public void setNbreExemplaire(int nbreExemplaire) {
        this.nbreExemplaire = nbreExemplaire;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getCathegories() {
        return cathegories;
    }

    public void setCathegories(String cathegories) {
        this.cathegories = cathegories;
    }

    public void afficherInfos() {
        System.out.println("Livre : " + titre +
                " | Auteur : " + auteur +
                " | Catégorie : " + cathegories +
                " | Exemplaires : " + nbreExemplaire);
    }
}
