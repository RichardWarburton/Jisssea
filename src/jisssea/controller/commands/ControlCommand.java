package jisssea.controller.commands;

import static jisssea.util.IrcUtility.getCorrespondant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.messages.ErrorMessage;
import jisssea.controller.messages.UserMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author richard
 * 
 *         Deals with channel control commands eg:
 *         op/deop/voice/devoice/kick/ban
 * 
 */
public class ControlCommand extends RegexCommand {

	private static final Log log = LogFactory.getLog(ControlCommand.class);

	private static final Pattern p = Pattern.compile("/(kick|ban|unban|op|deop|voice|devoice)( [^ ]+)( [^ ]+)?");

	@Override
	protected Pattern pattern() {
		return p;
	}

	enum Command {
		kick, ban, unban, op, deop, voice, devoice
	};

	@Override
	protected void guardedAct(Matcher m, UserMessage msg, BotRegistry irc, Controller ctrl) {
		log.debug("Found control command: " + m.group(1) + " for " + m.group(2));
		try {
			final String nick = m.group(2).trim();
			final Bot bot = irc.getContext();
			final String channel = getCorrespondant(ctrl.getPipe(msg.getWindow()).getDefaultPredicate().getDefaultCorrespondant());
			final Command cmd = Command.valueOf(m.group(1));
			switch (cmd) {
			case kick:
				bot.kick(channel, nick);
				break;
			case ban:
				bot.ban(channel, nick);
				break;
			case unban:
				bot.unBan(channel, nick);
				break;
			case op:
				bot.op(channel, nick);
				break;
			case deop:
				bot.deOp(channel, nick);
				break;
			case voice:
				bot.voice(channel, nick);
				break;
			case devoice:
				bot.deVoice(channel, nick);
				break;
			}
		} catch (IllegalArgumentException e) {
			ctrl.message(new ErrorMessage("Format: /(kick|ban|op|deop|voice|devoice) user [channel]"));
		}
	}
}
