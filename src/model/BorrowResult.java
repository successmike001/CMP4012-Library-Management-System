package model;

/**
 * Represents the outcome of a borrow book operation.
 */
public enum BorrowResult {

    SUCCESS,
    BOOK_NOT_FOUND,
    ALREADY_BORROWED
}