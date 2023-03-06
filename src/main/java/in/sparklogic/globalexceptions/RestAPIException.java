package in.sparklogic.globalexceptions;
/**
 * custom exception to be used while parsing documents 
 *
 * @author hemant.kumar
 * @version 1.0
 * @since 24-Mar-2022
 *
 *
 */
public class RestAPIException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RestAPIException(String message) {
		super(message);
	}

	public RestAPIException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public RestAPIException(Throwable throwable) {
		super(throwable);
	}
}