package services;

import model.*;
import utilities.Validation;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * ===============================================================
 * LibraryManager.java
 * ---------------------------------------------------------------
 * Manages the collection of books within the Library Management
 * System.

 Responsibilities:
 * • Store the library collection.
 * • Add, remove and update books.
 * • Search for books.
 * • Manage borrowing and returning.

 Does NOT:
 * • Read user input.
 * • Display menus.
 * • Save or load files.
 * ===============================================================
 */
public class LibraryManager {

    /* ============================================================
       Private Fields
       ============================================================ */

    /**
     * Stores every book in the library.
     */
    private final ArrayList<Book> books;

    /* ============================================================
       Constructor
       ============================================================ */

    /**
     * Creates an empty library.
     */
    public LibraryManager() {

        books = new ArrayList<>();
    }

    /* ============================================================
       Getters
       ============================================================ */

    /**
     * Returns every book currently stored.
     * @return the library collection
     */
    public ArrayList<Book> getBooks() {

        return books;
    }

    //-------------------------------------------------------------

    /**
     * Replaces the current collection with one loaded from storage.
     * @param books books loaded from the CSV file
     */
    public void setBooks(ArrayList<Book> books) {

        this.books.clear();

        if (books != null) {
            this.books.addAll(books);
        }

        sortBooksByID();
    }

    /* ============================================================
        Core Library Operations
       ============================================================ */

    /**
     * Adds a new book to the library.
     * @param book the book to add
     * @return the result of the add operation
     */
    public AddResult addBook(Book book) {

        if (bookIDExists(book.getBookID())) {
            return AddResult.DUPLICATE_BOOK_ID;
        }

        books.add(book);
        sortBooksByID();

        return AddResult.SUCCESS;
    }

    //-------------------------------------------------------------

    /**
     * Removes a book using its Book ID.
     * @param bookID the Book ID
     * @return the result of the remove operation
     */
    public RemoveResult removeBook(int bookID) {

        Book book = findBookByID(bookID);

        if (book == null) {
            return RemoveResult.BOOK_NOT_FOUND;
        }

        books.remove(book);

        return RemoveResult.SUCCESS;
    }

    //-------------------------------------------------------------

    /**
     * Updates an existing book.
     * @param bookID the Book ID
     * @param newTitle updated title
     * @param newAuthor updated author
     * @param newPublicationYear updated publication year
     * @param newGenre updated genre
     * @return the result of the update operation
     */
    public UpdateResult updateBook(
            int bookID,
            String newTitle,
            String newAuthor,
            int newPublicationYear,
            String newGenre) {

        Book book = findBookByID(bookID);

        if (book == null) {
            return UpdateResult.BOOK_NOT_FOUND;
        }

        if (!Validation.isValidTitle(newTitle)
                || !Validation.isValidAuthor(newAuthor)
                || !Validation.isValidPublicationYear(newPublicationYear)
                || !Validation.isValidGenre(newGenre)) {

            return UpdateResult.INVALID_DATA;
        }

        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        book.setYear(newPublicationYear);
        book.setGenre(newGenre);

        return UpdateResult.SUCCESS;
    }

        /* ============================================================
       Search Operations
       ============================================================ */

    /**
     * Finds a book using its Book ID.
     * @param bookID the Book ID to search for
     * @return the matching Book, or null if not found
     */
    public Book findBookByID(int bookID) {

        for (Book book : books) {

            if (book.getBookID() == bookID) {
                return book;
            }
        }

        return null;
    }

    //-------------------------------------------------------------

    /**
     * Searches for books by title.
     * @param title the title to search for
     * @return matching books
     */
    public ArrayList<Book> searchByTitle(String title) {

        ArrayList<Book> results = new ArrayList<>();

        if (!Validation.isValidTitle(title)) {
            return results;
        }

        String search = title.trim().toLowerCase();

        for (Book book : books) {

            if (book.getTitle().toLowerCase().contains(search)) {
                results.add(book);
            }
        }

        return results;
    }

    //-------------------------------------------------------------

    /**
     * Searches for books by author.
     *
     * @param author the author to search for
     * @return matching books
     */
    public ArrayList<Book> searchByAuthor(String author) {

        ArrayList<Book> results = new ArrayList<>();

        if (!Validation.isValidAuthor(author)) {
            return results;
        }

        String search = author.trim().toLowerCase();

        for (Book book : books) {

            if (book.getAuthor().toLowerCase().contains(search)) {
                results.add(book);
            }
        }

        return results;
    }

    //-------------------------------------------------------------

    /**
     * Searches for books by genre.
     *
     * @param genre the genre to search for
     * @return matching books
     */
    public ArrayList<Book> searchByGenre(String genre) {

        ArrayList<Book> results = new ArrayList<>();

        if (!Validation.isValidGenre(genre)) {
            return results;
        }

        String search = genre.trim().toLowerCase();

        for (Book book : books) {

            if (book.getGenre().toLowerCase().contains(search)) {
                results.add(book);
            }
        }

        return results;
    }

    /* ============================================================
       Retrieval Operations
       ============================================================ */

    /**
     * Returns all available books.
     *
     * @return available books
     */
    public ArrayList<Book> getAvailableBooks() {

        ArrayList<Book> availableBooks = new ArrayList<>();

        for (Book book : books) {

            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }

        return availableBooks;
    }

    //-------------------------------------------------------------

    /**
     * Returns all borrowed books.
     *
     * @return borrowed books
     */
    public ArrayList<Book> getBorrowedBooks() {

        ArrayList<Book> borrowedBooks = new ArrayList<>();

        for (Book book : books) {

            if (!book.isAvailable()) {
                borrowedBooks.add(book);
            }
        }

        return borrowedBooks;
    }

    /* ============================================================
       Borrowing Operations
       ============================================================ */

    /**
     * Borrows a book.
     *
     * @param bookID the Book ID
     * @param borrower the borrower's name
     * @return the result of the borrow operation
     */
    public BorrowResult borrowBook(int bookID, String borrower) {

        Book book = findBookByID(bookID);

        if (book == null) {
            return BorrowResult.BOOK_NOT_FOUND;
        }

        if (!book.isAvailable()) {
            return BorrowResult.ALREADY_BORROWED;
        }

        book.borrowBook(borrower);

        return BorrowResult.SUCCESS;
    }

    //-------------------------------------------------------------

    /**
     * Returns a borrowed book.
     *
     * @param bookID the Book ID
     * @return the result of the return operation
     */
    public ReturnResult returnBook(int bookID, String borrower) {

        Book book = findBookByID(bookID);

        if (book == null) {
            return ReturnResult.BOOK_NOT_FOUND;
        }

        if (book.isAvailable()) {
            return ReturnResult.ALREADY_RETURNED;
        }

        if (!book.getBorrower().equalsIgnoreCase(borrower.trim())) {
            return ReturnResult.NOT_BORROWER;
        }

        book.returnBook();

        return ReturnResult.SUCCESS;
    }

    /* ============================================================
       Helper Methods
       ============================================================ */

    /**
     * Checks whether a Book ID already exists.
     *
     * @param bookID the Book ID to check
     * @return true if the Book ID already exists
     */
    public boolean bookIDExists(int bookID) {

        return findBookByID(bookID) != null;
    }

    //-------------------------------------------------------------

    /**
     * Sorts the library by Book ID in ascending order.
     */
    private void sortBooksByID() {

        books.sort(Comparator.comparingInt(Book::getBookID));
    }
}