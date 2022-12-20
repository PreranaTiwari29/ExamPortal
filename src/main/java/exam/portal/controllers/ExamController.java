package exam.portal.controllers;

import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exam.portal.dtos.AnswerDTO;
import exam.portal.dtos.ExamDTO;
import exam.portal.dtos.Response;
import exam.portal.models.User;
import exam.portal.services.ExamService;
import exam.portal.services.UserService;


@RestController
@RequestMapping("/api/exams")
public class ExamController {

    @Autowired private ExamService examService;
    @Autowired private UserService userService;
    private static Logger log = LoggerFactory.getLogger(ExamController.class);

    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> saveExam(@RequestBody ExamDTO dto) throws Exception{
    	log.info(" === exam saved ===");
        if(examService.checkTestTaken(dto)){
            return ResponseEntity.badRequest().body("Exam already taken by user");
        }
        examService.saveExam(dto);
        return Response.success("Enrolled successfully");
    }

    @PostMapping("submit")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<?> submitExam(@RequestBody AnswerDTO dto){
    	log.info(" === exam submitted ===");
        examService.submitExam(dto);
        return Response.success("Exam submitted successfully");
    }

    @GetMapping
    public ResponseEntity<?> listall(){
    	User user=userService.getAuthenticatedUser();
    	if(!user.isIsadmin()) {
    		log.info(" === list all user exams ===");
    		return ResponseEntity.ok(examService.userExams(user.getId()));
    	}
    	else {
    		log.info(" === list all exams ===");
    		return ResponseEntity.ok(examService.allExams());
    	}
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findDetails(@PathVariable("id") int id){
    	log.info(" === get exam details ===");
        return ResponseEntity.ok().body(examService.findById(id));
    }

    @GetMapping("questions/{id}")
    public ResponseEntity<?> findExamQuestions(@PathVariable("id") int id){
    	log.info(" === list all exam questions ===");
        return ResponseEntity.ok().body(examService.allExamQuizs(id));
    }
}
