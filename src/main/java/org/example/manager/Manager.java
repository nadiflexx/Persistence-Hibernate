package org.example.manager;

import org.example.exceptions.VetStucomException;
import org.example.utils.Printer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Manager {
    private static Manager manager;
    private final Printer printer;
    private final BufferedReader bufferedReader;
    private final UserManager userManager;
    private final ExpedientsManager expedientsManager;

    public static Manager getInstance() {
        if (manager == null) manager = new Manager();
        return manager;
    }

    private Manager() {
        this.userManager = new UserManager();
        this.expedientsManager = new ExpedientsManager();
        this.printer = new Printer();
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void login() {
        boolean exit = false;

        while (!exit) {
            try {
                printer.showMenuLogin();

                switch (Integer.parseInt(bufferedReader.readLine())) {
                    case 1:
                        userManager.login();
                        menuUser(userManager.getUser().getUserType());

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
                System.out.println("Error. Wrong characters. Try again.");
            } catch (VetStucomException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void menuUser(int userType) {
        boolean exit = false;

        while (!exit) {
            try {
                int option = checkUserTypeMenu(userType);

                switch (option) {
                    case -1:
                        System.out.println("--> Error. Incorrect option. Try again. <-- ");
                        break;
                    case 1:
                        expedientsManager.consultExpedientsMini();
                        break;
                    case 2:
                        expedientsManager.registerExpedient(userManager.getUser());
                        break;
                    case 3:
                        expedientsManager.deleteExpedient();
                        break;
                    case 4:
                        expedientsManager.editExpedient();
                        break;
                    case 5:
                        userManager.registerUser();
                        break;
                    case 6:
                        userManager.deleteUser();
                        break;
                    case 7:
                        userManager.editUser();
                        break;
                    case 8:
                        userManager.consultUsers();
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        System.out.println("--> Error. Incorrect option. Try again <--");
                        break;
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Error. Wrong characters. Try again.");
            } catch (VetStucomException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int checkUserTypeMenu(int userType) throws IOException {
        int option;

        if(userType == 1) {
            printer.showMenuAssistant();
            option = Integer.parseInt(bufferedReader.readLine());
            if (option != 1) option = -1;
        } else if (userType == 2) {
            printer.showMenuVeterinary();
            option = Integer.parseInt(bufferedReader.readLine());
            if(option > 4) option = -1;
        } else  {
            printer.showMenuAdministrator();
            option = Integer.parseInt(bufferedReader.readLine());
        }

        return option;
    }
}