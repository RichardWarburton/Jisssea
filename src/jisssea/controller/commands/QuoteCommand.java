package jisssea.controller.commands;

import java.util.Map;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.commands.api.UserCommand;
import jisssea.controller.messages.UserMessage;

/**
 * @author richard
 * 
 *         /quote PRIVMSG mulletron :rawtest
 */
public class QuoteCommand extends UserCommand {

	@Override
	public void userAct(Map<String, ?> options, UserMessage msg, Pipe pipe, BotRegistry irc, Controller ctrl, String remainder) {
		Bot bot = irc.getContext();
		bot.sendRawLine(remainder);
	}
}
