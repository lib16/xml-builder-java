package com.lib16.java.xml;

public final class ProcessingInstruction
{
	private String target;
	private String content;
	private Attributes attributes;

	private ProcessingInstruction(String target, String content)
	{
		this.target = target;
		this.content = content;
	}

	public static ProcessingInstruction create(String target, String content)
	{
		return new ProcessingInstruction(target, content);
	}

	public static ProcessingInstruction create(String target)
	{
		return new ProcessingInstruction(target, null);
	}

	public ProcessingInstruction attrib(String name, String value)
	{
		if (attributes == null) {
			attributes = new Attributes(null);
		}
		attributes.set(name, value);
		return this;
	}

	public String getMarkup()
	{
		String markup = "<?" + target;
		if (content != null && !content.isEmpty()) {
			markup += " " + content;
		}
		if (attributes != null) {
			markup += attributes.getMarkup("");
		}
		markup += " ?>";
		return markup;
	}

	@Override
	public String toString()
	{
		return getMarkup();
	}
}
