package controllers;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

/**
 * Fichier: <nom>
 * Auteur: Brybry
 * Date de création: 12.01.2017
 * But: <but>
 */
abstract class Controller {

    static void rollback(Transaction t, Exception e) throws Exception {
        if(t != null) {
            try {
                t.rollback();
                e.printStackTrace();
                throw e;
            } catch (HibernateException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
    }
}
