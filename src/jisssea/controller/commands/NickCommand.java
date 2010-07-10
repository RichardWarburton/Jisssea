package jisssea.controller.commands;

import static jisssea.util.IrcUtility.getCorrespondant;
import static jisssea.util.IrcUtility.getNetwork;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.messages.UserMessage;
import jisssea.controller.predicates.DefaultPredicate;

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
				String channel = getCorrespondant(c);				
				bot.changeNick(nick);
			}
		}
	}

}
