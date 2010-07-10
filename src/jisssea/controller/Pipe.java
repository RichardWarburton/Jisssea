package jisssea.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jisssea.controller.messages.Message;
import jisssea.controller.predicates.DefaultPredicate;
import jisssea.controller.predicates.ViewPredicate;
import jisssea.ui.DisplayWindow;

public class Pipe {

	private final int cacheSize;
	
	private final List<Message> messages;
	
	public final DisplayWindow window;
	
	public final List<ViewPredicate> predicates;
	
	public final Map<String, Object> stash;
	
	public DefaultPredicate getDefaultPredicate() {
		for (ViewPredicate p : predicates) {
			if (p instanceof DefaultPredicate) {
				return (DefaultPredicate) p;
			}
		}
		return null;
	}
	
	public Pipe(final int windowNumber, final int cacheSize,Controller handler ) {
		super();
		this.cacheSize = cacheSize;
		this.window = new DisplayWindow(windowNumber,handler);
		this.messages = new ArrayList<Message>();
		this.predicates = new ArrayList<ViewPredicate>();
		this.stash = new HashMap<String, Object>();
	}
	
	public void enQueue(final Message msg) {
		messages.add(msg);
	}
	
	/**
	 * TODO: move to this message
	 * @param offset
	 * @param amount
	 * @return
	 */
	public List<Message> request(final int offset,final int amount) {
		int initialIndex = Math.max(0,messages.size()-(offset+cacheSize)),
			finalIndex = Math.min(messages.size()-1,initialIndex + amount);
		List<Message> subList = messages.subList(initialIndex, finalIndex);
		for(ViewPredicate pred:predicates) {
			subList = pred.filter(subList);
		}
		return subList;
	}

	public List<Message> requestAll() {
		List<Message> subList = new ArrayList<Message>(messages);
		for(ViewPredicate pred:predicates) {
			subList = pred.filter(subList);
		}
		return subList;
	}
}
