package kmaru.jchord;

public class ChordException extends Exception {

	public ChordException(String message) {
		super(message);
	}

	public ChordException(Throwable cause) {
		super(cause);
	}

	public ChordException() {
		super();
	}

	public ChordException(String message, Throwable cause) {
		super(message, cause);
	}
}
