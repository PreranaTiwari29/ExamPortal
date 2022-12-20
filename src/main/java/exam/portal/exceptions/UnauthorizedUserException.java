package exam.portal.exceptions;

public class UnauthorizedUserException extends Exception {

	public UnauthorizedUserException() {
		super("Unauthorized access");
	}
}
