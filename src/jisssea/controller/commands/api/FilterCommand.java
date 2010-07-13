package jisssea.controller.commands.api;

import static jisssea.controller.commands.api.FilterCommand.OnWhat.correspondant;

import java.util.Map;

import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.Target;
import jisssea.controller.messages.LogMessage;
import jisssea.controller.messages.MessageType;
import jisssea.controller.messages.UserMessage;
import jisssea.controller.predicates.DefaultPredicate;
import jisssea.util.CollectionsUtility;
import jisssea.util.CollectionsUtility.Action;

/**
 * /filter = print msgs
 * 
 * /filter remove|add correspondant|type uwcsnet:#bots|connect
 */
public class FilterCommand extends UserCommand {

	public enum OnWhat {
		correspondant, type
	};

	@Options({ @Option(name = "action", values = Action.class, required = false, requires = { "on", "payload" }),
			@Option(name = "on", values = OnWhat.class, required = false, requires = { "action", "payload" }),
			@Option(name = "payload", values = { TargetPredicate.class, MessageType.class }, required = false, requires = { "action", "on" }), })
	@Override
	public void userAct(Map<String, ?> options, UserMessage msg, Pipe pipe, BotRegistry irc, Controller ctrl) {
		DefaultPredicate pred = pipe.getDefaultPredicate();
		if (options.containsKey("action")) {
			ctrl.message(new LogMessage("Correspondants:"));
			for (String c : pred.correspondants) {
				ctrl.message(new LogMessage("\t" + c));
			}
			ctrl.message(new LogMessage("Message types:"));
			for (MessageType mt : pred.messageTypes) {
				ctrl.message(new LogMessage("\t" + mt));
			}
		} else {
			final Action act = (Action) options.get("action");
			if (options.get("on") == correspondant) {
				final Target target = (Target) options.get("payload");
				// TODO: refactor default predicate
				CollectionsUtility.doAct(act, pred.correspondants, "");
			} else {
				CollectionsUtility.doAct(act, pred.messageTypes, (MessageType) options.get("payload"));
			}
		}
	}

}
