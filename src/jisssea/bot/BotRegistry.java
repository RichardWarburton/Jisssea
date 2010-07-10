package jisssea.bot;

import static jisssea.util.IrcUtility.getNetwork;
import static jisssea.util.IrcUtility.isValidTarget;

import java.util.HashMap;
import java.util.Map;

import jisssea.config.Server;
import jisssea.config.User;
import jisssea.controller.Controller;
import jisssea.controller.messages.ErrorMessage;

public class BotRegistry {

	private final Map<String, Bot> registry;
	private final jisssea.config.User user;
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
	
	/**
	 * Returns the default network, based on the current context
	 * @return
	 */
	public Bot getContext() {
		// TODO: implement this properly
		return get("uwcsnet");
	}

	/**
	 * Gets default, or something specified by a target string
	 * @param target
	 * @return
	 */
	public Bot getContext(final String target) {
		return (isValidTarget(target))?get(getNetwork(target)):getContext();
	}
	
	public Bot get(final String name) {
		return registry.get(name);
	}
	
}
