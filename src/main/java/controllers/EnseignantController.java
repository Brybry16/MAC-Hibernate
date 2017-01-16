package controllers;

import models.Enseignant;
import org.hibernate.Session;

/**
 * Fichier: <nom>
 * Auteur: Brybry
 * Date de création: 16.01.2017
 * But: <but>
 */
public class EnseignantController extends Controller {
    public static void create(Enseignant... enseignants) throws Exception
    {
        Session session = MainController.getSession();
        try {
            session.beginTransaction();
            for(Enseignant e : enseignants) {
                session.save(e);
                //session.evict(e);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            rollback(session.getTransaction(), e);
        }
    }
}
