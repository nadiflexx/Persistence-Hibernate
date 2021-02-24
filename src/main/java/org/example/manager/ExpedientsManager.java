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
import java.util.Calendar;
import java.util.Scanner;

public class ExpedientsManager {
    private SessionFactory sessionFactory;
    private Printer printer;
    private Queries queries;
    private Scanner scanner;

    public ExpedientsManager() {
        Configuration con = new Configuration().configure().addAnnotatedClass(Expedient.class);
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(con.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        this.sessionFactory = con.buildSessionFactory(serviceRegistry);
        this.printer = new Printer();
        this.queries = new Queries();
        this.scanner = new Scanner(System.in);
    }

    public void consultExpedientsMini() throws VetStucomException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery(queries.SELECT_TABLE_EXPEDIENT);
        if(query.list().size() > 0) {
            printer.printExpedientsMini(query);
            transaction.commit();
            System.out.println("Select one expedient by its ID to show all information: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            consultExpedients(id);
        } else throw new VetStucomException(VetStucomException.contentVoid);
    }

    private void consultExpedients(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery(queries.SELECT_TABLE_EXPEDIENT_BY_ID);
        query.setParameter("id", id);
        Expedient expedient = (Expedient) query.getSingleResult();
        printer.printExpedient(expedient);
        transaction.commit();
    }

    public void registerExpedient(User user) throws VetStucomException {
        System.out.println("Type user's name: ");
        String name = scanner.nextLine();
        System.out.println("Type user's surnname: ");
        String surname = scanner.nextLine();
        System.out.println("Type user's DNI: ");
        String dni = scanner.nextLine();
        if(verifyFormatDni(dni)) {
            System.out.println("Type the postal code: ");
            String postalCode = scanner.nextLine();
            System.out.println("Type your phone: ");
            String phone = scanner.nextLine();
            System.out.println("Type the number of pets: ");
            int npets = scanner.nextInt();
            scanner.nextLine();
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
        Expedient expedient = new Expedient(searchLastID(), name, surname, dni, npets,
                new java.sql.Date(Calendar.getInstance().getTime().getTime()), postalCode, phone, user.getId());
        session.save(expedient);
        transaction.commit();
    }

    private int searchLastID() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery(queries.SELECT_MAX_ID_FROM_EXPEDIENT);
        int id;
        if(query.getSingleResult() == null) id = 0;
        else id = (Integer) query.getSingleResult();
        transaction.commit();
        return id + 1;
    }

    public void deleteExpedient() throws VetStucomException {
        consultExpedientsMini();
        System.out.println("Type the id again if you are sure that you want to delete the expedient: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        deleteExpedientById(id);
    }

    private void deleteExpedientById(int id) throws VetStucomException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query queryDelete = session.createQuery(queries.DELETE_FROM_EXPEDIENT_BY_ID);
        queryDelete.setParameter("id", id);

        if(queryDelete.executeUpdate() == 0) throw new VetStucomException(VetStucomException.expedientNotFound);
        transaction.commit();
    }

    public void editExpedient() {
    }
}