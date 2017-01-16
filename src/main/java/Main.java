import controllers.CoursController;
import controllers.EnseignantController;
import controllers.EtudiantController;
import controllers.MainController;
import models.*;

import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {

        //Peuplage de profs, chargés de cours
        Enseignant ELS = new Professeur("Eric", "Lefrançois", "ELS");
        Enseignant JBB = new ChargeDeCours("Jonathan Paul Bischof");
        Enseignant DGN = new Professeur("Didier", "Gern", "DGN");

        //Ajout des enseignants dans la DB
        System.out.println("Création des enseignants...");
        EnseignantController.create(ELS, JBB, DGN);

        //Création des objets cours
        Cours MAC = new Cours("MAC", 45, ELS);
        Cours PDG = new Cours("PDG", 2, JBB);
        Cours GET = new CoursExterieur("GET", 3, DGN, "St-Roch");

        //Ajout des cours dans la DB
        System.out.println("Création des cours...");
        CoursController.create(MAC, PDG, GET);

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
        Bryan.ajouterCours(MAC, GET);
        Toni.ajouterCours(GET, PDG);
        Paul.ajouterCours(MAC, GET, PDG);

        //Inscription à des cours, puis suppression
        System.out.println("\n==================\n");
        System.out.println("Affichage de Fred...");
        Fred.ajouterCours(MAC, GET, PDG);
        System.out.println(Fred);

        System.out.println("\n==================\n");
        System.out.println("Suppression de Fred...");
        MainController.getSession().clear();
        EtudiantController.delete(Fred);

        //Affiche les infos de chaque élève (Nom/Prénom, Date d'inscription, Cours suivis)
        System.out.println("\n==================\n");
        System.out.println("Affichage des étudiants...");
        for(Etudiant e : EtudiantController.getAll()) {
            System.out.println(e + "\n");
        }

        System.out.println("\n==================\n");
        System.out.println("Affichage des cours");
        //Affiche les infos de chaque cours (nom, étudiants)
        for(Cours c : CoursController.getAll()) {
            System.out.println(c + "\n");
        }

        //Attribuer grade
        Paul.attribuerGrade(MAC, 'A');
        Toni.attribuerGrade(GET, 'C');

        //Ne fonctionne pas si l'élève ne suit pas le cours
        System.out.println("\n==================\n");
        System.out.println("Exception en cas d'ajout d'une note à un cours non suivi...");
        try {
            Bryan.attribuerGrade(PDG, 'F');
        } catch (Exception e) {
            System.out.println("Exception levée : " + e.toString());
        }

        //Cours non crédités
        System.out.println("\n==================\n");
        System.out.println("Affichage des cours non crédités");
        for(Etudiant e : EtudiantController.getAll()) {
            System.out.println("\nCours non crédités de " + e.getPrenom() + " " + e.getNom() + ":");
            for(Cours c : e.coursNonCredites()) {
                System.out.println("- " + c.getTitre());
            }
        }

        //Etudiants en attente
        System.out.println("\n==================\n");
        System.out.println("Affichage des étudiants en attente...");
        for(Cours c : CoursController.getAll()) {
            System.out.println("\nEtudiants en attente dans le cours " + c.getTitre() + ":");
            for(Etudiant e : c.etudiantsEnAttente()) {
                System.out.println("- " + e.getPrenom() + " " + e.getNom());
            }
        }

        MainController.shutdown();
    }
}
