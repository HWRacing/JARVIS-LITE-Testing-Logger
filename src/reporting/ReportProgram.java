package reporting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class ReportProgram {

	
	public ReportProgram()
	{
		
	}
	
	
	
	public void createTestingReport(String Location) throws FileNotFoundException
	{
		FileOutputStream fos = new FileOutputStream("testinglog.pdf");
		System.out.println("Hello Creation");
		PdfWriter writer = new PdfWriter(fos);
		
		PdfDocument pdf = new PdfDocument(writer);
		
		Document document = new Document(pdf);
		
		document.add(new Paragraph("Hello World!"));
		
		document.close();
		
	}
	
}
