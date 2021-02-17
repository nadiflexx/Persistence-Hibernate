package org.example.manager;

import org.example.model.Expedient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class ExpedientsManager {
    private SessionFactory sessionFactory;

    public ExpedientsManager() {
        Configuration con = new Configuration().configure().addAnnotatedClass(Expedient.class);
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(con.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        this.sessionFactory = con.buildSessionFactory(serviceRegistry);
    }

    public void selectExpedients() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String selectAll = "FROM Expedient ";
        Query query = session.createQuery(selectAll);
        List<Expedient> list = query.list();
        for (Expedient value : list) {
            System.out.println("DNI: " + value.getDni());
            System.out.println("Name: " + value.getName());
            System.out.println("Surname: " + value.getSurnames());
            System.out.println();
        }
        transaction.commit();
    }
}
