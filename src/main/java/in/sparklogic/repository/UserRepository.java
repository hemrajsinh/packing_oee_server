package in.sparklogic.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.sparklogic.model.User;


@Repository
public interface UserRepository extends CustomRepository<User, Serializable> {

	public List<User> findByFlgIsDeletedFalseOrderByStUserType();
	
	public User findByFlgIsDeletedFalseAndStUserNameAndStPassword(String userName,String password);
	
	public User findByFlgIsDeletedFalseAndStUserNameIgnoreCaseAndStPassword(String userName,String password);
	
	public User findByStUserName(String userName);
	
	public User findById(Long id);
	
	@Query(value = "select * from public.user where lower(username) = ?1 or lower(email) = ?1", nativeQuery = true)
	Optional<User> findByStUserNameOrStEmail(final String username);
	
}
