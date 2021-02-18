package org.example.manager;

import org.example.model.User;
import org.example.utils.Printer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.Scanner;


public class UserManager {
    private SessionFactory sessionFactory;
    private User user;
    private Printer printer;
    private Scanner scanner;

    public UserManager() {
        Configuration con = new Configuration().configure().addAnnotatedClass(User.class);
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(con.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        this.sessionFactory = con.buildSessionFactory(serviceRegistry);
        this.printer = new Printer();
        this.scanner = new Scanner(System.in);
    }

    public User getUser() {
        return user;
    }

    public boolean login() {
        System.out.println("Type your username: ");
        String username = scanner.next();
        System.out.println("Type your password: ");
        String password = scanner.next();

        user = verifyLogin(username, password);
        if (user != null) return true;
        else System.out.println("Error. Username or password not found. Try again.");
        return false;
    }

    private User verifyLogin(String username, String password) {
        User userLogged = null;
        for (User user: selectUsers()) {
            if(user.getName().equals(username)) if(user.getPassword().equals(password)) userLogged = user;
        }
        return userLogged;
    }

    public List<User> selectUsers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String selectAll = "FROM User";
        Query query = session.createQuery(selectAll);
        transaction.commit();
        return query.list();
    }




}
