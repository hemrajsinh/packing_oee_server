package in.sparklogic.globalexceptions;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.sparklogic.params.ExceptionResponseDTO;



/**
 * This class act as intercepter advice to catch application level exception and
 * log it.
 * 
 * @author Dhaval Mistry
 * @since 16-May-2022
 * @version 1.0
 * 
 */

@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(value = ApplicationException.class)
	public ResponseEntity<ExceptionResponseDTO> handleGenericNotFoundException(ApplicationException e) {
		ExceptionResponseDTO error = new ExceptionResponseDTO(e.getStatus(), e.getStatusCode(), e.getErrorCode(),
				e.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(error, e.getStatus());
	}
}
