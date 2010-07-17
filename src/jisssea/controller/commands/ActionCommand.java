package jisssea.controller.commands;

import java.util.Map;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.Target;
import jisssea.controller.commands.api.Name;
import jisssea.controller.commands.api.UserCommand;
import jisssea.controller.messages.ActionMessage;
import jisssea.controller.messages.UserMessage;

@Name("me")
public class ActionCommand extends UserCommand {

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
