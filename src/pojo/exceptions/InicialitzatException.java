package pojo.exceptions;

@SuppressWarnings("serial")
public class InicialitzatException extends Exception {

	private static String msg = "El sistema ja esta inicialitzat!";
	
	@Override
	public String getMessage(){
		return msg;
	}
	
	@Override
	public void printStackTrace(){
		System.err.println(msg);
	}
}
