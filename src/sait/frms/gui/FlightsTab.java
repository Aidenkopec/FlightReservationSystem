package sait.frms.gui;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import sait.frms.manager.*;
import sait.frms.problemdomain.*;

/**
 * This class creates the flight section of the GUI.
 * @author Allyssa Preece
 * @author Aiden Kopec
 * @author Anusone Soula
 * @version March 27,2022
 */
public class FlightsTab extends TabBase {
	// Create a FlightManager instance.
	private final FlightManager flightManager;
	// Create a ReservationManager instance.
	private final ReservationManager reservationManager;

	// Create an JList instance of flights.
	private JList<Flight> flightsList;
	private DefaultListModel<Flight> flightsModel;
	private Flight chosen;

	private JTextField flightField;
	private JTextField airlineField;
	private JTextField dayField;
	private JTextField timeField;
	private JTextField costField;

	private ArrayList<Flight> f;
	private ArrayList<Flight> flightResults;

	/**
	 * Creates the graphical components for flights tab section.
	 *
	 * @param flightManager      Instance of FlightManager.
	 * @param reservationManager Instance of ReservationManager.
	 */
	public FlightsTab(FlightManager flightManager, ReservationManager reservationManager) {
		this.flightManager = flightManager;
		this.reservationManager = reservationManager;

		panel.setLayout(new BorderLayout());

		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel eastPanel = createEastPanel();
		panel.add(eastPanel, BorderLayout.EAST);

		JPanel southPanel = createSouthPanel();
		panel.add(southPanel, BorderLayout.SOUTH);

		JPanel centerPanel = createCenterPanel();
		panel.add(centerPanel, BorderLayout.CENTER);
	}

	/**
	 * Creates the panel that goes in the south.
	 *
	 * @return The south panel of the user interface.
	 */
	private JPanel createSouthPanel() {
		ArrayList<String> airports = new ArrayList<>();
		ArrayList<String> airportList = flightManager.getAirports();

		// Split the list of airports into a String[].
		for (int i = 0; i < airportList.size() - 1; i++) {
			String[] fields = airportList.get(i).split(",");
			airports.add(fields[0]);
		}

		// This String[] ensures that we don't have any raw uses of parameterized classes.
		// Example: Explicitly casting JComboBox<String> is much safer than JComboBox<>.
		String[] airportArray = airports.toArray(new String[0]);
		String[] weekdays = { "Any", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel titlePanel = new JPanel();

		JLabel title = new JLabel("Flight Finder", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 25));
		titlePanel.add(title);

		panel.add(titlePanel, BorderLayout.NORTH);

		JPanel choicePanel = new JPanel();
		choicePanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		JLabel from = new JLabel("From:");
		constraints.gridx = 0;
		constraints.gridy = 0;
		from.setHorizontalAlignment(JLabel.RIGHT);
		choicePanel.add(from, constraints);

		JComboBox<String> fromArea = new JComboBox<>(airportArray);
		fromArea.setEditable(false);
		fromArea.setPreferredSize(new Dimension(400, 25));
		constraints.gridx = 1;
		constraints.gridy = 0;
		choicePanel.add(fromArea, constraints);

		JLabel to = new JLabel("To:");
		to.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 1;
		choicePanel.add(to, constraints);

		JComboBox<String> toArea = new JComboBox<>(airportArray);
		toArea.setEditable(false);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 1;
		choicePanel.add(toArea, constraints);

		JLabel day = new JLabel("Day:");
		day.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 2;
		choicePanel.add(day, constraints);

		JComboBox<String> dayArea = new JComboBox<>(weekdays);
		dayArea.setEditable(false);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 2;
		choicePanel.add(dayArea, constraints);

		JButton findFlights = new JButton("Find Flights");
		constraints.gridy = 3;
		constraints.gridx = 0;
		constraints.ipadx = 100;
		constraints.gridwidth = 2;
		choicePanel.add(findFlights, constraints);

		// Find flights button action listener.
		findFlights.addActionListener(e -> {
			String fromAirport = fromArea.getItemAt(fromArea.getSelectedIndex());
			String toAirport = toArea.getItemAt(toArea.getSelectedIndex());
			String flightDay = dayArea.getItemAt(dayArea.getSelectedIndex());
			
			flightResults = new ArrayList<>();
			f = new ArrayList<>(flightManager.findFlights(fromAirport, toAirport, flightDay));
			flightsModel = new DefaultListModel<>();

			for (Flight flight : f) {
				flightsModel.addElement(flight);
			}
			
			flightsList.setModel(flightsModel);
		});
		panel.add(choicePanel);

		return panel;
	}

	/**
	 * Creates the panel that goes in the east.
	 *
	 * @return The east panel of the user interface.
	 */
	private JPanel createEastPanel() {
		JPanel largePanel = new JPanel();
		largePanel.setLayout(new BorderLayout());

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());

