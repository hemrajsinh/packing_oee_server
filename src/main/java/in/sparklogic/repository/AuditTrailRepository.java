package in.sparklogic.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import in.sparklogic.model.AuditTrail;


@Repository
public interface AuditTrailRepository extends CustomRepository<AuditTrail, Serializable> {
	
	public AuditTrail findById(Long id);
	public List<AuditTrail> findByStUpdUser(String user);
	public AuditTrail findByStUpdUserAndAppName(String user , String appName);
	
	
}
