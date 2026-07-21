package model;

import java.util.Objects;

/**
 ===============================================================
   Book.java
 ---------------------------------------------------------------
   This class represents a single book in the Library Management
   System.

   Every Book object stores its own information such as the title,
   author, publication year, genre, availability status and the
   member currently borrowing the book.

   This class was designed so that each book manages its own data
   while allowing the rest of the application to interact with it
   safely through public methods.

   Author : Michael Nmerenini
   Module : CMP4011
 =============================================================== */

public class Book {

    /* ============================================================
       Private Fields
       ------------------------------------------------------------
       All attributes are kept private so they cannot be modified
       directly from outside this class.

       This protects the integrity of each Book object and promotes
       one of the core principles of Object-Oriented Programming:
       encapsulation.
       ============================================================ */

    // This stores the unique identification number assigned to every book.
    // No two books in the library should ever share the same Book ID.
    private int bookID;

    // This stores the title of the book.
    private String title;

    // This stores the name of the book's author.
    private String author;

    // This stores the publication year of the book.
    private int year;

    // This stores the genre or category the book belongs to.
    private String genre;

    // This stores whether the book is currently Available or Borrowed.
    private String status;

    // This stores the name of the member currently borrowing the book.
    // If the book is available, this field remains empty.
    private String borrower;

    /* ============================================================
       Constructor
       ------------------------------------------------------------
       The constructor is called whenever a new Book object is
       created.

       It ensures that every book begins life with all of the
       information needed by the library system, preventing the
       creation of incomplete book records.
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
       These methods provide controlled access to the private
       attributes of the Book class.

       Rather than allowing other classes to access variables
       directly, the application interacts with each attribute
       through these public methods.
       ============================================================ */

    // Returns the unique Book ID whenever another class needs it.
    public int getBookID() {
        return bookID;
    }

    // Updates the Book ID when an authorised correction is required.
    // Duplicate Book IDs are prevented by the LibraryManager class
    // before this method is called.

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    // Returns the title of the book.
    public String getTitle() {
        return title;
    }

    // Updates the book title whenever its details change.
    public void setTitle(String title) {
        this.title = title;
    }

    // Returns the author's name.
    public String getAuthor() {
        return author;
    }

    // Updates the author's name whenever required.
    public void setAuthor(String author) {
        this.author = author;
    }

    // Returns the publication year of the book.
    public int getYear() {
        return year;
    }

    // Updates the publication year when corrections are required.
    public void setYear(int year) {
        this.year = year;
    }

    // Returns the genre assigned to the book.
    public String getGenre() {
        return genre;
    }

    // Updates the genre whenever necessary.
    public void setGenre(String genre) {
        this.genre = genre;
    }

    // Returns the current availability status of the book.
    public String getStatus() {
        return status;
    }

    // Updates the book's availability status.
    public void setStatus(String status) {
        this.status = status;
    }

    // Returns the name of the current borrower.
    public String getBorrower() {
        return borrower;
    }

    // Updates the borrower's name whenever a borrowing record changes.
    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    /* ============================================================
       Book Operations
       ------------------------------------------------------------
       These methods describe the behaviour of a Book object.

       Keeping this behaviour inside the class makes the code easier
       to understand and avoids repeating the same logic throughout
       the application.
       ============================================================ */

    /**
     * Checks whether the selected book is currently available for borrowing.

     * This method is used during the borrowing process before a member
       is allowed to check out a book.

     * @return true if the book is available; otherwise false.
     */
    public boolean isAvailable() {
        return status.equalsIgnoreCase("Available");
    }

    /**
     * Updates the book after a successful borrowing operation.

     * The availability status changes to "Borrowed" and the
       member's name is stored so the system knows who currently
       has possession of the book.
     */
    public void borrowBook(String memberName) {

        status = "Borrowed";
        borrower = memberName;
    }

    /**
     * Updates the book after it has been returned.

     * The status changes back to "Available" and the borrower's
       name is cleared, making the book ready for another member.
     */
    public void returnBook() {

        status = "Available";

        // Clear the borrower record because the book has now
        // been returned to the library.
        borrower = "";
    }

    /* ============================================================
       Overridden Methods
       ------------------------------------------------------------
       These methods customise Java's default behaviour so Book
       objects can be compared and displayed more meaningfully.
       ============================================================ */

    /**
     * Returns a neatly formatted representation of the book.

     * This makes the console output easier to read whenever the
       library catalogue is displayed.

     -*@return Formatted book information.
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

     * Since every Book ID is unique, two books with the same ID
       are treated as the same book regardless of any other fields.
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
     * Generates a hash code using the Book ID.

     * This allows Book objects to work correctly in Java
       collections such as HashSet and HashMap.
     */
    @Override
    public int hashCode() {
        return Objects.hash(bookID);
    }
}