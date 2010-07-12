package jisssea.controller.messages;

import static jisssea.controller.messages.MessageType.ACTION;
import jisssea.bot.Bot;

public class ActionMessage extends Message {

	private final String sender, login, hostname, target, action;

	private final Bot bot;

	public Bot getBot() {
		return bot;
	}

	public ActionMessage(Bot bot, String sender, String login, String hostname, String target, String action) {
		super(ACTION);
		this.bot = bot;
		this.sender = sender;
		this.login = login;
		this.hostname = hostname;
		this.target = target;
		this.action = action;
	}

	@Override
	public String toString() {
		return "ActionMessage [sender=" + sender + ", login=" + login + ", hostname=" + hostname + ", target=" + target + ", action=" + action + "]";
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

	public String getTarget() {
		return target;
	}

	public String getAction() {
		return action;
	}

}
