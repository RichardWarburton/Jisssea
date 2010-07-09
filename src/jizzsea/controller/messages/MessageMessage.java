package jizzsea.controller.messages;

import jizzsea.bot.Bot;

public class MessageMessage extends Message {

	final String channel, sender, login,
	hostname, message;
	
	private final Bot bot;
	
	public Bot getBot() {
		return bot;
	}
	
	public String getChannel() {
		return channel;
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

	public MessageMessage(final Bot bot, String channel, String sender,
			String login, String hostname, String message) {
		super(MessageType.MESSAGE);
		this.channel = channel;
		this.sender = sender;
		this.login = login;
		this.hostname = hostname;
		this.message = message;
		this.bot = bot;
	}

	@Override
	public String toString() {
		return "MessageMessage [channel=" + channel + ", sender=" + sender
				+ ", login=" + login + ", hostname=" + hostname + ", message="
				+ message + "]";
	}
}
