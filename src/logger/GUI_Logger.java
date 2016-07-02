package logger;
import mainframe.GUI_FileConfig;
import mainframe.GUI_MainFrame;
import reporting.ReportingProgram;

/* This program is a class which extends JPanel which contains the code for the logger
 * 
 * 
 * 
 */

//Import Java Swing and IO Libraries
import javax.swing.*;

import com.itextpdf.text.DocumentException;

import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

public class GUI_Logger extends JPanel implements Runnable {
	
	GUI_MainFrame parent_FRA;
	
	//JPanel for setting Grid Layout for Radio Buttons and Username
	JPanel subPanelForRadioPassword;
	
	//Radio Button Variables and Panel
	JRadioButton [] typeOfLog_RB = new JRadioButton[8];
	ButtonGroup typeOfLog_RBG;
	
	//Username Panel
	JLabel username_LAB;
	JTextField username_TF;
	JButton messageSave_BUT;
	JButton exportPDF_BUT;
	
	//Multiline TextField for Inputting Text
	JPanel userComment_PAN;
	JTextArea userComment_TA;
	JTextArea previousComment_TA;
	JScrollPane previousComment_SP;
	
	//Variables for where to store files
	File partLogFilePath;
	File logFilePath;
	//Font
	Font font = new Font("Gill Sans MT",Font.TRUETYPE_FONT,16);
	
	
	ArrayList<User> users = new ArrayList<User>();
	//Contructor
	
	ReportingProgram reportProgram;
	
	public GUI_Logger(GUI_MainFrame mainframe)
	{
		parent_FRA = mainframe;
		partLogFilePath = parent_FRA.getLogFilePath();

		initialiseGUI();
		readUsernames();
		
			logFilePath = createLogFile();
			
			reportProgram = new ReportingProgram(logFilePath);

	}
	
	
	
	//Initialise Components in GUI
	private void initialiseGUI()
	{
		
		//SubPanel for Radio Buttons and Password
		subPanelForRadioPassword = new JPanel(new GridLayout(1,2));
        subPanelForRadioPassword.add(	initialiseRadioButtons());
	    subPanelForRadioPassword.add(initialiseUsername());
		this.add(initialiseTextArea(),BorderLayout.CENTER);
		this.add(subPanelForRadioPassword, BorderLayout.SOUTH);
	}
	
	private JPanel initialiseRadioButtons()
	{
		//Initalising JPanel for buttons to go into
	   JPanel typeOfLog_PAN = new JPanel(new GridLayout(4,2));
		
		/* Creating Radio Buttons where
		 * 0 = General Technical, 1= Chassis,2=Driver 3= Engine and Drivetrain, 4 = Electrics, 5 = Vehicle Dynamics, 6 = HSE, 7= Logistics 
		 */
		typeOfLog_RBG = new ButtonGroup();
		
		
		typeOfLog_RB[0] = new JRadioButton("General Technical");
		typeOfLog_RB[0].setSelected(true);
		typeOfLog_RB[1] = new JRadioButton("Chassis");
		typeOfLog_RB[2] = new JRadioButton("Driver");
		typeOfLog_RB[3] = new JRadioButton("Engine");
		typeOfLog_RB[4] = new JRadioButton("Electrics");
		typeOfLog_RB[5] = new JRadioButton("Vehicle Dynamics");
		typeOfLog_RB[6] = new JRadioButton("HSE");
		typeOfLog_RB[7] = new JRadioButton("Logistics");
		
		for (int i = 0; i<typeOfLog_RB.length; i++)
		{
			typeOfLog_RB[i].setFont(font);
			typeOfLog_RBG.add(typeOfLog_RB[i]);
			typeOfLog_PAN.add(typeOfLog_RB[i]);
		}
		return typeOfLog_PAN;
		
	}
	
	
	private JPanel initialiseUsername()
	{
		//Initialise Username JPanel for Username
		JPanel username_PAN = new JPanel(new GridLayout(2,2));
		
		//Initialise Label and Text Field and Button for Information
		username_LAB = new JLabel("User:");
		username_LAB.setFont(font);
		username_TF = new JTextField("",10);
		username_TF.setFont(font);
		messageSave_BUT = new JButton("Save");
		messageSave_BUT.setFont(font);
		exportPDF_BUT = new JButton("Export PDF");
		exportPDF_BUT.setFont(font);
		
		//Add Label, Text Field and Button to JPanel
		username_PAN.add(username_LAB);
		username_PAN.add(username_TF);
		username_PAN.add(messageSave_BUT);
		username_PAN.add(exportPDF_BUT);
		
		//Adding Action Listener for the save button
		
		exportPDF_BUT.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						JOptionPane.showMessageDialog(null, "Run");
						try {
							reportProgram.createPDF();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (DocumentException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
		
		messageSave_BUT.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						saveButtonActivated();
					}
				}
				);
		return username_PAN;
	}
	
	private JPanel initialiseTextArea()
	{
		userComment_PAN = new JPanel();
		userComment_PAN.setLayout(new BorderLayout());
		
		//Edible Text Area for User to Type
		userComment_TA = new JTextArea(4,50);
		userComment_TA.setEditable(true);
		userComment_TA.setLineWrap(true);
		userComment_TA.setFont(new Font("Gill Sans MT",Font.TRUETYPE_FONT,14));
		userComment_PAN.add(userComment_TA,BorderLayout.SOUTH);
		
		userComment_TA.addKeyListener(new KeyListener()
				{
					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() ==KeyEvent.VK_ENTER)
					{
						saveButtonActivated();
					}
						
					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
				
			
				});
		
		//Non Edible Text Area for Previously Saved Logs
		previousComment_TA =  new JTextArea(10,50);
		previousComment_TA.setEditable(false);
		previousComment_TA.setLineWrap(true);
		previousComment_TA.setFont(new Font("Gill Sans MT",Font.TRUETYPE_FONT,14));
		previousComment_SP = new JScrollPane(previousComment_TA);
		previousComment_SP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		previousComment_SP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		previousComment_SP.setPreferredSize(new Dimension(300,600));
	    userComment_PAN.add(previousComment_SP,BorderLayout.CENTER);
	return userComment_PAN;	
	}

	private String getDateForFile()
	{
		DateFormat fileDateFormat = new SimpleDateFormat("dd_MM_yy");
		Date dateWhenCreated = new Date();
		return  fileDateFormat.format(dateWhenCreated);
	}
	
	public File createLogFile()
	{
		partLogFilePath = parent_FRA.getLogFilePath();
		String filePathString = partLogFilePath + "//" + "Log_" + getDateForFile() + "_" + parent_FRA.getTestingLocation() +  ".txt";
		logFilePath = new File(filePathString);
		if (logFilePath.exists() == false)
		{
		FileWriter fileCreator;
		try {
			fileCreator = new FileWriter(logFilePath,true);
			Date currentTime = new Date();
			DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			fileCreator.write("HWRacing Test Session" +  System.getProperty( "line.separator" ) + "Testing Location:" + parent_FRA.getTestingLocation()+ System.getProperty( "line.separator" ) + "Testing Date: " +getDateForFile() + System.getProperty( "line.separator" ) + "Time Created: " + timeFormat.format(currentTime) + System.getProperty( "line.separator" ));
			fileCreator.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Problem with the File Save Location Selected. Please Select Another One");
			GUI_FileConfig fileConfig = new GUI_FileConfig(parent_FRA);
			createLogFile();
		}

		}
		return logFilePath;
	}
	
