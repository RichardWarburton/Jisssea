package jisssea.controller.commands;

import java.util.Map;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.Target;
import jisssea.controller.commands.api.Option;
import jisssea.controller.commands.api.TargetPredicate;
import jisssea.controller.commands.api.UserCommand;
import jisssea.controller.messages.MessageMessage;
import jisssea.controller.messages.UserMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PMCommand extends UserCommand {

	private static final Log log = LogFactory.getLog(PMCommand.class);

	@Override
	@Option(name = "target", values = TargetPredicate.class)
	public void userAct(Map<String, ?> options, UserMessage msg, Pipe pipe, BotRegistry irc, Controller ctrl, String messageBody) {

		final Target target = (Target) options.get("target");

		log.debug("Sending: " + messageBody + " as a PM to " + target);

		// send fake message to self
		final Bot network = target.getBot();
		final String user = target.getCorrespondant();
		ctrl.message(new MessageMessage(network, user, network.getNick(), network.getLogin(), network.getName(), messageBody));

		// send the message
		network.sendMessage(user, messageBody);
	}
}
