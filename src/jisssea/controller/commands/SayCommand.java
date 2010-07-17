package jisssea.controller.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Target;
import jisssea.controller.commands.api.RegexCommand;
import jisssea.controller.messages.MessageMessage;
import jisssea.controller.messages.UserMessage;
import jisssea.controller.predicates.DefaultPredicate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author richard
 * 
 *         just normal typing messages into a channel
 * 
 */
public class SayCommand extends RegexCommand {

	private static final Log log = LogFactory.getLog(SayCommand.class);

	private static final Pattern p = Pattern.compile("^[^/].*");

	@Override
	protected Pattern pattern() {
		return p;
	}

	@Override
	protected void guardedAct(Matcher m, UserMessage msg, BotRegistry irc, Controller ctrl) {

		String say = m.group();
		log.debug("Saying:" + say);

		DefaultPredicate pred = ctrl.getPipe(msg.getWindow()).getDefaultPredicate();
		if (pred != null) {
			for (Target c : pred.correspondants) {
				final Bot bot = c.getBot();
				String channel = c.getCorrespondant();
				bot.sendMessage(channel, say);
				ctrl.message(new MessageMessage(bot, channel, bot.getNick(), bot.getLogin(), "localhost", say));
			}
		}
	}

}
