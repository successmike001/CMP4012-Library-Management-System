package utilities;

import java.time.Year;

/**
 * ============================================================
 * Validation.java
 * ============================================================
 * Provides reusable validation methods for user input.

 Responsibilities:
 * - Validate individual input values.
 * - Enforce input rules.

 Does NOT handle:
 * - User interaction.
 * - Library management.
 * - File storage.
 * - Object creation.
 * ============================================================
 */
public final class Validation {

    /* ============================================================
       Validation Constants
       ------------------------------------------------------------
       Define reusable validation limits.
       ============================================================ */

    public static final int MIN_YEAR = 1600;
    public static final int MAX_YEAR = Year.now().getValue();

    /* ============================================================
       Constructor
       ------------------------------------------------------------
       Prevent instantiation of this utility class.
       ============================================================ */

    /**
     * Prevents instantiation.
     */
    private Validation() {

    }

    /* ============================================================
       Public Static Validation Methods
       ------------------------------------------------------------
       Validate user input against application rules.
       ============================================================ */

    /**
     * Checks whether a book ID is valid.
     *
     * @param bookID the book ID to validate
     * @return true if the book ID is valid; otherwise false
     */
    public static boolean isValidBookID(int bookID) {

        return bookID > 0;
    }

    /**
     * Checks whether a book title is valid.
     *
     * @param title the title to validate
     * @return true if the title is valid; otherwise false
     */
    public static boolean isValidTitle(String title) {

        return !isNullOrBlank(title);
    }

    /**
     * Checks whether an author's name is valid.
     *
     * @param author the author to validate
     * @return true if the author is valid; otherwise false
     */
    public static boolean isValidAuthor(String author) {

        return !isNullOrBlank(author);
    }

    /**
     * Checks whether a publication year is valid.
     *
     * @param year the publication year to validate
     * @return true if the publication year is valid; otherwise false
     */
    public static boolean isValidPublicationYear(int year) {

        return year >= MIN_YEAR
                && year <= MAX_YEAR;
    }

    /**
     * Checks whether a genre is valid.
     *
     * @param genre the genre to validate
     * @return true if the genre is valid; otherwise false
     */
    public static boolean isValidGenre(String genre) {

        return !isNullOrBlank(genre);
    }

    /**
     * Checks whether a borrower name is valid.
     *
     * @param borrower the borrower name to validate
     * @return true if the borrower name is valid; otherwise false
     */
    public static boolean isValidBorrowerName(String borrower) {

        return !isNullOrBlank(borrower);
    }

    /* ============================================================
       Private Helper Methods
       ------------------------------------------------------------
       Support reusable validation logic.
       ============================================================ */

    /**
     * Checks whether a string is null or blank.
     *
     * @param value the string to check
     * @return true if the string is null or blank; otherwise false
     */
    private static boolean isNullOrBlank(String value) {

        return value == null || value.trim().isEmpty();
    }

}