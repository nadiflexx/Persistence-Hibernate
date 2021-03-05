package org.example.manager;

import org.example.exceptions.VetStucomException;
import org.example.model.Expedient;
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
import java.util.Scanner;

public class ExpedientsManager {
    private SessionFactory sessionFactory;
    private Printer printer;
    private Queries queries;
    private final BufferedReader bufferedReader;

    public ExpedientsManager() {
        Configuration con = new Configuration().configure().addAnnotatedClass(Expedient.class);
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(con.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        this.sessionFactory = con.buildSessionFactory(serviceRegistry);
        this.printer = new Printer();
        this.queries = new Queries();
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public int consultExpedientsMini() throws VetStucomException, IOException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery(queries.SELECT_TABLE_EXPEDIENT);
        if (query.list().size() > 0) {
            printer.printExpedientsMini(query);
            transaction.commit();
            System.out.println("Select one expedient by its ID to show all information: ");
            int id = Integer.parseInt(bufferedReader.readLine());
            consultExpedients(id);
            return id;
        } else throw new VetStucomException(VetStucomException.contentVoid);
    }

    private void consultExpedients(int id) throws VetStucomException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery(queries.SELECT_TABLE_EXPEDIENT_BY_ID);
        query.setParameter("id", id);
        if (query.getResultList().size() != 1) throw new VetStucomException(VetStucomException.expedientNotFound);
        Expedient expedient = (Expedient) query.getSingleResult();
        printer.printExpedient(expedient);
        transaction.commit();
    }

    public void registerExpedient(User user) throws VetStucomException, IOException {
        System.out.println("Type user's name: ");
        String name = bufferedReader.readLine();
        System.out.println("Type user's surname: ");
        String surname = bufferedReader.readLine();
        System.out.println("Type user's DNI: ");
        String dni = bufferedReader.readLine();
        if (verifyFormatDni(dni)) {
            System.out.println("Type the number of pets: ");
            int npets = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Type the postal code: ");
            String postalCode = bufferedReader.readLine();
            System.out.println("Type your phone: ");
            String phone = bufferedReader.readLine();

            insertExpedient(user, name, surname, dni, npets, postalCode, phone);
        }
    }

    public boolean verifyFormatDni(String dni) throws VetStucomException {
        boolean isDniCorrect = false;
        if (dni.length() == 9) {
            if (Character.isLetter(dni.charAt(8))) {
                for (int i = 0; i < 8; i++) { //Verifying that the first 8 characters are numbers.
                    if (!Character.isDigit(dni.charAt(i))) {
                        throw new VetStucomException(VetStucomException.incorrectFormatDNI);
                    } else {
                        isDniCorrect = true;
                    }
                }
            }
        } else throw new VetStucomException(VetStucomException.incorrectFormatDNI);
        return isDniCorrect;
    }

    private void insertExpedient(User user, String name, String surname, String dni, int npets, String postalCode, String phone) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new Expedient(searchLastID(), name, surname, dni, npets,
                new java.sql.Date(Calendar.getInstance().getTime().getTime()), postalCode, phone, user.getId()));
        transaction.commit();
    }

    private int searchLastID() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery(queries.SELECT_MAX_ID_FROM_EXPEDIENT);
        int id;
        if (query.getSingleResult() == null) id = 0;
        else id = (Integer) query.getSingleResult();
        transaction.commit();
        return id + 1;
    }

    public void deleteExpedient() throws VetStucomException, IOException {
        int id = consultExpedientsMini();
        System.out.println("Are sure that you want to delete this expedient? y/n: ");
        String answer = bufferedReader.readLine();
        if (answer.equalsIgnoreCase("y")) deleteExpedientById(id);
        else System.out.println("Canceling your request...");
    }

    private void deleteExpedientById(int id) throws VetStucomException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query queryDelete = session.createQuery(queries.DELETE_FROM_EXPEDIENT_BY_ID);
        queryDelete.setParameter("id", id);

        if (queryDelete.executeUpdate() == 0) throw new VetStucomException(VetStucomException.expedientNotFound);
        transaction.commit();
    }

    public void editExpedient() throws IOException, VetStucomException {
        int id = consultExpedientsMini();
        System.out.println("Are sure that you want to edit this expedient? y/n: ");
        String answer = bufferedReader.readLine();
        if (answer.equalsIgnoreCase("y")) selectEditOption(id);
        else System.out.println("Canceling your request...");
    }

    private void selectEditOption(int id) throws IOException, VetStucomException {
        printer.showMenuEditExpedient();
        int option = Integer.parseInt(bufferedReader.readLine());
        if (option == 1) editPets(id);
        else if (option == 2) editPhone(id);
        else if (option == 3) editPostalCode(id);
        else throw new VetStucomException(VetStucomException.incorrectOption);
    }

    private void editPostalCode(int id) throws IOException {
        System.out.println("Type the postal code: ");
        String postalCode = bufferedReader.readLine();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query queryUpdate = session.createQuery(queries.UPDATE_POSTAL_EXPEDIENT);
        queryUpdate.setParameter("postalCode", postalCode);
        queryUpdate.setParameter("id", id);
        queryUpdate.executeUpdate();
        transaction.commit();
    }

    private void editPhone(int id) throws IOException {
        System.out.println("Type your phone: ");
        String phone = bufferedReader.readLine();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query queryUpdate = session.createQuery(queries.UPDATE_PHONE_EXPEDIENT);
        queryUpdate.setParameter("phone", phone);
        queryUpdate.setParameter("id", id);
        queryUpdate.executeUpdate();
        transaction.commit();
    }

    private void editPets(int id) throws IOException {
        System.out.println("Type the number of pets: ");
        int npets = Integer.parseInt(bufferedReader.readLine());

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query queryUpdate = session.createQuery(queries.UPDATE_PETS_EXPEDIENT);
        queryUpdate.setParameter("npets", npets);
        queryUpdate.setParameter("id", id);
        queryUpdate.executeUpdate();
        transaction.commit();
    }

    public void editExpedientsId(int newUserId, int oldUserId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query queryUpdate = session.createQuery(queries.UPDATE_ID_EXPEDIENT);
        queryUpdate.setParameter("newUserId", newUserId);
        queryUpdate.setParameter("oldUserId", oldUserId);
        queryUpdate.executeUpdate();
        transaction.commit();
    }
}