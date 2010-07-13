package jisssea.controller.commands;

import static java.util.Arrays.copyOfRange;
import static jisssea.controller.messages.MessageType.USER;
import static jisssea.util.StringUtility.join;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.commands.api.Command;
import jisssea.controller.messages.LogMessage;
import jisssea.controller.messages.Message;
import jisssea.controller.messages.UserMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author richard
 * 
 *         creates, lists and applies aliases
 * 
 *         egs:
 * 
 *         /alias /alias j /join $@ /alias -j /alias j*
 * 
 *         Variables: $@ - all arguments, space separated
 */
public class AliasCommand implements Command {

	private static final Log log = LogFactory.getLog(AliasCommand.class);

	private final Map<String, String> aliases = new HashMap<String, String>();

	@Override
	public void act(Message msg, BotRegistry irc, Controller ctrl) {
		// store topic changes
		if (msg.getType() == USER) {
			final UserMessage umsg = (UserMessage) msg;
			final String input = umsg.getInput();
			if (input.startsWith("/alias")) {
				final String[] cmd = input.split(" ");
				if (cmd.length == 1) {
					for (Entry<String, String> e : aliases.entrySet()) {
						ctrl.message(new LogMessage(e.getKey() + " => " + e.getValue()));
					}
				} else if (cmd.length == 2) {
					// remove
					if (cmd[1].charAt(0) == '-') {
						aliases.remove(cmd[1].substring(1));
						// regex match
					} else {
						final Pattern p = Pattern.compile(cmd[1]);
						for (Entry<String, String> e : aliases.entrySet()) {
							final String name = e.getKey();
							if (p.matcher(name).matches())
								ctrl.message(new LogMessage(name + " => " + e.getValue()));
						}
					}
					// addition
				} else {
					final String to = join(" ", copyOfRange(cmd, 2, cmd.length));
					aliases.put(cmd[1], to);
				}
			} else if (input.charAt(0) == '/') {
				final String rest = input.substring(1).split(" ")[0];
				final String command = aliases.get(rest);
				final String args = input.substring(input.indexOf(' ')).trim();
				if (command != null) {
					String replaced = command.replaceAll("@", args);
					ctrl.message(new UserMessage(replaced, umsg.getWindow()));
				}
			}
		}
	}
}
