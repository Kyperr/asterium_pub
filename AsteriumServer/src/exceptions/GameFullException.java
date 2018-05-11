package exceptions;

/**
 * An Exception which indicates that the game that a player attempted to join was already full.
 * 
 * @author Studio Toozo
 */
public class GameFullException extends Exception {

	private static final long serialVersionUID = 1L;
	// Error message
	private static final String MESSAGE = "This game is full. Choose another.";

	/**
	 * Construct a new GameFullException with a general message.
	 */
	public GameFullException() {
		super(MESSAGE);
	}

	/**
	 * Constructs a new GameFullException with a custom message.
	 * @param message The custom message.
	 */
	public GameFullException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructs a new GameFullException with a throwable cause.
	 * @param cause The Throwable cause of the Exception.
	 */
	public GameFullException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructs a new GameFullException with a custom message and a Throwable cause.
	 * @param message The custom message.
	 * @param cause The Throwable cause of the Exception.
	 */
	public GameFullException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructs a new GameFullException with a custom message, 
	 * Throwable cause, suppression option, and writable stack trace option.
	 * @param message The custom message.
	 * @param cause The Throwable cause of the Exception.
	 * @param enableSuppression Whether or not to suppress the Exception.
	 * @param writableStackTrace Whether or not the Stack Trace is writable.
	 */
	public GameFullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
