package sait.frms.exception;

/**
 * An exception that is thrown when the name of a person is invalid.
 * @author Allyssa Preece
 * @author Aiden Kopec
 * @author Anusone Soula
 * @version March 27,2022
 */
public class InvalidNameException extends Exception {
    /**
     * Creates an exception object for invalid name
     */
    public InvalidNameException() {
        System.out.println("InvalidNameException thrown. User has inputted an invalid name.");
    }
}
