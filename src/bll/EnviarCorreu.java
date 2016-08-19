package bll;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import pojo.Persona;

public class EnviarCorreu {

	public EnviarCorreu() {

	}

	public void enviar(Persona desti, String tema, String text) throws SQLException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, AddressException, MessagingException {
		String usuari = obtindreUsuari();
		String pass = obtindrePass();
		// configuracio sense canvis
		// es necessari habilitar el permis al correu per a aplicacions poc
		// segures

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(usuari, pass);
			}
		});

		Message missatge = new MimeMessage(session);
		missatge.setFrom(new InternetAddress(usuari));
		missatge.setRecipients(Message.RecipientType.TO, InternetAddress.parse(desti.getCorreu_upv()));
		missatge.setSubject(tema);
		missatge.setText(text);

		Transport.send(missatge);

	}

	private String obtindreUsuari() throws SQLException {
		LectorRegistres instancia = LectorRegistres.getInstancia();
		return instancia.llegirMail();
	}

	private String obtindrePass() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, SQLException {
		LectorRegistres instancia = LectorRegistres.getInstancia();
		return instancia.llegirPass();
	}
}