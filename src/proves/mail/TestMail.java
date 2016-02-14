package proves.mail;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import bll.io.Encriptador;
import bll.io.LectorFitxers;
import bll.mail.EnviarCorreu;
import pojo.AlumneTutor;

public class TestMail {
	static String mail = "beta.integraetsinf@gmail.com";
	static String pass = "alfonsGUAPO";
	
	public static void main (String args[]) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, SQLException{
		EnviarCorreu env = new EnviarCorreu();
		//env.enviar(new AlumneTutor("1", "Jahel", "Carmona", "jacarvi@inf.upv.es"), "provando", "Tinc les pilotes com un egipci");
	}
}
