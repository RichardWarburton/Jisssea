package jizzsea.controller.messages;

import jizzsea.bot.Bot;

public class TopicMessage extends Message {

	final String channel, topic, setBy;
	final long date;
	final boolean changed;
	
	private final Bot bot;
	
	public Bot getBot() {
		return bot;
	}
	
	@Override
	public String toString() {
		return "TopicMessage [channel=" + channel + ", topic=" + topic
				+ ", setBy=" + setBy + ", date=" + date + ", changed="
				+ changed + ", bot=" + bot.getServerName() + "]";
	}

	public TopicMessage(final Bot bot, String channel, String topic,
			String setBy, long date, boolean changed) {
		super(MessageType.TOPIC);
		this.channel = channel;
		this.topic = topic;
		this.setBy = setBy;
		this.date = date;
		this.changed = changed;
		this.bot = bot;
	}
	public String getChannel() {
		return channel;
	}
	public String getTopic() {
		return topic;
	}
	public String getSetBy() {
		return setBy;
	}
	public long getDate() {
		return date;
	}
	public boolean isChanged() {
		return changed;
	}
}
