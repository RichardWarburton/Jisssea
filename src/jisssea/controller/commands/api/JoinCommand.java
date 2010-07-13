package jisssea.controller.commands.api;

import java.util.Map;

import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.messages.UserMessage;

public class JoinCommand extends UserCommand {

	@Override
	@Option(name = "", values = TargetPredicate.class)
	public void userAct(Map<String, ?> options, UserMessage msg, Pipe pipe, BotRegistry irc, Controller ctrl) {

	}

}
