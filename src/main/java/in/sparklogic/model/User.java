package in.sparklogic.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import in.sparklogic.auditor.FullAuditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User  extends FullAuditable<User> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "stUserName", nullable = false)
	private String stUserName;
	
	@Column(name = "stUserType", nullable = false)
	private String stUserType;
	
	@Column(name = "stPassword", nullable = false)
	private String stPassword;
	
	@Column(name = "stName", nullable = false)
	private String stName;
	
	@Column(name = "stEmail")
	private String stEmail;
	
	@Column(name = "flgIsEmailVerified", columnDefinition = "bit default 0")
	private boolean flgIsEmailVerified;
	
	@Column(name = "stLogo")
	private String stLogo;
	
	@Column(name = "stImage")
	private String stImage;
	
	@Column(name = "stImageFormattedName")
	private String stImageFormattedName;
	
	@Column(name = "stLogoFormattedName")
	private String stLogoFormattedName;
	
	@Column(name = "stSecurityKey", nullable = false)
	private String stSecurityKey;
	
	@Column(name = "flgIsActive", columnDefinition = "bit default 1")
	private boolean flgIsActive;

	@Column(name = "flgIsDeleted", columnDefinition = "bit default 0")
	private boolean flgIsDeleted;
	
//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
//	private Set<UserRoleMap> userRoleMaps = new HashSet<UserRoleMap>(0);
	
	public User() {
		flgIsDeleted = false;
	}



	public User(Map<String, Object> map) {
		this.setId(Long.parseLong(map.get("id").toString()));
		this.setStUserName(map.get("stUserName").toString());
		this.setStName(map.get("stName").toString());
		this.setStUserType(map.get("stUserType").toString());
		String email = (String)map.get("stEmail");
		email = email==null?"":email;
		this.setStEmail(email);
		this.setStSecurityKey(map.get("stSecurityKey").toString());
		this.setFlgIsEmailVerified((boolean)map.get("flgIsEmailVerified"));
		this.setFlgIsActive((boolean)map.get("flgIsActive"));
		this.setFlgIsDeleted((boolean)map.get("flgIsDeleted"));
	}
	
	public User(Long inUserId) {
		this.id = inUserId;
	}

}
