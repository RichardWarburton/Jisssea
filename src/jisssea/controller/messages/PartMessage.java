package jisssea.controller.messages;

import jisssea.bot.Bot;

public class PartMessage extends Message {

	final String channel, sender, login, hostname;
	
	private final Bot bot;
	
	public Bot getBot() {
		return bot;
	}
	
	@Override
	public String toString() {
		return "PartMessage [channel=" + channel + ", sender=" + sender
				+ ", login=" + login + ", hostname=" + hostname + ", bot="
				+ bot.getServerName() + "]";
	}

	public PartMessage(final Bot bot, String channel, String sender,
			String login, String hostname) {
		super(MessageType.PART);
		this.channel = channel;
		this.sender = sender;
		this.login = login;
		this.hostname = hostname;
		this.bot = bot;
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
	
	
}
