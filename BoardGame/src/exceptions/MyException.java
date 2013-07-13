package exceptions;

public class MyException extends Exception{

	String message = "Invalid coordinates: Please try again";
	private static final long serialVersionUID = 1L;

	public MyException(String s){
		message = s;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return message;
	}
	
}
