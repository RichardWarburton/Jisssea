package jisssea.util;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jisssea.controller.commands.Command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PluginUtility {

	private static final Log log = LogFactory.getLog(PluginUtility.class);

	public static void main(String[] args) throws Exception {
		List<Command> commands = loadPlugins(Command.class, "bin", "jisssea.controller.commands");
		for (Command command : commands) {
			System.out.println(command.getClass().getName());
		}
	}

	/**
	 * NB: throws an Error on failure, since this is assumed to be fatal.
	 * 
	 * @param <T>
	 * @param parent
	 * @param location
	 * @param pkg
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> loadPlugins(final Class<T> parent, final String location, final String pkg) {
		try {
			final List<T> plugins = new ArrayList<T>();
			final File dir = new File(location + File.separator + pkg.replace('.', File.separatorChar));
			final URLClassLoader ucl = new URLClassLoader(new URL[] { new URL("file://" + dir.getAbsolutePath()) });
			if (dir.isDirectory()) {
				for (File f : dir.listFiles()) {
					if (f.isFile()) {
						final String name = f.getName();
						final Class<?> cls = ucl.loadClass(pkg + "." + name.substring(0, name.indexOf('.')));
						if (!cls.isAnonymousClass() && !cls.isInterface() && !Modifier.isAbstract(cls.getModifiers())
								&& isAbstractSubType(parent, cls)) {
							log.debug("Found plugin: " + cls.getName());
							if (cls.isEnum()) {
								for (Object e : cls.getEnumConstants()) {
									plugins.add((T) e);
								}
							} else {
								plugins.add((T) cls.newInstance());
							}
						}
					}
				}
			}
			return plugins;
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	private static boolean isAbstractSubType(final Class<?> parent, final Class<?> child) {

		if (child == null)
			return false;

		if (parent.isInterface()) {
			return Arrays.asList(child.getInterfaces()).contains(parent) || isAbstractSubType(parent, child.getSuperclass());
		} else {
			// TODO: genericise for classes
			return false;
		}
	}
}
