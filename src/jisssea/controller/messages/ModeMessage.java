package jisssea.controller.messages;

import jisssea.bot.Bot;

public class ModeMessage extends Message {

	public ModeMessage(Bot bot, String channel, String sourceNick, String sourceLogin, String sourceHostname, String mode) {
		super(MessageType.MODE);
		this.bot = bot;
		this.channel = channel;
		this.sourceNick = sourceNick;
		this.sourceLogin = sourceLogin;
		this.sourceHostname = sourceHostname;
		this.mode = mode;
	}

	private final String channel, sourceNick, sourceLogin, sourceHostname, mode;

	public String getChannel() {
		return channel;
	}

	private final Bot bot;

	public Bot getBot() {
		return bot;
	}

	@Override
	public String toString() {
		return "ModeMessage [channel=" + channel + ", sourceNick=" + sourceNick + ", sourceLogin=" + sourceLogin + ", sourceHostname="
				+ sourceHostname + ", mode=" + mode + "]";
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

	public String getMode() {
		return mode;
	}

}
