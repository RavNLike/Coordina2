package proves.acreditacions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class ProvesGenerals {
	private static final String DEST = "./temp/eixida.pdf";
	protected static Font font;
	private static String pathimg = "./temp/ets.png";

	public static void main(String[] args) throws IOException, DocumentException {
		System.out.println("Pinta");
		File file = new File(DEST);
		file.getParentFile().mkdirs();
		createPdf(DEST);
	}

	public static void createPdf(String dest) throws IOException, DocumentException {
		font = new Font(FontFamily.HELVETICA, 10);
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(dest));
		;
		document.open();

		Font font = new Font(Font.FontFamily.HELVETICA, 12);

		// imatge de fons
		Image image = Image.getInstance(pathimg, false);
		// cell.setCellEvent(new ImageBackgroundEvent(image))

		for (int i = 0; i < 100; i++) {

			PdfPTable taulaexterior = new PdfPTable(2);

			// taulaexterior.getDefaultCell().setCellEvent(new
			// ImageBackgroundEvent(image));
			taulaexterior.getDefaultCell().setBorderWidthRight(0);
			taulaexterior.getDefaultCell().setBorderWidthLeft(0);
			taulaexterior.setWidthPercentage(45);
			taulaexterior.setHorizontalAlignment(Element.ALIGN_LEFT);
			taulaexterior.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

			// 1 es el nombre de columnes
			PdfPTable table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setBorderWidth(0);
			table.setWidthPercentage(50);

			table.addCell(new CeldaEspecial("Vicent Blanes Selva", font));
			table.addCell(new CeldaEspecial("G01", font));
			table.addCell(new CeldaEspecial("Alumne Tutor", font));

			taulaexterior.addCell(table);
			taulaexterior.addCell(image);

			document.add(taulaexterior);
		}

		document.close();
	}
}
