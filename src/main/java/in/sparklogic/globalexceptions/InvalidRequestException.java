package in.sparklogic.globalexceptions;

import org.springframework.http.HttpStatus;

/**
 * 
 * This exception class is use to handle any request which found to be invalid
 * 
 * @author Dhaval Mistry
 * @since 16-May-2022
 * @version 1.0
 * 
 */

public class InvalidRequestException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRequestException(String message) {
		super(message);
	}
	
	public InvalidRequestException(String message, HttpStatus status) {
		super(message, status);
	}

	public InvalidRequestException(String message, HttpStatus status, int errorCode) {
		super(message, status, errorCode);
	}

}
