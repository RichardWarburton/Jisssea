package jisssea.controller.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.messages.ErrorMessage;
import jisssea.controller.messages.UserMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author richard
 * 
 * /win 1
 *
 */
public class WindowCommand extends RegexCommand {

	private static final Log log = LogFactory.getLog(WindowCommand.class);
	
	private static final Pattern p = Pattern.compile("/win (\\d+)");
	
//	public static void main(String[] args) {
//		Matcher m = p.matcher("/win 0");
//		if(m.matches()) {
//			System.out.println(m.group(1));
//		}
//	}

	@Override
	protected Pattern pattern() {
		return p;
	}

	@Override
	protected void guardedAct(Matcher m, UserMessage msg, BotRegistry irc,
			Controller ctrl) {
		log.debug("Received Window Command: "+m.group());
		try {
			final int windowNumber = Integer.parseInt(m.group(1));
			ctrl.selectPipe(windowNumber);
		} catch (NumberFormatException e) {
			ctrl.message(new ErrorMessage("You need to pass the /win command a window number"));
		} catch (IllegalArgumentException e) {
			ctrl.message(new ErrorMessage("The supplied window number must be the number of a window"));
		}
	}

}
