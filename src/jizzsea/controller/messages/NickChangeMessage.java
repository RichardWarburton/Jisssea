package jizzsea.controller.messages;

import jizzsea.bot.Bot;

public class NickChangeMessage extends Message {

	final String oldNick, login, hostname, newNick;

	
	private final Bot bot;
	
	public Bot getBot() {
		return bot;
	}
	
	@Override
	public String toString() {
		return "NickChangeMessage [oldNick=" + oldNick + ", login=" + login
				+ ", hostname=" + hostname + ", newNick=" + newNick + ", bot="
				+ bot + "]";
	}

	public NickChangeMessage(final Bot bot, String oldNick, String login,
			String hostname, String newNick) {
		super(MessageType.NICKCHANGE);
		this.oldNick = oldNick;
		this.login = login;
		this.hostname = hostname;
		this.newNick = newNick;
		this.bot = bot;
	}

	public String getOldNick() {
		return oldNick;
	}

	public String getLogin() {
		return login;
	}

	public String getHostname() {
		return hostname;
	}

	public String getNewNick() {
		return newNick;
	}
	
}
