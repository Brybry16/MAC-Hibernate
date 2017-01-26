// Auteur: Eric Lefrançois - Janvier. 2015
package controllers;

import models.Etudiant;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.GregorianCalendar;
import java.util.List;

// Du jar hibernate-core-4.3.5.Final

//------------------------------------------------------------------------------
public class ORMAccess {


    @SuppressWarnings("all")

    private static SessionFactory sessionFactory;

    public ORMAccess() {
        sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();
    }

    public void transactionExceptionCallback(Exception e, Transaction tx) throws Exception {
        if (tx != null) {
            try {
                tx.rollback();
                System.out.println(e.toString());
                throw e;
            } catch (HibernateException he) {
                System.out.println(he.toString());
                throw he;
            }
        }
    }

    public void peuplerLaBase() throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();

            SAVE_ETUDIANT(new Etudiant("Bryan", "Perroud", new GregorianCalendar()));
            SAVE_ETUDIANT(new Etudiant("Toni", "Dias", new GregorianCalendar()));
            SAVE_ETUDIANT(new Etudiant("Paul", "Nta", new GregorianCalendar()));
            SAVE_ETUDIANT(new Etudiant("Fred", "Fyfer", new GregorianCalendar()));

            tx.commit();
        } catch (Exception e) {
            transactionExceptionCallback(e, tx);
        } finally {
            try {
                session.close();
            } catch (HibernateException he) {
                System.out.println(he.toString());
            }
        }
    }

    @SuppressWarnings("all")
    public List<Etudiant> GET_ETUDIANTS() throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<Etudiant> etudiants = null;
        try {

            tx = session.beginTransaction();

            etudiants = session.createCriteria(Etudiant.class).list();

            tx.commit();
        } catch (Exception e) {
            transactionExceptionCallback(e, tx);
        } finally {
            try {
                session.close();
            } catch (HibernateException he) {
                System.out.println(he.toString());
            }
        }

        return etudiants;
    }

    public Etudiant GET_ETUDIANT(int studentId) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Etudiant etudiant = null;
        try {

            tx = session.beginTransaction();

            etudiant = (Etudiant) session.createCriteria(Etudiant.class)
                    .add(Restrictions.eq("id", studentId))
                    .uniqueResult();

            tx.commit();
        } catch (Exception e) {
            transactionExceptionCallback(e, tx);
        } finally {
            try {
                session.close();
            } catch (HibernateException he) {
                System.out.println(he.toString());
            }
        }
        return etudiant;
    }

    public void SAVE_ETUDIANT(Etudiant etudiant) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();

            session.save(etudiant);

            tx.commit();
        } catch (Exception e) {
            transactionExceptionCallback(e, tx);
        } finally {
            try {
                session.close();
            } catch (HibernateException he) {
                System.out.println(he.toString());
            }
        }
    }

    public void UPDATE_ETUDIANT(Etudiant etudiant) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();

            //session.merge(etudiant);
            session.update(etudiant);

            tx.commit();
        } catch (Exception e) {
            transactionExceptionCallback(e, tx);
        } finally {
            try {
                session.close();
            } catch (HibernateException he) {
                System.out.println(he.toString());
            }
        }
    }

    public void DELETE_ETUDIANT(int studentId) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();

            session.delete(GET_ETUDIANT(studentId));

            tx.commit();
        } catch (Exception e) {
            transactionExceptionCallback(e, tx);
        } finally {
            try {
                session.close();
            } catch (HibernateException he) {
                System.out.println(he.toString());
            }
        }
    }

    public static void terminate() {
        try {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}