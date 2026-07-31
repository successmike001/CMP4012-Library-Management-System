package ui;

import model.*;
import services.LibraryManager;
import storage.FileHandler;
import utilities.Validation;
import exceptions.OperationCancelledException;


import java.util.ArrayList;

/**
 ===============================================================
 ReadWriteOperations.java
 ---------------------------------------------------------------
 Performs all operations that modify the library data.

 This class is responsible for coordinating user input,
 library management and file persistence for operations that
 add, update, borrow, return or remove books.

 Responsibilities:
 • Load library data.
 • Save library data.
 • Perform read-write library operations.

 Does NOT:
 • Display the main menu.
 • Handle search or display-only operations.
 ===============================================================
 */
public class ReadWriteOperations {

    /* ============================================================
       Private Fields
       --------------------------------------------------------------
       Store the objects required to coordinate read-write
       operations.
       ============================================================ */

    // Provides access to the input helper methods.
    private final Menu menu;

    // Manages all library operations.
    private final LibraryManager libraryManager;

    // Handles loading and saving the library.
    private final FileHandler fileHandler;

    /* ============================================================
       Constructor
       -------------------------------------------------------------
       Creates a ReadWriteOperations object and initialises
       the required dependencies.
       ============================================================ */

    /**
     * Creates a new ReadWriteOperations object.
     *
     * @param menu the application's menu
     * @param libraryManager manages the library
     * @param fileHandler handles file storage
     */
    public ReadWriteOperations(
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

    //-------------------------------------------------------------

    /**
     * Saves the current library data to the storage file.
     */
    private void saveLibrary() {

        fileHandler.saveBooks(libraryManager.getBooks());
    }

    /* ============================================================
   Read-Write Operations
   ============================================================ */

    /**
     * Adds a new book to the library.
     */
    /**
     * Adds a new book to the library.
     */
    public void addBook() {

        try {

            loadLibrary();

            System.out.println("\n========== Add New Book ==========");

            int bookID = readValidBookID();
            String title = readValidTitle();
            String author = readValidAuthor();
            int year = readValidPublicationYear();
            String genre = readValidGenre();

            Book book = new Book(
                    bookID,
                    title,
                    author,
                    year,
                    genre,
                    "Available",
                    "-"
            );

            AddResult result = libraryManager.addBook(book);

            switch (result) {

                case SUCCESS:
                    saveLibrary();
                    System.out.println("\nBook added successfully.");
                    break;

                case DUPLICATE_BOOK_ID:
                    System.out.println("\nUnexpected error: Duplicate Book ID detected.");
                    break;
            }

        } catch (OperationCancelledException e) {

            System.out.println("\nAdd Book operation cancelled.");

        } finally {

            menu.pause();
        }
    }

    /* ============================================================
   Input Validation Helpers
   ============================================================ */

    /**
     * Reads and validates a unique Book ID.
     *
     * @return a valid Book ID
     */
    private int readValidBookID() {

        while (true) {

            System.out.print("Enter Book ID: ");
            int id = menu.readInt();

            if (!Validation.isValidBookID(id)) {

                System.out.println("\nError: Book ID must be greater than zero.");

                if (!menu.confirm("Try entering the Book ID again?")) {
                    throw new OperationCancelledException();
                }

                continue;
            }

            if (libraryManager.bookIDExists(id)) {

                System.out.println("\nError: Book ID already exists.");

                if (!menu.confirm("Try another Book ID?")) {
                    throw new OperationCancelledException();
                }

                continue;
            }

            return id;
        }
    }

    /**
     * Reads and validates the book title.
     *
     * @return a valid title
     */
    private String readValidTitle() {

        while (true) {

            System.out.print("Enter Title: ");

            String title = menu.readString();

            if (Validation.isValidTitle(title)) {
                return title;
            }

            System.out.println("\nError: Title cannot be empty.");

            if (!menu.confirm("Try entering the title again?")) {
                throw new OperationCancelledException();
            }
        }
    }

    /**
     * Reads and validates the author name.
     *
     * @return a valid author
     */
    private String readValidAuthor() {

        while (true) {

            System.out.print("Enter Author: ");

            String author = menu.readString();

            if (Validation.isValidAuthor(author)) {
                return author;
            }

            System.out.println("\nError: Author name cannot be empty.");

            if (!menu.confirm("Try entering the author name again?")) {
                throw new OperationCancelledException();
            }
        }
    }

    /**
     * Reads and validates the publication year.
     *
     * @return a valid publication year
     */
    private int readValidPublicationYear() {

        while (true) {

            System.out.print("Enter Publication Year: ");

            int year = menu.readInt();

            if (Validation.isValidPublicationYear(year)) {
                return year;
            }

            System.out.println(
                    "\nError: Publication year must be between "
                            + Validation.MIN_YEAR
                            + " and "
                            + Validation.MAX_YEAR
                            + ".");

            if (!menu.confirm("Try entering the publication year again?")) {
                throw new OperationCancelledException();
            }
        }
    }

    /**
     * Reads and validates the genre.
     *
     * @return a valid genre
     */
    private String readValidGenre() {

        while (true) {

            System.out.print("Enter Genre: ");

            String genre = menu.readString();

            if (Validation.isValidGenre(genre)) {
                return genre;
            }

            System.out.println("\nError: Genre cannot be empty.");

            if (!menu.confirm("Try entering the genre again?")) {
                throw new OperationCancelledException();
            }
        }
    }

    //------------------------------------------------------------
    /**
     * Removes a book from the library.
     */
    public void removeBook() {

        loadLibrary();

        System.out.println("\n========== Remove Book ==========");

        System.out.print("Enter Book ID: ");
        int bookID = menu.readInt();

        RemoveResult result = libraryManager.removeBook(bookID);

        switch (result) {

            case SUCCESS:

                saveLibrary();
                System.out.println("\nBook removed successfully.");
                break;

            case BOOK_NOT_FOUND:

                System.out.println("\nBookID not found.");
                break;
        }

        menu.pause();
    }

    //------------------------------------------------------------
    /**
     * Updates the details of an existing book.
     */
    public void updateBook() {

        loadLibrary();

        System.out.println("\n========== Update Book ==========");

        System.out.print("Enter Book ID: ");
        int bookID = menu.readInt();
        String title = readValidTitle();
        String author = readValidAuthor();
        int year = readValidPublicationYear();
        String genre = readValidGenre();

        UpdateResult result = libraryManager.updateBook(
                bookID,
                title,
                author,
                year,
                genre
        );

        switch (result) {

            case SUCCESS:

                saveLibrary();
                System.out.println("\nBook updated successfully.");
                break;

            case BOOK_NOT_FOUND:

                System.out.println("\nNo book exists with that Book ID.");
                break;

            case INVALID_DATA:

                System.out.println("\nThe updated book details are invalid.");
                break;
        }

        menu.pause();
    }
    //-------------------------------------------------------------
    /**
     * Borrows a book from the library.
     */
    public void borrowBook() {

        loadLibrary();

        System.out.println("\n========== Borrow Book ==========");

        System.out.print("Enter Book ID: ");
        int bookID = menu.readInt();

        System.out.print("Enter Borrower's Name: ");
        String borrower = menu.readString();

        BorrowResult result = libraryManager.borrowBook(bookID, borrower);

        switch (result) {

            case SUCCESS:

                saveLibrary();
                System.out.println("\nBook borrowed successfully.");
                break;

            case BOOK_NOT_FOUND:

                System.out.println("\nNo book exists with that Book ID.");
                break;

            case ALREADY_BORROWED:

                System.out.println("\nThis book is already borrowed.");
                break;
        }

        menu.pause();
    }
    //------------------------------------------------------------
    /**
     * Returns a borrowed book to the library.
     */
    public void returnBook() {

        loadLibrary();

        System.out.println("\n========== Return Book ==========");

        System.out.print("Enter Book ID: ");
        int bookID = menu.readInt();

        System.out.print("Enter Your Name: ");
        String borrower = menu.readString();

        ReturnResult result = libraryManager.returnBook(bookID,  borrower);

        switch (result) {

            case SUCCESS:

                saveLibrary();
                System.out.println("\nBook returned successfully.");
                break;

            case BOOK_NOT_FOUND:

                System.out.println("\nNo book exists with that Book ID.");
                break;

            case ALREADY_RETURNED:

                System.out.println("\nThis book has already been returned.");
                break;

            case NOT_BORROWER:

                System.out.println(
                        "\nOnly the member who borrowed this book can return it.");
                break;
        }

        menu.pause();
    }

}