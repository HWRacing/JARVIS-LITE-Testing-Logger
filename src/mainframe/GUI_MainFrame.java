package mainframe;


//Importing Libraries
import javax.swing.*;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXStatusBar;

import logger.GUI_Logger;
//import logger.GUI_Logger;
//import fileconfiguration.GUI_FileConfig;
import startprogram.StartProgram;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.*;

public class GUI_MainFrame extends JXFrame{

	//Declaring Global Variables
	
	//Reference to Parent Program
	StartProgram startProgram;
	
	//Status Bar Variables
	GUI_StatusBar status_BAR;
	Thread tStatusBar;
	
	//Logging Variables
	JPanel logger_PAN;
	Thread tLogger;
	
	//Variables which hold where the log file is to be stored
     File logFilePath = new File(System.getProperty("user.home")+ "/JARVIS/");
     File usernamesFilePath = new File(System.getProperty("user.home")+ "/JARVIS/users.txt");
	String testingLocation;
	
	//Menu Bar Variables
	//GUI_MenuBar menu_BAR;
	
	
	public GUI_MainFrame(StartProgram startProgram)
	{
		this.startProgram = startProgram;
		
		//Setting up Windows Look And Feel
		settingWindowsLookAndFeel();
		
		//Initialise GUI
		initialiseGUI();
	}
	
	//Method which sets the swingx envrioment to match the system
	private void settingWindowsLookAndFeel()
	{
		//Setting Windows Look and Feel
				try {
					  UIManager.setLookAndFeel(
					            UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	//Initialise GUI method which creates the current Frame
	private void initialiseGUI()
	{	
			//Setting OS Look and Feel
			settingWindowsLookAndFeel();
			
			//Create FileConfig Window
			GUI_FileConfig fileConfig_FRA = new GUI_FileConfig(this);
			
			//Setting Frame Name
			this.setTitle("JARVIS LITE Testing Logger");
			ImageIcon icon = new ImageIcon("Icon.JPG");
			this.setIconImage(icon.getImage());
			//Setting Maximised
			this.setExtendedState(JXFrame.MAXIMIZED_BOTH);
			this.setMinimumSize(new Dimension(600,800));
			this.setVisible(true);
			
			//Add Panels
			addPanels();
}
	
	//Method to Add Panels to Main Frame
		private void addPanels()
		{
			//Initialising Status Bar
			initaliseStatusBar();
			logger_PAN = new GUI_Logger(this);
			this.add(logger_PAN,BorderLayout.CENTER);
			
		}
		
		private void initaliseStatusBar()
		{
			status_BAR = new GUI_StatusBar(this,testingLocation);
			this.setStatusBar(status_BAR);
			tStatusBar = new Thread(status_BAR);
			tStatusBar.start();
		}
		
		public File getLogFilePath()
		{
			return logFilePath;
		}
		
		public File getUsernamesFilePath()
		{
			return usernamesFilePath;
		}
		
		public void updateFilesPath(File usernamesFilePath,File logFilePath)
		{
			this.usernamesFilePath =usernamesFilePath;
			this.logFilePath =logFilePath;
			
		}

		public void updateTestingLocation(String location) {
			testingLocation = location;
		}
		
		public String getTestingLocation()
		{
			return testingLocation;
		}

		public void closeProgram() {
			// TODO Auto-generated method stub
			System.exit(DISPOSE_ON_CLOSE);
		}
}
