package baseball.exception;

public class BadRequestException extends RuntimeException {
	public BadRequestException(String message) {
		super("[ERROR] " + message);
	}
}
