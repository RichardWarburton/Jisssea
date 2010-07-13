package jisssea.controller.messages;

import java.util.Arrays;

import jisssea.bot.Bot;

import org.jibble.pircbot.User;

public class UserListMessage extends Message {
	final String channel;
	final User[] users;
	final Bot bot;

	public Bot getBot() {
		return bot;
	}

	public String getChannel() {
		return channel;
	}

	public User[] getUsers() {
		return users;
	}

	@Override
	public String toString() {
		return "UserListMessage [channel=" + channel + ", users=" + Arrays.toString(users) + "]";
	}

	public UserListMessage(Bot bot, String channel, User[] users) {
		super(MessageType.USER_LIST);
		this.bot = bot;
		this.channel = channel;
		this.users = users;
	}
}
