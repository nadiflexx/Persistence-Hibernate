package org.example.manager;

import org.example.exceptions.VetStucomException;
import org.example.model.Expedient;
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
        printer.welcome();
        boolean exit = false;

        while (!exit) {
            try {
                printer.showMenuLogin();

                switch (Integer.parseInt(bufferedReader.readLine())) {
                    case 1:
                        userManager.login();
                        menuVetStucom(userManager.getUser().getUserType());
                        break;
                    case 0:
                        printer.exit();
                        exit = true;
                        break;
                    default:
                        throw new VetStucomException(VetStucomException.incorrectOption);
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Error. Wrong characters. Try again.");
            } catch (VetStucomException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void menuVetStucom(int userType) {
        boolean exit = false;

        while (!exit) {
            try {
                int option = checkUserTypeMenu(userType);

                switch (option) {
                    case -1:
                        throw new VetStucomException(VetStucomException.permissionDenied);
                    case 1:
                        //THIS IS FOR PRINT THE NAME OF THE CREATOR OF THE FULL EXPEDIENT INSTEAD OF PRINTING THE USERID
                        showExpedientWithCreatorName();
                        break;
                    case 2:
                        expedientsManager.registerExpedient(userManager.getUser());
                        break;
                    case 3:
                        int id = showExpedientWithCreatorName();
                        expedientsManager.deleteExpedient(id);
                        break;
                    case 4:
                        int idd = showExpedientWithCreatorName();
                        expedientsManager.editExpedient(idd);
                        break;
                    case 5:
                        userManager.registerUser();
                        break;
                    case 6:
                        //THIS IS FOR UPDATE THE ID OF THE DELETED CREATOR OF THE EXPEDIENT FOR THE NEW ONE.
                        int oldUserId = userManager.getDeleteUser();
                        expedientsManager.editExpedientsId(userManager.getUser().getId(), oldUserId);
                        userManager.deleteUserByID(oldUserId);
                        break;
                    case 7:
                        userManager.editUser();
                        //If you change your own type you will be redirected to the login menu because you can't use
                        //the administrator cases.
                        if (userManager.getUser().getUserType() != 3) exit = true;
                        break;
                    case 8:
                        userManager.consultUsers();
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        throw new VetStucomException(VetStucomException.incorrectOption);
                }
            } catch (NumberFormatException e) {
                System.out.println("--> Error. Wrong characters. Try again. <--");
            } catch (VetStucomException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int showExpedientWithCreatorName() throws VetStucomException, IOException {
        int id = expedientsManager.consultExpedientsMini();
        Expedient expedient = expedientsManager.consultFullExpedient(id);
        printer.printExpedient(expedient, userManager.getUserNameById(expedient.getUserId()));
        return id;
    }

    private int checkUserTypeMenu(int userType) throws IOException {
        int option;

        if (userType == 1) {
            printer.showMenuAssistant();
            option = Integer.parseInt(bufferedReader.readLine());
            if (option != 1 && option != 0 && option < 9) option = -1;
        } else if (userType == 2) {
            printer.showMenuVeterinary();
            option = Integer.parseInt(bufferedReader.readLine());
            if (option > 4  && option < 9) option = -1;
        } else {
            printer.showMenuAdministrator();
            option = Integer.parseInt(bufferedReader.readLine());
        }

        return option;
    }
}