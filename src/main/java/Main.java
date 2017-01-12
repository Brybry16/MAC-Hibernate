import controllers.CoursController;
import controllers.EtudiantController;
import controllers.MainController;
import models.Cours;
import models.Etudiant;

import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {

        Cours MAC = new Cours("MAC", 45);
        Cours PDG = new Cours("PDG", 2);
        Cours GET = new Cours("GET", 3);

        System.out.println("Creating courses...");
        CoursController.create(MAC, PDG, GET);

        Etudiant Bryan = new Etudiant("Bryan", "Perroud", new Date(System.currentTimeMillis()));
        Etudiant Toni = new Etudiant("Toni", "Dias", new Date(System.currentTimeMillis()));
        Etudiant Fred = new Etudiant("Frederic", "Fyfer", new Date(System.currentTimeMillis()));

        System.out.println("Creating students...");
        EtudiantController.create(Bryan, Toni, Fred);

        Bryan.ajouterCours(MAC, GET);
        Toni.ajouterCours(GET, PDG);
        Fred.ajouterCours(MAC, GET, PDG);

        for(Etudiant e : EtudiantController.getAll()) {
            System.out.println(e + "\n");
        }

        for(Cours c : CoursController.getAll()) {
            System.out.println(c + "\n");
        }

        MainController.shutdown();
    }
}
