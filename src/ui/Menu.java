package ui;

import services.LibraryManager;
import storage.FileHandler;
import model.UserRole;
import services.LoginManager;

import java.util.Scanner;

/**
 ===============================================================
 Menu.java
 ---------------------------------------------------------------
 Provides the console-based user interface for the Library
 Management System.

 This class is responsible for displaying the application's
 main menu, reading user input and coordinating navigation
 between the read-write and read-only operation classes.

 Responsibilities:
 • Display the main menu.
 • Read and validate user input.
 • Coordinate user navigation.
 • Delegate operations to the appropriate classes.

 Does NOT:
 • Store books.
 • Perform business logic.
 • Modify library data.
 • Handle CSV processing directly.
 ===============================================================
 */
public class Menu {
    /* ============================================================
   Private Fields
   ============================================================ */

    // Reads user input.
    private final Scanner scanner;

    // Manages library data.
    private final LibraryManager libraryManager;

    // Handles file storage.
    private final FileHandler fileHandler;

    // Performs read-write operations.
    private final ReadWriteOperations readWriteOperations;

    // Performs read-only operations.
    private final ReadOnlyOperations readOnlyOperations;

    // Handles user authentication.
    private final LoginManager loginManager;

    // Stores the role of the currently logged-in user.
    private UserRole currentRole;

    /* ============================================================
   Constructor
   ------------------------------------------------------------
   Creates a Menu object and initialises the components
   required to run the Library Management System.
   ============================================================ */

    /**
     * Creates a new Menu object.
     */
    public Menu() {

        scanner = new Scanner(System.in);

        libraryManager = new LibraryManager();

        fileHandler = new FileHandler();

        loginManager = new LoginManager();

        readWriteOperations =
                new ReadWriteOperations(
                        this,
                        libraryManager,
                        fileHandler);

        readOnlyOperations =
                new ReadOnlyOperations(
                        this,
                        libraryManager,
                        fileHandler);
    }
    //-------------------------------------------------------------
    /**
     * Prompts the user to log in.
     */
    private boolean login() {

        currentRole = loginManager.login(this);

        if (currentRole == null) {
            return false;
        }

        System.out.print("______________________________");
        System.out.println("\n   **** Welcome " + currentRole+" ****");
        System.out.println("______________________________");

        return true;
    }

    public void start() {

        boolean running = true;

        while (running) {

            if (!login()) {
                running = false;
                continue;
            }

            if (isAdmin()) {
                startAdminSession();
            } else {
                startMemberSession();
            }
        }

        displayExitMessage();
    }

    //-------------------------------------------------------------
    /**
     * This starts admin session when user logs in as administrator
     */
    private void startAdminSession() {

        boolean running = true;

        while (running) {

            displayAdminMenu();

            switch (readMenuChoice(9)) {

                case 1:
                    readWriteOperations.addBook();
                    break;

                case 2:
                    readWriteOperations.removeBook();
                    break;

                case 3:
                    readWriteOperations.updateBook();
                    break;

                case 4:
                    readWriteOperations.borrowBook();
                    break;

                case 5:
                    readWriteOperations.returnBook();
                    break;

                case 6:
                    readOnlyOperations.searchBooks();
                    break;

                case 7:
                    readOnlyOperations.displayAllBooks();
                    break;

                case 8:
                    readOnlyOperations.displayAvailableBooks();
                    break;

                case 9:
                    readOnlyOperations.displayBorrowedBooks();
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    System.out.println("\nInvalid option.");
            }
        }
    }

    //-------------------------------------------------------------
    /**
     * This starts member session when user logs in as member
     */
    private void startMemberSession() {

        boolean running = true;

        while (running) {

            displayMemberMenu();

            switch (readMenuChoice(5)) {

                case 1:
                    readWriteOperations.borrowBook();
                    break;

                case 2:
                    readWriteOperations.returnBook();
                    break;

                case 3:
                    readOnlyOperations.searchBooks();
                    break;

                case 4:
                    readOnlyOperations.displayAllBooks();
                    break;

                case 5:
                    readOnlyOperations.displayAvailableBooks();
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    System.out.println("\nInvalid option.");
            }
        }
    }

