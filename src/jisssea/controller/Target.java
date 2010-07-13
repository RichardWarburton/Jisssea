package jisssea.controller;

import jisssea.bot.Bot;

public class Target {

	private final Bot bot;
	private final String correspondant;

	protected Target(Bot bot, String correspondant) {
		super();
		this.bot = bot;
		this.correspondant = correspondant;
	}

	public Bot getBot() {
		return bot;
	}

	public String getCorrespondant() {
		return correspondant;
	}

	@Override
	public String toString() {
		return "Target [bot=" + bot + ", correspondant=" + correspondant + "]";
	}

}
