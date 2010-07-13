package jisssea.controller.commands.api;

public class ParseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2713687898713857321L;

	protected ParseException(String message, Throwable cause) {
		super(message, cause);
	}

	protected ParseException(String message) {
		super(message);
	}

}
