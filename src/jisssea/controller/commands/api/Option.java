package jisssea.controller.commands.api;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(METHOD)
@Retention(RUNTIME)
public @interface Option {
	String name();

	String help() default "";

	boolean required() default true;

	/**
	 * gives a list of other option that are required if this option is provided
	 * 
	 * @return
	 */
	String[] requires() default {};

	/**
	 * Subclass of ValuePredicate or enumeration
	 * 
	 * @return
	 */
	Class<?>[] values();
}
