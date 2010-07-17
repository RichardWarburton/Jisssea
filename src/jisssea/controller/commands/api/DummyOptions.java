package jisssea.controller.commands.api;

import java.lang.annotation.Annotation;

class DummyOptions implements Options {
	private final Option[] options;

	public DummyOptions(Option option) {
		this.options = new Option[] { option };
	}

	@Override
	public Class<? extends Annotation> annotationType() {
		return Options.class;
	}

	@Override
	public Option[] value() {
		return options;
	}
}