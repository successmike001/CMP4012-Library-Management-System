package utilities;

/**
 * ===============================================================
 * DisplayFormatter.java
 * ---------------------------------------------------------------
 * Provides reusable methods for displaying formatted tables and
 * headings throughout the Library Management System.
  * ===============================================================
 */

public class DisplayFormatter {

    /* ============================================================
       Table Column Widths
       ============================================================ */

    public static final int ID_WIDTH = 8;
    public static final int TITLE_WIDTH = 40;
    public static final int AUTHOR_WIDTH = 30;
    public static final int YEAR_WIDTH = 6;
    public static final int GENRE_WIDTH = 25;
    public static final int STATUS_WIDTH = 15;
    public static final int BORROWER_WIDTH = 17;

    /* ============================================================
       Private Constants
       ============================================================ */

    private static final String ADMIN_DIVIDER =
            "-".repeat(ID_WIDTH
                    + TITLE_WIDTH
                    + AUTHOR_WIDTH
                    + YEAR_WIDTH
                    + GENRE_WIDTH
                    + STATUS_WIDTH
                    + BORROWER_WIDTH
                    + 6);

    private static final String MEMBER_DIVIDER =
            "-".repeat(ID_WIDTH
                    + TITLE_WIDTH
                    + AUTHOR_WIDTH
                    + YEAR_WIDTH
                    + GENRE_WIDTH
                    + STATUS_WIDTH
                    + 23);

    /* ============================================================
       Display Methods
       ============================================================ */

    /**
     * Displays a formatted table heading.
     *
     * @param title the table title
     * @param showBorrower true for Admin view, false for Member view
     */
    public static void displayTableHeader(String title,
                                          boolean showBorrower) {

        System.out.print("===================================================================================================================================================");
        System.out.printf("\n%65s", title);
        System.out.print("\n===================================================================================================================================================\n");

        if (showBorrower) {

            System.out.printf(
                    "%-" + ID_WIDTH + "s " +
                            "%-" + TITLE_WIDTH + "s " +
                            "%-" + AUTHOR_WIDTH + "s " +
                            "%-" + YEAR_WIDTH + "s " +
                            "%-" + GENRE_WIDTH + "s " +
                            "%-" + STATUS_WIDTH + "s " +
                            "%-" + BORROWER_WIDTH + "s%n",
                    "ID",
                    "Title",
                    "Author",
                    "Year",
                    "Genre",
                    "Status",
                    "Borrower");

            System.out.println(ADMIN_DIVIDER);

        } else {

            System.out.printf(
                    "%-" + ID_WIDTH + "s " +
                            "%-" + TITLE_WIDTH + "s " +
                            "%-" + AUTHOR_WIDTH + "s " +
                            "%-" + YEAR_WIDTH + "s " +
                            "%-" + GENRE_WIDTH + "s " +
                            "%-" + STATUS_WIDTH + "s%n",
                    "ID",
                    "Title",
                    "Author",
                    "Year",
                    "Genre",
                    "Status");

            System.out.println(MEMBER_DIVIDER);
        }
    }

    /**
     * Displays the closing divider.
     *
     * @param showBorrower true for Admin view, false for Member view
     */
    public static void displayTableFooter(boolean showBorrower) {

        if (showBorrower) {
            System.out.println(ADMIN_DIVIDER);
        } else {
            System.out.println(MEMBER_DIVIDER);
        }
    }
}