private void writeLogToFile(String textToBeWritten)

{
	FileWriter appendToFile;
		try{
			appendToFile = new FileWriter(logFilePath,true);
			appendToFile.write(textToBeWritten);
			appendToFile.write(System.getProperty( "line.separator" ));
			appendToFile.close();
		}catch (IOException e)
		{
			e.printStackTrace();
		}
}

private String constructStringToBeWritten()
{
	DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	Date currentTime = new Date();
	String time =  timeFormat.format(currentTime);
	
	return "|" +   time+ " - " + usernameValid(username_TF.getText()) + " - " + (checkForRadioButton()) +  " - " + userComment_TA.getText() ;
}

private String usernameValid(String username)
{
 Iterator<User> iter = users.iterator();
 while (iter.hasNext())
 {
	 User tempUser = iter.next();
	 if (tempUser.getUserName().equals(username))
	 {
		 return tempUser.getName() ;
	 }
 }
 return null;
 
}

private boolean checkForText()
{
	if (userComment_TA.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null,"Please enter a comment.","No Comment",JOptionPane.WARNING_MESSAGE);
				return false;
			}
	return true;
}

private String checkForRadioButton()
{
	for (int i =0; i<typeOfLog_RB.length;i++)
	{
		if (typeOfLog_RB[i].isSelected())
		{
			return typeOfLog_RB[i].getText();
		}
	}
	return null;
}

//Method activated when Save Button activated
private void saveButtonActivated()
{
	if (checkForText() == true && usernameValid(username_TF.getText()) != null)
	{
		String textWritten = constructStringToBeWritten();
		writeLogToFile(textWritten);
		printToTextArea(textWritten);
		clearUserCommentArea();
		
	}else if (usernameValid(username_TF.getText()) == null)
	{
		JOptionPane.showMessageDialog(null,"Please enter a valid Username.","Invalid Username",JOptionPane.WARNING_MESSAGE);
	}
}

//Method to clear user comment and username area;
private void clearUserCommentArea()

{
	userComment_TA.setText(" ");
	userComment_TA.setCaretPosition(0);
}

private void printToTextArea(String newComment)
{
	Dimension tempSize = previousComment_TA.getSize();
	tempSize.setSize(tempSize.getHeight()+1,tempSize.getWidth());
	previousComment_TA.setSize(tempSize);
	String previousComTemp = previousComment_TA.getText();
	previousComment_TA.setText(newComment + "\n" + previousComTemp);

}

private void readUsernames()
{
	File usernamesFilePath = parent_FRA.getUsernamesFilePath();
	try {
		FileReader fileReader = new FileReader(usernamesFilePath);
		BufferedReader br = new BufferedReader(fileReader);
		String tempLine;
		
		try {
			while ((tempLine = br.readLine()) != null )
			{
				String [] tempUserString = tempLine.split(",");
				User tempUser = new User(tempUserString[0],tempUserString[1]);
				users.add(tempUser);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Users File Not Found","No Users Files Found",JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
		

	} catch (FileNotFoundException e) {
		JOptionPane.showMessageDialog(null,"Users File Not Found","No Users Files Found",JOptionPane.WARNING_MESSAGE);
		System.exit(0);
	}
	
}


//Runnable 
public void run() {
	//

}

}

