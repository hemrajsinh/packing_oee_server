package in.sparklogic.params;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * This DTO holds properties related to user data for REST API back/forth
 * transfer
 * 
 * @author Dhaval Mistry
 * @since 16-May-2022
 * @version 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ParamsDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String machineId;
	private String lineId;
	private String dashboardType;
	private String daySelection;
	private String fromDate;
	private String shiftSelection;
	private String toDate;
}