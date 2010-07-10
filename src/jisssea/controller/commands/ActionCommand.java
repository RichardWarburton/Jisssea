package jisssea.controller.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.messages.UserMessage;

public class ActionCommand extends RegexCommand {

	private static final Pattern p = Pattern.compile("/me ( .*)?");

	@Override
	protected Pattern pattern() {
		return p;
	}

	@Override
	protected void guardedAct(Matcher m, UserMessage msg, BotRegistry irc, Controller ctrl) {
		final String actionBody = m.group(1);
		final Bot network = irc.getContext();
		final String target = ctrl.getPipe(msg.getWindow()).getDefaultPredicate().getDefaultCorrespondant();

		network.sendAction(target, actionBody);
	}
}
