package in.sparklogic.globalexceptions;

import org.springframework.http.HttpStatus;

/**
 * 
 * This is base exception class to handle all the application level exception
 * along with status and error code to manage exception more controlled way
 * rather than using native exception.
 * 
 * @author Dhaval Mistry
 * @since 16-May-2022
 * @version 1.0
 * 
 */

public class ApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpStatus status;
	private int errorCode;

	public ApplicationException() {
		super();
	}

	public ApplicationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public ApplicationException(String message, HttpStatus status, int errorCode) {
		super(message);
		this.status = status;
		this.errorCode = errorCode;
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public int getStatusCode() {
		return this.status.value();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
