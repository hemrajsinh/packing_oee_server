package in.sparklogic.globalexceptions;

import org.springframework.http.HttpStatus;

/**
 * 
 * This exception class is use to inform caller/client application about
 * requested resource is not available.
 * 
 * @author Dhaval Mistry
 * @since 16-May-2022
 * @version 1.0
 * 
 */
public class ResourceNotFoundException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String message, HttpStatus status) {
		super(message, status);
	}

	public ResourceNotFoundException(String message, HttpStatus status, int errorCode) {
		super(message, status, errorCode);
	}
}
