package in.sparklogic.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import in.sparklogic.globalexceptions.ErrorCodes;
import in.sparklogic.globalexceptions.GeneralExceptions;
import in.sparklogic.model.User;
import in.sparklogic.params.LoginParams;
import in.sparklogic.repository.UserRepository;

@Component
public class LoginService {

	@Autowired
	Environment springEnvironment;

	@Autowired
	UserRepository userRegistrationRepo;
	
	public User login(LoginParams loginParams) {
		User user = userRegistrationRepo.findByFlgIsDeletedFalseAndStUserNameAndStPassword(loginParams.username,
				loginParams.password);
		if (Objects.isNull(user)) {
			throw GeneralExceptions.invalidUser(User.class, null, ErrorCodes.GeneralErrorCodes.UNAUTHORIZED.getErrorCode())
			.get();
		}
		return user;
	}
	
	public User changePassword(String userName, String password) {
		User user = userRegistrationRepo.findByFlgIsDeletedFalseAndStUserNameAndStPassword(userName,password);
		return user;
	}

	public boolean resetPassword(Long id) {
		boolean isSuccess = false;
		User user = userRegistrationRepo.findById(id);
		if (user != null) {
			user.setStPassword(user.getStUserName());
			userRegistrationRepo.save(user);
//			userRegistrationRepo.refresh(user);
			isSuccess = true;
		}
		return isSuccess;
	}

	public boolean removeUser(Long id) {
		boolean isSuccess = false;
		User user = userRegistrationRepo.findById(id);
		if (user != null) {
			user.setFlgIsDeleted(true);
			userRegistrationRepo.save(user);
//			userRegistrationRepo.refresh(user);
			isSuccess = true;
		}
		return isSuccess;
	}

	public boolean updateRole(Long id, String userRole) {
		boolean isSuccess = false;
		User user = userRegistrationRepo.findById(id);
		if (user != null) {
			user.setStUserType(userRole);
			userRegistrationRepo.save(user);
//			userRegistrationRepo.refresh(user);
			isSuccess = true;
		}
		return isSuccess;
	}
	
	public boolean updatePassword(Long id, String newPassword) {
		boolean isSuccess = false;
		User user = userRegistrationRepo.findById(id);
		if (user != null) {
			user.setStPassword(newPassword);
			userRegistrationRepo.save(user);
//			userRegistrationRepo.refresh(user);
			isSuccess = true;
		}
		return isSuccess;
	}

	
}
