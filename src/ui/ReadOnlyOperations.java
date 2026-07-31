package ui;

import model.*;
import services.LibraryManager;
import storage.FileHandler;
import utilities.DisplayFormatter;

import java.util.ArrayList;

/**
 ===============================================================
 ReadOnlyOperations.java
 ---------------------------------------------------------------
 Performs all operations that retrieve and display library data.

 This class is responsible for coordinating user input and
 displaying search results and library information.

 Responsibilities:
 • Load library data.
 • Perform search operations.
 • Display library information.

 Does NOT:
 • Modify library data.
 • Save library data.
 • Display the main menu.
 ===============================================================
 */
public class ReadOnlyOperations {

    /* ============================================================
       Private Fields
       ------------------------------------------------------------
       Store the objects required to coordinate read-only
       operations.
       ============================================================ */

    // Provides access to the input helper methods.
    private final Menu menu;

    // Manages all library operations.
    private final LibraryManager libraryManager;

    // Handles loading the library.
    private final FileHandler fileHandler;

    /* ============================================================
       Constructor
       ------------------------------------------------------------
       Creates a ReadOnlyOperations object and initialises
       the required dependencies.
       ============================================================ */

    /**
     * Creates a new ReadOnlyOperations object.
     *
     * @param menu the application's menu
     * @param libraryManager manages the library
     * @param fileHandler handles file storage
     */
    public ReadOnlyOperations(
            Menu menu,
            LibraryManager libraryManager,
            FileHandler fileHandler) {

        this.menu = menu;
        this.libraryManager = libraryManager;
        this.fileHandler = fileHandler;
    }

    /* ============================================================
       Library Persistence
       ============================================================ */

    /**
     * Loads the latest library data from the storage file.
     */
    private void loadLibrary() {

        ArrayList<Book> books = fileHandler.loadBooks();

        libraryManager.setBooks(books);
    }
    //-----------------------------------------------------------------
    /* ============================================================
   Search Operations
   ============================================================ */

    /**
     * Displays the search menu and allows the user to search
     * for books using different criteria.
     */
    public void searchBooks() {

        loadLibrary();

        boolean searching = true;

        while (searching) {

            System.out.println("\n========== Search Books ==========");
            System.out.println("1. Search by Book ID");
            System.out.println("2. Search by Title");
            System.out.println("3. Search by Author");
            System.out.println("4. Search by Genre");
            System.out.println("0. Return to Main Menu");
            System.out.println("==================================");
            System.out.print("Enter your option: ");

            int choice = menu.readInt();

            //=========================//

            switch (choice) {

                case 1:
                    searchByID();
                    break;

                case 2:
                    searchByTitle();
                    break;

                case 3:
                    searchByAuthor();
                    break;

                case 4:
                    searchByGenre();
                    break;

                case 0:
                    searching = false;
                    break;

                default:
                    System.out.println("\nInvalid option. Please try again.");
            }
        }
    }
    //-----------------------------------------------------------
    /**
     * Searches for a book by its ID.
     */
    /**
     * Searches for a book by its ID.
     */
    public void searchByID() {

        System.out.print("\nEnter Book ID: ");
        int bookID = menu.readInt();

        Book book = libraryManager.findBookByID(bookID);

        if (book == null) {

            System.out.println("\nBook not found.");

        } else {

            boolean isAdmin = menu.getCurrentRole() == UserRole.ADMIN;

            DisplayFormatter.displayTableHeader("BOOK FOUND", isAdmin);

            if (isAdmin) {
                System.out.println(book);
            } else {
                System.out.println(book.toMemberString());
            }

            DisplayFormatter.displayTableFooter(isAdmin);
        }

        menu.pause();
    }
    /**
     * Searches for books by title.
     */
    public void searchByTitle() {

        System.out.print("\nEnter Title: ");
        String title = menu.readString();

        displayBooks(
                libraryManager.searchByTitle(title),
                "SEARCH RESULTS",
                "No books found."
        );

        menu.pause();
    }

    //-----------------------------------------------------------
    /**
     * Searches for books by author.
     */
    /**
     * Searches for books by author.
     */
    public void searchByAuthor() {

        System.out.print("\nEnter Author: ");
        String author = menu.readString();

        displayBooks(
                libraryManager.searchByAuthor(author),
                "SEARCH RESULTS",
                "No books found."
        );

        menu.pause();
    }

    /**
     * Searches for books by genre.
     */
    public void searchByGenre() {

        System.out.print("\nEnter Genre: ");
        String genre = menu.readString();

        displayBooks(
                libraryManager.searchByGenre(genre),
                "SEARCH RESULTS",
                "No books found."
        );

        menu.pause();
    }

    /* ============================================================
   Display Operations
   ============================================================ */

    /**
     * Displays every book in the library.
     */
    public void displayAllBooks() {

        loadLibrary();

        displayBooks(
                libraryManager.getBooks(),
                "ALL BOOKS",
                "The library is currently empty."
        );

        menu.pause();
    }

    //-------------------------------------------------------------
    /**
     * Displays all available books.
     */
    public void displayAvailableBooks() {

        loadLibrary();

        displayBooks(
                libraryManager.getAvailableBooks(),
                "AVAILABLE BOOKS",
                "No available books found."
        );

        menu.pause();
    }

    //--------------------------------------------------------------
    /**
     * Displays all borrowed books.
     */
    public void displayBorrowedBooks() {

        loadLibrary();

        displayBooks(
                libraryManager.getBorrowedBooks(),
                "BORROWED BOOKS",
                "No borrowed books found."
        );

        menu.pause();
    }

    /* ============================================================
   Private Helper Methods
   ============================================================ */

    /**
     * Displays a formatted list of books.
     *
     * @param books the books to display
     * @param title the table heading
     * @param emptyMessage the message displayed if no books exist
     */
    private void displayBooks(
            ArrayList<Book> books,
            String title,
            String emptyMessage) {

        if (books.isEmpty()) {

            System.out.println("\n" + emptyMessage);
            return;
        }

        boolean isAdmin = menu.getCurrentRole() == UserRole.ADMIN;

        DisplayFormatter.displayTableHeader(title, isAdmin);

        for (Book book : books) {

            if (isAdmin) {
                System.out.println(book);
            } else {
                System.out.println(book.toMemberString());
            }
        }

        DisplayFormatter.displayTableFooter(isAdmin);
    }
}