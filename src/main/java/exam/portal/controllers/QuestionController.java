package exam.portal.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exam.portal.dtos.Response;
import exam.portal.exceptions.QuestionInUseException;
import exam.portal.exceptions.UnauthorizedUserException;
import exam.portal.models.Question;
import exam.portal.services.QuestionService;
import exam.portal.services.UserService;


@RestController
@RequestMapping("/api/questions")
public class QuestionController {

	@Autowired private QuestionService service;
	@Autowired private UserService uservice;
	private static Logger log = LoggerFactory.getLogger(QuestionController.class);
	
	@PostMapping
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<?> saveQuestion(@Valid @RequestBody Question cat) throws UnauthorizedUserException {
		log.info(" === saving question ===");
		service.save(cat);
		return Response.success("Question saved");
	}
	
	@GetMapping("/category/{id}")
	@RolesAllowed("ROLE_ADMIN")
	public List<Question> listall(@PathVariable("id") int id){
		log.info(" === list questions ===");
		return service.findByCagegory(id);
	} 	
	
	@GetMapping("{id}")
	public ResponseEntity<?> getQuestion(@PathVariable("id") int id){
		log.info(" === get Question details ===");		
		return Response.success(service.findById(id));
	}
	
	@PutMapping("{id}")
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<?> updateQuestion(@PathVariable("id") int id,@Valid @RequestBody Question question)  throws UnauthorizedUserException {
		log.info(" === update Question details ===");		
		question.setId(id);
		service.save(question);
		return Response.success("Question updated successfully");
	}
	
	@DeleteMapping("{id}")
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<?> deleteQuestion(@PathVariable("id") int id) throws QuestionInUseException,UnauthorizedUserException{
		log.info(" === delete Question ===");	  
		service.deleteQuestionById(id);
		return Response.success("Deleted successfully");
	}
}
