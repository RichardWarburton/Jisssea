package jisssea.controller.commands;

import static jisssea.util.IrcUtility.getCorrespondant;
import static jisssea.util.IrcUtility.getNetwork;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.messages.MessageMessage;
import jisssea.controller.messages.UserMessage;
import jisssea.controller.predicates.DefaultPredicate;
import jisssea.ui.DisplayWindow;
import jisssea.util.IrcUtility;

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
				String channel = getCorrespondant(c);
				bot.sendMessage(channel, say);
				ctrl.message(new MessageMessage(bot, channel, bot.getNick(),
						bot.getLogin(), "localhost", say));
			}
		}
	}

}
