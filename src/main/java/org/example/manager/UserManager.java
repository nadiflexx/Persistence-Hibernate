package org.example.manager;

import org.example.exceptions.VetStucomException;
import org.example.model.User;
import org.example.utils.Printer;
import org.example.utils.Queries;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;


public class UserManager {
    private SessionFactory sessionFactory;
    private User user;
    private Printer printer;
    private Queries queries;
    private final BufferedReader bufferedReader;

    public UserManager() {
        Configuration con = new Configuration().configure().addAnnotatedClass(User.class);
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(con.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        this.sessionFactory = con.buildSessionFactory(serviceRegistry);
        this.printer = new Printer();
        this.queries = new Queries();
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public User getUser() {
        return user;
    }

    public void login() throws VetStucomException, IOException {
        System.out.println("Type your username: ");
        String username = bufferedReader.readLine();
        System.out.println("Type your password: ");
        String password = bufferedReader.readLine();

        user = verifyLogin(username, password);
        if (user == null) throw new VetStucomException(VetStucomException.wrongLogin);
    }

    private User verifyLogin(String username, String password) {
        User userLogged = null;
        for (User user: selectUsers()) {
            if(user.getName().equalsIgnoreCase(username)) if(user.getPassword().equals(password)) userLogged = user;
        }
        return userLogged;
    }

    public List<User> selectUsers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery(queries.SELECT_TABLE_USER);
        transaction.commit();
        return query.list();
    }


    public void consultUsers() {
        printer.printUsers(selectUsers());
    }

    public void registerUser() throws VetStucomException, IOException {
        System.out.println("Type user's name: ");
        String name = bufferedReader.readLine();
        System.out.println("Type user's surnname: ");
        String surname = bufferedReader.readLine();
        System.out.println("Type user's DNI: ");
        String dni = bufferedReader.readLine();

        if(verifyFormatDni(dni)) {
            System.out.println("Type user's password: ");
            String password = bufferedReader.readLine();
            System.out.println("Type user's personal license: ");
            String license = bufferedReader.readLine();
            System.out.println("Type user's type: ");
            int type = Integer.parseInt(bufferedReader.readLine());
            insertUser(name, surname, dni, password, license, type);
        }
    }

    public boolean verifyFormatDni(String dni) throws VetStucomException {
        boolean isDniCorrect = false;
        if (dni.length() == 9) {
            if (Character.isLetter(dni.charAt(8))) {
                for (int i = 0; i < 8; i++) { //Verifying that the first 8 characters are numbers.
                    if (!Character.isDigit(dni.charAt(i))) {
                        throw new VetStucomException(VetStucomException.contentVoid);
                    } else {
                        isDniCorrect = true;
                    }
                }
            }
        } else throw new VetStucomException(VetStucomException.contentVoid);
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

        Query query = session.createQuery(queries.SELECT_MAX_ID_FROM_USER);
        int id = (Integer) query.getSingleResult();
        transaction.commit();
        return id + 1;
    }

    public void deleteUser() throws VetStucomException, IOException {
        consultUsers();
        System.out.println("Choose the expedient by its id: ");
        int id = Integer.parseInt(bufferedReader.readLine());
        /// TODO: 24/02/2021 ADMIN CANNOT DELETE HIMSELF
        deleteUserByID(id);
    }

    private void deleteUserByID(int id) throws VetStucomException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        Query queryDelete = session.createQuery(queries.DELETE_FROM_USER_BY_ID);
        queryDelete.setParameter("id", id);

        if(queryDelete.executeUpdate() == 0) throw new VetStucomException(VetStucomException.userNotFound);
        transaction.commit();
    }

    public void editUser() {
    }
}