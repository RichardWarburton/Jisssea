package jisssea.controller.messages;

import jisssea.bot.Bot;

public class KickMessage extends Message {
	final String channel, kickerNick,
	kickerLogin, kickerHostname, recipientNick,
	reason;
	
	private final Bot bot;
	
	public Bot getBot() {
		return bot;
	}
	public KickMessage(final Bot bot, String channel, String kickerNick,
			String kickerLogin, String kickerHostname, String recipientNick,
			String reason) {
		super(MessageType.KICK);
		this.channel = channel;
		this.kickerNick = kickerNick;
		this.kickerLogin = kickerLogin;
		this.kickerHostname = kickerHostname;
		this.recipientNick = recipientNick;
		this.reason = reason;
		this.bot = bot;
	}

	public String getChannel() {
		return channel;
	}

	public String getKickerNick() {
		return kickerNick;
	}

	public String getKickerLogin() {
		return kickerLogin;
	}

	public String getKickerHostname() {
		return kickerHostname;
	}

	public String getRecipientNick() {
		return recipientNick;
	}

	public String getReason() {
		return reason;
	}
	
	
}
