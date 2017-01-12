import controllers.CoursController;
import controllers.EtudiantController;
import controllers.MainController;
import models.Cours;
import models.Etudiant;

import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {

        //Création des objets cours
        Cours MAC = new Cours("MAC", 45);
        Cours PDG = new Cours("PDG", 2);
        Cours GET = new Cours("GET", 3);

        //Ajout des cours dans la DB
        System.out.println("Creating courses...");
        CoursController.create(MAC, PDG, GET);

        //Création des objets étudiant
        Etudiant Bryan = new Etudiant("Bryan", "Perroud", new Date(System.currentTimeMillis()));
        Etudiant Toni = new Etudiant("Toni", "Dias", new Date(System.currentTimeMillis()));
        Etudiant Paul = new Etudiant("Paul", "Nta", new Date(System.currentTimeMillis()));

        //Ajout des étudiants dans la DB
        System.out.println("Creating students...");
        EtudiantController.create(Bryan, Toni, Paul);

        //Ajout de cours à chaque étudiant
        Bryan.ajouterCours(MAC, GET);
        Toni.ajouterCours(GET, PDG);
        Paul.ajouterCours(MAC, GET, PDG);

        //Affiche les infos de chaque élève (Nom/Prénom, Date d'inscription, Cours suivis)
        for(Etudiant e : EtudiantController.getAll()) {
            System.out.println(e + "\n");
        }

        //Affiche les infos de chaque cours (nom, étudiants)
        for(Cours c : CoursController.getAll()) {
            System.out.println(c + "\n");
        }

        //Attribuer grade
        Paul.attribuerGrade(MAC, 'A');
        Toni.attribuerGrade(GET, 'C');

        //Ne fonctionne pas si l'élève ne suit pas le cours
        try {
            Bryan.attribuerGrade(PDG, 'F');
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Cours non crédités
        for(Etudiant e : EtudiantController.getAll()) {
            System.out.println("\nCours non crédités de " + e.getPrenom() + " " + e.getNom() + ":");
            for(Cours c : e.coursNonCredites()) {
                System.out.println("- " + c.getTitre());
            }
        }

        //Etudiants en attente
        for(Cours c : CoursController.getAll()) {
            System.out.println("\nEtudiants en attente dans le cours " + c.getTitre() + ":");
            for(Etudiant e : c.etudiantsEnAttente()) {
                System.out.println("- " + e.getPrenom() + " " + e.getNom());
            }
        }

        MainController.shutdown();
    }
}
