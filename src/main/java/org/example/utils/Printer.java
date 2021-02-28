package org.example.utils;

import org.example.model.Expedient;
import org.example.model.User;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Printer {

    public Printer() {
    }


    public void welcome() {
        System.out.println("----------------------------------");
        System.out.println("-------------WELCOME!-------------");
        System.out.println("----------------------------------");
    }

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

    public void showUserTypes() {
        System.out.println();
        System.out.println(" -------TYPES------");
        System.out.println("| 1) Assistant     |");
        System.out.println("| 2) Veterinary    |");
        System.out.println("| 3) Administrator |");
        System.out.println(" ------------------");
    }

    public void showMenuAssistant() {
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
        System.out.println("| 0) Exit     |");
        System.out.println(" -------------");
    }


    public void exit() {
        System.out.println("----------------------------------");
        System.out.println("-------------GOODBYE!-------------");
        System.out.println("----------------------------------");
    }

    public void showMenuEditExpedient() {
        System.out.println();
        System.out.println("Choose an option: ");
        System.out.println();
        System.out.println(" -------EDIT--------");
        System.out.println("| 1) Number of pets |");
        System.out.println("| 2) Phone          |");
        System.out.println("| 3) Postal code    |");
        System.out.println(" -------------------");
    }

    public void showMenuEditUser() {
        System.out.println();
        System.out.println("Choose an option: ");
        System.out.println();
        System.out.println(" -----EDIT-----");
        System.out.println("| 1) Type      |");
        System.out.println("| 2) Password  |");
        System.out.println(" --------------");
    }

    public void printUsers(List<User> userList) {
        for (User user : userList) {
            System.out.println("(" + user.getId() + ")");
            System.out.println(" |  Name: " + user.getName());
            System.out.println(" |  Surname: " + user.getSurnames());
            System.out.println(" |  DNI: " + user.getDni());
            System.out.println(" |  User type: " + printUserType(user.getUserType()));
            System.out.println(" |  Personal license: " + user.getLicense());
            if (user.getLastAccess() != null) System.out.println(" |  Last access: " + user.getLastAccess());
            System.out.println(" |");
        }
    }

    private String printUserType(int userType) {
        String type = "Administrator";
        if (userType == 1) type = "Assistant";
        else if (userType == 2) type = "Veterinary";
        return type;
    }

    public void printExpedient(Expedient expedient) {
        System.out.println("(" + expedient.getId() + ")");
        System.out.println(" |  Name: " + expedient.getName());
        System.out.println(" |  Surname: " + expedient.getSurnames());
        System.out.println(" |  DNI: " + expedient.getDni());
        System.out.println(" |  Discharge date: " + expedient.getDischargeDate());
        System.out.println(" |  Number of pets: " + expedient.getNpets());
        System.out.println(" |  Postal code: " + expedient.getPostalCode());
        System.out.println(" |  Phone: " + expedient.getPhone());
        System.out.println(" |");
    }

    public void printExpedientsMini(Query query) {
        List<Expedient> list = query.list();
        for (Expedient expedient : list) {
            System.out.println("(" + expedient.getId() + ")");
            System.out.println(" |  Name: " + expedient.getName());
            System.out.println(" |  Surname: " + expedient.getSurnames());
            System.out.println(" |");
        }
    }
}