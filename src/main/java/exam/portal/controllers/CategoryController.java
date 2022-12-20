package exam.portal.controllers;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import exam.portal.models.Category;
import exam.portal.services.CategoryService;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired private CategoryService cservice;
	private static Logger log = LoggerFactory.getLogger(CategoryController.class);
	
	@PostMapping
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<?> saveCategory(@Valid @RequestBody Category cat) {
		log.info(" === saving category ===");
		cservice.save(cat);
		return Response.success("Category saved");
	}
	
	@GetMapping
	   public List<Category> listall(){
		log.info(" === list category ===");
		return cservice.listAll();
	}
	
	@PutMapping("{id}")
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<?> updateCategory(@PathVariable("id") int id,@RequestBody Category cat){
		log.info(" === updating category ===");
		cat.setId(id);
		cservice.save(cat);
		return Response.success("Category updated successfully");
	}
	
	@DeleteMapping("{id}")
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") int id){
		log.info(" === deleteing category ===");
		cservice.deleteCategory(id);
		return Response.success("Category deleted successfully");
	}
}
