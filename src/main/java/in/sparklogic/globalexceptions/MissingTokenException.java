package in.sparklogic.globalexceptions;

import org.springframework.http.HttpStatus;

/**
 * 
 * This exception class is use to inform caller/client application about
 * resource is found to be null.
 * 
 * @author Dhaval Mistry
 * @since 16-May-2022
 * @version 1.0
 * 
 */
public class MissingTokenException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingTokenException(String message) {
		super(message);
	}

	public MissingTokenException(String message, HttpStatus status, int errorCode) {
		super(message, status, errorCode);
	}
}
