package jisssea.controller.commands;

import static jisssea.util.IrcUtility.getSafeCorrespondant;
import static jisssea.util.IrcUtility.isValidChannel;
import static jisssea.util.IrcUtility.isValidTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.messages.UserMessage;
import jisssea.util.Procedure;

public class PMCommand extends RegexCommand {

	private static final Pattern p = Pattern.compile("/pm (.*?) ( .*)?");

	@Override
	protected Pattern pattern() {
		return p;
	}

	@Override
	protected void guardedAct(Matcher m, UserMessage msg, BotRegistry irc, Controller ctrl) {
		final String target = m.group(1);
		final String messageBody = m.group(2);
		final Bot network = irc.getContext(target);
		final String user = getSafeCorrespondant(target);

		// are we already talking to this user?
		// or do we need to create a new window
		final Set<String> correspondants = new HashSet<String>();
		ctrl.foreachPipe(new Procedure<Pipe>() {
			@Override
			public boolean f(Pipe a) {
				for (String correspondantName : a.getDefaultPredicate().correspondants) {
					if (isValidChannel(correspondantName))
						correspondants.add(correspondantName);
				}
				return true;
			}
		});
		if (!correspondants.contains(user)) {
			ctrl.createPipe((isValidTarget(target)) ? target : network.getName() + ":" + target);
		}

		// send the message
		network.sendMessage(target, messageBody);
	}
}
