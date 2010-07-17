package jisssea.controller;

import static jisssea.util.IrcUtility.isValidChannel;
import jisssea.bot.Bot;

public class Target {

	private final Bot bot;
	private String correspondant;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bot == null) ? 0 : bot.hashCode());
		result = prime * result + ((correspondant == null) ? 0 : correspondant.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Target other = (Target) obj;
		if (bot == null) {
			if (other.bot != null)
				return false;
		} else if (!bot.equals(other.bot))
			return false;
		if (correspondant == null) {
			if (other.correspondant != null)
				return false;
		} else if (!correspondant.equals(other.correspondant))
			return false;
		return true;
	}

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

	public boolean isChannel() {
		return isValidChannel(correspondant);
	}

}
