package jisssea.ui.completers;

import charva.awt.event.KeyEvent;

/**
 * @author richard
 */
public enum ShortcutsCompleter implements Completer {
	QUOTE {
		@Override
		@Key(KeyEvent.VK_UP)
		public String complete(String initial) {
			if (initial.length() == 0)
				return "/quote ";
			else
				return initial + "'";
		}
	};

	@Override
	public abstract String complete(String initial);

}
