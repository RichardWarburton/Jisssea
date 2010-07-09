package jizzsea.controller.commands;

import static jizzsea.util.IrcUtility.isValidChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jizzsea.bot.Bot;
import jizzsea.bot.BotRegistry;
import jizzsea.controller.Controller;
import jizzsea.controller.Pipe;
import jizzsea.controller.messages.ErrorMessage;
import jizzsea.controller.messages.UserMessage;
import jizzsea.controller.predicates.DefaultPredicate;
import jizzsea.ui.DisplayWindow;
import jizzsea.util.IrcUtility;
import jizzsea.util.Procedure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author richard
 * 
 * /join #bots
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
	protected void guardedAct(Matcher m, UserMessage msg, BotRegistry irc,
			final Controller ctrl) {
		DisplayWindow context = msg.getWindow();
		final String channel = m.group(1);
		
		if(!isValidChannel(channel)) {
			ctrl.message(new ErrorMessage("You must /join a valid channel name"));
			return;
		}
		
		// TODO: generic networks
		// check if channel exists
		Bot bot = irc.get("uwcsnet");
		if(!bot.getChannelSet().contains(channel)) {
			// create a new pipe with the default filter
			Pipe pipe = ctrl.createPipe();
			DefaultPredicate pred = new DefaultPredicate(bot.getServerName()+":"+channel);
			pipe.predicates.add(pred);
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
					for(String s:pred.correspondants) {
						String chan = IrcUtility.getChannel(s);
						if(chan.equals(channel))
							ctrl.selectPipe(pipe);
							return false;
					}
					return true;
				}
			});
		}
	}

}
