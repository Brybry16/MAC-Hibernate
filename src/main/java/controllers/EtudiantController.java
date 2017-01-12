package controllers;

import models.Etudiant;
import org.hibernate.Session;

import java.util.List;

public class EtudiantController extends Controller {

    public static void create(Etudiant... etudiants) throws Exception
    {
        Session session = MainController.getSession();
        try {
            session.beginTransaction();
            for(Etudiant e : etudiants) {
                session.save(e);
                //session.evict(e);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            rollback(session.getTransaction(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Etudiant> getAll() throws Exception
    {
        Session session = MainController.getSession();
        List<Etudiant> etudiants = null;

        try {
            session.beginTransaction();

            etudiants = session.createCriteria(Etudiant.class).list();

            session.getTransaction().commit();
        } catch (Exception e) {
            rollback(session.getTransaction(), e);
        }

        return etudiants;
    }
}
