package jisssea.bot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jisssea.config.Server;
import jisssea.config.User;
import jisssea.controller.Controller;
import jisssea.controller.messages.ActionMessage;
import jisssea.controller.messages.ConnectMessage;
import jisssea.controller.messages.DisconnectMessage;
import jisssea.controller.messages.JoinMessage;
import jisssea.controller.messages.KickMessage;
import jisssea.controller.messages.MessageMessage;
import jisssea.controller.messages.NickChangeMessage;
import jisssea.controller.messages.PartMessage;
import jisssea.controller.messages.PrivateMessageMessage;
import jisssea.controller.messages.TopicMessage;
import jisssea.controller.messages.VoiceMessage;

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
		log.message(new DisconnectMessage(this, server.getName()));
		super.onDisconnect();
	}

	@Override
	protected void onPrivateMessage(String sender, String login, String hostname, String message) {
		log.message(new PrivateMessageMessage(this, sender, login, hostname, message));
		super.onPrivateMessage(sender, login, hostname, message);
	}

	@Override
	protected void onAction(String sender, String login, String hostname, String target, String action) {
		log.message(new ActionMessage(this, sender, login, hostname, target, action));
		super.onAction(sender, login, hostname, target, action);
	}

	@Override
	protected void onPart(String channel, String sender, String login, String hostname) {
		log.message(new PartMessage(this, channel, sender, login, hostname));
		super.onPart(channel, sender, login, hostname);
	}

	@Override
	protected void onNickChange(String oldNick, String login, String hostname, String newNick) {
		log.message(new NickChangeMessage(this, oldNick, login, hostname, newNick));
		super.onNickChange(oldNick, login, hostname, newNick);
	}

	@Override
	protected void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
		log.message(new KickMessage(this, channel, kickerNick, kickerLogin, kickerHostname, recipientNick, reason));
		super.onKick(channel, kickerNick, kickerLogin, kickerHostname, recipientNick, reason);
	}

	@Override
	protected void onTopic(String channel, String topic, String setBy, long date, boolean changed) {
		log.message(new TopicMessage(this, channel, topic, setBy, date, changed));
		super.onTopic(channel, topic, setBy, date, changed);
	}

	@Override
	protected void onVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
		log.message(new VoiceMessage(this, channel, sourceNick, sourceLogin, sourceHostname, recipient));
		super.onVoice(channel, sourceNick, sourceLogin, sourceHostname, recipient);
	}

	@Override
	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
		log.message(new MessageMessage(this, channel, sender, login, hostname, message));
		super.onMessage(channel, sender, login, hostname, message);
	}

	@Override
	protected void onJoin(String channel, String sender, String login, String hostname) {
		log.message(new JoinMessage(this, channel, sender, login, hostname));
		super.onJoin(channel, sender, login, hostname);
	}

	@Override
	protected void onConnect() {
		log.message(new ConnectMessage(this, server.getName()));
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
