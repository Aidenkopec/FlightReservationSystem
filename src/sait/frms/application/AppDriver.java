package sait.frms.application;

import sait.frms.gui.*;

import java.io.*;

/**
 * Application driver.
 */
public class AppDriver {
	/**
	 * Entry point to Java application.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// Instantiate the and display the main window;
		MainWindow mainWindow = new MainWindow();
		mainWindow.display();
	}
}
