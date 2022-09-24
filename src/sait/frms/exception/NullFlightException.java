package sait.frms.exception;

/**
 * An exception that is thrown when there is no flight found.
 * @author Allyssa Preece
 * @author Aiden Kopec
 * @author Anusone Soula
 * @version March 27,2022
 */
public class NullFlightException extends Exception {
    /**
     * Creates an exception object for invalid flight.
     */
    public NullFlightException() {
        System.out.println("Invalid flight detected!");
    }
}
