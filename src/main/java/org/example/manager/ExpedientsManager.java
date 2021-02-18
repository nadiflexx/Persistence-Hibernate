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
import java.util.Scanner;

public class ExpedientsManager {
    private SessionFactory sessionFactory;
    private Printer printer;
    private Scanner scanner;

    public ExpedientsManager() {
        Configuration con = new Configuration().configure().addAnnotatedClass(Expedient.class);
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(con.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        this.sessionFactory = con.buildSessionFactory(serviceRegistry);
        this.printer = new Printer();
        this.scanner = new Scanner(System.in);
    }

    public void consultExpedientsMini() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String selectAll = "FROM Expedient ";
        Query query = session.createQuery(selectAll);
        printer.printExpedientsMini(query);
        transaction.commit();
        System.out.println("Select one expedient by its ID to show all information: ");
        int id = scanner.nextInt();
        consultExpedients(id);
    }

    private void consultExpedients(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String selectAll = "FROM Expedient where id=:id ";
        Query query = session.createQuery(selectAll);
        query.setParameter("id", id);
        Expedient expedient = (Expedient) query.getSingleResult();
        printer.printExpedient(expedient);
        transaction.commit();
    }

    public void registerExpedient(User user) {
        System.out.println("Type user's name: ");
        String name = scanner.nextLine();
        System.out.println("Type user's surnname: ");
        String surname = scanner.nextLine();
        System.out.println("Type user's DNI: ");
        String dni = scanner.nextLine();
        if(verifyFormatDni(dni)) {
            System.out.println("Type the number of pets: ");
            int npets = scanner.nextInt();
            System.out.println("Type the postal code: ");
            String postalCode = scanner.nextLine();
            System.out.println("Type your phone: ");
            String phone = scanner.nextLine();
            insertExpedient(user, name, surname, dni, npets, postalCode, phone);
        }
    }

    public boolean verifyFormatDni(String dni) {
        boolean isDniCorrect = false;
        if (dni.length() == 9) {
            if (Character.isLetter(dni.charAt(8))) {
                for (int i = 0; i < 8; i++) { //Verifying that the first 8 characters are numbers.
                    if (!Character.isDigit(dni.charAt(i))) {
                        System.out.println("Incorrect DNI format.");
                    } else {
                        isDniCorrect = true;
                    }
                }
            }
        } else System.out.println("Incorrect DNI format.");
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

        String selectAll = "SELECT max(id) FROM Expedient ";
        Query query = session.createQuery(selectAll);
        int id = (Integer) query.getSingleResult();
        transaction.commit();
        return id + 1;
    }

    public void deleteExpedient() {
        consultExpedientsMini();
        System.out.println("Choose the expedient by its id: ");
        int id = scanner.nextInt();
        deleteExpedientById(id);
    }

    private void deleteExpedientById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String delete = "delete from Expedient where id =:id";
        Query queryDelete = session.createQuery(delete);
        queryDelete.setParameter("id", id);

        if(queryDelete.executeUpdate() == 0) System.out.println("Error. Expedient not found.");
        transaction.commit();
    }

    public void editExpedient() {
    }
}
