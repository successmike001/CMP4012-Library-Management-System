# Library Management System

## Overview
This project is a Java console-based Library Management System developed for the Computer Science and Applications module (CMP4012) at Cardiff Metropolitan University.

## Features
- Role-based authentication (Administrator and Member)
- Add, update, remove and search books
- Borrow and return books
- Display all, available and borrowed books
- CSV-based persistent storage
- Input validation and exception handling
- Modular layered architecture

## Technologies Used
- Java
- IntelliJ IDEA
- Git
- GitHub

## Project Structure

```
src
- exceptions
    - operationCancelledExeption (exception)
    
- model
    - AddResult (Enum)
    - BorrowResult (Enum)
    - RemoveResult (Enum)
    - ReturnResult (Enum)
    - UpdateResult (Enum)
    - UserRole (Enum)
    - Book (class)
    
- services
    - LibraryManager (class)
    - LoginManager (class)

- storage
    - FileHandler (class)

- ui
    - Menu (class)
    - ReadOnlyOperations (class)
    -   ReadWriteOperations (class)
    
- utilities
    - DisplayFormatter (class)
    - Validation (class)
    
- Main.java
```


## Author

* Michael C. Nmerenini (Team Leader)
* Krish Rana
* Srijana Tamata
* Sadip Baniya