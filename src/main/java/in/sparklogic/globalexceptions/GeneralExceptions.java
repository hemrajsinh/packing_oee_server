package in.sparklogic.globalexceptions;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;

/**
 * 
 * This is general exception class to handle all the application level exception
 * along with status and error code to manage exception more controlled way
 * rather than using native exception.
 * 
 * @author Dhaval Mistry
 * @since 16-May-2022
 * @version 1.0
 * 
 */

public class GeneralExceptions {

	/**
	 * Use this method throw exception whenever any resource is not available in the
	 * system.
	 * 
	 * @param clazz
	 * @param obj
	 * @param errorCode
	 * @return
	 */
	public static Supplier<ResourceNotFoundException> notFound(final Class<?> clazz, final Object obj, int errorCode) {
		return () -> new ResourceNotFoundException(String.format("%s with %s not found", clazz.getSimpleName(), obj),
				HttpStatus.NOT_FOUND, errorCode);
	}
	
	
	public static Supplier<InvalidUserException> invalidUser(final Class<?> clazz, final Object obj, int errorCode) {
		return () -> new InvalidUserException("Invalid User",
				HttpStatus.UNAUTHORIZED, errorCode);
	}
	
	public static Supplier<MissingTokenException> MissingToken(final Class<?> clazz, final Object obj, int errorCode) {
		return () -> new MissingTokenException("Missing Token",
				HttpStatus.NO_CONTENT, errorCode);
	}

	public static Supplier<InvalidTokenException> InvalidToken(final Class<?> clazz, final Object obj, int errorCode) {
		return () -> new InvalidTokenException("Invalid Token",
				HttpStatus.NO_CONTENT, errorCode);
	}


	/**
	 * Use this method to throw exception when resource is already exist in the
	 * system and operation is trying to create same instance again.
	 * 
	 * @param clazz
	 * @param obj
	 * @param errorCode
	 * @return
	 */
	public static Supplier<ResourceAlreadyExistException> alreadyExist(final Class<?> clazz, final Object obj,
			int errorCode) {
		return () -> new ResourceAlreadyExistException(
				String.format("%s with %s is already exist", clazz.getSimpleName(), obj), HttpStatus.BAD_REQUEST,
				errorCode);
	}

	/**
	 * Use this method to throw exception when any object/property should not be
	 * null but found null in received parameter.
	 * 
	 * @param clazz
	 * @param errorCode
	 * @return
	 */
	public static Supplier<ResourceNullException> nullOrEmpty(final Class<?> clazz, int errorCode) {
		return () -> new ResourceNullException(String.format("%s is cannot be null", clazz.getSimpleName()),
				HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
	}

	/**
	 * Use this method to throw exception when any object/property should not have
	 * value but actually it has the value.
	 * 
	 * @param clazz
	 * @param obj
	 * @param errorCode
	 * @return
	 */
	public static Supplier<InvalidRequestException> shouldNotHaveValue(final Class<?> clazz, final Object obj,
			int errorCode) {
		return () -> new InvalidRequestException(
				String.format("%s : %s should be empty", clazz.getSimpleName(), obj), HttpStatus.NOT_ACCEPTABLE,
				errorCode);
	}

	/**
	 * Use this method to throw exception whenever operation is not permit
	 * 
	 * @param clazz
	 * @param obj
	 * @param errorCode
	 * @return
	 */
	public static Supplier<InvalidRequestException> cannotPerformOperation(final Class<?> clazz, final Object obj,
			int errorCode) {
		return () -> new InvalidRequestException(String.format("%s : %s", clazz.getSimpleName(), obj),
				HttpStatus.BAD_REQUEST, errorCode);
	}

	/**
	 * Use this method to throw exception when any object/property should not be
	 * null but found null in received parameter.
	 * 
	 * 
	 * @param clazz
	 * @param obj
	 * @param errorCode
	 * @return
	 */
	public static Supplier<ResourceNullException> nullOrEmpty(final Class<?> clazz, final Object obj, int errorCode) {
		return () -> new ResourceNullException(
				String.format("%s cannot be null for given %s", clazz.getSimpleName(), obj),
				HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
	}
	
	/**
	 * Use this method to throw exception when any object/property should not be
	 * null but found null in received parameter.
	 * 
	 * 
	 * @param clazz
	 * @param obj
	 * @param errorCode
	 * @return
	 */
	public static Supplier<UnknownException> unknownError(final Class<?> clazz, final Object obj, int errorCode) {
		return () -> new UnknownException(
				String.format("%s Unknown error occured %s", clazz.getSimpleName(), obj),
				HttpStatus.BAD_REQUEST, errorCode);
	}
}
