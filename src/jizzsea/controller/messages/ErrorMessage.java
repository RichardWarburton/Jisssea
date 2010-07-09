package jizzsea.controller.messages;


public class ErrorMessage extends Message {

	private final String str;
	
	public String getMsg() {
		return str;
	}

	public Exception getNested() {
		return nested;
	}

	private final Exception nested;
	
	public ErrorMessage(String str, Exception nested) {
		super(MessageType.ERROR);
		this.str = str;
		this.nested = nested;
	}

	public ErrorMessage(String string) {
		this(string,null);
	}

}
