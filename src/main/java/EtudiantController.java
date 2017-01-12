import org.hibernate.Session;

import java.util.Arrays;

public class EtudiantController {
    public static void create(Etudiant[] etudiants)
    {
        Session session = MainController.getSession();
        session.beginTransaction();

        for(int i = 0; i < etudiants.length; i++) {
            session.save(etudiants[i]);
            session.evict(etudiants[i]);
        }

        session.getTransaction().commit();
    }

    public static Etudiant[] getAll()
    {
        Session session = MainController.getSession();
        Object[] tmp = session.createQuery("from Etudiant").list().toArray();

        return Arrays.copyOf(tmp, tmp.length, Etudiant[].class);
    }
}
