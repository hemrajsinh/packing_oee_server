package in.sparklogic.controller;

import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.context.SecurityContextHolder;

import in.sparklogic.model.User;
import in.sparklogic.params.LoginParams;
import in.sparklogic.params.UserDTO;
import in.sparklogic.service.LoginService;
import in.sparklogic.util.JWTHelper;

@RestController
@Scope("request")
public class LoginController {

	@Autowired
	JWTHelper jwtHelper;

	@Autowired
	LoginService loginService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	ResponseEntity<UserDTO> login(@RequestBody LoginParams loginParams) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				User userDtl = (User) auth.getPrincipal();
				System.out.println(userDtl.getStUserName());
			}
			UserDTO userDTO = new UserDTO();
			User user = loginService.login(loginParams);
			String jwt = jwtHelper.generateJWT(loginParams.getUsername(), user);
			BeanUtils.copyProperties(user,userDTO);
			userDTO.setJwtToken(jwt);
		return ResponseEntity.ok(userDTO);
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody ResponseEntity<?> resetPwd(@RequestBody Long id) {
			loginService.resetPassword(id);
		return ResponseEntity.noContent().build();
	}

	
	@RequestMapping(value = "/removeUser", method = RequestMethod.POST)
	@ResponseBody ResponseEntity<?> removeUser(@RequestBody Long id) {
		return ResponseEntity.ok(loginService.removeUser(id));
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
	@ResponseBody ResponseEntity<?> changePassword(@RequestParam String userName, 
											 	   @RequestParam String oldPassword, 
											       @RequestParam String newPassword) {
		User user = loginService.changePassword(userName, oldPassword);
		if (!Objects.isNull(user)) {
			return ResponseEntity.ok(loginService.updatePassword(user.getId(), newPassword));
		}
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	@ResponseBody ResponseEntity<?> updateRole(@RequestParam("userId") Long id, @RequestParam("userRole") String userRole) {
		return ResponseEntity.ok(loginService.updateRole(id,userRole));
	}
}
