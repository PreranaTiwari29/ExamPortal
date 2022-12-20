package exam.portal.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import exam.portal.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	@Query(value = "select * from question where cat_id=?1",nativeQuery = true)
	List<Question> findByCategoryId(int id);

}
