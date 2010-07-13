package jisssea.controller.commands.api;

import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.messages.Message;

public interface Command {

	public void act(Message msg, BotRegistry irc, Controller ctrl);
	
}
