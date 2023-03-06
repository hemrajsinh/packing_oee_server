package in.sparklogic.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import in.sparklogic.model.Role;



/**
 * Role repository class which provides various methods interact with database.
 * 
 * @author Dhaval Mistry
 * @since 16-May-2022
 * @version 1.0
 * 
 */
@Repository
public interface RoleRepository extends CustomRepository<Role, Serializable> {

	/**
	 * This method return role entity by given name
	 * 
	 * @param name
	 * @return
	 */
	Optional<Role> findOneByStRoleName(final String name);

	/**
	 * This method queries database and find role entity by given name.
	 * 
	 * @param name
	 * @return
	 */
	Role findRoleByStRoleName(final String name);
}
