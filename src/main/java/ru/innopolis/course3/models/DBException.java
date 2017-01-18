package ru.innopolis.course3.models;

/**
 * @author Danil Popov
 */
public class DBException extends RuntimeException {

    public DBException() {
        super("Sorry, something went wrong, try later");
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(Throwable cause) {
        super(cause);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
