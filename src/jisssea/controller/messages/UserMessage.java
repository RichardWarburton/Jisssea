package jisssea.controller.messages;

import jisssea.ui.DisplayWindow;

public class UserMessage extends Message {

	private final String input;
	
	private final DisplayWindow window;
	
	@Override
	public String toString() {
		return "UserMessage [input=" + input + ", window=" + window + "]";
	}

	public UserMessage(String input, DisplayWindow window) {
		super(MessageType.USER);
		this.input = input;
		this.window = window;
	}

	public String getInput() {
		return input;
	}

	public DisplayWindow getWindow() {
		return window;
	}
	
}
