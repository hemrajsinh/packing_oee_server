package in.sparklogic.auditor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties({ "dtInsDate", "stInsUser", "stInsTerm" })
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CreateAuditable<U> {

	@CreatedBy
	@Column(name="stInsUser")
	protected String stInsUser;

	@CreatedDate
	@Column(name="dtInsDate")
	protected Date dtInsDate;
	
	@Column(name="stInsTerm", nullable = true, updatable = false)
	protected String stInsTerm;

}