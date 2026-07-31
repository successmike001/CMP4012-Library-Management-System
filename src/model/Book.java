package model;

import utilities.DisplayFormatter;
import java.util.Objects;

/**
 ===============================================================
 Book.java
 ---------------------------------------------------------------
 Represents a single book in the Library Management System.

 Each Book object stores its own details and manages its
 borrowing status, allowing the rest of the application to
 interact with it through well-defined methods.
 ===============================================================
 */

public class Book {

    /* ============================================================
       Private Fields
       ------------------------------------------------------------
       Store the information associated with each book while
       preserving encapsulation.
       ============================================================ */

    // Must remain unique for every book.
    private int bookID;

    private String title;
    private String author;
    private int year;
    private String genre;
    private String status;
    private String borrower;

    /* ============================================================
       Constructor
       ------------------------------------------------------------
       Creates a fully initialised Book object.
       ============================================================ */

    public Book(int bookID,
                String title,
                String author,
                int year,
                String genre,
                String status,
                String borrower) {

        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.status = status;

        if ("Available".equalsIgnoreCase(status)) {
            this.borrower = "-";
        } else {
            this.borrower = borrower;
        }
    }

    /* ============================================================
       Getters and Setters
       ------------------------------------------------------------
       Provide controlled access to the book's private fields.
       ============================================================ */

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {

        this.status = status;

        if ("Available".equalsIgnoreCase(status)) {
            borrower = "-";
        }
    }

    public String getBorrower() {

        if (borrower == null || borrower.isBlank()) {
            return "-";
        }

        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    /* ============================================================
       Book Operations
       ------------------------------------------------------------
       Define the behaviour associated with borrowing and returning
       a book.
       ============================================================ */

    /**
     * Checks whether the book is available for borrowing.
     * @return is true if the book is available; otherwise false.
     */
    public boolean isAvailable() {
        return "Available".equalsIgnoreCase(status);
    }

    /**
     * Borrows the book if it is currently available.
     * @param memberName is assigned to name of the borrower.
     * @return true if the book was borrowed successfully; otherwise false.
     */
    public boolean borrowBook(String memberName) {

        // Stop if the book is already on loan.
        if (!isAvailable()) {
            return false;
        }

        // Stop if no borrower name was supplied.
        if (memberName == null
                || memberName.trim().isEmpty()
                || "-".equals(memberName.trim())) {

            return false;
        }

        status = "Borrowed";
        borrower = memberName.trim();

        return true;
    }

    /**
     * Returns the book to the library.
     * @return is true if the return was successful; otherwise false.
     */
    public boolean returnBook() {

        // Stop if the book is already available.
        if (isAvailable()) {
            return false;
        }

        status = "Available";
        borrower = "-";

        return true;
    }
    //----------------------------------------------------------------
    /**
     * Shortens text that exceeds the specified width.
     * @param text the text to shorten
     * @param maxLength the maximum permitted length
     * @return the original text if it fits; otherwise a truncated version
     */
    private String truncate(String text, int maxLength) {

        if (text == null) {
            return "-";
        }

        if (text.length() <= maxLength) {
            return text;
        }

        return text.substring(0, maxLength - 3) + "...";
    }

    /* ============================================================
       Overridden Methods
       ------------------------------------------------------------
       Provide meaningful behaviour when displaying and comparing
       Book objects.
       ============================================================ */

    /**
     * Returns a formatted representation of the book.
     * @return Formatted book information.
     */
    @Override
    public String toString() {

        return String.format(
                "%-" + DisplayFormatter.ID_WIDTH + "d " +
                        "%-" + DisplayFormatter.TITLE_WIDTH + "s " +
                        "%-" + DisplayFormatter.AUTHOR_WIDTH + "s " +
                        "%-" + DisplayFormatter.YEAR_WIDTH + "d " +
                        "%-" + DisplayFormatter.GENRE_WIDTH + "s " +
                        "%-" + DisplayFormatter.STATUS_WIDTH + "s " +
                        "%-" + DisplayFormatter.BORROWER_WIDTH + "s",
                bookID,
                truncate(title, DisplayFormatter.TITLE_WIDTH),
                truncate(author, DisplayFormatter.AUTHOR_WIDTH),
                year,
                truncate(genre, DisplayFormatter.GENRE_WIDTH),
                status,
                truncate(borrower, DisplayFormatter.BORROWER_WIDTH)
        );
    }

    //------------------------------------------------------------------
    public String toMemberString() {

        return String.format(
                "%-8d %-40s %-30s %-6d %-25s %-15s",
                bookID,
                truncate(title, DisplayFormatter.TITLE_WIDTH),
                truncate(author, DisplayFormatter.AUTHOR_WIDTH),
                year,
                truncate(genre, DisplayFormatter.GENRE_WIDTH),
                status
        );
    }

    //------------------------------------------------------------------
    /**
     * Compares two Book objects using their unique Book ID.
     * @param obj is the object to compare.
     * @return is true if both objects represent the same book; otherwise false.
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Book)) {
            return false;
        }

        Book other = (Book) obj;

        return this.bookID == other.bookID;
    }

    /**
     * Generates a hash code based on the Book ID.
     * @return Hash code for this Book object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(bookID);
    }
}