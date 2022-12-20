package exam.portal.exceptions;

public class QuestionInUseException extends Exception {

	public QuestionInUseException() {
		super("Question is in used in some exam");
	}
}
