package model;

import java.util.Objects;

/**
 ===============================================================
 Book.java
 ---------------------------------------------------------------
 Represents a single book in the Library Management System.

 Each Book object stores its own details and manages its
 borrowing status, allowing the rest of the application to
 interact with it through well-defined methods.

 Author : Michael Nmerenini
 Module : CMP4011
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
        this.borrower = borrower;
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
    }

    public String getBorrower() {
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
        return status.equalsIgnoreCase("Available");
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

        status = "Borrowed";
        borrower = memberName;

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
        borrower = "";

        return true;
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
                "%-8d %-30s %-20s %-6d %-15s %-12s %-20s",
                bookID,
                title,
                author,
                year,
                genre,
                status,
                borrower
        );
    }

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