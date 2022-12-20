package exam.portal.services;

import java.util.List;

import exam.portal.exceptions.QuestionInUseException;
import exam.portal.models.Question;

public interface IQuestionService {
	void save(Question quest);
	List<Question> findByCagegory(int id);
	Question findById(int id);
	void deleteQuestionById(int id) throws QuestionInUseException;
}
