package jizzsea.controller.commands;

import static jizzsea.controller.messages.MessageType.PART;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jizzsea.bot.Bot;
import jizzsea.bot.BotRegistry;
import jizzsea.controller.Controller;
import jizzsea.controller.Pipe;
import jizzsea.controller.messages.Message;
import jizzsea.controller.messages.PartMessage;
import jizzsea.controller.predicates.DefaultPredicate;
import jizzsea.util.Procedure;

/**
 * 
 * @author richard
 * 
 * closes windows when you part them
 *
 */
public class PartReceivedCommand implements Command {

	private static final Log log = LogFactory.getLog(PartReceivedCommand.class);
	
	@Override
	public void act(Message msg, BotRegistry irc, final Controller ctrl) {
		if(msg.getType() == PART) {
			final PartMessage part = (PartMessage)msg;
			final Bot bot = part.getBot();
			final String senderNick = part.getSender();
			if(bot.getNick().equals(senderNick)) {
				final String id = bot.getServerName() + ":" + part.getChannel();
				ctrl.foreachPipe(new Procedure<Pipe>() {
					@Override
					public boolean f(Pipe pipe) {
						final DefaultPredicate pred = pipe.getDefaultPredicate();
						if(pred != null) {
							final Set<String> correspondants = pred.correspondants;
							
							log.debug("checking part for: "+correspondants);
							
							// don't remove status windows
							if(!correspondants.isEmpty()) {
								correspondants.remove(id);
								if(correspondants.isEmpty()) {
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
