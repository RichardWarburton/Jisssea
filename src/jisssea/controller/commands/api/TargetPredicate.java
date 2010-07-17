package jisssea.controller.commands.api;

import static jisssea.util.IrcUtility.getCorrespondant;
import static jisssea.util.IrcUtility.getNetwork;
import static jisssea.util.IrcUtility.isValidTarget;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Target;

public class TargetPredicate implements ValuePredicate<Target> {

	@Override
	public Target check(String s, BotRegistry irc, Controller ctrl) {
		// complete target
		if (isValidTarget(s)) {
			return new Target(irc.get(getNetwork(s)), getCorrespondant(s));
		} else {
			// just a channel
			return new Target(irc.getContext(), s);
		}
	}

}
