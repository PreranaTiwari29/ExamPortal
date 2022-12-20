package exam.portal.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ExamQuiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int qno;
	private int userans;
	@ManyToOne
    @JoinColumn(name ="question_id")
    private Question question;
	@ManyToOne
    @JoinColumn(name ="exam_id")
	private Exam exam;
	
	
	public ExamQuiz() {
		// TODO Auto-generated constructor stub
	}

	public ExamQuiz(int qno,Exam exam, Question question) {
		this.qno = qno;
		this.exam=exam;
		this.question = question;
	}
	
	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQno() {
		return qno;
	}
	public void setQno(int qno) {
		this.qno = qno;
	}
	public int getUserans() {
		return userans;
	}
	public void setUserans(int userans) {
		this.userans = userans;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	
}
