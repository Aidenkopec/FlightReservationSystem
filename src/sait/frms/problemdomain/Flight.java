package sait.frms.problemdomain;

import sait.frms.exception.InvalidFlightCodeException;

import java.util.regex.Pattern;

/**
 * Class Flight, which creates, sets and gets the information for Flight objects.
 * @author Allyssa Preece
 * @author Anusone Soula
 * @author Aiden Kopec
 * @version March 27, 2022
 */
public class Flight {
    private final String code;
    private final String airlineName;
    private final String from;
    private final String to;
    private final String weekday;
    private final String time;
    private final int seats;
    private final double costPerSeat;
    private int numOfSeatsAvailable;

    /**
     * Creates a Flight object.
     *
     * @param code        The flight code.
     * @param airlineName Name of the airline.
     * @param from        The departure airport.
     * @param to          The destination airport.
     * @param weekday     The day of the flight.
     * @param time        Time of flight.
     * @param seats       Seats on the flight.
     * @param costPerSeat Cost of each seat.
     */
    public Flight(String code, String airlineName, String from, String to, String weekday, String time, int seats, double costPerSeat) {
        parseCode(code);
        this.code = code;
        this.airlineName = airlineName;
        this.from = from;
        this.to = to;
        this.weekday = weekday;
        this.time = time;
        this.seats = seats;
        this.costPerSeat = Math.round(costPerSeat * 100.00) / 100.00;
        this.numOfSeatsAvailable = seats;
    }

    /**
     * Returns the flight code.
     * @return The flight code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the name of the airline.
     * @return The airline name.
     */
    public String getAirlineName() {
        return airlineName;
    }

    /**
     * Gets the departure airport.
     * @return The departure airport.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Gets the destination airport.
     * @return The destination airport.
     */
    public String getTo() {
        return to;
    }

    /**
     * Returns the day of the flight.
     * @return The day of the week.
     */
    public String getWeekday() {
        return weekday;
    }

    /**
     * Returns the departure time of the flight.
     * @return The departure time.
     */
    public String getTime() {
        return time;
    }

    /**
     * Returns the number of seats on the flight.
     * @return Number of seats.
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Return the cost of each seat.
     * @return Cost of seat.
     */
    public double getCostPerSeat() {
        return costPerSeat;
    }

    /**
     * Checks to see if the flight is domestic
     * @return True if flight is domestic.
     */
    public boolean isDomestic(){
        return to.toUpperCase().startsWith("Y") && from.toUpperCase().startsWith("Y");
    }

    /**
     * Returns the number seats available on the flight.
     * @return Number of available seats.
     */
    public int getNumOfSeatsAvailable() {
        return numOfSeatsAvailable;
    }

    /**
     * Checks and see if the flight code is valid.
     * @param code The flight code to be checked.
     */
    private void parseCode(String code){
        if (Pattern.compile("[A-Z]{2}-[0-9]{4}").matcher(code).matches()) {
            try {
                throw new InvalidFlightCodeException();
            } catch (InvalidFlightCodeException ex) {
                System.out.println("[Flight Information] " + code + "\n" + "[Exception] " + ex + "\n");
            }
        }
    }

    /**
     * Returns the number of available seats on the flight.
     * @return Number of available seats.
     */
    public int getNumOfAvailableSeats() {
        return numOfSeatsAvailable;
    }

    /**
     * Sets the number of available seats on the flight.
     * @param numOfSeatsAvailable Number of available seats on the flight.
     */
    public void setNumOfSeatsAvailable(int numOfSeatsAvailable) {
        this.numOfSeatsAvailable = numOfSeatsAvailable;
    }

    /**
     * Overrides the toString method.
     * @return The object toString.
     */
    @Override
    public String toString() {
        return code +
                ", From: " + from +
                ", To: " + to +
                ", Day: " + weekday +
                ", Cost: " + costPerSeat;
    }
}
