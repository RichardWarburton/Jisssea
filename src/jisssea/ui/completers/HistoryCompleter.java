package jisssea.ui.completers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import charva.awt.event.KeyEvent;

/**
 * 
 * @author richard
 * 
 * FauxFaux told me to do this, honest
 *
 */
public enum HistoryCompleter implements Completer {
	UP {
		@Override
		@Key(KeyEvent.VK_UP)
		public String complete(String initial) {
			log.debug("Up: "+initial+" "+asStr());
			if(index >= 0) {
				final String val = history.get(index);
				index = Math.max(0, index - 1);
				return val;
			} else {
				return initial;
			}
		}
	},
	DOWN {
		@Override
		@Key(KeyEvent.VK_DOWN)
		public String complete(String initial) {
			log.debug("Down: "+initial+" "+asStr());
			if(index < history.size()) {
				final String val = history.get(index);
				index = Math.min(history.size(), index+1);
				return val;
			} else {
				return "";
			}
		}
	},
	ENTER {
		@Override
		@Key(KeyEvent.VK_ENTER)
		public String complete(String initial) {
			log.debug("Enter: "+initial+" "+asStr());
			history.add(initial);
			index = history.size() - 1;
			return "";
		}
	};

	private static final Log log = LogFactory.getLog(HistoryCompleter.class);
	private static int index = 0;
	private static final List<String> history  = new ArrayList<String>();
	
	private static String asStr() {
		return index+" - "+history;
	}
	
	@Override
	public abstract String complete(String initial);

}
