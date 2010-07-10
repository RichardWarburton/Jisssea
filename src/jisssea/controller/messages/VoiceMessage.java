package jisssea.controller.messages;

import jisssea.bot.Bot;

public class VoiceMessage extends Message {
	final String channel, sourceNick,
	sourceLogin, sourceHostname, recipient;
	
	private final Bot bot;
	
	public Bot getBot() {
		return bot;
	}
	
	public VoiceMessage(final Bot bot, String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String recipient) {
		super(MessageType.VOICE);
		this.channel = channel;
		this.sourceNick = sourceNick;
		this.sourceLogin = sourceLogin;
		this.sourceHostname = sourceHostname;
		this.recipient = recipient;
		this.bot = bot;
	}

	public String getChannel() {
		return channel;
	}

	public String getSourceNick() {
		return sourceNick;
	}

	public String getSourceLogin() {
		return sourceLogin;
	}

	public String getSourceHostname() {
		return sourceHostname;
	}

	public String getRecipient() {
		return recipient;
	}
}
