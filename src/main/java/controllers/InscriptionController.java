package controllers;

import models.Cours;
import models.Etudiant;
import models.Inscription;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class InscriptionController extends Controller {

    public static void create(Inscription... inscriptions) throws Exception
    {
        Session session = MainController.getSession();
        try {
            session.beginTransaction();
            for(Inscription i : inscriptions) {
                session.save(i);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            rollback(session.getTransaction(), e);
        }
    }

    public static void update(Inscription inscription) throws Exception {
        Session session = MainController.getSession();

        try {
            session.beginTransaction();
            session.saveOrUpdate(inscription);
            session.getTransaction().commit();
        } catch (Exception e) {
            rollback(session.getTransaction(), e);
        }
    }



    @SuppressWarnings("unchecked")
    public static List<Cours> getNonCredites(Etudiant etudiant) throws Exception
    {
        Session session = MainController.getSession();
        List<Cours> cours = null;

        try {
            session.beginTransaction();

            cours = session.createCriteria(Cours.class)
                    .createAlias("inscriptions", "i")
                    .add(Restrictions.and(Restrictions.eq("i.etudiant", etudiant), Restrictions.eq("i.grade", '\u0000')))
                    .list();

            session.getTransaction().commit();
        } catch (Exception e) {
            rollback(session.getTransaction(), e);
        }

        return cours;
    }

    @SuppressWarnings("unchecked")
    public static List<Etudiant> getEnAttente(Cours cours) throws Exception
    {
        Session session = MainController.getSession();
        List<Etudiant> etudiants = null;

        try {
            session.beginTransaction();

            etudiants = session.createCriteria(Etudiant.class)
                    .createAlias("inscriptions", "i")
                    .add(Restrictions.and(Restrictions.eq("i.cours", cours), Restrictions.eq("i.grade", '\u0000')))
                    .list();

            session.getTransaction().commit();
        } catch (Exception e) {
            rollback(session.getTransaction(), e);
        }

        return etudiants;
    }
}
