package in.sparklogic.globalexceptions;

/**
 * 
 * This is error code class manage all the custom error code which will help at
 * client application to take more appropriate action based on code.
 * 
 * @author Dhaval Mistry
 * @since 16-May-2022
 * @version 1.0
 */

public class ErrorCodes {

	public enum GeneralErrorCodes {
		MUST_BE_EMPTY(101), OPERATION_NOT_ALLOWED(102), DUPLICATE(103), NOT_FOUND(104), NULL_OR_EMPTY(105),
		ALREADY_EXIST(106), UNKNOWN_ERROR(107), UNAUTHORIZED(108);

		int errorCode;

		GeneralErrorCodes(int errorCode) {
			this.errorCode = errorCode;
		}

		public int getErrorCode() {
			return errorCode;
		}
	}
}
