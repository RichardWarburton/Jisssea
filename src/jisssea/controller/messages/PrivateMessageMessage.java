package jisssea.controller.messages;

import jisssea.bot.Bot;

public class PrivateMessageMessage extends Message {

	final String sender, login, hostname, message;

	
	private final Bot bot;
	
	public Bot getBot() {
		return bot;
	}
	
	public PrivateMessageMessage(final Bot bot, String sender, String login,
			String hostname, String message) {
		super(MessageType.PRIVATEMESSAGE);
		this.sender = sender;
		this.login = login;
		this.hostname = hostname;
		this.message = message;
		this.bot = bot;
	}

	public String getSender() {
		return sender;
	}

	public String getLogin() {
		return login;
	}

	public String getHostname() {
		return hostname;
	}

	public String getMessage() {
		return message;
	}
	
}
