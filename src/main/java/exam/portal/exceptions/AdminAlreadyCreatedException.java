package exam.portal.exceptions;

public class AdminAlreadyCreatedException extends Exception {

	public AdminAlreadyCreatedException() {
		super("Admin user already exists");
	}
}
