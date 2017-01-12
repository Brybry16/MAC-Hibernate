import java.util.Date;

public class Main {
    public static void main(String[] args) {
        System.out.println("Creating students...");
        EtudiantController.create(new Etudiant[] {
                new Etudiant("Frederic", "Fyfer", new Date(System.currentTimeMillis())),
                new Etudiant("Tony", "Stark", new Date(System.currentTimeMillis())),
                new Etudiant("Fanny", "Labrune", new Date(System.currentTimeMillis()))
        });

        System.out.println("Creating courses...");
        CoursController.create(new Cours[]
                {
                        new Cours("MAC", 45),
                        new Cours("PDG", 2),
                        new Cours("GET", 3)
                });


        Etudiant[] studentsInDB = EtudiantController.getAll();
        for(int i = 0; i < studentsInDB.length; i++)
        {
            System.out.println(studentsInDB[i].getPrenom());
        }

        Cours[] coursesInDB = CoursController.getAll();
        for(int i = 0; i < coursesInDB.length; i++)
        {
            System.out.println(coursesInDB[i].getTitre());
        }

        MainController.shutdown();
    }
}
