package jizzsea.controller.commands;

import static jizzsea.util.IrcUtility.getChannel;
import static jizzsea.util.IrcUtility.getNetwork;
import static jizzsea.util.IrcUtility.isValidName;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jizzsea.bot.Bot;
import jizzsea.bot.BotRegistry;
import jizzsea.controller.Controller;
import jizzsea.controller.Pipe;
import jizzsea.controller.messages.MessageMessage;
import jizzsea.controller.messages.NickChangeMessage;
import jizzsea.controller.messages.UserMessage;
import jizzsea.controller.predicates.DefaultPredicate;
import jizzsea.ui.DisplayWindow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author richard
 * 
 * Changes nicks:
 * 
 * /nick mullet
 *
 */
public class NickCommand extends RegexCommand {

	private static final Log log = LogFactory.getLog(NickCommand.class);
	
	private static final Pattern p = Pattern.compile("/nick (.*)");

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
		
		String nick = m.group(1);
		log.debug("Changing nick to:" + nick);

		DefaultPredicate pred = ctrl.getPipe(msg.getWindow())
				.getDefaultPredicate();
		if (pred != null) {
			for (String c : pred.correspondants) {
				final Bot bot = irc.get(getNetwork(c));
				String channel = getChannel(c);				
				bot.changeNick(nick);
			}
		}
	}

}