		JLabel title = new JLabel("Reserve", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 25));
		titlePanel.add(title, BorderLayout.NORTH);

		largePanel.add(titlePanel, BorderLayout.NORTH);

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.ipadx = 10;

		JLabel flight = new JLabel("Flight:");
		flight.setHorizontalAlignment(JLabel.RIGHT);
		constraints.insets = new Insets(20, 0, 0, 0);
		constraints.gridx = 0;
		constraints.gridy = 0;
		inputPanel.add(flight, constraints);

		flightField = new JTextField(10);
		flightField.setEditable(false);
		constraints.gridx = 1;
		constraints.gridy = 0;
		inputPanel.add(flightField, constraints);

		JLabel airline = new JLabel("Airline:");
		airline.setHorizontalAlignment(JLabel.RIGHT);
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.gridx = 0;
		constraints.gridy = 1;
		inputPanel.add(airline, constraints);

		airlineField = new JTextField(10);
		airlineField.setEditable(false);
		constraints.gridx = 1;
		constraints.gridy = 1;
		inputPanel.add(airlineField, constraints);

		JLabel day = new JLabel("Day:");
		day.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 2;
		inputPanel.add(day, constraints);

		dayField = new JTextField(10);
		dayField.setEditable(false);
		constraints.gridx = 1;
		constraints.gridy = 2;
		inputPanel.add(dayField, constraints);

		JLabel time = new JLabel("Time:");
		time.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 3;
		inputPanel.add(time, constraints);

		timeField = new JTextField(10);
		timeField.setEditable(false);
		constraints.gridx = 1;
		constraints.gridy = 3;
		inputPanel.add(timeField, constraints);

		JLabel cost = new JLabel("Cost:");
		cost.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 4;
		inputPanel.add(cost, constraints);

		costField = new JTextField(10);
		costField.setEditable(false);
		constraints.gridx = 1;
		constraints.gridy = 4;
		inputPanel.add(costField, constraints);

		JLabel name = new JLabel("Name:");
		name.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 5;
		inputPanel.add(name, constraints);

		JTextField nameField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 5;
		inputPanel.add(nameField, constraints);

		JLabel citizenship = new JLabel("Citizenship:");
		citizenship.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 6;
		inputPanel.add(citizenship, constraints);

		JTextField citizenshipField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 6;
		inputPanel.add(citizenshipField, constraints);

		JButton reserve = new JButton("Reserve");
		constraints.gridx = 0;
		constraints.gridy = 7;
		constraints.gridwidth = 2;
		constraints.insets = new Insets(20, 0, 0, 0);
		inputPanel.add(reserve, constraints);

		// Add action listeners to the buttons.
		reserve.addActionListener(e -> {
			String inputName = nameField.getText();
			String inputCitizenship = citizenshipField.getText();
			Reservation made = reservationManager.makeReservation(chosen, inputName, inputCitizenship);
			JOptionPane.showMessageDialog(null, "Reservation created. Your code is " + made.getCode());
		});
		
		largePanel.setBorder(new EmptyBorder(0, 10, 0, 5));
		largePanel.add(inputPanel, BorderLayout.CENTER);

		return largePanel;
	}

	/**
	 * Creates the panel that goes in the north.
	 *
	 * @return The north panel of the user interface.
	 */
	private JPanel createNorthPanel() {
		JPanel panel = new JPanel();

		JLabel title = new JLabel("Flights", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);

		return panel;
	}

	/**
	 * Creates the panel that goes in the center.
	 *
	 * @return The center panel of the user interface.
	 */
	private JPanel createCenterPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		flightsModel = new DefaultListModel<>();
		flightsList = new JList<>(flightsModel);

		// Ensure that the user can only select one item at a time.
		flightsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.flightsList);
		flightsList.addListSelectionListener(new MyListSelectionListener());

		panel.setBorder(new EmptyBorder(0, 10, 0, 0));
		panel.add(scrollPane);

		return panel;
	}

	private class MyListSelectionListener implements ListSelectionListener {
		/**
		 * A {@link ListSelectionListener} that listens for a value changed
		 * event.
		 *
		 * @param e Event that is called when user selects an item in the JList.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selectedIndex = flightsList.getSelectedIndex();

			for (int i = 0; i < flightsList.getModel().getSize(); i++) {
				flightResults.add(flightsList.getModel().getElementAt(i));
			}

			if (selectedIndex >= 0) {
				String flightCode = flightResults.get(selectedIndex).getCode();
				flightField.setText(flightCode);

				String airline = flightResults.get(selectedIndex).getAirlineName();
				airlineField.setText(airline);

				String date = flightResults.get(selectedIndex).getWeekday();
				dayField.setText(date);

				String time = flightResults.get(selectedIndex).getTime();
				timeField.setText(time);

				double cost = flightResults.get(selectedIndex).getCostPerSeat();
				DecimalFormat decimalFormat = new DecimalFormat("0.00");
				String formattedCost = decimalFormat.format(cost);
				costField.setText(formattedCost + "");
			}
			
			// If no date was found, it would generate an ArrayIndexOutOfBoundsException, but the program will still work.
			// This is just to prevent the program from spamming the console with errors.
			try {
				chosen = flightResults.get(selectedIndex);
			} catch (ArrayIndexOutOfBoundsException ex) {
				// Do nothing.
			}
		}
	}
}
