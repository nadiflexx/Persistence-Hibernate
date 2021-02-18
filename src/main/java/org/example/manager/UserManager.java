package org.example.manager;

import org.example.model.Expedient;
import org.example.model.User;
import org.example.utils.Printer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.Calendar;
import java.util.Date;
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
        String username = scanner.nextLine();
        System.out.println("Type your password: ");
        String password = scanner.nextLine();

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


    public void consultUsers() {
        printer.printUsers(selectUsers());
    }

    public void registerUser() {
        System.out.println("Type user's name: ");
        String name = scanner.nextLine();
        System.out.println("Type user's surnname: ");
        String surname = scanner.nextLine();
        System.out.println("Type user's DNI: ");
        String dni = scanner.nextLine();

        if(verifyFormatDni(dni)) {
            System.out.println("Type user's password: ");
            String password = scanner.nextLine();
            System.out.println("Type user's personal license: ");
            String license = scanner.nextLine();
            System.out.println("Type user's type: ");
            int type = scanner.nextInt();
            insertUser(name, surname, dni, password, license, type);
        }
    }

    public boolean verifyFormatDni(String dni) {
        boolean isDniCorrect = false;
        if (dni.length() == 9) {
            if (Character.isLetter(dni.charAt(8))) {
                for (int i = 0; i < 8; i++) { //Verifying that the first 8 characters are numbers.
                    if (!Character.isDigit(dni.charAt(i))) {
                        System.out.println("Incorrect DNI format. ");
                    } else {
                        isDniCorrect = true;
                    }
                }
            }
        } else System.out.println("Incorrect DNI format.");
        return isDniCorrect;
    }

    private void insertUser(String name, String surname, String dni, String password, String license, int type) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User(searchLastID(), name, surname, dni, license, password, type,
                new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        session.save(user);
        transaction.commit();
    }

    private int searchLastID() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String selectAll = "SELECT max(id) FROM User ";
        Query query = session.createQuery(selectAll);
        int id = (Integer) query.getSingleResult();
        transaction.commit();
        return id + 1;
    }

    public void deleteUser() {
        consultUsers();
        System.out.println("Choose the expedient by its id: ");
        int id = scanner.nextInt();
        deleteUserByID(id);
    }

    private void deleteUserByID(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String delete = "delete from User where id =:id";
        Query queryDelete = session.createQuery(delete);
        queryDelete.setParameter("id", id);

        if(queryDelete.executeUpdate() == 0) System.out.println("Error. User not found.");
        transaction.commit();
    }

    public void editUser() {
    }
}
