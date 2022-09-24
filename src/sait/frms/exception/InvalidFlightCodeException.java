package sait.frms.exception;

/**
 * An exception that is thrown when there is an invalid flight code error.
 * @author Allyssa Preece
 * @author Aiden Kopec
 * @author Anusone Soula
 * @version March 27,2022
 */
public class InvalidFlightCodeException extends Exception {
    /**
     * Creates an exception object for invalid flight code
     */
    public InvalidFlightCodeException() {
        System.out.println("Invalid flight code detected! Skipping flight code...");
    }
}
