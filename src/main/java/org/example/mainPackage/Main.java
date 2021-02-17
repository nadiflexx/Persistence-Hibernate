package org.example.mainPackage;

import org.example.model.Expedient;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Configuration con = new Configuration().configure().addAnnotatedClass(Expedient.class).addAnnotatedClass(User.class);
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(con.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

        SessionFactory sf = con.buildSessionFactory(serviceRegistry);
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        //SELECT
        String selectAll = "FROM User";
        Query query = session.createQuery(selectAll);
        List<User> list = query.list();
        for (User value : list) {
            System.out.println("DNI: " + value.getDni());
            System.out.println("Name: " + value.getName());
            System.out.println("Surname: " + value.getSurnames());
            System.out.println();
        }

        tx.commit();
       /* Expedient person = new Expedient();
        person.setDni("12345678C");
        person.setName("JOSE");
        person.setSurname("Fernandez");

        //SELECT
        String selectAll = "FROM Expedient";
        Query query = session.createQuery(selectAll);
        List<Expedient> list = query.list();
        for (Expedient value : list) {
            System.out.println("DNI: " + value.getDni());
            System.out.println("Name: " + value.getName());
            System.out.println("Surname: " + value.getSurname());
            System.out.println();
        }

        //DELETE
        String delete = "delete from Expedient where dni =:dni";
        Query queryDelete = session.createQuery(delete);
        queryDelete.setParameter("dni", "12345678A");
        queryDelete.executeUpdate();


        //UPDATE
        String update = "update from Expedient set dni =:newDni where dni=:dni";
        Query queryUpdate = session.createQuery(update);
        queryUpdate.setParameter("dni", "12345678g");
        queryUpdate.setParameter("newDni", "12345678b");
        queryUpdate.executeUpdate();

        tx.commit();*/
    }
}