    /* ============================================================
   Menu Display
   ============================================================ */

    /**
     * Displays the admin menu options.
     */
    private void displayAdminMenu() {

        System.out.println("\n====================================================");
        System.out.println("        LIBRARY MANAGEMENT SYSTEM (ADMIN MENU)");
        System.out.println("====================================================");

        System.out.println("1. Add Book");
        System.out.println("2. Remove Book");
        System.out.println("3. Update Book");
        System.out.println("4. Borrow Book");
        System.out.println("5. Return Book");
        System.out.println("6. Search Books");
        System.out.println("7. Display All Books");
        System.out.println("8. Display Available Books");
        System.out.println("9. Display Borrowed Books");
        System.out.println("0. Logout");

        System.out.println("====================================================");
        System.out.print("Enter your option: ");
    }

    //----------------------------------------------------------------
    /**
     * Displays the member menu options.
     */
    private void displayMemberMenu() {

        System.out.println("\n====================================================");
        System.out.println("        LIBRARY MANAGEMENT SYSTEM (MEMBER MENU)");
        System.out.println("====================================================");

        System.out.println("1. Borrow Book");
        System.out.println("2. Return Book");
        System.out.println("3. Search Books");
        System.out.println("4. Display All Books");
        System.out.println("5. Display Available Books");
        System.out.println("0. Logout");

        System.out.println("===============================================");
        System.out.print("Enter your option: ");
    }

    //----------------------------------------------------------------
    /**
     * Displays a farewell message before the application closes.
     */
    private void displayExitMessage() {

        System.out.println("\n==============================================");
        System.out.println("Thank you for using the Library Management System.");
        System.out.println("Goodbye!");
        System.out.println("==============================================");
    }

    /* ============================================================
   Input Helper Methods
   ============================================================ */

    /**
     * Checks whether the current user is an administrator.
     *
     * @return true if the current user is an administrator
     */
    private boolean isAdmin() {

        return currentRole == UserRole.ADMIN;
    }

    //--------------------------------------------------------------
    /**
    * Returns the role of the currently logged-in user.
    * @return the current user's role
    */
    public UserRole getCurrentRole() {

        return currentRole;
    }

    //--------------------------------------------------------------
    /**
     * Reads and validates the user's menu choice.
     *
     * @return the selected menu option
     */
    public int readMenuChoice(int maxOption) {

        while (true) {

            if (scanner.hasNextInt()) {

                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear the newline character

                if (choice >= 0 && choice <= maxOption) {
                    return choice;
                }

                System.out.printf(
                        "Invalid option. Please enter a number between 0 and %d: ",
                        maxOption);

            } else {

                System.out.print("Invalid input. Please enter a number: ");

                scanner.nextLine(); // Discard invalid input
            }
        }
    }

    //-------------------------------------------------------------
    /**
     * Reads and validates an integer entered by the user.
     *
     * @return a valid integer
     */
    int readInt() {

        while (true) {

            if (scanner.hasNextInt()) {

                int value = scanner.nextInt();
                scanner.nextLine(); // Clear the newline character

                return value;

            } else {

                System.out.print("Invalid input. Please enter a whole number: ");

                scanner.nextLine(); // Discard invalid input
            }
        }
    }

    //-------------------------------------------------------------
    /**
     * Reads and validates a non-empty string entered by the user.
     *
     * @return a valid string
     */
    public String readString() {

        while (true) {

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.print("Input cannot be empty. Please try again: ");
        }
    }

    public boolean confirm(String message) {

        while (true) {

            System.out.print(message + " (Y/N): ");

            String choice = readString().trim().toUpperCase();

            switch (choice) {

                case "Y":
                    return true;

                case "N":
                    return false;

                default:
                    System.out.println("Invalid choice. Please enter Y or N.");
            }
        }
    }

    //-------------------------------------------------------------
    /**
     * Pauses the program until the user presses Enter.
     */
    void pause() {

        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}