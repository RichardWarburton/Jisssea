package jisssea.ui.completers;

import static jisssea.util.PluginUtility.loadPlugins;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import charvax.swing.JTextField;

public class CompleterRegistry {

	private static final Log log = LogFactory.getLog(CompleterRegistry.class);

	public final static CompleterRegistry v = new CompleterRegistry();

	private final List<Completer> listeners;

	private CompleterRegistry() {
		listeners = loadPlugins(Completer.class, "bin", "jisssea.ui.completers");
	}

	public void addCompleters(JTextField field) {
		log.debug("Adding: " + listeners + " to " + field);
		for (Completer comp : listeners) {
			try {
				final Method meth = comp.getClass().getMethod("complete", String.class);
				final Key key = meth.getAnnotation(Key.class);
				field.addKeyListener(new KeyHook(key.value(), comp));
			} catch (Exception e) {
				log.error("Error adding completers", e);
			}
		}
	}
}
