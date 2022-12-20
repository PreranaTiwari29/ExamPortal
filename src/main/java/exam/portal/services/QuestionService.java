package exam.portal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exam.portal.exceptions.QuestionInUseException;
import exam.portal.models.Question;
import exam.portal.repos.QuestionRepository;

@Service
public class QuestionService implements IQuestionService {

	@Autowired private QuestionRepository repo;
	
	public void save(Question quest) {
		repo.save(quest);
	}
	
	public List<Question> findByCagegory(int id){
		return repo.findByCategoryId(id);
	}
	
	public Question findById(int id) {
		return repo.findById(id).get();
	}
	
	public void deleteQuestionById(int id) throws QuestionInUseException {
		try {
			repo.deleteById(id);
		}catch(Exception ex) {
			throw new QuestionInUseException();
		}
	}
}
