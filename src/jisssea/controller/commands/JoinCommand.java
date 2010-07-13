package jisssea.controller.commands;

import static jisssea.util.IrcUtility.isValidChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.commands.api.RegexCommand;
import jisssea.controller.messages.ErrorMessage;
import jisssea.controller.messages.UserMessage;
import jisssea.controller.predicates.DefaultPredicate;
import jisssea.ui.DisplayWindow;
import jisssea.util.IrcUtility;
import jisssea.util.Procedure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author richard
 * 
 *         /join #bots
 * 
 */
public class JoinCommand extends RegexCommand {

	private static final Log log = LogFactory.getLog(JoinCommand.class);

	private static final Pattern p = Pattern.compile("/join ([^ ]*)");

	@Override
	protected Pattern pattern() {
		return p;
	}

	@Override
	protected void guardedAct(Matcher m, UserMessage msg, BotRegistry irc, final Controller ctrl) {
		DisplayWindow context = msg.getWindow();
		final String channel = m.group(1);

		if (!isValidChannel(channel)) {
			ctrl.message(new ErrorMessage("You must /join a valid channel name"));
			return;
		}

		// TODO: generic networks
		// check if channel exists
		Bot bot = irc.get("uwcsnet");
		if (!bot.getChannelSet().contains(channel)) {
			// create a new pipe with the default filter
			Pipe pipe = ctrl.createPipe(bot.getServerName() + ":" + channel);
			ctrl.selectPipe(pipe);

			// join the channel
			bot.joinChannel(channel);
			log.debug("JoinCommand completed processing for" + channel);
		} else {
			// move to channel if you're already in it.
			ctrl.foreachPipe(new Procedure<Pipe>() {
				@Override
				public boolean f(Pipe pipe) {
					final DefaultPredicate pred = pipe.getDefaultPredicate();
					for (String s : pred.correspondants) {
						String chan = IrcUtility.getCorrespondant(s);
						if (chan.equals(channel))
							ctrl.selectPipe(pipe);
						return false;
					}
					return true;
				}
			});
		}
	}

}
