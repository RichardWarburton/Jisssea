package jizzsea.controller.commands;

import static jizzsea.util.IrcUtility.getChannel;
import static jizzsea.util.IrcUtility.getNetwork;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jizzsea.bot.Bot;
import jizzsea.bot.BotRegistry;
import jizzsea.controller.Controller;
import jizzsea.controller.Pipe;
import jizzsea.controller.messages.MessageMessage;
import jizzsea.controller.messages.UserMessage;
import jizzsea.controller.predicates.DefaultPredicate;
import jizzsea.ui.DisplayWindow;
import jizzsea.util.IrcUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author richard
 * 
 *         /join #bots
 * 
 */
public class SayCommand extends RegexCommand {

	private static final Log log = LogFactory.getLog(SayCommand.class);

	private static final Pattern p = Pattern.compile("^[^/].*");

	// public static void main(String[] args) {
	// for (String s : Arrays.asList("foo bar /")) {
	// Matcher matcher = p.matcher(s);
	// if (matcher.matches()) {
	// System.out.println(matcher.group());
	// }
	// }
	// }

	@Override
	protected Pattern pattern() {
		return p;
	}

	@Override
	protected void guardedAct(Matcher m, UserMessage msg, BotRegistry irc,
			Controller ctrl) {

		String say = m.group();
		log.debug("Saying:" + say);

		DefaultPredicate pred = ctrl.getPipe(msg.getWindow())
				.getDefaultPredicate();
		if (pred != null) {
			for (String c : pred.correspondants) {
				final Bot bot = irc.get(getNetwork(c));
				String channel = getChannel(c);
				bot.sendMessage(channel, say);
				ctrl.message(new MessageMessage(bot, channel, bot.getNick(),
						bot.getLogin(), "localhost", say));
			}
		}
	}

}
