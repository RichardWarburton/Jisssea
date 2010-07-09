package jizzsea.bot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jizzsea.config.Server;
import jizzsea.config.User;
import jizzsea.controller.Controller;
import jizzsea.controller.messages.ConnectMessage;
import jizzsea.controller.messages.DisconnectMessage;
import jizzsea.controller.messages.JoinMessage;
import jizzsea.controller.messages.KickMessage;
import jizzsea.controller.messages.MessageMessage;
import jizzsea.controller.messages.NickChangeMessage;
import jizzsea.controller.messages.PartMessage;
import jizzsea.controller.messages.PrivateMessageMessage;
import jizzsea.controller.messages.TopicMessage;
import jizzsea.controller.messages.VoiceMessage;

import org.jibble.pircbot.PircBot;

public class Bot extends PircBot {

	private final Controller log;
	private final Server server;
	private final String serverName;

	public String getServerName() {
		return serverName;
	}

	@Override
	protected void onDisconnect() {
		log.message(new DisconnectMessage(this,server.getName()));
		super.onDisconnect();
	}

	@Override
	protected void onPrivateMessage(String sender, String login,
			String hostname, String message) {
		log.message(new PrivateMessageMessage(this,sender, login, hostname, message));
		super.onPrivateMessage(sender, login, hostname, message);
	}

	@Override
	protected void onPart(String channel, String sender, String login,
			String hostname) {
		log.message(new PartMessage(this,channel, sender, login, hostname));
		super.onPart(channel, sender, login, hostname);
	}

	@Override
	protected void onNickChange(String oldNick, String login, String hostname,
			String newNick) {
		log.message(new NickChangeMessage(this,oldNick, login, hostname, newNick));
		super.onNickChange(oldNick, login, hostname, newNick);
	}

	@Override
	protected void onKick(String channel, String kickerNick,
			String kickerLogin, String kickerHostname, String recipientNick,
			String reason) {
		log.message(new KickMessage(this,channel, kickerNick, kickerLogin, kickerHostname, recipientNick, reason));
		super.onKick(channel, kickerNick, kickerLogin, kickerHostname, recipientNick,
				reason);
	}

	@Override
	protected void onTopic(String channel, String topic, String setBy,
			long date, boolean changed) {
		log.message(new TopicMessage(this,channel, topic, setBy, date, changed));
		super.onTopic(channel, topic, setBy, date, changed);
	}

	@Override
	protected void onVoice(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String recipient) {
		log.message(new VoiceMessage(this,channel, sourceNick, sourceLogin, sourceHostname, recipient));
		super.onVoice(channel, sourceNick, sourceLogin, sourceHostname, recipient);
	}
	
	@Override
	protected void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		log.message(new MessageMessage(this,channel, sender, login, hostname, message));
		super.onMessage(channel, sender, login, hostname, message);
	}
	
	@Override
	protected void onJoin(String channel, String sender, String login,
			String hostname) {
		log.message(new JoinMessage(this,channel, sender, login, hostname));
		super.onJoin(channel, sender, login, hostname);
	}
	
	@Override
	protected void onConnect() {
		log.message(new ConnectMessage(this,server.getName()));
		super.onConnect();
	}
	
	
	public Bot(final Controller log, final Server server, final User user) throws ConnectionException {
		try {
			this.log = log;
			this.server = server;
			this.serverName = server.getName();
			setName(user.getName());
			connect(server.getAddress(), server.getPort());
			
		} catch (Exception e) {
			throw new ConnectionException(e);
		}
	}
	
	public Set<String> getChannelSet() {
		return new HashSet<String>(Arrays.asList(getChannels()));
	}

}
