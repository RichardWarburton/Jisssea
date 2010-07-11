package jisssea.controller.commands;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.messages.UserMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

/**
 * @author richard
 * 
 *         /quote action faux foo
 */
public class ReconnectCommand extends RegexCommand {

	private static final Log log = LogFactory.getLog(ReconnectCommand.class);

	private static final Pattern p = Pattern.compile("/reconnect( [^ ]*)?");

	@Override
	protected Pattern pattern() {
		return p;
	}

	@Override
	protected void guardedAct(Matcher m, UserMessage msg, BotRegistry irc, final Controller ctrl) {
		final String network = m.group(1);
		final Bot bot = (network != null) ? irc.get(network) : irc.getContext();
		try {
			bot.reconnect();
		} catch (NickAlreadyInUseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IrcException e) {
			e.printStackTrace();
		}
	}
}
