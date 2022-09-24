package sait.frms.gui;

import java.awt.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

import sait.frms.manager.*;
import sait.frms.problemdomain.*;

/**
 * Holds the components for the reservations tab.
 * @author Allyssa Preece
 * @author Aiden Kopec
 * @author Anusone Soula
 * @version March 27,2022
 */
public class ReservationsTab extends TabBase {
	/**
	 * Instance of reservation manager.
	 */
	private final ReservationManager reservationManager;
	
	private JList<Reservation> reservationsList;

	private DefaultListModel<Reservation> reservationsModel;
	private ArrayList<Reservation> r;
	private ArrayList<Reservation> reservationResults;
	private JTextField codeField;
	private JTextField flightField;
	private JTextField airlineField;
	private JTextField costField;
	private JTextField nameField;
	private JTextField citizenshipField;
	private JComboBox<Boolean> statusField;
	private final String[] statusList = {"Active", "Inactive"};

	/**
	 * Creates the components for reservations tab.
	 */
	public ReservationsTab(ReservationManager reservationManager) {
		this.reservationManager = reservationManager;
		panel.setLayout(new BorderLayout());
		
		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = createCenterPanel();
		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel eastPanel = createEastPanel();
		panel.add(eastPanel, BorderLayout.EAST);

		JPanel southPanel = createSouthPanel();
		panel.add(southPanel, BorderLayout.SOUTH);
	}

	/**
	 * Creates the south panel.
	 * @return The south panel.
	 */
	private JPanel createSouthPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel titlePanel = new JPanel();

