package jisssea.controller.commands;

import static jisssea.util.IrcUtility.getCorrespondant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.messages.ActionMessage;
import jisssea.controller.messages.UserMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ActionCommand extends RegexCommand {

	private static final Log log = LogFactory.getLog(ActionCommand.class);

	private static final Pattern p = Pattern.compile("/me (.*)");

	@Override
	protected Pattern pattern() {
		return p;
	}

	@Override
	protected void guardedAct(Matcher m, UserMessage msg, BotRegistry irc, Controller ctrl) {
		final String actionBody = m.group(1);
		final String target = ctrl.getPipe(msg.getWindow()).getDefaultPredicate().getDefaultCorrespondant();
		final Bot network = irc.getTargetContext(target);
		final String correspondant = getCorrespondant(target);

		log.debug("ACTION: " + actionBody + " - sending to - " + correspondant);

		ctrl.message(new ActionMessage(network, network.getNick(), network.getLogin(), network.getName(), correspondant, actionBody));
		network.sendAction(correspondant, actionBody);
	}
}
