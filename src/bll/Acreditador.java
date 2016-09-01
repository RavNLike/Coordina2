package bll;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import pojo.AlumneTutor;
import pojo.Grup;
import pojo.Professor;
import pojo.Tutelat;


public class Acreditador {

	/**************************************************************
	 * CLASSE SINGLETON AMB METODES PER A GENERAR LES ACREDITACIONS
	 ***************************************************************/

	private static Acreditador INSTANCIA = new Acreditador();
	private final int ACREDITACIONS_PER_PAGINA = 6;

	private Acreditador() {
	}

	public static Acreditador getInstancia() {
		return INSTANCIA;
	}

	public void acreditarTutelats(String desti) throws IOException, DocumentException {
		creaPDFTutelats(desti);
	}

	public void acreditarProfessors(String desti) throws IOException, DocumentException {
		creaPDFProfessors(desti);
	}

	public void acreditarAlumnesTutors(String desti) throws IOException, DocumentException {
		creaPDFTutors(desti);
	}
	
	public void acreditarBlanc(String desti) throws DocumentException, IOException{
		acreditacionsBlanc(desti);
	}

	private void creaPDFProfessors(String desti) throws IOException, DocumentException {

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(desti + "/profes.pdf"));
		System.out.println(desti + "/profes.pdf");
		document.open();

		Font font = new Font(Font.FontFamily.HELVETICA, 12);
		Font gran = new Font(Font.FontFamily.HELVETICA, 15);

		// imatge de fons
		Image image = Image.getInstance(LectorRegistres.getInstancia().getValorRegistre("acreditacions_img"), false);
		// cell.setCellEvent(new ImageBackgroundEvent(image))

		Coordina2 coordina2 = bll.Coordina2.getInstancia();
		ArrayList<Professor> profes = coordina2.llistarProfessors();

		for (Professor prof : profes) {

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
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setBorderWidth(0);
			table.setWidthPercentage(50);

			table.addCell(new CeldaEspecial(prof.getNom().toUpperCase(), font));
			table.addCell(new CeldaEspecial("Professor Tutor", font));
			table.addCell(new CeldaEspecial(grupsToString(coordina2.grupsPerProfessor(prof)), gran));

			taulaexterior.addCell(table);
			taulaexterior.addCell(image);

			document.add(taulaexterior);
		}

		document.close();
	}

	private void creaPDFTutors(String desti) throws IOException, DocumentException {

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(desti + "/alumnestutors.pdf"));
		System.out.println(desti + "/alumnestutors.pdf");
		document.open();

		Font font = new Font(Font.FontFamily.HELVETICA, 12);
		Font gran = new Font(Font.FontFamily.HELVETICA, 15);

		// imatge de fons
		Image image = Image.getInstance(LectorRegistres.getInstancia().getValorRegistre("acreditacions_img"), false);
		// cell.setCellEvent(new ImageBackgroundEvent(image))

		Coordina2 coordina2 = bll.Coordina2.getInstancia();
		ArrayList<AlumneTutor> tutors = coordina2.llistarAlumnesTutors();

		for (AlumneTutor tut : tutors) {
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
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setBorderWidth(0);
			table.setWidthPercentage(50);

			table.addCell(new CeldaEspecial(tut.getNom().toUpperCase(), font));
			table.addCell(new CeldaEspecial("Alumne Tutor", font));
			table.addCell(new CeldaEspecial(grupsToString(coordina2.grupsPerAlumneTutor(tut)), gran));

			taulaexterior.addCell(table);
			taulaexterior.addCell(image);

			document.add(taulaexterior);
		}

		document.close();
	}

	private void creaPDFTutelats(String desti) throws IOException, DocumentException {

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(desti + "/tutelats.pdf"));
		System.out.println(desti + "/tutelats.pdf");
		document.open();

		Font font = new Font(Font.FontFamily.HELVETICA, 12);
		Font gran = new Font(Font.FontFamily.HELVETICA, 15);

		// imatge de fons
		Image image = Image.getInstance(LectorRegistres.getInstancia().getValorRegistre("acreditacions_img"), false);
		// cell.setCellEvent(new ImageBackgroundEvent(image))

		Coordina2 coordina2 = bll.Coordina2.getInstancia();
		ArrayList<Tutelat> tutelats = coordina2.llistarTutelats();

		for (Tutelat tute : tutelats) {

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
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setBorderWidth(0);
			table.setWidthPercentage(50);

			table.addCell(new CeldaEspecial(tute.getCognoms() + ", " + tute.getNom().toUpperCase(), font));
			table.addCell(new CeldaEspecial("Tutelat", font));
			table.addCell(new CeldaEspecial(tute.getGrup_patu().getNom(), gran));

			taulaexterior.addCell(table);
			taulaexterior.addCell(image);

			document.add(taulaexterior);
		}

		document.close();
	}

	// funcio creada per a obtindre acreditacions en blanc
	private void acreditacionsBlanc(String desti) throws DocumentException, IOException {

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(desti + "/blanc.pdf"));
		System.out.println(desti + "/blanc.pdf");
		document.open();

		Font font = new Font(Font.FontFamily.HELVETICA, 12);
		Font gran = new Font(Font.FontFamily.HELVETICA, 15);

		// imatge de fons
		Image image = Image.getInstance(LectorRegistres.getInstancia().getValorRegistre("acreditacions_img"), false);
		// cell.setCellEvent(new ImageBackgroundEvent(image))

		Coordina2 coordina2 = bll.Coordina2.getInstancia();
		ArrayList<Professor> profes = coordina2.llistarProfessors();

		for (int i = 0; i<ACREDITACIONS_PER_PAGINA;i++) {

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
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setBorderWidth(0);
			table.setWidthPercentage(50);

			table.addCell(new CeldaEspecial("\t\t\t", font));
			table.addCell(new CeldaEspecial("\t\t\t", font));
			table.addCell(new CeldaEspecial("\t\t\t", gran));

			taulaexterior.addCell(table);
			taulaexterior.addCell(image);

			document.add(taulaexterior);
		}

		document.close();

	}

	private String grupsToString(ArrayList<Grup> grups) {
		String text = "";
		for (Grup g : grups) {
			text += g.getNom() + ", ";
		}
		return text.substring(0, (text.length() - 2));

	}
}
