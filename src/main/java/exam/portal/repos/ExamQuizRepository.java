package exam.portal.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import exam.portal.models.ExamQuiz;

@Repository
public interface ExamQuizRepository extends JpaRepository<ExamQuiz, Integer> {
	
	@Query(value="select * from examquiz where exam_id=?1",nativeQuery=true)
	List<ExamQuiz> findByExamId(int id);
}
