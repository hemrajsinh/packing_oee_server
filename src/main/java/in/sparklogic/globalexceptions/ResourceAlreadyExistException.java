package in.sparklogic.globalexceptions;

import org.springframework.http.HttpStatus;

/**
 * 
 * This exception class is use to handle any object which already exist and have
 * to inform client application about the issue.
 * 
 * @author Dhaval Mistry
 * @since 16-May-2022
 * @version 1.0
 * 
 */
public class ResourceAlreadyExistException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceAlreadyExistException(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}

	public ResourceAlreadyExistException(String message, HttpStatus status, int errorCode) {
		super(message, status, errorCode);
	}

}
