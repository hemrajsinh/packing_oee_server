package in.sparklogic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import in.sparklogic.auditor.FullAuditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AUDIT_TRAIL", indexes = {@Index(columnList = "dtUpdDate")})
@EntityListeners(AuditingEntityListener.class)
public class AuditTrail  extends FullAuditable<User>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "transId", nullable = false)
	private Long transId;
	
	@Column(name = "remarks", nullable = false)
	private String remarks;
	
	@Column(name = "appName", nullable = false)
	private String appName;
	
	@Column(name = "action", nullable = false)
	private String action;
	
	public AuditTrail(Long transId, String remarks) {
		super();
		this.transId = transId;
		this.remarks = remarks;
	}
}
