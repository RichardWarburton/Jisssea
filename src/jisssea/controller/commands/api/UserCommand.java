package jisssea.controller.commands.api;

import static jisssea.controller.messages.MessageType.USER;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jisssea.bot.Bot;
import jisssea.bot.BotRegistry;
import jisssea.controller.Controller;
import jisssea.controller.Pipe;
import jisssea.controller.messages.ErrorMessage;
import jisssea.controller.messages.Message;
import jisssea.controller.messages.UserMessage;
import jisssea.util.PluginUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author rlmw
 * 
 *         Reworked UserCommand api to replace RegexCommand api
 * 
 *         Generalise Options Parsing for user commands
 * 
 *         - Should return appropriate typesafe values (eg enums)
 * 
 *         - whitespace + case insensitive for command names
 * 
 *         - generated help messages on a parse fail
 * 
 *         - declarative, annotations based
 * 
 *         - make all commands standardise on sending to 1 pipe's default
 *         channel, with standard target based option
 * 
 */
public abstract class UserCommand implements Command {

	protected final Log log = LogFactory.getLog(getClass());
	private Option option;
	private Options options;
	private String name;

	public String getName() {
		return name;
	}

	/**
	 * Hooks up appropriate information for the act method
	 */
	public UserCommand() {
		try {
			final Class<? extends UserCommand> cls = getClass();
			final Method userAct = cls.getMethod("userAct", Map.class, Pipe.class, Bot.class, BotRegistry.class, Controller.class);
			option = userAct.getAnnotation(Option.class);
			options = userAct.getAnnotation(Options.class);
			final Name annotation = cls.getAnnotation(Name.class);
			if (annotation == null) {
				final String className = cls.getName();
				if (className.endsWith("Command")) {
					name = className.substring(0, className.length() - 6);
				} else {
					name = className;
				}
				name = name.toLowerCase();
			} else {
				name = annotation.value();
			}
			if (option != null && options != null) {
				throw new UserCommandError("You may not specify both an option and a set of Options for the UserCommand " + name);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void act(Message msg, BotRegistry irc, Controller ctrl) {
		if (msg.getType() == USER) {
			final UserMessage umsg = (UserMessage) msg;
			String input = umsg.getInput();
			if (input.startsWith('/' + name)) {
				input = input.substring(name.length());
				final Pipe pipe = ctrl.getPipe(umsg.getWindow());
				final Map<String, Object> optionResults = new HashMap<String, Object>();
				final Map<String, String[]> requirements = new HashMap<String, String[]>();
				try {
					if (option != null) {
						checkOption(optionResults, requirements, option, input, pipe, irc, ctrl);
					} else {
						for (Option option : options.value()) {
							checkOption(optionResults, requirements, option, input, pipe, irc, ctrl);
						}
					}
					for (Entry<String, String[]> e : requirements.entrySet()) {
						if (optionResults.containsKey(e.getKey())) {
							for (String required : e.getValue()) {
								if (!optionResults.containsKey(required))
									throw new ParseException(name + " requires that if you provide " + e.getKey() + " that you must also provide "
											+ e.getValue());
							}
						}
					}
					userAct(optionResults, umsg, pipe, irc, ctrl);
				} catch (ParseException e) {
					log.debug("ParseException:", e);
					ctrl.message(new ErrorMessage(e.getMessage(), e));
				}
			}
		}
	}

	public void checkOption(final Map<String, Object> optionResults, final Map<String, String[]> requirements, final Option opt, final String input,
			final Pipe pipe, final BotRegistry irc, final Controller ctrl) {
		requirements.put(opt.name(), opt.requires());

		final Class<?>[] values = opt.values();
		for (Class<?> predicate : values) {
			if (PluginUtility.isAbstractSubType(ValuePredicate.class, predicate)) {
				try {
					ValuePredicate<?> pred = (ValuePredicate<?>) predicate.newInstance();
					optionResults.put(opt.name(), pred.check(input, irc, ctrl));
				} catch (ParseException e) {
					throw e;
				} catch (Exception e) {
					throw new UserCommandError("Error running option " + opt.name() + " for user command " + name + " on " + input);
				}
			} else if (predicate.isEnum()) {
				try {
					final Method method = predicate.getMethod("valueOf", String.class);
					final Object enumInstance = method.invoke(null, input);
					optionResults.put(opt.name(), enumInstance);
				} catch (IllegalArgumentException e) {
					throw new ParseException("Error trying to parse option: " + opt.name() + " expected one of: " + predicate.getEnumConstants(), e);
				} catch (Exception e) {
					throw new UserCommandError("Error Setting up: " + opt.name());
				}
			} else {
				throw new UserCommandError("The UserCommand option" + opt.name() + " must either specify enum or a ValuePredicate");
			}
		}
	}

	public abstract void userAct(Map<String, ?> options, UserMessage msg, Pipe pipe, BotRegistry irc, Controller ctrl);

}
