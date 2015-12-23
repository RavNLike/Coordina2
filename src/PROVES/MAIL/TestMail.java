package PROVES.MAIL;

import BLL.MAIL.EnviarCorreu;
import POJO.AlumneTutor;

public class TestMail {
	public static void main (String args[]){
		EnviarCorreu env = new EnviarCorreu();
		AlumneTutor jahel = new AlumneTutor("7896547K", "Jahel", "Carmona Vila", "jacarvi@inf.upv.es");
		System.out.println(env.enviar(jahel, "holi", "provat"));
	}
}
