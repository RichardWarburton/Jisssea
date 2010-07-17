package jisssea.controller.commands;

import static jisssea.controller.messages.MessageType.PART;

import java.util.Set;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.Target;
import jisssea.controller.commands.api.Command;
import jisssea.controller.messages.Message;
import jisssea.controller.messages.PartMessage;
import jisssea.controller.predicates.DefaultPredicate;
import jisssea.util.Procedure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author richard
 * 
 *         closes windows when you part them
 * 
 */
public class PartReceivedCommand implements Command {

	private static final Log log = LogFactory.getLog(PartReceivedCommand.class);

	@Override
	public void act(Message msg, BotRegistry irc, final Controller ctrl) {
		if (msg.getType() == PART) {
			final PartMessage part = (PartMessage) msg;
			final Bot bot = part.getBot();
			final String senderNick = part.getSender();
			if (bot.getNick().equals(senderNick)) {
				final Target id = new Target(bot, part.getChannel());
				ctrl.foreachPipe(new Procedure<Pipe>() {
					@Override
					public boolean f(Pipe pipe) {
						final DefaultPredicate pred = pipe.getDefaultPredicate();
						if (pred != null) {
							final Set<Target> correspondants = pred.correspondants;

							log.debug("checking part for: " + correspondants);

							// don't remove status windows
							if (!correspondants.isEmpty()) {
								correspondants.remove(id);
								if (correspondants.isEmpty()) {
									ctrl.closePipe(pipe);
								}
							}
						}
						return true;
					}
				});
			}
		}
	}

}
