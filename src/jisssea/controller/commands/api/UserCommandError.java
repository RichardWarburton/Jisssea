package jisssea.controller.commands.api;

public class UserCommandError extends Error {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2631490306290184145L;

	protected UserCommandError(String message, Throwable cause) {
		super(message, cause);
	}

	protected UserCommandError(String message) {
		super(message);
	}

}
