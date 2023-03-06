package in.sparklogic.service;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.sparklogic.globalexceptions.ErrorCodes;
import in.sparklogic.globalexceptions.GeneralExceptions;
import in.sparklogic.model.User;
import in.sparklogic.repository.RoleRepository;
import in.sparklogic.repository.UserRepository;
import in.sparklogic.repository.UserRoleMapRepository;
import in.sparklogic.util.FileUploadUtils;

@Service
public class UserService {

	@Autowired
	UserRepository userRegistrationRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRoleMapRepository userRoleMapRepository;

	@Value("${location.files.attachments}")
	private String imgFileLocation;
	
	public User save(User user, Map<String, MultipartFile> fileObj) {
		User userRegistration = null;
		try {
			User exist = this.userRegistrationRepository.findByStUserName(user.getStUserName());
			if (!Objects.isNull(exist) && false == exist.isFlgIsDeleted()) {
				throw GeneralExceptions.alreadyExist(User.class, "same name",
						ErrorCodes.GeneralErrorCodes.ALREADY_EXIST.getErrorCode()).get();
			}
			
			userRegistration = userRegistrationRepository.save(user);
			if (!Objects.isNull(fileObj) && !fileObj.isEmpty()) {
				
				MultipartFile file = (MultipartFile) fileObj.get("image");
				if(!Objects.isNull(file)){
					String[] imageFile = FileUploadUtils.saveUploadedFileToLocation(file, imgFileLocation + File.separator + "USER" + File.separator + userRegistration.getId());
					user.setStImageFormattedName(imageFile[1]);
					user.setStImage(imageFile[0]);
				}
				
				MultipartFile fileLogo = (MultipartFile) fileObj.get("logo");
				if(!Objects.isNull(fileLogo)){
					String[] imageFile = FileUploadUtils.saveUploadedFileToLocation(fileLogo, imgFileLocation + File.separator + "USER" + File.separator + userRegistration.getId());
					user.setStLogoFormattedName(imageFile[1]);
					user.setStLogo(imageFile[0]);
				}
				user.setId(userRegistration.getId());
				userRegistration = userRegistrationRepository.save(user);
			}
		}catch(IllegalStateException | IOException e){
			e.printStackTrace();
		}
		return userRegistration;
	}

	
	public User update(User user, Map<String, MultipartFile> fileObj) {
		User userRegistration = null;
		try {
			
			User exist = this.userRegistrationRepository.findByStUserName(user.getStUserName());
			if (!Objects.isNull(exist) && false == exist.isFlgIsDeleted() && user.getId() != exist.getId()) {
				throw GeneralExceptions.alreadyExist(User.class, "same name",
						ErrorCodes.GeneralErrorCodes.ALREADY_EXIST.getErrorCode()).get();
			}
			
			if (!Objects.isNull(fileObj) && !fileObj.isEmpty()) {
				
				MultipartFile file = (MultipartFile) fileObj.get("image");
				if(!Objects.isNull(file)){
					String[] imageFile = FileUploadUtils.saveUploadedFileToLocation(file, imgFileLocation + File.separator + "USER" + File.separator + exist.getId());
					user.setStImageFormattedName(imageFile[1]);
					user.setStImage(imageFile[0]);
				}
				
				MultipartFile fileLogo = (MultipartFile) fileObj.get("logo");
				
				if(!Objects.isNull(fileLogo)){
					String[] imageFile = FileUploadUtils.saveUploadedFileToLocation(fileLogo, imgFileLocation + File.separator + "USER" + File.separator + exist.getId());
					user.setStLogoFormattedName(imageFile[1]);
					user.setStLogo(imageFile[0]);
				}
				user.setDtInsDate(exist.getDtInsDate());
				user.setStInsUser(exist.getStInsUser());
				user.setStInsTerm(exist.getStInsTerm());
				user.setId(exist.getId());
				userRegistration = userRegistrationRepository.saveAndFlush(user);
			}else{
				user.setDtInsDate(exist.getDtInsDate());
				user.setStInsUser(exist.getStInsUser());
				user.setStInsTerm(exist.getStInsTerm());
				user.setId(exist.getId());
				userRegistration = userRegistrationRepository.saveAndFlush(user);
			}
		}catch(IllegalStateException | IOException e){
			e.printStackTrace();
		}
		return userRegistration;
	}
	
	public List<User> findAll() {
			return userRegistrationRepository.findByFlgIsDeletedFalseOrderByStUserType();
	}

	public boolean delete(Long id) {
		User userOperation = userRegistrationRepository.findById(id);
		if (Objects.isNull(userOperation)){
			throw GeneralExceptions.notFound(User.class, id, ErrorCodes.GeneralErrorCodes.NOT_FOUND.getErrorCode())
			.get();
		}
		userOperation.setFlgIsDeleted(true);
		userRegistrationRepository.saveAndFlush(userOperation);
		return true;
	}
	
	public boolean deleteFile(Long id, String type) {
		User user = userRegistrationRepository.findById(id);
		if (Objects.isNull(user)){
			throw GeneralExceptions.notFound(User.class, id, ErrorCodes.GeneralErrorCodes.NOT_FOUND.getErrorCode())
			.get();
		}

		String folder = imgFileLocation + File.separator + "USER" + File.separator + user.getId();
		if ("image".equalsIgnoreCase(type)) {
			java.io.File file = new java.io.File(folder + java.io.File.separator + user.getStImageFormattedName());

			if (!Objects.isNull(file)) {
				user.setStImage(null);
				user.setStImageFormattedName(null);
				if (file.exists())
					file.delete();
			}
		} else {
			java.io.File file = new java.io.File(folder + java.io.File.separator + user.getStLogoFormattedName());

			if (file != null) {
				user.setStLogo(null);
				user.setStLogoFormattedName(null);
				if (file.exists())
					file.delete();
			}
		}
		userRegistrationRepository.saveAndFlush(user);
		return true;
	}

	public User getUser(Long id) {
		User userOperation = userRegistrationRepository.findById(id);
		if (Objects.isNull(userOperation)) {
			throw GeneralExceptions.notFound(User.class, id, ErrorCodes.GeneralErrorCodes.NOT_FOUND.getErrorCode())
					.get();
		}
		return userOperation;
	}
	
//	private void createUserToRoleMappings(User dto, User user) {
//		List<Role> availableRoles = roleRepository.findAll();
//		Role roleToBind = dto.getStUserType();
//		List<UserRoleMap> urMaps = userRoleMapRepository.findByUserEmailIgnoreCase(dto.getStEmail());
//
//		// iterating over and creating new mapping list for user to multiple
//		// roles.
//		Optional<Role> role = availableRoles.stream().filter(x -> x.getId() == roleToBind.getId()).findFirst();
//
//		if (role.isPresent()) {
//			long counter = urMaps.stream().filter(x -> x.getRole().getName().equalsIgnoreCase(role.get().getName()))
//					.count();
//			if (counter == 0) {
//				UserRoleMap urMap = new UserRoleMap();
//				urMap.setRole(role.get());
//				urMap.setUser(user);
//				urMaps.add(urMap);
//			}
//		}
//		// save user to role mapping
//		userRoleMapRepository.save(urMaps);
//	}
	
}
