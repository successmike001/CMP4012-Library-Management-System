package services;

import model.Book;

import java.util.ArrayList;

/**
 ===============================================================
 LibraryManager.java
 ---------------------------------------------------------------
 Manages the collection of books within the Library Management
 System.

 This class is responsible for storing, searching, updating,
 borrowing and displaying books while maintaining the integrity
 of the library catalogue.

 Author : Michael Nmerenini
 Module : CMP4011
 ===============================================================
 */

public class LibraryManager {

    /* ============================================================
       Private Fields
       ------------------------------------------------------------
       Store and manage the library's book collection.
       ============================================================ */

    // Stores every book in the library.
    private final ArrayList<Book> books;

    // Separates sections of console output.
    private static final String DIVIDER =
            "-------------------------------------------------------------------" +
                    "---------------------------------------";


    /* ============================================================
       Constructor
       ------------------------------------------------------------
       Creates an empty library ready to store books.
       ============================================================ */

    public LibraryManager() {
        books = new ArrayList<>();
    }


    /* ============================================================
       Getters
       ------------------------------------------------------------
       Provide access to information about the library collection.
       ============================================================ */

    public ArrayList<Book> getBooks() {
        return books;
    }

    public int getTotalBooks() {
        return books.size();
    }


    /* ============================================================
       Core Library Operations
       ------------------------------------------------------------
       Perform the main tasks involved in managing the library.
       ============================================================ */

    /**
     * Adds a new book to the library.
     * @param book is the book to add.
     * @return is true if the book was added successfully; otherwise false.
     */
    public boolean addBook(Book book) {

        // Stop duplicate Book IDs.
        if (bookIDExists(book.getBookID())) {
            return false;
        }

        books.add(book);

        return true;
    }

    /**
     * Removes a book from the library.
     * @param bookID is the Book ID to remove.
     * @return true if the book was removed; otherwise false.
     */
    public boolean removeBook(int bookID) {

        Book book = findBookByID(bookID);

        // Stop if the book cannot be found.
        if (book == null) {
            return false;
        }

        books.remove(book);

        return true;
    }

    /**
     * Updates the details of an existing book.
     * @param currentBookID - The current Book ID.
     * @param newBookID - The new Book ID.
     * @param newTitle - The updated title.
     * @param newAuthor - The updated author.
     * @param newPublicationYear - The updated publication year.
     * @param newGenre - The updated genre.
     * @return true if the update was successful; otherwise false.
     */
    public boolean updateBook(int currentBookID,
                              int newBookID,
                              String newTitle,
                              String newAuthor,
                              int newPublicationYear,
                              String newGenre) {

        Book book = findBookByID(currentBookID);

        // Stop if the book does not exist.
        if (book == null) {
            return false;
        }

        // Prevent duplicate Book IDs.
        if (currentBookID != newBookID && bookIDExists(newBookID)) {
            return false;
        }

        book.setBookID(newBookID);
        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        book.setYear(newPublicationYear);
        book.setGenre(newGenre);

        return true;
    }

    /* ============================================================
       Search Operations
       ------------------------------------------------------------
       Locate books using different search criteria.
       ============================================================ */

    /**
     * Finds a book using its unique Book ID.
     * @param bookID - The Book ID to search for.
     * @return The matching Book object, or null if not found.
     */
    public Book findBookByID(int bookID) {

        for (Book book : books) {

            if (book.getBookID() == bookID) {
                return book;
            }
        }

        return null;
    }

    /**
     * Searches for books whose titles contain the specified text.
     * The search is case-insensitive.
     * @param title The title to search for.
     * @return A list of matching books.
     */
    public ArrayList<Book> searchByTitle(String title) {

        ArrayList<Book> results = new ArrayList<>();

        for (Book book : books) {

            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(book);
            }
        }

