package sait.frms.exception;

/**
 * An exception that is thrown when there is an invalid citizenship error.
 * @author Allyssa Preece
 * @author Aiden Kopec
 * @author Anusone Soula
 * @version March 27,2022
 */
public class InvalidCitizenshipException extends Exception {
    /**
     * Creates an exception object to handle invalid citizenship
     */
    public InvalidCitizenshipException() {
        System.out.println("Invalid Citizenship");
    }
}
