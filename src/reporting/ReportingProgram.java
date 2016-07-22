package reporting;



import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class ReportingProgram {
	
	File logFilePath;
	
	
	public ReportingProgram(File logFilePath)
	{
		this.logFilePath = logFilePath;
	}
	
	
	
	
	
	
	
	public void createPDF() throws FileNotFoundException, DocumentException
	{
		Document document = new Document();
		//Setting page to A4
		document.setPageSize(PageSize.A4.rotate());
		
		PdfWriter.getInstance(document, new FileOutputStream("D://Hello.pdf"));
		
		document.open();
		
		try {
			Image hwRacingLogo = Image.getInstance("HWRacing.png");
			hwRacingLogo.scaleAbsolute(340, 30);
			
			Image hwLogo = Image.getInstance("hw.png");
			
			hwLogo.scaleAbsolute(90, 45);
			hwLogo.setAbsolutePosition(710, 525);
			document.add(hwRacingLogo);
			document.add(hwLogo);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		document.add(generateHeader());
		document.add(generateTable());
		document.close();
		
	
	}
	
	private Paragraph generateHeader()
	{
			FileReader fileReader;
			String [] tempStrings = new String[4];
			
			com.itextpdf.text.Font headerFont = FontFactory.getFont("Gill Sans MT",12,BaseColor.BLACK);
			
			try {
				fileReader = new FileReader(logFilePath);
				BufferedReader br = new BufferedReader(fileReader);
				

				for (int i = 0; i<4;i++)
				{
					tempStrings[i] = br.readLine();
					
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			Paragraph paragraph = new Paragraph("HWRacing Testing Session" + System.getProperty( "line.separator" )  + tempStrings[1] + System.getProperty( "line.separator" ) + tempStrings[2] + System.getProperty( "line.separator" )+ tempStrings[3] + System.getProperty( "line.separator"),headerFont);
		
			return paragraph;
			
			
			
			


			

		
		
	}

	private PdfPTable generateTable()
	{
		//Creating Table
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		
		BaseColor color = new BaseColor(117,219,255 );
				
		com.itextpdf.text.Font headerFont = FontFactory.getFont("Gill Sans MT",11,BaseColor.BLACK);
		com.itextpdf.text.Font cellFont = FontFactory.getFont("Gill Sans MT",10,BaseColor.BLACK);
		
		table.addCell(new Paragraph("Time",headerFont));
		table.addCell(new Paragraph("Team Member",headerFont));
		table.addCell(new Paragraph("System/Area",headerFont));
		table.addCell(new Paragraph("Comment",headerFont));
		table.setHeaderRows(1);
		PdfPCell [] headerCells =  table.getRow(0).getCells();
		

		
		for (int i =0; i<headerCells.length;i++)
		{
			headerCells[i].setBackgroundColor(color);
			headerCells[i].setHorizontalAlignment(Element.ALIGN_CENTER);
		}
		
		
		
		//Creating fileReader
		try
		{
			
		FileReader fileReader = new FileReader(logFilePath);
		BufferedReader br = new BufferedReader(fileReader);
		String tempLine;
		String toSplitString = new String();
		String [] splitString;
		
			tempLine = br.readLine();
			while(tempLine !=null)
			{
				//Checking if line contains the marker
				if(tempLine.startsWith("|"))
				{

					toSplitString = tempLine;
					tempLine = br.readLine();
					//Checking if comment goes across many lines
					while (tempLine != null && tempLine.startsWith("|") == false)
					{
						toSplitString =  toSplitString + tempLine;
						tempLine = br.readLine();

						}
					toSplitString =  toSplitString.substring(1);
					System.out.println(toSplitString);
					splitString = toSplitString.split("-");
					for (int i=0; i<splitString.length;i++)
					{
						PdfPCell tempCell = new PdfPCell(new Paragraph( splitString[i],cellFont));
						table.addCell(tempCell);
						
					}

				}
				else
				{
					tempLine = br.readLine();
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"The Log File Could Not Be Read.\n PDF Creation was Unsuccessful","PDF Creation",JOptionPane.ERROR_MESSAGE);
		}
		
		
		
		return table;
	}
	

	
}
