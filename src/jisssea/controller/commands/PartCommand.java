package jisssea.controller.commands;

import java.util.Map;

import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.Target;
import jisssea.controller.commands.api.Option;
import jisssea.controller.commands.api.TargetPredicate;
import jisssea.controller.commands.api.UserCommand;
import jisssea.controller.messages.UserMessage;

/**
 * @author richard
 * 
 *         /part
 * 
 *         /part sigh /part #bots /part #bots sigh
 * 
 */
public class PartCommand extends UserCommand {

	private void partChannel(String msg, Target t) {
		t.getBot().partChannel(t.getCorrespondant(), msg);
	}

	@Override
	@Option(name = "target", values = TargetPredicate.class, required = false)
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
