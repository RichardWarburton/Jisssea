package jisssea.controller.predicates;

import java.util.ArrayList;
import java.util.List;

import jisssea.controller.messages.Message;

/**
 * @author richard
 * 
 * A predicate that doesn't care about history
 *
 */
public abstract class SimplePredicate implements ViewPredicate {

	@Override
	public List<Message> filter(List<Message> msgs) {
		List<Message> list = new ArrayList<Message>();
		for (Message message : msgs) {
			if(check(message))
				list.add(message);
		}
		return list;
	}
	
	protected abstract boolean check(Message msg);

}
