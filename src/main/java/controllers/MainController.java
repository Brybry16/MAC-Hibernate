package controllers;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class MainController {
    private static Session session;

    public static Session getSession()
    {
        if(session == null)
            session = new Configuration().configure("./hibernate.cfg.xml").buildSessionFactory().openSession();
            //session = new Configuration().configure("./hibernate.cfg.xml").addResource("./map.xml").buildSessionFactory().openSession();
        return session;
    }

    public static void shutdown()
    {
        session.close();
    }
}
