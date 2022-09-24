package sait.frms.manager;

import sait.frms.exception.InvalidCitizenshipException;
import sait.frms.exception.InvalidNameException;
import sait.frms.exception.NoMoreSeatsException;
import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;

import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * The ReservationManager class is a class that manages the reservations.
 * This class is responsible for creating, deleting, and modifying reservations.
 * 
 * @author      Allyssa Preece
 * @author      Anusone Soula
 * @author      Aiden Kopec
 * @version March 27, 2022
 */
public class ReservationManager {
    private final ArrayList<Reservation> reservations = new ArrayList<>();
    private RandomAccessFile randomAccessFile;


    public ReservationManager() {
        try {
            // The ReservationManager random access file binary.
            randomAccessFile = new RandomAccessFile("res/reservations.bin", "rw");
            populateFromBinary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Makes a reservation and saves it to the random access file.
     * The method first checks to see if the selected flight has seats available.
     * If there are seats available, the reservation is made and the seats are decremented.
     * 
     * @param flight      The flight to make the reservation for.
     * @param name        The traveler's name.
     * @param citizenship The traveler's citizenship.
     * @return            The traveler's reservation.
     */
    public Reservation makeReservation(Flight flight, String name, String citizenship) {
        Reservation res = null;
        
        try {
            int seats = getAvailableSeats(flight);
            
            String code = generateReservationCode(flight);
            String airline = flight.getAirlineName();
            double cost = flight.getCostPerSeat();
            boolean active = true;
            
            flight.setNumOfSeatsAvailable(seats - 1);

            if (citizenship == null || citizenship.equals("")) {
                throw new InvalidCitizenshipException();
            }
            if (name == null || name.equals("")){
                throw new InvalidNameException();
            }
            if (seats > 0) {
                res = new Reservation(code, flight.getCode(), airline, name, citizenship, cost, active);
                reservations.add(res);
            }
            // Persist changes to the binary file.
            persist();
        } catch (InvalidCitizenshipException noCitizen) {
            JOptionPane.showMessageDialog(null, "Please enter a valid citizenship.");
        } catch (InvalidNameException noName) {
            JOptionPane.showMessageDialog(null, "Please enter a valid name.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    /**
     * Finds the reservation by the reservation code, airline, or name.
     *
     * @param code    The reservation code.
     * @param airline The airline name.
     * @param name    The traveler's name.
     * @return        The reservation filtered from the search criteria.
     */
    public ArrayList<Reservation> findReservations(String code, String airline, String name) {
        ArrayList<Reservation> foundReservations = new ArrayList<>();
        for (Reservation res : reservations) {
            if (res.getCode().toLowerCase().contains(code.toLowerCase()) && res.getAirline().toLowerCase().contains(airline.toLowerCase()) && res.getName().toLowerCase().contains(name.toLowerCase())){
                foundReservations.add(res);
            }
        }
        return foundReservations;
    }

    /**
     * Finds the reservation by the reservation code.
     * 
     * @param code The reservation code.
     * @return    The reservation filtered by the code.
     */
    public Reservation findReservationByCode(String code) {
        for (Reservation res : reservations) {
            if (res.getCode().equals(code)) {
                return res;
            }
        }
        return null;
    }

    /**
     * Saves all reservation to the binary file.
     * 
     * @throws IOException If the file cannot be written to.
     */
    public void persist() throws IOException {
        randomAccessFile.setLength(0);
        for (Reservation res : reservations) {
            randomAccessFile.writeUTF(res.getCode());
            randomAccessFile.writeUTF(res.getFlightCode());
            randomAccessFile.writeUTF(res.getAirline());
            randomAccessFile.writeUTF(res.getName());
            randomAccessFile.writeUTF(res.getCitizenship());
            randomAccessFile.writeDouble(res.getCost());
            randomAccessFile.writeBoolean(res.isActive());
        }
    }
    
    private int getAvailableSeats(Flight flight) {
        int seats = -10;
        
        try {
            // Throw an exception if the seats available is <= 0.
            if (flight.getSeats() <= 0) {
                throw new NoMoreSeatsException();
            } else {
                seats = flight.getSeats();
            }
        } catch (NoMoreSeatsException noSeats) {
            JOptionPane.showMessageDialog(null,"There are no more seats available");
        }
        
        return seats;
    }

    /**
     * Generates a unique reservation code.
     * 
     * @param flight The flight to generate the code for.
     * @return       The unique reservation code.
     */
    private String generateReservationCode(Flight flight) {
        String generatedCode;
        int number = (int) (Math.random() * 9000 + 1000);
        if (flight.isDomestic()) {
            generatedCode = "D" + number;
        } else {
            generatedCode = "I" + number;
        }
        return generatedCode;
    }

    /**
     * Populates the reservation ArrayList with the reservations from the binary file.
     */
    private void populateFromBinary() {
        try {
            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                String code = randomAccessFile.readUTF();
                String flightCode = randomAccessFile.readUTF();
                String airline = randomAccessFile.readUTF();
                String name = randomAccessFile.readUTF();
                String citizenship = randomAccessFile.readUTF();
                double cost = randomAccessFile.readDouble();
                boolean active = randomAccessFile.readBoolean();
                
                Reservation res = new Reservation(code, flightCode, airline, name, citizenship, cost, active);
                reservations.add(res);
            }
        } catch (IOException e) {
            System.out.println("Error reading from binary file");
        }
    }
}
