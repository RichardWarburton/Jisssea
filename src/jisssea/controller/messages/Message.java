package jisssea.controller.messages;

public class Message {

	private final MessageType type;

	public Message(MessageType type) {
		super();
		this.type = type;
	}

	public MessageType getType() {
		return type;
	}

}