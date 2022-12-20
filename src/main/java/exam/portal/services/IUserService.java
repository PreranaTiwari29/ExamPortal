package exam.portal.services;

import java.util.List;
import java.util.Optional;

import exam.portal.exceptions.AdminAlreadyCreatedException;
import exam.portal.models.User;

public interface IUserService {
	void saveuser(User user) throws Exception;
	Optional<User> findByUserId(String userid);
	List<User> listall();
	List<User> searchUsers(String search);
	User getAuthenticatedUser();
}
