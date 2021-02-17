package org.example.manager;

import org.example.utils.Printer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Manager {
    private static Manager manager;
    private final UserManager userManager;
    private final ExpedientsManager expedientsManager;

    public static Manager getInstance() {
        if (manager == null) manager = new Manager();
        return manager;
    }

    private Manager() {
        userManager = new UserManager();
        expedientsManager = new ExpedientsManager();
    }

    public void login() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Printer printer = new Printer();
        boolean exit = false;

        while (!exit) {
            try {
                printer.showMenuLogin();

                switch (Integer.parseInt(bufferedReader.readLine())) {
                    case 1:
                        expedientsManager.selectExpedients();
                        break;
                    case 2:
                        userManager.selectUsers();
                        break;
                    case 0:
                        printer.exit();
                        exit = true;
                        break;
                    default:
                        System.err.println("--> Error. Incorrect option. Choose an option between 0 and 2 <--");
                        break;
                }
            } catch (NumberFormatException | IOException e) {
                System.err.println("Error. Wrong characters. Try again.");
            }
        }
    }
}
