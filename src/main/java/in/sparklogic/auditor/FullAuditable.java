package in.sparklogic.auditor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "dtUpdDate", "stUpdUser", "stUpdTerm" })
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class FullAuditable<U> extends CreateAuditable<U> {

	@LastModifiedBy
	@Column(name="stUpdUser")
	protected String stUpdUser;

	@LastModifiedDate
	@Column(name="dtUpdDate")
	protected Date dtUpdDate;
	
	@Column(name="stUpdTerm", nullable = true, updatable = false)
	protected String stUpdTerm;

}