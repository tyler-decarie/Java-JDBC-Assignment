package saitMLS.exceptions;

public class InvalidPhoneNumberException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPhoneNumberException() {
		
	}
	
	public InvalidPhoneNumberException(String message) {
		
		printStackTrace();
		
	}

}
