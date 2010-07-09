package jizzsea.controller.messages;

import jizzsea.bot.Bot;

public class JoinMessage extends Message {
	
	@Override
	public String toString() {
		return "JoinMessage [channel=" + channel + ", sender=" + sender
				+ ", login=" + login + ", hostname=" + hostname + "]";
	}

	final String channel;
	final String sender;
	final String login;
	final String hostname;
	
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

	public JoinMessage(final Bot bot, String channel, String sender,
			String login, String hostname) {
		super(MessageType.JOIN);
		this.channel = channel;
		this.sender = sender;
		this.login = login;
		this.hostname = hostname;
		this.bot = bot;
	}

}
