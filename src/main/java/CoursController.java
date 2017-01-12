import org.hibernate.Session;

import java.util.Arrays;

public class CoursController {
    public static void create(Cours[] courses)
    {
        Session session = MainController.getSession();
        session.beginTransaction();

        for(int i = 0; i < courses.length; i++) {
            session.save(courses[i]);
            session.evict(courses[i]);
        }

        session.getTransaction().commit();
    }

    public static Cours[] getAll()
    {
        Session session = MainController.getSession();
        Object[] tmp = session.createQuery("from Cours").list().toArray();

        return Arrays.copyOf(tmp, tmp.length, Cours[].class);
    }
}
