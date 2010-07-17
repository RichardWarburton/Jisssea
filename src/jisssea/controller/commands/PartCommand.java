package jisssea.controller.commands;

import java.util.Map;

import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.Target;
import jisssea.controller.commands.api.UserCommand;
import jisssea.controller.messages.UserMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author richard
 * 
 *         /part
 * 
 *         /part sigh /part #bots /part #bots sigh
 * 
 */
public class PartCommand extends UserCommand {

	private static final Log log = LogFactory.getLog(PartCommand.class);

	private void partChannel(String msg, Target t) {
		t.getBot().partChannel(t.getCorrespondant(), msg);
	}

	@Override
	public void userAct(Map<String, ?> options, UserMessage msg, Pipe pipe, BotRegistry irc, Controller ctrl, String message) {
		message = (message.length() == 0) ? "goodbye chumps" : message;

		// /part and /part sigh
		if (!options.containsKey("target")) {
			for (Target t : pipe.getDefaultPredicate().correspondants) {
				partChannel(message, t);
			}
			// /part uwcsnet:#bots and /part uwcsnet:#bots sigh
		} else {
			partChannel(message, (Target) options.get("target"));
		}
	}

}
