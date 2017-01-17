package controllers;

import models.Enseignant;
import models.Etudiant;
import models.Inscription;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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

    public static void delete(Etudiant... etudiants) throws Exception
    {
        Session session = MainController.getSession();
        try {
            session.beginTransaction();
            for(Etudiant e : etudiants) {
                e.getInscriptions().forEach(session::delete);
                session.delete(e);
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

    public static List<Enseignant> getEnseignants(Etudiant etudiant) throws Exception {
        Session session = MainController.getSession();

        return session.createCriteria(Inscription.class)
                .createAlias("etudiant", "e")
                .createAlias("cours", "c")
                .add(Restrictions.eq("e.id", etudiant.getId()))
                .setProjection(Projections.distinct(Projections.property("c.enseignant")))
                .list();
    }
}
