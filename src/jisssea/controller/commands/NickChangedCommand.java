package jisssea.controller.commands;

import static jisssea.controller.messages.MessageType.NICKCHANGE;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.Target;
import jisssea.controller.commands.api.Command;
import jisssea.controller.messages.Message;
import jisssea.controller.messages.NickChangeMessage;
import jisssea.controller.predicates.DefaultPredicate;
import jisssea.util.Procedure;

/**
 * Updates all correspondants lists on a new change
 * 
 * @author rlmw
 */
public class NickChangedCommand implements Command {

	@Override
	public void act(Message msg, BotRegistry irc, Controller ctrl) {
		if (msg.getType() == NICKCHANGE) {
			NickChangeMessage ncm = (NickChangeMessage) msg;
			final String oldNick = ncm.getOldNick();
			final String newNick = ncm.getNewNick();
			ctrl.foreachPipe(new Procedure<Pipe>() {
				@Override
				public boolean f(Pipe a) {
					final DefaultPredicate pred = a.getDefaultPredicate();
					for (Target target : pred.correspondants) {
						if (target.getCorrespondant().equals(oldNick))
							target.setCorrespondant(newNick);
					}
					return true;
				}
			});
		}
	}
}
