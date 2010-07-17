package jisssea.controller.commands;

import java.util.Map;

import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.Target;
import jisssea.controller.commands.api.UserCommand;
import jisssea.controller.messages.UserMessage;

/**
 * @author richard
 * 
 *         Changes nicks:
 * 
 *         /nick mullet
 * 
 */
public class NickCommand extends UserCommand {

	@Override
	public void userAct(Map<String, ?> options, UserMessage msg, Pipe pipe, BotRegistry irc, Controller ctrl, String nick) {
		log.debug("Changing nick to:" + nick);

		Target target = ctrl.getPipe(msg.getWindow()).getDefaultPredicate().getDefaultTarget();
		target.getBot().changeNick(nick);
	}

}
