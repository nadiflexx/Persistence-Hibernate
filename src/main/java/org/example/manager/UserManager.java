package org.example.manager;

import com.sun.xml.bind.v2.TODO;
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
        else updateDateAccess();
    }

    private User verifyLogin(String username, String password) {
        User userLogged = null;
        for (User user: selectUsers()) {
            if(user.getName().equalsIgnoreCase(username)) if(user.getPassword().equals(password)) userLogged = user;
        }
        return userLogged;
    }

    private void updateDateAccess() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query queryUpdate = session.createQuery(queries.UPDATE_DATE_USER);
        queryUpdate.setParameter("dni", user.getDni());
        queryUpdate.setParameter("date", new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        queryUpdate.executeUpdate();
        transaction.commit();
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
        System.out.println("Type user's surname: ");
        String surname = bufferedReader.readLine();
        System.out.println("Type user's DNI: ");
        String dni = bufferedReader.readLine();

        if(verifyFormatDni(dni)) {
            System.out.println("Type user's password: ");
            String password = bufferedReader.readLine();
            System.out.println("Type user's personal license: ");
            String license = bufferedReader.readLine();
            int type = selectType();
            insertUser(name, surname, dni, password, license, type);
        }
    }

    private int selectType() throws IOException, VetStucomException {
        printer.showUserTypes();
        System.out.println("Type user's type: ");
        int type = Integer.parseInt(bufferedReader.readLine());

        if(type < 1 || type > 3) throw new VetStucomException(VetStucomException.wrongType);
        return type;
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

        User user = new User(searchLastID(), name, surname, dni, license, password, type, null);
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
        System.out.println("Choose the user by its id: ");
        int id = Integer.parseInt(bufferedReader.readLine());
        if(id != user.getId()) deleteUserByID(id);
        else throw new VetStucomException(VetStucomException.deleteAdministrator);
        // TODO: 27/02/2021 IF USER TO DELETE HAVE EXPEDIENTS UPDATE EXPEDIENT USER ID BY ACTUAL USER
    }

    private void deleteUserByID(int id) throws VetStucomException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        Query queryDelete = session.createQuery(queries.DELETE_FROM_USER_BY_ID);
        queryDelete.setParameter("id", id);

        if(queryDelete.executeUpdate() == 0) throw new VetStucomException(VetStucomException.userNotFound);
        transaction.commit();
    }

    public void editUser() throws IOException, VetStucomException {
        consultUsers();
        System.out.println("Choose the user by its id: ");
        int id = Integer.parseInt(bufferedReader.readLine());
        selectEditOption(id);
    }

    private void selectEditOption(int id) throws IOException, VetStucomException {
        printer.showMenuEditUser();
        int option = Integer.parseInt(bufferedReader.readLine());
        if(option == 1) editType(id);
        else if(option == 2) editPassword(id);
        else throw new VetStucomException(VetStucomException.incorrectOption);
    }

    private void editPassword(int id) throws IOException {
        System.out.println("Type your new password: ");
        String password = bufferedReader.readLine();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query queryUpdate = session.createQuery(queries.UPDATE_PASSWORD_USER);
        queryUpdate.setParameter("pass", password);
        queryUpdate.setParameter("id", id);
        queryUpdate.executeUpdate();
        transaction.commit();
    }

    private void editType(int id) throws IOException, VetStucomException {
        int type = selectType();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query queryUpdate = session.createQuery(queries.UPDATE_TYPE_USER);
        queryUpdate.setParameter("type", type);
        queryUpdate.setParameter("id", id);
        queryUpdate.executeUpdate();
        transaction.commit();
    }
}