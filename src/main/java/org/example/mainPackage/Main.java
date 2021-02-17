package org.example.mainPackage;

import org.example.manager.Manager;

public class Main {
    public static void main(String[] args) {
        Manager manager = Manager.getInstance();
        manager.login();
    }
}
