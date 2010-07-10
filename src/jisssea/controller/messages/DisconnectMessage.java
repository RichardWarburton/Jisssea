package jisssea.controller.messages;

import jisssea.bot.Bot;

public class DisconnectMessage extends Message {

	private final String name;
	
	private final Bot bot;
	
	public Bot getBot() {
		return bot;
	}
	
	public String getName() {
		return name;
	}

	public DisconnectMessage(final Bot bot, final String name) {
		super(MessageType.CONNECT);
		this.name = name;
		this.bot = bot;
	}

	@Override
	public String toString() {
		return "ConnectMessage [name=" + name + "]";
	}

}
