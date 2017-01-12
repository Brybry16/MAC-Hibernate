package controllers;

import models.Cours;
import org.hibernate.Session;

import java.util.List;

public class CoursController extends Controller {

    public static void create(Cours... cours) throws Exception
    {
        Session session = MainController.getSession();
        try {
            session.beginTransaction();
            for(Cours c : cours) {
                session.save(c);
                //session.evict(c);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            rollback(session.getTransaction(), e);
        }
    }



    @SuppressWarnings("unchecked")
    public static List<Cours> getAll() throws Exception
    {
        Session session = MainController.getSession();
        List<Cours> cours = null;

        try {
            session.beginTransaction();

            cours = session.createCriteria(Cours.class).list();

            session.getTransaction().commit();
        } catch (Exception e) {
            rollback(session.getTransaction(), e);
        }

        return cours;
    }
}
