package org.example.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Printer {

    public Printer() {}

    public void showMenuAdministrator() {
        System.out.println();
        System.out.println("Choose an option: ");
        System.out.println();
        System.out.println(" ------Administrator-----");
        System.out.println("| 1) Consult expedients  |");
        System.out.println("| 2) Register expedient  |");
        System.out.println("| 3) Delete expedient    |");
        System.out.println("| 4) Edit expedient      |");
        System.out.println("| 5) Register user       |");
        System.out.println("| 6) Delete user         |");
        System.out.println("| 7) Edit user           |");
        System.out.println("| 8) Consult users       |");
        System.out.println("| 0) Exit                |");
        System.out.println(" ------------------------");
    }

    public void showMenuVeterinary() {
        System.out.println();
        System.out.println("Choose an option: ");
        System.out.println();
        System.out.println(" -------Veterinary-------");
        System.out.println("| 1) Consult expedients  |");
        System.out.println("| 2) Register expedient  |");
        System.out.println("| 3) Delete expedient    |");
        System.out.println("| 4) Edit expedient      |");
        System.out.println("| 0) Exit                |");
        System.out.println(" ------------------------");
    }

    public void showMenuAuxiliary() {
        System.out.println();
        System.out.println("Choose an option: ");
        System.out.println();
        System.out.println(" -------Auxiliary--------");
        System.out.println("| 1) Consult expedients  |");
        System.out.println("| 0) Exit                |");
        System.out.println(" ------------------------");
    }


    public void showMenuLogin() {
        System.out.println();
        System.out.println("Choose an option: ");
        System.out.println();
        System.out.println(" ----LOGIN----");
        System.out.println("| 1) Login    |");
        System.out.println("| 2) Register |");
        System.out.println("| 0) Exit     |");
        System.out.println(" -------------");
    }


    public void exit() {
        System.out.println("----------------------------------");
        System.out.println("-------------GOODBYE!-------------");
        System.out.println("----------------------------------");
    }

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
