package exam.portal.dtos;

import java.util.HashMap;
import java.util.HashSet;

public class AnswerDTO {
    private int examid;
    private HashMap<Integer, Integer> answers;

    public HashMap<Integer, Integer> getAnswers() {
		return answers;
	}

	public void setAnswers(HashMap<Integer, Integer> answers) {
		this.answers = answers;
	}

	public int getExamid() {
        return examid;
    }

    public void setExamid(int examid) {
        this.examid = examid;
    }

}
