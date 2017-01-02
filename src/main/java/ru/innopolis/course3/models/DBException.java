package ru.innopolis.course3.models;

/**
 * @author Danil Popov
 */
public class DBException extends Exception {

    public DBException() {
        super("Sorry, something went wrong, try later");
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
