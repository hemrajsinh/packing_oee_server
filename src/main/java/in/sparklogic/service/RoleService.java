package in.sparklogic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sparklogic.model.Role;
import in.sparklogic.repository.RoleRepository;



/**
 * 
 * This service class holds the business logic for all the direct/indirect
 * operations related to {@link Role} entity.
 * 
 * @author Dhaval Mistry
 * @since 16-May-2022
 * @version 1.0
 * 
 */

@Service
public class RoleService {

	/* dependencies goes here */
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * Get all roles available in database.
	 * 
	 * @return
	 */
	public List<Role> getRoles() {
		List<Role> roles = roleRepository.findAll();
		return roles;
	}

}
