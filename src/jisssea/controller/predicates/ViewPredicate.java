package jisssea.controller.predicates;

import java.util.List;

import jisssea.controller.messages.Message;

/**
 * @author richard
 * 
 * Predicates should be idempotent and compositional
 */
public interface ViewPredicate {

	List<Message> filter(List<Message> msgs);
	
}
