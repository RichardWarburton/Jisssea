package jizzsea.controller.commands;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jizzsea.bot.BotRegistry;
import jizzsea.controller.Controller;
import jizzsea.controller.messages.ErrorMessage;
import jizzsea.controller.messages.LogMessage;
import jizzsea.controller.messages.MessageType;
import jizzsea.controller.messages.UserMessage;
import jizzsea.controller.predicates.DefaultPredicate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author richard
 * 
 * Changes the filters of a DefaultViewPredicate
 * 
 * /filter = print msgs
 * /filter remove|add correspondant|type uwcsnet:#bots|connect
 * 
 */
public class FilterCommand extends RegexCommand {

	private static final Log log = LogFactory.getLog(FilterCommand.class);

	private static final Pattern p = Pattern.compile("/filter( .*)?");

	@Override
	protected Pattern pattern() {
		return p;
	}

	enum Action { remove , add };
	enum OnWhat { correspondant , type };
	
	@Override
	protected void guardedAct(Matcher m, UserMessage msg, BotRegistry irc,
			Controller ctrl) {
		final DefaultPredicate pred = ctrl.getPipe(msg.getWindow())
						.getDefaultPredicate();

		if (m.group(1) == null) {
			ctrl.message(new LogMessage("Correspondants:"));
			for (String c : pred.correspondants) {
				
				ctrl.message(new LogMessage("\t"+c));
			}
			ctrl.message(new LogMessage("Message types:"));
			for (MessageType mt : pred.messageTypes) {
				ctrl.message(new LogMessage("\t"+mt));
			}
		} else {
			final String[] words = m.group(1).trim().split(" ");
			
			try {
				final boolean add = Action.valueOf(words[0]) == Action.add;
				if(OnWhat.valueOf(words[1]) == OnWhat.type) {
					final String typeName = words[2].toUpperCase();
					if(add) {
						pred.messageTypes.add(MessageType.valueOf(typeName));
					} else {
						if(!pred.messageTypes.remove(MessageType.valueOf(typeName))) {
							ctrl.message(new LogMessage("This window isn't matching messages of type "+typeName));
						}
					}
				} else {
					if(add) {
						pred.correspondants.add(words[2]);
					} else {
						pred.correspondants.remove(words[2]);
					}
				}
			} catch (Exception e) {
				ctrl.message(new ErrorMessage("Format: /filter remove|add correspondant|type 'channel id'|'message name'"));
			}
		}
	}

}