        return results;
    }

    /**
     * Searches for books written by a particular author.
     * The search is case-insensitive.
     * @param author The author to search for.
     * @return A list of matching books.
     */
    public ArrayList<Book> searchByAuthor(String author) {

        ArrayList<Book> results = new ArrayList<>();

        for (Book book : books) {

            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(book);
            }
        }

        return results;
    }

    /**
     * Searches for books belonging to a particular genre.
     * The search is case-insensitive.
     * @param genre The genre to search for.
     * @return A list of matching books.
     */
    public ArrayList<Book> searchByGenre(String genre) {

        ArrayList<Book> results = new ArrayList<>();

        for (Book book : books) {

            if (book.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                results.add(book);
            }
        }

        return results;
    }


    /* ============================================================
       Borrowing Operations
       ------------------------------------------------------------
       Manage the borrowing and returning of books.
       ============================================================ */

    /**
     * Borrows a book from the library.
     * @param bookID The Book ID of the book to borrow.
     * @param borrowerName The name of the borrower.
     * @return true if the book was borrowed successfully; otherwise false.
     */
    public boolean borrowBook(int bookID, String borrowerName) {

        Book book = findBookByID(bookID);

        // Stop if the book does not exist.
        if (book == null) {
            return false;
        }

        // Let the Book object handle the borrowing.
        return book.borrowBook(borrowerName);
    }

    /**
     * Returns a borrowed book to the library.
     * @param bookID The Book ID of the book to return.
     * @return true if the book was returned successfully; otherwise false.
     */
    public boolean returnBook(int bookID) {

        Book book = findBookByID(bookID);

        // Stop if the book does not exist.
        if (book == null) {
            return false;
        }

        // Let the Book object handle the return.
        return book.returnBook();
    }


    /* ============================================================
       Display Operations
       ------------------------------------------------------------
       Display library information in a clear, tabular format.
       ============================================================ */

    /**
     * Displays every book in the library.
     */
    public void displayAllBooks() {

        // Stop if the library is empty.
        if (books.isEmpty()) {
            System.out.println("\nThe library currently contains no books.");
            return;
        }

        displayTableHeader("LIBRARY CATALOGUE");

        for (Book book : books) {
            System.out.println(book);
        }

        displayTableFooter();

        System.out.println("Total Books: " + books.size());
    }

    /**
     * Displays all books that are available for borrowing.
     */
    public void displayAvailableBooks() {

        boolean availableBooksFound = false;

        displayTableHeader("AVAILABLE BOOKS");

        for (Book book : books) {

            if (book.isAvailable()) {
                System.out.println(book);
                availableBooksFound = true;
            }
        }

        displayTableFooter();

        if (!availableBooksFound) {
            System.out.println("No available books were found.");
        }
    }

    /**
     * Displays all books that are currently on loan.
     */
    public void displayBorrowedBooks() {

        boolean borrowedBooksFound = false;

        displayTableHeader("BORROWED BOOKS");

        for (Book book : books) {

            if (!book.isAvailable()) {
                System.out.println(book);
                borrowedBooksFound = true;
            }
        }

        displayTableFooter();

        if (!borrowedBooksFound) {
            System.out.println("No borrowed books were found.");
        }
    }

    /**
     * Displays the results of a search.
     * @param results The list of matching books.
     */
    public void displaySearchResults(ArrayList<Book> results) {

        System.out.println("\nSearch complete.");

        // Stop if no matches were found.
        if (results.isEmpty()) {
            System.out.println("No matching books were found.");
            return;
        }

        System.out.println(results.size() + " matching book(s) found.");

        displayTableHeader("SEARCH RESULTS");

        for (Book book : results) {
            System.out.println(book);
        }

        displayTableFooter();

        System.out.println("Matches Found: " + results.size());
    }


    /* ============================================================
       Helper Methods
       ------------------------------------------------------------
       Support the internal operations of the LibraryManager class.
       ============================================================ */

    /**
     * Displays the heading used for library tables.
     *
     * @param title The title displayed above the table.
     */
    private void displayTableHeader(String title) {

        System.out.println("\n========================= " + title + " =========================");

        System.out.printf("%-8s %-30s %-20s %-6s %-15s %-12s %-20s%n",
                "ID",
                "Title",
                "Author",
                "Year",
                "Genre",
                "Status",
                "Borrower");

        System.out.println(DIVIDER);
    }

    /**
     * Displays the closing divider for library tables.
     */
    private void displayTableFooter() {
        System.out.println(DIVIDER);
    }

    /**
     * Checks whether a Book ID already exists.
     * @param bookID The Book ID to check.
     * @return true if the Book ID exists; otherwise false.
     */
    private boolean bookIDExists(int bookID) {

        return findBookByID(bookID) != null;
    }

}