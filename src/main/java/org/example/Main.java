package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Main {
    public static void main(String[] args) {
        Configuration con = new Configuration().configure().addAnnotatedClass(Person.class);
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(con.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

        SessionFactory sf = con.buildSessionFactory(serviceRegistry);
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        Person person = new Person();
        person.setDni("12345678G");
        person.setName("Nadeem");
        person.setSurname("Rashid");

        session.save(person);
        tx.commit();
    }
}
