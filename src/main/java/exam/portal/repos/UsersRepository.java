package exam.portal.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import exam.portal.models.User;

public interface UsersRepository extends JpaRepository<User, Integer> {
	
	@Query(value = "select * from users where userid=?1",nativeQuery = true)
	Optional<User> findByUserid(String userid);
	@Query(value = "select * from users where isadmin=1",nativeQuery = true)
	Optional<User> findByIsadmin();
	@Query(value = "select * from users where uname like %?1% or userid like %?1%",nativeQuery = true)
	List<User> findByUnameOrUseridContaining(String search);

}
