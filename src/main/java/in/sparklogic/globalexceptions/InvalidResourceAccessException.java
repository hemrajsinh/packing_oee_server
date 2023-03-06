package in.sparklogic.globalexceptions;

import org.springframework.http.HttpStatus;

/**
 * 
 * This exception class is use to handle any invalid access of the resource.
 * 
 * @author Dhaval Mistry
 * @since 16-May-2022
 * @version 1.0
 * 
 */

public class InvalidResourceAccessException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidResourceAccessException(String message) {
		super(message);
	}

	public InvalidResourceAccessException(String message, HttpStatus status) {
		super(message, status);
	}
}
