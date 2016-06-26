package mainframe;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdesktop.swingx.JXStatusBar;
import mainframe.GUI_MainFrame;


public class GUI_StatusBar extends JXStatusBar implements Runnable {
	
	//Parent Frame Variable
	GUI_MainFrame parent_FRA;

	//Status Bar Variables
		JPanel bar_PAN;
		JLabel time_LAB;
		JLabel location_LAB;
		
		public GUI_StatusBar(GUI_MainFrame parentFrame,String location)
		{
			parent_FRA = parentFrame;
			 initialiseBar();
			 location_LAB.setText("Location: " + location);
		}

		
		private void initialiseBar()
		{
			
			Font barFont_FONT = new Font("Gill Sans MT",Font.TRUETYPE_FONT,14);
			
			//TimeBar
			time_LAB = new JLabel("Time:");
			time_LAB.setFont(barFont_FONT);
			this.add(time_LAB);
			
			//Location Lab
			location_LAB=new JLabel("Not Recording");
			location_LAB.setFont(barFont_FONT);
			this.add(location_LAB);			
		}
		
		public void run() {
			// TODO Auto-generated method stub
			while (true)
			{
				try {
					time_LAB.setText("Time: " + generateTime());
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		private String generateTime()
		{
			Date currentTime = new Date();
			SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
			return formatTime.format(currentTime);
		}
		
}
