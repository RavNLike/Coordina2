package bll.io;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encriptador {

	/*
	 * Métode per a encriptar, generic
	 */
	public String encriptar(String aEncriptar, SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		Cipher AesCipher = Cipher.getInstance("AES");
		AesCipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] byteCipherText = AesCipher.doFinal(aEncriptar.getBytes());
		return encriptatAString(byteCipherText);
	}

	/*
	 * Metode per a desencriptar, generic
	 */
	public String desencriptar(String aDesencriptar, SecretKey key) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		byte[] baits = stringAEncriptat(aDesencriptar);
		Cipher AesCipher = Cipher.getInstance("AES");
		AesCipher.init(Cipher.DECRYPT_MODE, key);
		byte[] bytePlainText = AesCipher.doFinal(baits);
		return new String(bytePlainText);
	}

	/*
	 * Metodes auxiliars per a passar un clau encriptada a String, etc
	 */
	private String encriptatAString(byte[] encriptat) {
		// Mime per a que ignore els simbols que no pot decodificar
		byte[] res = Base64.getMimeEncoder().encode(encriptat);
		return new String(res);
	}

	private byte[] stringAEncriptat(String s) {
		return Base64.getMimeDecoder().decode(s);
	}

	/*
	 * Este metode obté la clau per a desencriptar la contrasenya del mail la
	 * clau serà el propi mail, ho se, soc un geni
	 */
	public SecretKey generarClau() throws SQLException {
		String clau = LectorFitxers.llegirMail();
		clau = normalitzarClau(clau);
		byte[] encoded = stringAEncriptat(clau);
		SecretKey secretKey = new SecretKeySpec(encoded, "AES");
		// normalitza el nombre de bytes
		byte[] normalitzat = new byte[16];
		byte[] actual = secretKey.getEncoded();
		for (int i = 0; i < 16; i++) {
			normalitzat[i] = actual[i % actual.length];
		}
		SecretKey resultat = new SecretKeySpec(normalitzat, "AES");
		return resultat;
	}

	/*
	 * Versió generic que obte una clau a partir de cualsevol cosa
	 */
	public SecretKey generarClau(String clau) {
		clau = normalitzarClau(clau);
		byte[] encoded = stringAEncriptat(clau);
		SecretKey secretKey = new SecretKeySpec(encoded, "AES");
		// normalitza el nombre de bytes
		byte[] normalitzat = new byte[16];
		byte[] actual = secretKey.getEncoded();
		for (int i = 0; i < 16; i++) {
			normalitzat[i] = actual[i % actual.length];
		}
		SecretKey resultat = new SecretKeySpec(normalitzat, "AES");
		return resultat;
	}

	private String normalitzarClau(String clau) {
		// retorna una clau de 16 digits
		if (clau.length() >= 16) {
			return clau.substring(0, 16);

		} else {
			while (clau.length() < 16) {
				clau += clau.charAt(0);
			}
			return clau;
		}

	}

}