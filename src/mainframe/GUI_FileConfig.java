package mainframe;
import mainframe.GUI_MainFrame;
import startprogram.StartProgram;

//This 


//importing Swing Libraries and other essential Java Libraries
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class GUI_FileConfig extends JDialog{

	//Variables
	GUI_MainFrame parent_FRA;
	JLabel instructions_LAB;
	GUI_FileConfigPanel [] fileConfig_PAN = new GUI_FileConfigPanel[2];
	JButton ok_BUT;
	JButton cancel_BUT;
	JPanel okCancel_PAN;
	JPanel file_PAN;
	File[] files_F = new File[2];

	
	//The constructor of this Frame doesn't serve much purpose as the gui is initialised in a new frame using the run method from the Runnable interface
	//Constructor
	public GUI_FileConfig(GUI_MainFrame parent)
	{
		this.setVisible(false);
		this.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL );
		parent_FRA = parent;
		files_F[0] = parent_FRA.getLogFilePath();
		files_F[1] = parent_FRA.getLogFilePath();
		initialiseGUI();
	}
	
	
	private void initialiseGUI()
	{

	//Setting Layout Manager
	this.setLayout(new BorderLayout());
	file_PAN =new JPanel(new GridLayout(3,1));
	
	//Instructions Label
	instructions_LAB = new JLabel("Enter Test Location and Select the file paths of where files produced by JARVIS LITE are to be stored.");
	instructions_LAB.setFont(new Font("Gill Sans MT",Font.BOLD,16));
	this.add(instructions_LAB,BorderLayout.NORTH);
	
	//Location
	JPanel location_PAN = new JPanel();
	JLabel location_LAB = new JLabel("Testing Location:");
	location_LAB.setFont(new Font("Gill Sans MT",Font.TRUETYPE_FONT,14));
	JTextField location_TF = new JTextField(30);
	location_TF.setFont(new Font("Gill Sans MT",Font.TRUETYPE_FONT,14));
	location_PAN.add(location_LAB);
	location_PAN.add(location_TF,BorderLayout.CENTER);
	file_PAN.add(location_PAN);
	
	//Configuring UsernameFile Path Panel and Border
	fileConfig_PAN[0]= new GUI_FileConfigPanel("Username File",files_F[0]);
	TitledBorder userBorder = BorderFactory.createTitledBorder(null, "Username File", 0,0, new Font("Gill Sans MT",Font.TRUETYPE_FONT,14));
	fileConfig_PAN[0].setBorder(userBorder);
	file_PAN.add(fileConfig_PAN[0]);
	
	//Configuring TestinglogPath
	fileConfig_PAN[1]= new GUI_FileConfigPanel("Testing Log",files_F[1]);
	TitledBorder logBorder = BorderFactory.createTitledBorder(null, "Testing Log Path", 0,0, new Font("Gill Sans MT",Font.TRUETYPE_FONT,14));
	fileConfig_PAN[1].setBorder(logBorder);
	file_PAN.add(fileConfig_PAN[1]);

	this.add(file_PAN);
	
	okCancel_PAN = new JPanel(new GridLayout(1,4));
	ok_BUT = new JButton("OK");
	//Adding Action Listner to OK_Button
	ok_BUT.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					parent_FRA.updateTestingLocation(location_TF.getText());
					for (int i =0;i<files_F.length;i++)
					{
						files_F[i] = fileConfig_PAN[i].getFile();
					}
					JOptionPane.showMessageDialog(null, "The File Paths are: \nUsername File Path: " + files_F[0] + "\nTesting Log File Path: " + files_F[1] , "File Paths", JOptionPane.INFORMATION_MESSAGE, null);
					parent_FRA.updateFilesPath(files_F[0],files_F[1]);
					closeWindow();
				}
		;
			});
	cancel_BUT=new JButton("Cancel");
	cancel_BUT.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					closeWindow();
			}
	});
	//A
	ok_BUT.setFont(new Font("Gill Sans MT",Font.TRUETYPE_FONT,18));
	cancel_BUT.setFont(new Font("Gill Sans MT",Font.TRUETYPE_FONT,18));
	okCancel_PAN.add(new JLabel(""));
	okCancel_PAN.add(new JLabel(""));
	okCancel_PAN.add(ok_BUT);
	okCancel_PAN.add(cancel_BUT);
	okCancel_PAN.add(new JLabel(""));
	okCancel_PAN.add(new JLabel(""));
	
	this.add(okCancel_PAN,BorderLayout.SOUTH);
	this.setTitle("File Paths for JARVIS LITE");
	this.setSize(800,400);
	this.setLocationRelativeTo(parent_FRA);
	this.setVisible(true);

	}
	
	//
	
	private void closeWindow()
	{
		this.dispose();
	}

	}
