package controllers;

import models.Inscription;
import org.hibernate.Session;

import java.util.Arrays;

public class InscriptionController {
    public static void create(Inscription... inscriptions)
    {
        Session session = MainController.getSession();
        session.beginTransaction();

        for(int i = 0; i < inscriptions.length; i++) {
            session.save(inscriptions[i]);
            session.evict(inscriptions[i]);
        }

        session.getTransaction().commit();
    }

    public static Inscription[] getAll()
    {
        Session session = MainController.getSession();
        Object[] tmp = session.createQuery("from Inscription").list().toArray();

        return Arrays.copyOf(tmp, tmp.length, Inscription[].class);
    }
}
