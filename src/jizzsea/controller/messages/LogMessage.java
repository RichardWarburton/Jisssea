package jizzsea.controller.messages;


public class LogMessage extends Message {

	private final String str;
	
	public String getMsg() {
		return str;
	}

	public LogMessage(String str) {
		super(MessageType.LOG);
		this.str = str;
	}
}
