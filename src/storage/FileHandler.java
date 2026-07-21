package storage;

import model.Book;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ============================================================
 * FileHandler
 * ============================================================
 * Handles the persistent storage of library data by saving
   and loading Book objects from a CSV file.

 * Responsibilities:
 * - Save books to a CSV file.
 * - Load books from a CSV file.
 * - Convert between Book objects and CSV records.

 * Does NOT handle:
 * - User interaction.
 * - Book management.
 * - Input validation.
 * ============================================================
 */
public class FileHandler {

    /* ============================================================
       Private Constants
       ------------------------------------------------------------
       Store the file configuration used by the FileHandler class.
       ============================================================ */

    private static final String FILE_NAME = "library.csv";

    private static final String HEADER =
            "BookID,Title,Author,Year,Genre,Status,Borrower";

    /* ============================================================
   Constructor
   ------------------------------------------------------------
   Initialise the FileHandler and prepare the storage file.
   ============================================================ */

    /**
     * Creates a FileHandler and ensures that the storage file exists.
     */
    public FileHandler() {
        createFileIfMissing();
    }

    /* ============================================================
   Public Save Operations
   ------------------------------------------------------------
   Save library data to persistent storage.
   ============================================================ */

    /**
     * Saves all books to the CSV storage file.
     * @param books The collection of books to save.
     */
    public void saveBooks(ArrayList<Book> books) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {

            writer.println(HEADER);

            for (Book book : books) {
                writer.println(formatBook(book));
            }

        }
        catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    /* ============================================================
   Public Load Operations
   ------------------------------------------------------------
   Load library data from persistent storage.
   ============================================================ */

    /**
     * Loads all books from the CSV storage file.
     * @return A list containing all books stored in the CSV file.
     */
    public ArrayList<Book> loadBooks() {

        ArrayList<Book> books = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {

            // Skip the header
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Read each remaining line
            while (scanner.hasNextLine()) {

                // Convert each line to a Book
                String line = scanner.nextLine();

                // Add the Book to the list
                books.add(parseBook(line));
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error loading books: " + e.getMessage());
        }

        return books;
    }

    /* ============================================================
   Private Data Conversion
   ------------------------------------------------------------
   Convert between Book objects and CSV records.
   ============================================================ */

    /**
     * Converts a Book object into a CSV record.
     * @param book - The book to convert.
     * @return - A CSV representation of the book.
     */
    private String formatBook(Book book) {

        return book.getBookID() + "," +
                book.getTitle() + "," +
                book.getAuthor() + "," +
                book.getYear() + "," +
                book.getGenre() + "," +
                book.getStatus() + "," +
                book.getBorrower();
    }

    /**
     * Converts a CSV record into a Book object.
     * @param line A CSV record representing a book.
     * @return The corresponding Book object.
     */
    private Book parseBook(String line) {

        String[] data = line.split(",");

        int bookID = Integer.parseInt(data[0]);
        String title = data[1];
        String author = data[2];
        int year = Integer.parseInt(data[3]);
        String genre = data[4];
        String status = data[5];
        String borrower = data[6];

        /*-----------------------------------------------------*/
        return new Book(
                bookID,
                title,
                author,
                year,
                genre,
                status,
                borrower
        );
    }
    /* ============================================================
   Private Helper Methods
   ------------------------------------------------------------
   Support file handling operations.
   ============================================================ */

    /**
     * Creates the storage file if it does not already exist.
     */
    private void createFileIfMissing() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {

                writer.println(HEADER);

            } catch (IOException e) {
                System.err.println("Error creating storage file: " + e.getMessage());
            }
        }
    }
}