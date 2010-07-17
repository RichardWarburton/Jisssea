package jisssea.controller.commands.api;

import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;

public class StringPredicate implements ValuePredicate<String> {

	@Override
	public String check(String s, BotRegistry irc, Controller ctrl) {
		return s;
	}

}
