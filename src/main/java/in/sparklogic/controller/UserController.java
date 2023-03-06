package in.sparklogic.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.sparklogic.model.User;
import in.sparklogic.service.UserService;
import in.sparklogic.util.JWTHelper;

@RestController
// @EnableAutoConfiguration
@Scope("request")
@RequestMapping("/user")
public class UserController {

	@Autowired
	JWTHelper jwtHelper;

	@Autowired
	UserService userRegistrationService;
	
	@Value("${location.files.attachments}")
	private String imgFileLocation;

	@GetMapping("/{id:\\d+}")
	public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
		return ResponseEntity.ok(userRegistrationService.getUser(id));
	}

	@GetMapping()
	public ResponseEntity<List<User>> getAllUser() {
		return ResponseEntity.ok(userRegistrationService.findAll());
	}

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<User> save(@RequestPart(value = "imgFiles", required = false) MultipartFile imgFiles,
									@RequestPart(value = "logoFiles", required = false) MultipartFile logoFiles,
									@RequestParam("userObj") String userRegistration) 
	throws URISyntaxException {
		User userSave = null;
		try {

			Map<String, MultipartFile> fileObj = new HashMap<String, MultipartFile>();
			if (imgFiles != null && !imgFiles.isEmpty()) {
				fileObj.put("image", imgFiles);
			}
			if (logoFiles != null && !logoFiles.isEmpty()) {
				fileObj.put("logo", logoFiles);
			}
			ObjectMapper objectMapper = new ObjectMapper();
			User dto = objectMapper.readValue(userRegistration, User.class);
			User user = new User();
			BeanUtils.copyProperties(dto, user);
			userSave = userRegistrationService.save(user, fileObj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.created(new URI("/user/" + userSave.getId())).body(userSave);
	}

	@PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<User> update(@RequestPart(value = "imgFiles", required = false) MultipartFile imgFiles,
									@RequestPart(value = "logoFiles", required = false) MultipartFile logoFiles,
									@RequestParam("userObj") String userRegistration)
	throws URISyntaxException { 
		User updatedRequest = null;
		try {

			Map<String, MultipartFile> fileObj = new HashMap<String, MultipartFile>();
			if (imgFiles != null && !imgFiles.isEmpty()) {
				fileObj.put("image", imgFiles);
			}
			if (logoFiles != null && !logoFiles.isEmpty()) {
				fileObj.put("logo", logoFiles);
			}
			ObjectMapper objectMapper = new ObjectMapper();
			User user = objectMapper.readValue(userRegistration, User.class);
			updatedRequest = userRegistrationService.update(user, fileObj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(updatedRequest);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
		userRegistrationService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadFile(@RequestParam("id") Long id, @RequestParam("type") String type) 
	{
		User user = userRegistrationService.getUser(id);
		if (Objects.isNull(user)) {
			return null;
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		java.io.File file = null;
		String folder = imgFileLocation + File.separator + "USER" + File.separator + user.getId();
		if("image".equalsIgnoreCase(type)){
			file = FileUtils.getFile(folder + java.io.File.separator + user.getStImageFormattedName());
		}
		else{
			file = FileUtils.getFile(folder + java.io.File.separator + user.getStLogoFormattedName());
		}
		return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/deleteFile", method = RequestMethod.PUT)
	public ResponseEntity<?> deleteFile(@RequestParam("id") Long id,
										@RequestParam("type") String type)
	{
		userRegistrationService.deleteFile(id, type);
		return ResponseEntity.noContent().build();
	}
}
