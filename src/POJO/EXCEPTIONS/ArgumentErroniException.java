package POJO.EXCEPTIONS;

/*
 * Les tres classes principals de POJO extenen de Persona
 * com hem creat una classe DAO general que utilitza Persona
 * pero no es compatible amb Tutelat, si algu ens passa un com argument,
 * llancarem aquest error 
 */
@SuppressWarnings("serial")
public class ArgumentErroniException extends Exception{
	private static String msg = "Esperat objecte Tutor/Professor";
	
	@Override
	public String getMessage(){
		return msg;
	}
	@Override
	public void printStackTrace(){
		System.err.println(msg);
	}
}
