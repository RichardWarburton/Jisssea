package jisssea.controller.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.messages.Message;
import jisssea.controller.messages.UserMessage;

public abstract class RegexCommand implements Command {

	abstract protected Pattern pattern();
	
	abstract protected void guardedAct(Matcher m, UserMessage msg, BotRegistry irc, Controller ctrl);

	@Override
	public void act(Message msg, BotRegistry irc, Controller ctrl) {
		if (msg instanceof UserMessage) {
			UserMessage umsg = (UserMessage) msg;
			Pattern pattern = pattern();
			Matcher m = pattern.matcher(umsg.getInput());
			if(m.matches())
				guardedAct(m,umsg, irc, ctrl);
		}
	}
	
}
