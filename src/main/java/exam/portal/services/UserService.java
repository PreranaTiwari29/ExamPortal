package exam.portal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import exam.portal.exceptions.AdminAlreadyCreatedException;
import exam.portal.models.User;
import exam.portal.repos.UsersRepository;

@Service
public class UserService implements IUserService {
	
	@Autowired private UsersRepository repo;
	@Autowired private PasswordEncoder encoder;
	public void saveuser(User user) throws Exception {	
		if(user.isIsadmin()) {
			if(repo.findByIsadmin().isPresent()) {
				throw new AdminAlreadyCreatedException();
			}
		}
		if(findByUserId(user.getUserid()).isPresent()) {
			throw new Exception("Userid already registered");
		}
		String password=encoder.encode(user.getPwd());
		user.setPwd(password);
		repo.save(user);
	}
	
	public Optional<User> findByUserId(String userid) {
		return repo.findByUserid(userid);
	}

	public final User getAuthenticatedUser() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findByUserId(user.getUserid()).get();
    }
	
	public List<User> listall(){
		return repo.findAll();
	}
	
	public List<User> searchUsers(String search){
		return repo.findByUnameOrUseridContaining(search);
	}
}
