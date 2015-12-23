package BLL.MAIL;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import POJO.Persona;


public class EnviarCorreu {

	public EnviarCorreu(){
		
	}
	
	public boolean enviar(Persona desti, String tema, String text){
		String usuari = obtindreUsuari();
		String pass = obtindrePass();
		
		 Properties props = new Properties();
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");

	        Session session = Session.getInstance(props,
	          new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(usuari, pass);
	            }
	          });

	        try {

	            Message missatge = new MimeMessage(session);
	            missatge.setFrom(new InternetAddress(usuari));
	            missatge.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(desti.getCorreu_upv()));
	            missatge.setSubject(tema);
	            missatge.setText(text);

	            Transport.send(missatge);


	        } catch (MessagingException e) {
	            e.printStackTrace();
	            return false;
	        }
	        return true;
	}
	
	/*
	 * TODO EVITAR LA PASS EN TEXT PLA!!!!!
	 */
	private String obtindreUsuari(){
		return "beta.integraetsinf@gmail.com";
	}
	
	private String obtindrePass(){
		return "alfonsGUAPO";
	}
}