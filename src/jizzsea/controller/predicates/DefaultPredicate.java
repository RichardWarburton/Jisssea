package jizzsea.controller.predicates;

import static jizzsea.controller.messages.MessageType.ERROR;
import static jizzsea.controller.messages.MessageType.JOIN;
import static jizzsea.controller.messages.MessageType.KICK;
import static jizzsea.controller.messages.MessageType.LOG;
import static jizzsea.controller.messages.MessageType.MESSAGE;
import static jizzsea.controller.messages.MessageType.NICKCHANGE;
import static jizzsea.controller.messages.MessageType.PART;
import static jizzsea.controller.messages.MessageType.PRIVATEMESSAGE;
import static jizzsea.controller.messages.MessageType.TOPIC;
import static jizzsea.controller.messages.MessageType.VOICE;
import static jizzsea.controller.messages.MessageType.WARNING;
import static jizzsea.util.IrcUtility.getChannel;
import static jizzsea.util.IrcUtility.getNetwork;
import static jizzsea.util.IrcUtility.isValidChannel;
import static jizzsea.util.IrcUtility.isValidName;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jizzsea.bot.Bot;
import jizzsea.controller.messages.Message;
import jizzsea.controller.messages.MessageType;
import jizzsea.controller.messages.NickChangeMessage;
import jizzsea.util.IrcUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jibble.pircbot.User;

/**
 * When Pipes are created, a Default Predicate is attached
 * @author richard
 */
public class DefaultPredicate extends SimplePredicate {

	private static final Log log = LogFactory.getLog(DefaultPredicate.class);

	public final Set<String> correspondants;
	public final Set<MessageType> messageTypes;
	
	public DefaultPredicate(String correspondant) {
		correspondants = new HashSet<String>();
		correspondants.add(correspondant);
		messageTypes = new HashSet<MessageType>();
		messageTypes.addAll(Arrays.asList(
			JOIN,
			PART,
			VOICE,
			PRIVATEMESSAGE,
			TOPIC,
			WARNING,
			ERROR,
			MESSAGE,
			KICK,
			LOG));
	}

	@Override
	protected boolean check(Message msg) {
		
		log.debug("Checking: "+msg+" for "+ correspondants);

		final MessageType type = msg.getType();
		// TODO: generise this fail
		if(type == NICKCHANGE) {
			final NickChangeMessage ncm = (NickChangeMessage) msg;
			final String newNick = ncm.getNewNick();
			final Bot bot = ncm.getBot();
			final String nick = bot.getNick();
			for (String id: correspondants) {
				final String maybeChan = getChannel(id);
				// same network
				if(bot.getServerName().equals(getNetwork(id))) {
					log.debug("same network");
					if(isValidChannel(maybeChan)) {
						log.debug("is a channel");
						// check members of a channel
						for(User user : bot.getUsers(maybeChan)) {
							if(log.isDebugEnabled())
								log.debug(user.getNick()+" is "+user.getNick().equals(newNick));
							if(user.getNick().equals(newNick)) 
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
				
				return containsType &&
					 (correspondants.isEmpty() || correspondants.contains(type.origin(msg)));
			} catch (IllegalArgumentException e) {
				return containsType;
			}
		}
	}
	
}
