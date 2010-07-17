package jisssea.controller.commands;

import static jisssea.controller.messages.MessageType.TOPIC;
import static jisssea.util.CollectionsUtility.getOrDefault;

import java.util.HashMap;
import java.util.Map;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.Target;
import jisssea.controller.commands.api.Option;
import jisssea.controller.commands.api.TargetPredicate;
import jisssea.controller.commands.api.UserCommand;
import jisssea.controller.messages.ErrorMessage;
import jisssea.controller.messages.Message;
import jisssea.controller.messages.TopicMessage;
import jisssea.controller.messages.UserMessage;

/**
 * @author richard
 * 
 *         Handles: storing topics when they change /topic command
 * 
 *         /topic /topic someTopic /topic uwcsnet:#bots someTopic
 * 
 */
public class TopicCommand extends UserCommand {

	private final Map<Target, String> topics = new HashMap<Target, String>();

	@Override
	public void act(Message msg, BotRegistry irc, Controller ctrl) {
		// store topic changes
		if (msg.getType() == TOPIC) {
			TopicMessage tmsg = (TopicMessage) msg;
			topics.put(TOPIC.origin(msg), tmsg.getTopic());
		}
		// respond to /topic
		super.act(msg, irc, ctrl);
	}

	@Override
	@Option(name = "target", values = TargetPredicate.class, required = false)
	public void userAct(Map<String, ?> options, UserMessage msg, Pipe pipe, BotRegistry irc, Controller ctrl, String remainder) {
		final Target defaultTarget = pipe.getDefaultPredicate().getDefaultTarget();
		Target target = getOrDefault(options, "target", defaultTarget);
		if (!target.isChannel()) {
			remainder = target.getCorrespondant() + " " + remainder;
			target = defaultTarget;
		}
		try {
			final Bot bot = target.getBot();
			bot.setTopic(target.getCorrespondant(), remainder);
			topics.put(target, remainder);
		} catch (Exception e) {
			log.error("Error setting topic", e);
			ctrl.message(new ErrorMessage("Error setting topic: " + e.getMessage()));
		}
	}
}
