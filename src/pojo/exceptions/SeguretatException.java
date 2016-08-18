package pojo.exceptions;

@SuppressWarnings("serial")
public class SeguretatException extends Exception {

	private static String msg = "No pots borra la BD per motius de segurat, "
			+ "edita els registres i fica el forellat a 0";
	
	@Override
	public String getMessage(){
		return msg;
	}
	
	@Override
	public void printStackTrace(){
		System.err.println(msg);
	}
}
