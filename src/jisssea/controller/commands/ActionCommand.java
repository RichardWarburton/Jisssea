package jisssea.controller.commands;

import java.util.Map;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.Target;
import jisssea.controller.commands.api.UserCommand;
import jisssea.controller.messages.ActionMessage;
import jisssea.controller.messages.UserMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ActionCommand extends UserCommand {

	private static final Log log = LogFactory.getLog(ActionCommand.class);

	@Override
	public void userAct(Map<String, ?> options, UserMessage msg, Pipe pipe, BotRegistry irc, Controller ctrl, String remainder) {
		final Target target = ctrl.getPipe(msg.getWindow()).getDefaultPredicate().getDefaultTarget();

		log.debug("ACTION: " + remainder + " - sending to - " + target);

		final Bot network = target.getBot();
		final String correspondant = target.getCorrespondant();
		ctrl.message(new ActionMessage(network, network.getNick(), network.getLogin(), network.getName(), correspondant, remainder));
		network.sendAction(correspondant, remainder);
	}
}