		JLabel title = new JLabel("Search",SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 25));
		titlePanel.add(title);

		panel.add(titlePanel, BorderLayout.NORTH);

		JPanel choicePanel = new JPanel();
		choicePanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		JLabel code = new JLabel("Code:");
		constraints.gridx = 0;
		constraints.gridy = 0;
		code.setHorizontalAlignment(JLabel.RIGHT);
		choicePanel.add(code, constraints);

		JTextField codeArea = new JTextField();
		codeArea.setPreferredSize(new Dimension(400,20));
		constraints.gridx = 1;
		constraints.gridy = 0;
		choicePanel.add(codeArea, constraints);

		JLabel airline = new JLabel("Airline:");
		airline.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 1;
		choicePanel.add(airline, constraints);

		JTextField airlineArea = new JTextField();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 1;
		choicePanel.add(airlineArea, constraints);

		JLabel name = new JLabel("Name:");
		name.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 2;
		choicePanel.add(name, constraints);

		JTextField nameArea = new JTextField();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 2;
		choicePanel.add(nameArea, constraints);

		JButton findReservations = new JButton("Find Reservations");
		constraints.gridy = 3;
		constraints.gridx = 0;
		constraints.ipadx = 100;
		constraints.gridwidth = 2;
		choicePanel.add(findReservations, constraints);
		
		// Find Reservations button listener
		findReservations.addActionListener(e -> {
			String codeInput = codeArea.getText();
			String airlineInput = airlineArea.getText();
			String nameInput = nameArea.getText();
			reservationResults = new ArrayList<>();
			r = new ArrayList<>(reservationManager.findReservations(codeInput, airlineInput, nameInput));
			reservationsModel = new DefaultListModel<>();

			for (Reservation reservation : r) {
				reservationsModel.addElement(reservation);
			}
			reservationsList.setModel(reservationsModel);
		});
		panel.add(choicePanel);

		return panel;
	}

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
		
		JLabel code = new JLabel("Code:");
		code.setHorizontalAlignment(JLabel.RIGHT);
		constraints.insets = new Insets(20, 0, 0, 0);
		constraints.gridx = 0;
		constraints.gridy = 0;
		inputPanel.add(code, constraints);

		codeField = new JTextField(10);
		codeField.setEditable(false);
		constraints.gridx = 1;
		constraints.gridy = 0;
		inputPanel.add(codeField, constraints);

		JLabel flight = new JLabel("Flight:");
		flight.setHorizontalAlignment(JLabel.RIGHT);
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.gridx = 0;
		constraints.gridy = 1;
		inputPanel.add(flight, constraints);

		flightField = new JTextField(10);
		flightField.setEditable(false);
		constraints.gridx = 1;
		constraints.gridy = 1;
		inputPanel.add(flightField, constraints);

		JLabel airline = new JLabel("Airline:");
		airline.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 2;
		inputPanel.add(airline, constraints);

		airlineField = new JTextField(10);
		airlineField.setEditable(false);
		constraints.gridx = 1;
		constraints.gridy = 2;
		inputPanel.add(airlineField, constraints);

		JLabel cost = new JLabel("Cost:");
		cost.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 3;
		inputPanel.add(cost, constraints);

		costField = new JTextField(10);
		costField.setEditable(false);
		constraints.gridx = 1;
		constraints.gridy = 3;
		inputPanel.add(costField, constraints);

		JLabel name = new JLabel("Name:");
		name.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 4;
		inputPanel.add(name, constraints);

		nameField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 4;
		inputPanel.add(nameField, constraints);

		JLabel citizenship = new JLabel("Citizenship:");
		citizenship.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 5;
		inputPanel.add(citizenship, constraints);

		citizenshipField = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 5;
		inputPanel.add(citizenshipField, constraints);

		JLabel status = new JLabel("Status:");
		status.setHorizontalAlignment(JLabel.RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 6;
		inputPanel.add(status, constraints);

		JComboBox<String> statusField = new JComboBox<>(statusList);
		constraints.gridx = 1;
		constraints.gridy = 6;
		inputPanel.add(statusField, constraints);

		JButton update = new JButton("Update");
		constraints.gridx = 0;
		constraints.gridy = 7;
		constraints.gridwidth = 2;
		constraints.insets = new Insets(20,0,0,0);
		inputPanel.add(update, constraints);

		// Update the side panel with the selected reservation information.
		update.addActionListener(e -> {
			String nameInput = nameField.getText();
			String citizenshipInput = citizenshipField.getText();
			Reservation updateRes = reservationManager.findReservationByCode(codeField.getText());
			updateRes.setName(nameInput);
			updateRes.setCitizenship(citizenshipInput);
			updateRes.setActive(Boolean.parseBoolean(Objects.requireNonNull((String) statusField.getSelectedItem()).equals("Active") ? "true" : "false"));
			
			JOptionPane.showMessageDialog(null, "Reservation Updated");
			try {
				reservationManager.persist();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		largePanel.setBorder(new EmptyBorder(0, 10, 0, 5));
		largePanel.add(inputPanel, BorderLayout.CENTER);

		return largePanel;
	}

	/**
	 * Creates the north panel.
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() {
		JPanel panel = new JPanel();
		
		JLabel title = new JLabel("Reservations", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);

		return panel;
	}

	/**
	 * Create the center panel.
	 * @return The center panel.
	 */
	private JPanel createCenterPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());

		reservationsModel = new DefaultListModel<>();
		reservationsList = new JList<>(reservationsModel);

		// User can only select one item at a time.
		reservationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.reservationsList);
		reservationsList.addListSelectionListener(new ReservationsTab.MyListSelectionListener());

		panel.setBorder(new EmptyBorder(0,10,0,0));
		panel.add(scrollPane);
		
		return panel;
	}
	
	private class MyListSelectionListener implements ListSelectionListener {
		/**
		 * Called when user selects an item in the JList.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selectedIndex = reservationsList.getSelectedIndex();

			for (int i = 0; i < reservationsList.getModel().getSize(); i++) {
				reservationResults.add(reservationsList.getModel().getElementAt(i));
			}

			if (selectedIndex >= 0) {
				String resCode = reservationResults.get(selectedIndex).getCode();
				codeField.setText(resCode);
				
				String flightCode = reservationResults.get(selectedIndex).getFlightCode();
				flightField.setText(flightCode);
				
				String airlineName = reservationResults.get(selectedIndex).getAirline();
				airlineField.setText(airlineName);
				
				double cost = reservationResults.get(selectedIndex).getCost();
				DecimalFormat decimalFormat = new DecimalFormat("0.00");
				String formattedCost = decimalFormat.format(cost);
				costField.setText(formattedCost + "");
				
				String passengerName = reservationResults.get(selectedIndex).getName();
				nameField.setText(passengerName);
				
				String citizenship = reservationResults.get(selectedIndex).getCitizenship();
				citizenshipField.setText(citizenship);
				
				boolean status = reservationResults.get(selectedIndex).isActive();
				
				if (status) {
					statusField.setSelectedItem(statusList[0]);
				} else {
					statusField.setSelectedItem(statusList[1]);
				}
			}
		}
	}
}

