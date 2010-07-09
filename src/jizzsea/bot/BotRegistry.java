package jizzsea.bot;

import java.util.HashMap;
import java.util.Map;

import jizzsea.config.Server;
import jizzsea.config.User;
import jizzsea.controller.Controller;
import jizzsea.controller.messages.ErrorMessage;

public class BotRegistry {

	private final Map<String, Bot> registry;
	private final jizzsea.config.User user;
	private final Controller ctrl;
	
	public BotRegistry(User u, Controller ctrl) {
		registry = new HashMap<String, Bot>();
		this.user = u;
		this.ctrl = ctrl;
	}
	
	public Bot connect(Server server) {
		final String name = server.getName();
		try {
			final Bot bot = new Bot(ctrl,server, user);
			registry.put(name,bot);
			return bot;
		} catch (ConnectionException e) {
			ctrl.message(new ErrorMessage("Error whilst connecting to " + name, e));
			return null;
		}
	}
	
	public Bot get(final String name) {
		return registry.get(name);
	}
	
}
