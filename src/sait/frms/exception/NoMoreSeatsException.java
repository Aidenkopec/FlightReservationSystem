package sait.frms.exception;

/**
 * An exception that is thrown when there are no seats left on the flight.
 * @author Allyssa Preece
 * @author Aiden Kopec
 * @author Anusone Soula
 * @version March 27,2022
 */
public class NoMoreSeatsException extends Exception {
    /**
     * Creates exception object for no more seats on the flight
     */
    public NoMoreSeatsException() {
        System.out.println("There are no more seats on that flight");
    }
}
