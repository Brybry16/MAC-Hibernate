import controllers.CoursController;
import controllers.EnseignantController;
import controllers.EtudiantController;
import controllers.MainController;
import models.*;

import java.util.Date;

public class Main {

    private static Etudiant populateDB() throws Exception {
        //Peuplage de profs, chargés de cours
        Enseignant ELS = new Professeur("Eric", "Lefrançois", "ELS");
        Enseignant DGN = new ChargeDeCours("Didier Gern");

        //Ajout des enseignants dans la DB
        System.out.println("Création des enseignants...");
        EnseignantController.create(ELS, DGN);

        //Création des objets cours
        Cours MAC = new Cours("MAC", 45, ELS);
        Cours SER = new Cours("SER", 2, ELS);
        Cours RES = new Cours("RES", 3, DGN);
        Cours GET = new CoursExterieur("GET", 3, DGN, "St-Roch");

        //Ajout des cours dans la DB
        System.out.println("Création des cours...");
        CoursController.create(MAC, SER, GET, RES);

        //Création des objets étudiant
        Etudiant Bryan = new Etudiant("Bryan", "Perroud", new Date(System.currentTimeMillis()));
        Etudiant Toni = new Etudiant("Toni", "Dias", new Date(System.currentTimeMillis()));
        Etudiant Paul = new Etudiant("Paul", "Nta", new Date(System.currentTimeMillis()));
        Etudiant Fred = new Etudiant("Fred", "Fyfer", new Date(System.currentTimeMillis()));

        //Ajout des étudiants dans la DB
        System.out.println("\n==================\n");
        System.out.println("Création des étudiants...");
        EtudiantController.create(Bryan, Toni, Paul, Fred);

        //Ajout de cours à chaque étudiant
        Bryan.ajouterCours(MAC, GET, RES);
        Toni.ajouterCours(GET, SER);
        Paul.ajouterCours(MAC, GET, SER);
        Fred.ajouterCours(MAC, GET, SER);

        //Attribuer grade
        Paul.attribuerGrade(MAC, 'A');
        Toni.attribuerGrade(GET, 'C');

        //Ne fonctionne pas si l'élève ne suit pas le cours
        System.out.println("\n==================\n");
        System.out.println("Exception en cas d'ajout d'une note à un cours non suivi...");
        try {
            Bryan.attribuerGrade(SER, 'F');
        } catch (Exception e) {
            System.out.println("Exception levée : " + e.toString());
        }

        return Fred;
    }

    public static void main(String[] args) throws Exception {

        Etudiant Fred = populateDB();

        //Inscription à des cours, puis suppression
        System.out.println("\n==================\n");
        System.out.println("Affichage de Fred...");
        System.out.println(Fred);

        System.out.println("\n==================\n");
        System.out.println("Suppression de Fred...");
        MainController.getSession().clear();
        EtudiantController.delete(Fred);

        //Affiche les infos de chaque élève (Nom/Prénom, Date d'inscription, Cours suivis)
        System.out.println("\n==================\n");
        System.out.println("Affichage des étudiants...");
        EtudiantController.getAll().forEach(e -> System.out.println(e + "\n"));

        System.out.println("\n==================\n");
        System.out.println("Affichage des cours");
        //Affiche les infos de chaque cours (nom, étudiants)
        CoursController.getAll().forEach(c -> System.out.println(c + "\n"));

        //Cours non crédités
        System.out.println("\n==================\n");
        System.out.println("Affichage des cours non crédités");
        for(Etudiant e : EtudiantController.getAll()) {
            System.out.println("\nCours non crédités de " + e.getPrenom() + " " + e.getNom() + ":");
            e.coursNonCredites().forEach(c -> System.out.println("- " + c.getTitre()));
        }

        //Etudiants en attente
        System.out.println("\n==================\n");
        System.out.println("Affichage des étudiants en attente...");
        for(Cours c : CoursController.getAll()) {
            System.out.println("\nEtudiants en attente dans le cours " + c.getTitre() + ":");
            c.etudiantsEnAttente().forEach(et -> System.out.println("- " + et.getPrenom() + " " + et.getNom()));
        }

        //Enseignants de chaque étudiant
        System.out.println("\n==================\n");
        System.out.println("Affichage des enseignants pour chaque étudiant...");
        for(Etudiant e : EtudiantController.getAll()) {
            System.out.println("\nEnseignants de " + e.getPrenom() + " " + e.getNom() + ":");
            e.getEnseignants().forEach(ens -> System.out.println("- " + ens));
        }

        MainController.shutdown();
    }
}
