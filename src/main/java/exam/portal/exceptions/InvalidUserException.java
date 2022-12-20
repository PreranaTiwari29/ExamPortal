package exam.portal.exceptions;

public class InvalidUserException extends Exception {

	public InvalidUserException() {
		super("Invalid username or password");
	}
}
