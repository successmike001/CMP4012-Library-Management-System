package services;

import model.UserRole;
import ui.Menu;

/**
 * ===============================================================
 * LoginManager.java
 * ---------------------------------------------------------------
 * Handles user authentication for the Library Management System.

 * Responsibilities:
 * • Display the login menu.
 * • Validate user credentials.
 * • Return the authenticated user role.
 * ===============================================================
 */
public class LoginManager {

    /**
     * Displays the login menu and authenticates the user.
     *
     * @param menu the application's input helper
     * @return the authenticated user role, or null if the user exits
     */
    public UserRole login(Menu menu) {

        while (true) {

            System.out.println("\n=======================================");
            System.out.println("WELCOME TO MY LIBRARY MANAGEMENT SYSTEM");
            System.out.println("=======================================");
            System.out.println("1. Login");
            System.out.println("0. Exit");
            System.out.print("Enter your option (1 or 0): ");

            int option = menu.readMenuChoice(1);

            switch (option) {

                case 1:
                    UserRole role = authenticate(menu);

                    if (role != null) {
                        return role;
                    }

                    break;

                case 0:
                    return null;
            }
        }
    }

    /**
     * Authenticates a user's credentials.
     *
     * @param menu the application's input helper
     * @return the authenticated user role, or null if authentication fails
     */
    private UserRole authenticate(Menu menu) {

        System.out.print("\nUsername: ");
        String username = menu.readString();

        System.out.print("Password: ");
        String password = menu.readString();

        if (username.equals("admin") && password.equals("admin")) {

            System.out.println("\nAdministrator login successful.\n");

            return UserRole.ADMIN;
        }

        if (username.equals("member") && password.equals("member")) {

            System.out.println("\nMember login successful.");

            return UserRole.MEMBER;
        }

        System.out.println("\nInvalid username or password.");

        return null;
    }
}