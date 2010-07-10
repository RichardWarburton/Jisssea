package jisssea.controller.messages;

public enum MessageType {
	ERROR,
	LOG,
	WARNING,
	CONNECT,
	DISCONNECT,
	USER,
	JOIN {
		@Override public String origin(Message msg) {
			JoinMessage msg2 = (JoinMessage)msg;
			return msg2.getBot().getServerName()+":"+msg2.getSender();
		}
	},
	MESSAGE {
		@Override public String origin(Message msg) {
			MessageMessage msg2 = (MessageMessage)msg;
			return msg2.getBot().getServerName()+":"+msg2.getChannel();
		}
	},
	KICK {
		@Override public String origin(Message msg) {
			KickMessage msg2 = (KickMessage)msg;
			return msg2.getBot().getServerName()+":"+msg2.getKickerNick();
		}
	},
	VOICE {
		@Override public String origin(Message msg) {
			VoiceMessage msg2 = (VoiceMessage)msg;
			return msg2.getBot().getServerName()+":"+msg2.getSourceNick();
		}
	},
	TOPIC {
		@Override public String origin(Message msg) {
			TopicMessage msg2 = (TopicMessage)msg;
			return msg2.getBot().getServerName()+":"+msg2.getChannel();
		}
	},
	NICKCHANGE {
		@Override public String origin(Message msg) {
			NickChangeMessage msg2 = (NickChangeMessage)msg;
			return msg2.getBot().getServerName()+":"+msg2.getOldNick();
		}
	},
	PART {
		@Override public String origin(Message msg) {
			PartMessage msg2 = (PartMessage)msg;
			return msg2.getBot().getServerName()+":"+msg2.getChannel();
		}
	},
	PRIVATEMESSAGE {
		@Override public String origin(Message msg) {
			PrivateMessageMessage msg2 = (PrivateMessageMessage)msg;
			return msg2.getBot().getServerName()+":"+msg2.getSender();
		}
	};
	
	public String origin(Message msg) {
		throw new IllegalArgumentException();
	}
}