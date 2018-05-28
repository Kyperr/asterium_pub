package com.toozo.asteriumwebserver.exceptions;

public class PlayerNameTakenException extends Exception {

	/**
	 * Auto-generated serial ID.
	 */
	private static final long serialVersionUID = -1857409905387094550L;
	
	// Error message
		private static final String DEFAULT_MESSAGE = "Requested game does not exist.";

		/**
		 * Construct a new GameFullException with a general message.
		 */
		public PlayerNameTakenException() {
			super(DEFAULT_MESSAGE);
		}

		/**
		 * Constructs a new GameFullException with a custom message.
		 * 
		 * @param message The custom message.
		 */
		public PlayerNameTakenException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		/**
		 * Constructs a new GameFullException with a throwable cause.
		 * @param cause The Throwable cause of the Exception.
		 */
		public PlayerNameTakenException(Throwable cause) {
			super(cause);
		}

		/**
		 * Constructs a new GameFullException with a custom message and a Throwable cause.
		 * @param message The custom message.
		 * @param cause The Throwable cause of the Exception.
		 */
		public PlayerNameTakenException(String message, Throwable cause) {
			super(message, cause);
		}

		/**
		 * Constructs a new GameFullException with a custom message, 
		 * Throwable cause, suppression option, and writable stack trace option.
		 * @param message The custom message.
		 * @param cause The Throwable cause of the Exception.
		 * @param enableSuppression Whether or not to suppress the Exception.
		 * @param writableStackTrace Whether or not the Stack Trace is writable.
		 */
		public PlayerNameTakenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
		}

}
