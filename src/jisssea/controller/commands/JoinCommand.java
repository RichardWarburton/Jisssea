package jisssea.controller.commands;

import static jisssea.util.IrcUtility.isValidChannel;

import java.util.Map;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.Target;
import jisssea.controller.commands.api.Option;
import jisssea.controller.commands.api.TargetPredicate;
import jisssea.controller.commands.api.UserCommand;
import jisssea.controller.messages.ErrorMessage;
import jisssea.controller.messages.UserMessage;
import jisssea.controller.predicates.DefaultPredicate;
import jisssea.util.Procedure;

public class JoinCommand extends UserCommand {

	@Override
	@Option(name = "target", values = TargetPredicate.class)
	public void userAct(Map<String, ?> options, UserMessage msg, Pipe originalPipe, BotRegistry irc, final Controller ctrl, String remainder) {
		final Target target = (Target) options.get("target");
		final String channel = target.getCorrespondant();

		if (!isValidChannel(channel)) {
			ctrl.message(new ErrorMessage("You must /join a valid channel name"));
			return;
		}

		// check if channel exists
		Bot bot = target.getBot();
		if (!bot.getChannelSet().contains(channel)) {
			// create a new pipe with the default filter
			Pipe pipe = ctrl.createPipe(target);
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
					for (Target t : pred.correspondants) {
						if (t.getCorrespondant().equals(channel))
							ctrl.selectPipe(pipe);
						return false;
					}
					return true;
				}
			});
		}
	}

}
