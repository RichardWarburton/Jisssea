package jisssea.controller.commands;

import static jisssea.util.CollectionsUtility.getOrDefault;

import java.util.Map;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.Target;
import jisssea.controller.commands.api.Option;
import jisssea.controller.commands.api.Options;
import jisssea.controller.commands.api.StringPredicate;
import jisssea.controller.commands.api.TargetPredicate;
import jisssea.controller.commands.api.UserCommand;
import jisssea.controller.messages.UserMessage;

/**
 * @author richard
 * 
 *         Deals with channel control commands eg:
 *         op/deop/voice/devoice/kick/ban
 * 
 */
public class ControlCommand extends UserCommand {

	// private static final Log log = LogFactory.getLog(ControlCommand.class);

	enum Command {
		kick, ban, unban, op, deop, voice, devoice
	};

	@Override
	@Options({ @Option(name = "command", values = Command.class, required = true),
			@Option(name = "nick", values = StringPredicate.class, required = true),
			@Option(name = "target", values = TargetPredicate.class, required = false) })
	public void userAct(Map<String, ?> options, UserMessage msg, Pipe pipe, BotRegistry irc, Controller ctrl, String remainder) {
		final Command cmd = (Command) options.get("command");
		final String nick = (String) options.get("nick");
		final Target target = getOrDefault(options, "target", pipe.getDefaultPredicate().getDefaultTarget());
		final Bot bot = target.getBot();
		final String channel = target.getCorrespondant();
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
	}
}
