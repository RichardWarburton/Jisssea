package jizzsea.controller.commands;

import static jizzsea.util.IrcUtility.isValidName;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jizzsea.bot.Bot;
import jizzsea.bot.BotRegistry;
import jizzsea.controller.Controller;
import jizzsea.controller.Pipe;
import jizzsea.controller.messages.UserMessage;
import jizzsea.controller.predicates.DefaultPredicate;
import jizzsea.ui.DisplayWindow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author richard
 * 
 * /part
 * 
 * /part sigh
 * /part #bots
 * /part #bots sigh
 *
 */
public class PartCommand extends RegexCommand {

	private static final Log log = LogFactory.getLog(PartCommand.class);
	
	private static final Pattern p = Pattern.compile("/part( [^ ]*)?( [^ ]*)?");

//	public static void main(String[] args) {
//		for (String s : Arrays.asList("/part #bots","/part", "/part sigh", "/part #bots sigh")) {
//			Matcher matcher = p.matcher(s);
//			if(matcher.matches()) {
//				System.out.println(matcher.group());
//				System.out.println(matcher.group(1));
//				if(matcher.groupCount() > 1)
//					System.out.println(matcher.group(2));
//				if(matcher.groupCount() > 2)
//					System.out.println(matcher.group(3));
//			}
//		}
//		
//	}
	
	@Override
	protected Pattern pattern() {
		return p;
	}

	@Override
	protected void guardedAct(Matcher m, UserMessage msg, BotRegistry irc,
			Controller ctrl) {
		
		// TODO: get default from some user defined setting
		String message = "goodbye chumps";
		DisplayWindow context = msg.getWindow();
		
		// /part
		if(m.group(1) == null) {
			partChannels(message, context, irc, ctrl);
		// /part sigh
		} else if(m.group(2) == null && ! isValidName(m.group(2))) {
			partChannels(m.group(2), context, irc, ctrl);
	
		// /part uwcsnet:#bots
		} else if(m.group(2) == null) {
			partChannel(message, irc, m.group(1));
		// /part uwcsnet:#bots sigh
		} else {
			partChannel(m.group(2), irc, m.group(1));
		}
		
	}

	private void partChannels(String msg, DisplayWindow context, BotRegistry irc, Controller ctrl) {
		Pipe pipe = ctrl.getPipe(context);
		DefaultPredicate pred = pipe.getDefaultPredicate();
		for (String e: pred.correspondants) {
			partChannel(msg, irc, e);
		}
	}

	private void partChannel(String msg, BotRegistry irc, String e) {
		final String[] split = e.split(":");
		final Bot bot = irc.get(split[0]);
		final String channel = split[1];
		bot.partChannel(channel,msg);
	}

}
