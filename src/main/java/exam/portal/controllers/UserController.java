package exam.portal.controllers;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exam.portal.config.JwtTokenUtil;
import exam.portal.dtos.AuthResponse;
import exam.portal.dtos.LoginDTO;
import exam.portal.dtos.SuccessResponse;
import exam.portal.exceptions.InvalidUserException;
import exam.portal.models.User;
import exam.portal.services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired AuthenticationManager authManager;
	@Autowired JwtTokenUtil jwtUtil;
	@Autowired private UserService uservice;

	@PostMapping("/validate")
	public ResponseEntity<?> validateUser(@Valid @RequestBody LoginDTO dto) throws InvalidUserException {
		log.info("==== validating user ====");
		try {
			Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUserid(), dto.getPwd())
            );
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            log.info("auth done "+accessToken+" "+user);
            AuthResponse response = new AuthResponse(user.getUserid(), accessToken,user.getUname(),user.getId(),user.isIsadmin()?"Admin":"User");
             
            return ResponseEntity.ok().body(response);
		}catch(Exception ex) {
			throw new InvalidUserException();
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user) throws Exception {
		uservice.saveuser(user);
		return ResponseEntity.ok().body(new SuccessResponse("Registered successfully"));
	}
	
	@GetMapping("/verify")
	public ResponseEntity<?> verifyEmail(String email){
		log.info("Received "+email);
		User user= uservice.findByUserId(email).orElse(null);
		return ResponseEntity.ok(user==null);
	}
	
	@PostMapping("/profile")	
	public ResponseEntity<?> updateProfile(@RequestBody User user) throws Exception {
		uservice.saveuser(user);
		return ResponseEntity.ok(new SuccessResponse("Profile updated"));
	}
	
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile(){
		return ResponseEntity.ok(uservice.getAuthenticatedUser());
	}
	
	@GetMapping
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<?> getAllusers(String search){
		log.info("=== Get user list ====");
		if(search==null) {
			return ResponseEntity.ok(uservice.listall());
		}else {
			return ResponseEntity.ok(uservice.searchUsers(search));
		}
	}
}
