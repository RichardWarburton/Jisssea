package jisssea.controller.commands;

import static jisssea.controller.commands.FilterCommand.OnWhat.correspondant;

import java.util.Collection;
import java.util.Map;

import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.Target;
import jisssea.controller.commands.api.Option;
import jisssea.controller.commands.api.Options;
import jisssea.controller.commands.api.TargetPredicate;
import jisssea.controller.commands.api.UserCommand;
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
	public void userAct(Map<String, ?> options, UserMessage msg, Pipe pipe, BotRegistry irc, Controller ctrl, String reminader) {
		DefaultPredicate pred = pipe.getDefaultPredicate();
		if (options.containsKey("action")) {
			printMessage("Correspondants", pred.correspondants, ctrl);
			printMessage("Message types:", pred.messageTypes, ctrl);
		} else {
			final Action act = (Action) options.get("action");
			if (options.get("on") == correspondant) {
				final Target target = (Target) options.get("payload");
				CollectionsUtility.doAct(act, pred.correspondants, target);
			} else {
				CollectionsUtility.doAct(act, pred.messageTypes, (MessageType) options.get("payload"));
			}
		}
	}

	private void printMessage(String name, Collection<?> msgs, Controller ctrl) {
		ctrl.message(new LogMessage(name));
		for (Object c : msgs) {
			ctrl.message(new LogMessage("\t" + c.toString()));
		}
	}

}
