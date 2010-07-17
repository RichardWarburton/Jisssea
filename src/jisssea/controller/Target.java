package jisssea.controller;

import jisssea.bot.Bot;

public class Target {

	private final Bot bot;
	private String correspondant;

	public Target(Bot bot, String correspondant) {
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

	public void setCorrespondant(String correspondant) {
		this.correspondant = correspondant;
	}

	@Override
	public String toString() {
		return "Target [bot=" + bot + ", correspondant=" + correspondant + "]";
	}

}
