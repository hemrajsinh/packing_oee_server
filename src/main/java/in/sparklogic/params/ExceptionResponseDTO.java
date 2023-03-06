package in.sparklogic.params;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * This DTO holds properties related to exception data for REST API back/forth
 * transfer
 * 
 * @author Dhaval Mistry
 * @since 16-May-2022
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseDTO {

	private HttpStatus status;
	private int statusCode;
	private int errorCode;
	private String message;
	private LocalDateTime dateTime;
}
