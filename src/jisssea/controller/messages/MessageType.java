package jisssea.controller.messages;

import jisssea.controller.Target;

public enum MessageType {
	ERROR, LOG, WARNING, CONNECT, DISCONNECT, USER, MODE, ACTION {
		@Override
		public Target origin(Message msg) {
			ActionMessage msg2 = (ActionMessage) msg;
			return new Target(msg2.getBot(), msg2.getTarget());
		}
	},
	JOIN {
		@Override
		public Target origin(Message msg) {
			JoinMessage msg2 = (JoinMessage) msg;
			return new Target(msg2.getBot(), msg2.getSender());
		}
	},
	MESSAGE {
		@Override
		public Target origin(Message msg) {
			MessageMessage msg2 = (MessageMessage) msg;
			return new Target(msg2.getBot(), msg2.getChannel());
		}
	},
	KICK {
		@Override
		public Target origin(Message msg) {
			KickMessage msg2 = (KickMessage) msg;
			return new Target(msg2.getBot(), msg2.getKickerNick());
		}
	},
	VOICE {
		@Override
		public Target origin(Message msg) {
			VoiceMessage msg2 = (VoiceMessage) msg;
			return new Target(msg2.getBot(), msg2.getSourceNick());
		}
	},
	TOPIC {
		@Override
		public Target origin(Message msg) {
			TopicMessage msg2 = (TopicMessage) msg;
			return new Target(msg2.getBot(), msg2.getChannel());
		}
	},
	NICKCHANGE {
		@Override
		public Target origin(Message msg) {
			NickChangeMessage msg2 = (NickChangeMessage) msg;
			return new Target(msg2.getBot(), msg2.getOldNick());
		}
	},
	PART {
		@Override
		public Target origin(Message msg) {
			PartMessage msg2 = (PartMessage) msg;
			return new Target(msg2.getBot(), msg2.getChannel());
		}
	},
	PRIVATEMESSAGE {
		@Override
		public Target origin(Message msg) {
			PrivateMessageMessage msg2 = (PrivateMessageMessage) msg;
			return new Target(msg2.getBot(), msg2.getSender());
		}
	},
	USER_LIST {
		@Override
		public Target origin(Message msg) {
			UserListMessage msg2 = (UserListMessage) msg;
			return new Target(msg2.getBot(), msg2.getChannel());
		}
	};

	public Target origin(Message msg) {
		throw new IllegalArgumentException();
	}

}