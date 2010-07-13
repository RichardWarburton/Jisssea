package jisssea.controller.commands.api;

import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;

/**
 * throws a parse exception when it fails
 * 
 * @author rlmw
 * 
 * @param <T>
 */
public interface ValuePredicate<T> {

	public T check(String s, BotRegistry irc, Controller ctrl);

}
