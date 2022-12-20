package exam.portal.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import exam.portal.dtos.AnswerDTO;
import exam.portal.dtos.ExamDTO;
import exam.portal.dtos.ExamQuizResponse;
import exam.portal.models.Category;
import exam.portal.models.Exam;
import exam.portal.models.ExamQuiz;
import exam.portal.models.Question;
import exam.portal.repos.ExamQuizRepository;
import exam.portal.repos.ExamRepository;

@Service
public class ExamService implements IExamService {
    @Autowired private ExamRepository repo;
    @Autowired private UserService userService;
    @Autowired private CategoryService catservice;
    @Autowired private ExamQuizRepository examQuizRepository;
    @Autowired private QuestionService qservice;
    private static Logger log = LoggerFactory.getLogger(ExamService.class);

    public void saveExam(ExamDTO dto) throws Exception{
        Exam exam=new Exam();
        Category category=catservice.findById(dto.getCatid());
        if(!category.isIsactive()) {
        	throw new Exception("Invalid category selected");
        }
        exam.setCategory(category);
        exam.setUser(userService.findByUserId(dto.getUserid()).get());
        exam=repo.save(exam);

        List<Question> questions=qservice.findByCagegory(dto.getCatid());
        Collections.shuffle(questions);
        List<ExamQuiz> examQuizs=new ArrayList<>();
        int i=1;
        for(Question q : questions){
            examQuizs.add(new ExamQuiz(i++,exam,q));
        }
        examQuizRepository.saveAll(examQuizs);
    }

    public boolean checkTestTaken(ExamDTO dto){
    	return false;
        //return repo.findByTestAndUser(testService.findById(dto.getTestid()), userService.findById(dto.getUserid()))!=null;
    }

    public void submitExam(AnswerDTO dto){
        Exam exam=repo.findById(dto.getExamid()).get();
        exam.setStatus("Completed");
        int totalobtained=0,totalmarks=0;
        for(int qid:dto.getAnswers().keySet()) {        	
	        for(ExamQuiz eq : examQuizRepository.findByExamId(dto.getExamid()))
	        {
	        	if(eq.getQuestion().getId()==qid) {
	        		totalmarks+=eq.getQuestion().getMarks();
	        		eq.setUserans(dto.getAnswers().get(qid));
	        		examQuizRepository.save(eq);
	        		if(eq.getQuestion().getAnswer()==dto.getAnswers().get(qid)) {
	        			log.info(" qid "+qid +" ans "+dto.getAnswers().get(qid)+" uans "+eq.getQuestion().getAnswer());
	        			totalobtained+=eq.getQuestion().getMarks();
	        		}
	        		break;
        		}
	        	
        	}
        }
        exam.setTotalmarks(totalmarks);
        exam.setTestScore(totalobtained);
        float percent=totalobtained*100/totalmarks;
        exam.setResult(percent>=50 ? "Pass":"Fail");
        repo.save(exam);
    }

    public List<Exam> allExams(){
        return repo.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }
    
    public List<Exam> userExams(int userid){
        return repo.findByUserUserid(userid);
    }

    public void deleteExam(int id){
        repo.deleteById(id);
    }

    public Exam findById(int id){
        return repo.findById(id).get();
    }

    public List<ExamQuizResponse> allExamQuizs(int id){
    	List<ExamQuizResponse> questions=new ArrayList<ExamQuizResponse>();
    	int no=1;
    	for(ExamQuiz quiz:examQuizRepository.findByExamId(id)) {
    		ExamQuizResponse qq=new ExamQuizResponse();
    		qq.setCatname(quiz.getQuestion().getCategory().getName());
    		qq.setId(quiz.getQuestion().getId());
    		qq.setQno(no++);
    		qq.setDescription(quiz.getQuestion().getDescription());
    		qq.setOption1(quiz.getQuestion().getOption1());
    		qq.setOption2(quiz.getQuestion().getOption2());
    		qq.setOption3(quiz.getQuestion().getOption3());
    		qq.setOption4(quiz.getQuestion().getOption4());
    		qq.setMarks(quiz.getQuestion().getMarks());
    		questions.add(qq);
    	}
        return questions;
    }
}
