package sait.frms.manager;

import sait.frms.exception.*;
import sait.frms.problemdomain.*;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Class FlightManager to control the interactions of the flightsTab
 * @author Allyssa Preece
 * @author Aiden Kopec
 * @author Anusone Soula
 * @version March 27,2022
 */
public class FlightManager {
    public final String WEEKDAY_ANY = "Any";
    public final String WEEKDAY_SUNDAY = "Sunday";
    public final String WEEKDAY_MONDAY = "Monday";
    public final String WEEKDAY_TUESDAY = "Tuesday";
    public final String WEEKDAY_WEDNESDAY = "Wednesday";
    public final String WEEKDAY_THURSDAY = "Thursday";
    public final String WEEKDAY_FRIDAY = "Friday";
    public final String WEEKDAY_SATURDAY = "Saturday";

    private static final ArrayList<Flight> flights = new ArrayList<>();
    private final ArrayList<String> airports = new ArrayList<>();
    private static final String AIRPORT_PATH = "res/airports.csv";
    private static final String FLIGHT_PATH = "res/flights.csv";

    /**
     * Creates a FlightManager object and calls methods to populate arraylists for flights and airports
     * @throws FileNotFoundException when none of the source files exist
     */
    public FlightManager() throws FileNotFoundException {
        populateAirports();
        populateFlights();
    }

    /**
     * Gets the list of flights
     * @return flights the list of flights
     */
    public static ArrayList<Flight> getFlights() {
        return flights;
    }

    /**
     * Gets the list of airports
     * @return airports the list of airports
     */
    public ArrayList<String> getAirports() {
        return this.airports;
    }

    /**
     * finds the airport name using the airport code
     * @param code airport code
     * @return airportName name of airport
     */
    public String findAirportByCode(String code) {
        String airportName = "";
        boolean found = false;
        
        for (int i = 0; i < airports.size() - 1; i++) {
            String[] fields = airports.get(i).split(",");
            
            if (fields[0].equals(code)) {
                found = true;
                airportName = fields[1];
            }
        }
        
        if (found) {
            return airportName;
        } else {
            return "Airport not found";
        }
    }

    /**
     * finds flight using the flight code
     * @param code    flight code
     * @return flight the flight matching the flight code
     */
    public static Flight findFlightByCode(String code) {
        Flight flight = null;
        boolean found = false;
        
        try {
            for (Flight f : flights) {
                if (Objects.equals(f.getCode(), code)) {
                    found = true;
                    flight = f;
                }
            }
            if (!found) {
                throw new InvalidFlightCodeException();
            }
        } catch (InvalidFlightCodeException invalidFlightCode) {
            JOptionPane.showMessageDialog(null, "Please enter a valid flight code");
        }
        return flight;
    }

    /**
     * finds all flights matching departure and destination airports and day of the week
     * @param from        departure airport
     * @param to          destination airport
     * @param weekday     day of the week
     * @return flightList list of flights matching the criteria
     */
    public ArrayList<Flight> findFlights(String from, String to, String weekday) {
        ArrayList<Flight> flightList = new ArrayList<>();
        try {
            for (Flight f: flights) {
                if (f.getFrom().equalsIgnoreCase(from) && f.getTo().equalsIgnoreCase(to)){
                    if (weekday.equalsIgnoreCase(WEEKDAY_ANY)) {
                        flightList.add(f);
                    } else if (weekday.equalsIgnoreCase(WEEKDAY_MONDAY) && f.getWeekday().equalsIgnoreCase(weekday)) {
                        flightList.add(f);
                    } else if (weekday.equalsIgnoreCase(WEEKDAY_TUESDAY) && f.getWeekday().equalsIgnoreCase(weekday)) {
                        flightList.add(f);
                    } else if (weekday.equalsIgnoreCase(WEEKDAY_WEDNESDAY) && f.getWeekday().equalsIgnoreCase(weekday)) {
                        flightList.add(f);
                    } else if (weekday.equalsIgnoreCase(WEEKDAY_THURSDAY) && f.getWeekday().equalsIgnoreCase(weekday)) {
                        flightList.add(f);
                    } else if (weekday.equalsIgnoreCase(WEEKDAY_FRIDAY) && f.getWeekday().equalsIgnoreCase(weekday)) {
                        flightList.add(f);
                    } else if (weekday.equalsIgnoreCase(WEEKDAY_SATURDAY) && f.getWeekday().equalsIgnoreCase(weekday)) {
                        flightList.add(f);
                    } else if (weekday.equalsIgnoreCase(WEEKDAY_SUNDAY) && f.getWeekday().equalsIgnoreCase(weekday)) {
                        flightList.add(f);
                    }
                }
            }
            if (flightList.size() == 0 ) {
                throw new NullFlightException();
            }
        } catch (NullFlightException nullFlight) {
            JOptionPane.showMessageDialog(null, "There are no flights found for those destinations at that time");
        }
        return flightList;
    }
    
    /**
     * Populates flight information from the {@code flights.csv} file into an 
     * ArrayList of flight objects.
     * 
     * @throws FileNotFoundException If the resource file does not exist.
     */
    private void populateFlights() throws FileNotFoundException {
        Scanner flightScan = new Scanner(new File(FLIGHT_PATH));
        
        while (flightScan.hasNext()) {
            String line = flightScan.nextLine();
            String[] fields = line.split(",");
            
            String airline = fields[0].substring(0, 2);
            String flightCode = fields[0];
            String airlineFullName = "";

                // Check to see if we have a valid flight code.
                if (Pattern.compile("[A-Z]{2}-[0-9]{4}").matcher(flightCode).matches()) {
                    switch (airline) {
                        case "OA":
                            airlineFullName = "Otto Airlines";
                            break;
                        case "CA":
                            airlineFullName = "Conned Air";
                            break;
                        case "TB":
                            airlineFullName = "Try a Bus Airways";
                            break;
                        case "VA":
                            airlineFullName = "Vertical Airways";
                            break;
                        default:
                    }
                    flights.add(new Flight(fields[0], airlineFullName, fields[1], fields[2], fields[3], fields[4], Integer.parseInt(fields[5]), Math.round(Double.parseDouble(fields[6]) * 100.00) / 100.00));
                }
        }
        flightScan.close();
    }

    /**
     * Populates the airports from the file to the airports arraylist
     */
    private void populateAirports() {
        try {
            Scanner airportScan = new Scanner(new File(AIRPORT_PATH));
            
            while (airportScan.hasNext()) {
                String line = airportScan.nextLine();
                airports.add(line);
            }
            
            airportScan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
