package jisssea.controller.predicates;

import static jisssea.controller.messages.MessageType.ACTION;
import static jisssea.controller.messages.MessageType.ERROR;
import static jisssea.controller.messages.MessageType.JOIN;
import static jisssea.controller.messages.MessageType.KICK;
import static jisssea.controller.messages.MessageType.LOG;
import static jisssea.controller.messages.MessageType.MESSAGE;
import static jisssea.controller.messages.MessageType.MODE;
import static jisssea.controller.messages.MessageType.NICKCHANGE;
import static jisssea.controller.messages.MessageType.PART;
import static jisssea.controller.messages.MessageType.PRIVATEMESSAGE;
import static jisssea.controller.messages.MessageType.TOPIC;
import static jisssea.controller.messages.MessageType.USER_LIST;
import static jisssea.controller.messages.MessageType.VOICE;
import static jisssea.controller.messages.MessageType.WARNING;
import static jisssea.util.IrcUtility.isValidChannel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jisssea.bot.Bot;
import jisssea.controller.Target;
import jisssea.controller.messages.Message;
import jisssea.controller.messages.MessageType;
import jisssea.controller.messages.NickChangeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jibble.pircbot.User;

/**
 * When Pipes are created, a Default Predicate is attached
 * 
 * @author richard
 */
public class DefaultPredicate extends SimplePredicate {

	private static final Log log = LogFactory.getLog(DefaultPredicate.class);

	public final Set<Target> correspondants;
	public final Set<MessageType> messageTypes;

	private Target defaultTarget;

	public Target getDefaultTarget() {
		return defaultTarget;
	}

	public void setDefaultTarget(Target defaultTarget) {
		this.defaultTarget = defaultTarget;
	}

	public DefaultPredicate() {
		correspondants = new HashSet<Target>();
		messageTypes = new HashSet<MessageType>();
	}

	public DefaultPredicate(Target t) {
		this();
		correspondants.add(t);
		messageTypes.addAll(Arrays.asList(JOIN, PART, VOICE, PRIVATEMESSAGE, TOPIC, WARNING, ERROR, MESSAGE, KICK, LOG, ACTION, MODE, USER_LIST));
		defaultTarget = t;
	}

	@Override
	protected boolean check(Message msg) {

		log.debug("Checking: " + msg + " for " + correspondants);

		final MessageType type = msg.getType();
		// TODO: generise this fail
		if (type == NICKCHANGE) {
			final NickChangeMessage ncm = (NickChangeMessage) msg;
			final String newNick = ncm.getNewNick();
			final Bot bot = ncm.getBot();
			final String nick = bot.getNick();
			for (Target id : correspondants) {
				final String maybeChan = id.getCorrespondant();
				// same network
				if (bot == id.getBot()) {
					log.debug("same network");
					if (isValidChannel(maybeChan)) {
						log.debug("is a channel");
						// check members of a channel
						for (User user : bot.getUsers(maybeChan)) {
							if (log.isDebugEnabled())
								log.debug(user.getNick() + " is " + user.getNick().equals(newNick));
							if (user.getNick().equals(newNick))
								return true;
						}
					} else {
						return maybeChan.equals(nick);
					}
				}
			}
			return false;
		} else {
			final boolean containsType = messageTypes.contains(type);
			try {
				return containsType && (correspondants.isEmpty() || correspondants.contains(type.origin(msg)));
			} catch (IllegalArgumentException e) {
				return containsType;
			}
		}
	}
}
