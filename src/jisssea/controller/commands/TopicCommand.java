package jisssea.controller.commands;

import static jisssea.controller.messages.MessageType.TOPIC;
import static jisssea.util.IrcUtility.getCorrespondant;
import static jisssea.util.IrcUtility.getNetwork;
import static jisssea.util.IrcUtility.isValidTarget;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.commands.api.RegexCommand;
import jisssea.controller.messages.LogMessage;
import jisssea.controller.messages.Message;
import jisssea.controller.messages.TopicMessage;
import jisssea.controller.messages.UserMessage;
import jisssea.controller.predicates.DefaultPredicate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author richard
 * 
 * Handles:
 * 	storing topics when they change
 * 	/topic command
 * 
 * /topic
 * /topic faux is gay
 * /topic uwcsnet:#bots faux is gay
 *
 */
public class TopicCommand extends RegexCommand {

	private static final Log log = LogFactory.getLog(TopicCommand.class);
	
	private static final Pattern p = Pattern.compile("/topic( .*)?");

	public static void main(String[] args) {
		String change = " uwcsnet:#jizz faux is gay".trim();
		System.out.println(change.substring(change.indexOf(' ')).trim());
		for (String s : Arrays.asList("/topic","/topic uwcsnet:#jizz faux is gay", "/topic faux is gay")) {
			Matcher matcher = p.matcher(s);
			if(matcher.matches()) {
				System.out.println(matcher.group());
				String g1 = matcher.group(1);
				System.out.println(g1);
				if(g1 != null)
					System.out.println(Arrays.toString(g1.split(" ")));
			}
		}
	}
	
	@Override
	protected Pattern pattern() {
		return p;
	}
	
	private final Map<String, String> topics = new HashMap<String, String>();

	public void act(Message msg, BotRegistry irc, Controller ctrl) {
		// store topic changes
		if(msg.getType() == TOPIC) {
			TopicMessage tmsg = (TopicMessage) msg;
			topics.put(TOPIC.origin(msg),tmsg.getTopic());
		}
		// respond to /topic
		super.act(msg, irc, ctrl);
	}
	
	@Override
	protected void guardedAct(Matcher m, UserMessage msg, BotRegistry irc,
			Controller ctrl) {
		String change = m.group(1);
		final Pipe pipe = ctrl.getPipe(msg.getWindow());
		
		// return current topic
		final DefaultPredicate pred = pipe.getDefaultPredicate();
		if(change == null) {
			for(String c: pred.correspondants) {
				final String topic = topics.get(c);
				if(topic == null) {
					ctrl.message(new LogMessage("Topic unknown or unset for "+c));
				} else {
					ctrl.message(new LogMessage("Topic for "+c+" is "+topic));
				}
			}

		// change topic
		} else {
			final String maybeChan = change.split(" ")[1];
			log.debug(maybeChan+":-"+isValidTarget(maybeChan));
			if (isValidTarget(maybeChan)) {
				final String trimmed = change.trim();
				String setTo = trimmed.substring(trimmed.indexOf(' ')).trim();
				setTopic(irc, change, maybeChan,setTo);
			} else {
				if(pred.correspondants.size() == 1) {
					log.debug(pred.correspondants);
					String chan = pred.correspondants.iterator().next();
					log.debug(chan);
					setTopic(irc, change, chan,maybeChan);
				} else {
					ctrl.message(new LogMessage("You must specify a channel id if you have multiple channels for this window"));
				}
			}
		}
	}

	private void setTopic(BotRegistry irc, String change, final String maybeChan, String setTo) {
		try {
			final Bot bot = irc.get(getNetwork(maybeChan));
			bot.setTopic(getCorrespondant(maybeChan), setTo);
		} catch (Exception e) {
			log.error("Error setting topic",e);
		}
	}

}
