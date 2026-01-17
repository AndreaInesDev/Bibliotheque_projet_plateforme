import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static LivreDAO livreDAO = new LivreDAO();
    private static  EmpruntDAO empruntDAO = new EmpruntDAO();
    private  static MembreDAO membreDAO = new MembreDAO();
    public static void main(String[] args) {
        DBConnexion.getConnection();

        boolean running = true;

        while (running) {
            System.out.println("\n---- SYSTEME DE GESTION DE BIBLIOTHEQUES----");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Afficher tous les livres");
            System.out.println("3. Rechercher un livre");
            System.out.println("4. Supprimer un livre");
            System.out.println("5. Emprunter un livre");
            System.out.println("6. Liste de tous les emprunts");
            System.out.println("7. Liste de tous les livres en retard");
            System.out.println("8. Retourner un livre");
            System.out.println("9. Ajouter un membre");
            System.out.println("10.Liste de tous les membres");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");
            int reponse = scanner.nextInt();
            scanner.nextLine();


            switch (reponse) {
                case 1:
                    ajoutLivre();
                    break;

                case 2:
                    ListeDeLivres();
                    break;

                case 3:
                    rechercherLivre();
                    break;

                case 4:
                    supprimerLivre();
                    break;

                case  5:
                    empruntLivre();
                    break;

                case 6:
                    afficherTousLesEmprunts();
                    break;

                case 7:
                    livreEnRetard();
                    break;

                case 8:
                    retournerLivre();
                    break;

                case 9:
                    ajouterMembre();
                    break;

                case 10:
                    listeMembre();
                    break;

                case 0 :
                    running = false;
            }
        }
    }

    private  static void  ajoutLivre(){
        System.out.println("titre");
        String titre = scanner.nextLine();

        System.out.println(("auteur"));
        String auteur = scanner.nextLine();

        System.out.println("cathegorie");
        String cathegorie = scanner.nextLine();

        System.out.println("exemplaire");
        int nombre_exemplaire = scanner.nextInt();
        scanner.nextLine();

        Livres livres = new Livres(titre, auteur, cathegorie, nombre_exemplaire);
        livreDAO.ajouterLivre(livres);
    }

    private static void ListeDeLivres(){
        List<Livres> livres = livreDAO.afficherTousLesLivres();
        System.out.println("==== LISTE DE LIVRE ====");
        if (livres.isEmpty()){
            System.out.println("Aucun livre disponible dans la bibliothèque.");
        }else {
            for (Livres l : livres){
                l.afficherInfos();
            }
        }
    }

    private static void supprimerLivre(){
        System.out.println("Veuillez entrez le titre du livre à supprimer");
        String titre1 = scanner.nextLine();
        livreDAO.supprimerLivreParTitre(titre1);
    }

    public static void rechercherLivre(){
        System.out.println("Veuillez entrez le titre ou la cathegorie du livre à rechercher");
        String nom = scanner.nextLine();

        livreDAO.rechercheLivre(nom);
    }

    private static void empruntLivre(){

        System.out.println("ID du membre qui emprunte le livre");
        int id_membre = scanner.nextInt();
        scanner.nextLine();

        System.out.println("ID du livre à emprunter");
        int id_livre = scanner.nextInt();
        scanner.nextLine();

        Livres livres = new Livres(id_livre, "", "", "",0);
        Membres membres = new Membres(id_membre, "", "", "", new Date());

        long date = 24 * 60 * 60 * 1000;

        Date dateAjourdhui = new Date(System.currentTimeMillis());
        Date dateRetour = new Date(System.currentTimeMillis() + 2 * date);

        Emprunts emprunts = new Emprunts(dateAjourdhui, dateRetour, null, membres, livres, 0.0 );

        empruntDAO.AjouterEmprunt(emprunts);


    }

    private static void afficherTousLesEmprunts(){
        List<Emprunts> emprunts = empruntDAO.afficherEmprunt();
        System.out.println("====== LISTE DES EMPRUNTS =====");

        for (Emprunts e : emprunts){
            e.afficherInfos();
        }

    }

    private static void ajouterMembre(){
        System.out.println("Entrez le nom");
        String nom = scanner.nextLine();

        System.out.println("Entrez le prenom");
        String prenom = scanner.nextLine();

        System.out.println("Entrez l'email");
        String email = scanner.nextLine();


        Date dateAdhesion = new Date(System.currentTimeMillis());

        Membres membres = new Membres(nom, prenom, email, dateAdhesion);

        membreDAO.ajouterUnMembre(membres);
    }

    private static void listeMembre(){
        List<Membres> membresList = membreDAO.listeMembres();

        if (membresList.isEmpty()){
            System.out.println("Aucun membre pour le moment");
        }else {
            for (Membres membres : membresList){
                membres.afficherInfos();
            }
        }

    }

    private  static  void  retournerLivre(){
        System.out.println("Veuillez entrez l'ID de votre empprunt");

        int idEmprunt = scanner.nextInt();
        scanner.nextLine();
        empruntDAO.retounerLivre(idEmprunt);
    }

    private static void livreEnRetard(){
        System.out.println("==== liste de tous les emprunts en retard");

        List<Emprunts> empruntsList = empruntDAO.liveRetarder();

        if (empruntsList.isEmpty()){
            System.out.println("Aucun retard n'est enregistré");
            return;
        }

        for (Emprunts emprunts : empruntsList){

           long jours = emprunts.calculerJoursRetard();

           if (jours > 0){
               System.out.println("Livre : " + emprunts.getLivresId().getId_livre());
               System.out.println("Jours de retard : " + jours);
               System.out.println("Amende à payer : " + emprunts.calculerAmende(100) + " FCFA");
           }
            emprunts.afficherInfos();
        }
    }
}
