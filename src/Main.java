import ui.Menu;

/**
 * ===============================================================
 * Main.java
 * ---------------------------------------------------------------
 * Entry point for the Library Management System.

 * This class is responsible for creating the application's
 * main menu and starting the program.

 * Responsibilities:
 * • Create the Menu object.
 * • Launch the Library Management System.

 * Does NOT:
 * • Manage library data.
 * • Handle user input.
 * • Perform library operations.


 * Authors :
 * Michael Chukwuemeka Nmerenini - ST20350588 (Leader)
 * Krish Rana – ST20351250
 * Srijana Tamata – ST20350508
 * Sadip Baniya – ST20351059
 *
 * Module : Computer Sci. & App. (CMP4012)
 * Assessment : PRAC1
 * ===============================================================
 */
public class Main {

    /* ============================================================
       Main Method
       ------------------------------------------------------------
       Creates the application's menu and starts the program.
       ============================================================ */

    /**
     * Starts the Library Management System.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        final Menu menu = new Menu();

        menu.start();
    }
}