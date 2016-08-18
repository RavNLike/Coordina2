package bll;


import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class CeldaEspecial extends PdfPCell{

	public CeldaEspecial(String msg, Font font){
		super(new Phrase(msg, font));
		this.setBorderWidth(NO_BORDER);
	}
	
	public CeldaEspecial(PdfPTable taula){
		super(taula);
		this.setBorderWidth(NO_BORDER);
	}
}
