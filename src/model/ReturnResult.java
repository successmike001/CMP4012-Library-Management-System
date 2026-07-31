package model;

/**
 * Represents the outcome of a return book operation.
 */
public enum ReturnResult {

    SUCCESS,
    BOOK_NOT_FOUND,
    ALREADY_RETURNED,
    NOT_BORROWER
}