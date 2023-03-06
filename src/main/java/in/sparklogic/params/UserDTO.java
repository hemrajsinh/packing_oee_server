package in.sparklogic.params;

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
public class UserDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String stUserName;
	private String stUserType;
	private String stPassword;
	private String stName;
	private String stEmail;
	private boolean flgIsEmailVerified;
	private String stLogo;
	private String stImage;
	private String stImageOriginalName;
	private String stLogoOriginalName;
	private String stSecurityKey;
	private boolean flgIsActive;
	private boolean flgIsDeleted;
	private String jwtToken;

}