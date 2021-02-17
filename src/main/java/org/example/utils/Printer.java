package org.example.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Printer {

    public Printer() {
    }

    public void showMenu() {
        System.out.println();
        System.out.println("Choose an option: ");
        System.out.println();
        System.out.println(" --------MAIN MENU--------");
        System.out.println("| 1) Administration       |");
        System.out.println("| 2) Management           |");
        System.out.println("| 0) Exit.                |");
        System.out.println(" -------------------------");
    }

    public void showMenuManagement() {
        System.out.println();
        System.out.println("Choose an option: ");
        System.out.println();
        System.out.println(" -------------MANAGEMENT-------------");
        System.out.println("| 1) Assign a passenger to a route   |");
        System.out.println("| 2) Cancel a passenger from a route |");
        System.out.println("| 3) View the passengers on a route  |");
        System.out.println("| 0) Exit                            |");
        System.out.println(" -----------------------------------");
    }

    public void showMenuAdministration() {
        System.out.println();
        System.out.println("Choose an option: ");
        System.out.println();
        System.out.println(" -------ADMINISTRATION--------");
        System.out.println("| 1) Register a driver        |");
        System.out.println("| 2) Cancel a driver          |");
        System.out.println("| 3) Register a vehicle       |");
        System.out.println("| 4) Cancel a vehicle         |");
        System.out.println("| 5) Register a new route     |");
        System.out.println("| 6) Cancel an existing route |");
        System.out.println("| 7) Show the list of routes  |");
        System.out.println("| 0) Exit                     |");
        System.out.println(" -----------------------------");
    }


    public void showMenuPassenger() {
        System.out.println();
        System.out.println("Choose an option: ");
        System.out.println();
        System.out.println(" -------------PASSENGER-------------");
        System.out.println("| 1) Create a new passenger         |");
        System.out.println("| 2) Select an existing passenger   |");
        System.out.println(" ----------------------------------");
    }


    public void exit() {
        System.out.println("----------------------------------");
        System.out.println("-------------GOODBYE!-------------");
        System.out.println("----------------------------------");
    }
}
