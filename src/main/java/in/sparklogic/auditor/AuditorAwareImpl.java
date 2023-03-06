package in.sparklogic.auditor;

// https://programmingmitra.blogspot.in/2017/02/automatic-jpa-auditing-persisting-audit-logs-automatically-using-entityListeners.html

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class AuditorAwareImpl implements AuditorAware<String> {

//	@Override
//	public User getCurrentAuditor() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication == null) {
//			return null;
//		}
//		return (User) authentication.getDetails();
//	}
	
	@Override
	public String getCurrentAuditor() {
		return getLoggedInUserName();
	}
	
	private String getLoggedInUserName() {
		String val = null;
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Object obj = authentication != null ? authentication.getPrincipal() : null;
			if (obj != null) {
				val = (String)obj;
			}
		} catch (Exception ex) {
			// Intentionally throwing exception as here as it does not make
			// sense.
			System.out.println(ex.getMessage());

		}
		return val;
	}
}