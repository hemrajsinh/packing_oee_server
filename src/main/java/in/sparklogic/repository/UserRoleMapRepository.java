package in.sparklogic.repository;

import org.springframework.stereotype.Repository;

import in.sparklogic.model.UserRoleMap;



/**
 * UserRoleMap repository class which provides various methods interact with
 * database.
 *
 * @author Dhaval Mistry
 * @since 16-May-2022
 * @version 1.0
 * 
 */
@Repository
public interface UserRoleMapRepository extends CustomRepository<UserRoleMap, Long> {

}
