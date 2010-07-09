package jizzsea.controller.commands;

import jizzsea.bot.BotRegistry;
import jizzsea.controller.Controller;
import jizzsea.controller.messages.Message;

public interface Command {

	public void act(Message msg, BotRegistry irc, Controller ctrl);
	
}
