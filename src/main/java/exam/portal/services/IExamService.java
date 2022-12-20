package exam.portal.services;

import java.util.List;

import exam.portal.dtos.AnswerDTO;
import exam.portal.dtos.ExamDTO;
import exam.portal.dtos.ExamQuizResponse;
import exam.portal.models.Exam;

public interface IExamService {
	void saveExam(ExamDTO dto) throws Exception;
	void submitExam(AnswerDTO dto);
	List<Exam> allExams();
	List<Exam> userExams(int userid);
	void deleteExam(int id);
	Exam findById(int id);
	List<ExamQuizResponse> allExamQuizs(int id);
}
