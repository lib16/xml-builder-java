package com.lib16.java.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Simplifies respectively unifies the creation of XML documents.
 */
public final class Xml
{
	public static final String CDATA_START = "<![CDATA[";
	public static final String CDATA_STOP = "]]>";

	private String name;
	private String content;
	private Attributes attributes;
	private ArrayList<Xml> children = new ArrayList<Xml>();
	private Xml root;
	private Xml parent;
	private boolean sub;
	private boolean cdata;
	private LanguageProperties properties;
	private HashMap<Option, Boolean> options;
	private ArrayList<ProcessingInstruction> instructions;

	/**
	 * The constructor is used internally to create child elements.
	 */
	private Xml(String name, String content, Xml root, Xml parent, LanguageProperties properties)
	{
		if (properties == null) {
			properties = new XmlProperties();
		}
		this.name = name;
		this.content = content;
		this.attributes = new Attributes(properties);
		this.root = root == null ? this : root;
		this.parent = parent;
		this.properties = properties;
	}

	public static Xml createRoot(String name, LanguageProperties properties)
	{
		Xml root = new Xml(name, null, null, null, properties);
		root.setXmlns();
		return root;
	}

	/**
	 * Creates the root of a subtree.
	 */
	public static Xml createSub(LanguageProperties properties)
	{
		Xml element = new Xml(null, null, null, null, properties);
		element.sub = true;
		return element;
	}

	public String getContent()
	{
		return content;
	}

	public Xml getRoot()
	{
		return root.isRoot() ? root : root.getRoot();
	}

	/**
	 * @param  level  Number of recursions (>=2).
	 */
	public Xml getParent(int level)
	{
		return (level > 1 && parent != null) ? parent.getParent(--level) : parent;
	}

	public Xml getParent()
	{
		return getParent(1);
	}

	public int countChildElements()
	{
		return children.size();
	}

	public Xml getChild(int index)
	{
		return children.get(index);
	}

	/**
	 * Subordinated content, elements will be inserted into a CDATA section.
	 */
	public Xml cdata()
	{
		this.cdata = true;
		return this;
	}

	/**
	 * Adds a child element.
	 */
	public Xml append(String name, String... content)
	{
		if (content.length == 0) {
			Xml element = new Xml(name, null, root, this, properties);
			children.add(element);
			return element;
		}
		int i = children.size();
		for (String c: content) {
			children.add(new Xml(name, c, root, this, properties));
		}
		return children.get(i);
	}

	/**
	 * Appends a previously created subtree.
	 *
	 * @param  element  Root element of the subtree.
	 */
	public Xml inject(Xml element)
	{
		element.root = getRoot();
		element.parent = this;
		children.add(element);
		return element;
	}

	/**
	 * Appends a text line.
	 */
	public Xml appendText(String text)
	{
		return append(null, text);
	}

	/**
	 * Appends a comment.
	 */
	public Xml comment(String content)
	{
		return appendText("<!-- " + content + " -->");
	}

	/**
	 * Sets attribute list.
	 *
	 * @see Attributes#setAttributes(Attributes)
	 */
	public Xml setAttributes(Attributes attributes)
	{
		this.attributes.setAttributes(attributes);
		return this;
	}

	/**
	 * Sets the {@code xml:lang} attribute.
	 *
	 * @param  lang  A BCP 47 language tag. For example "en" or "fr-CA".
	 */
	public Xml setLang(String lang)
	{
		attributes.set("xml:lang", lang);
		return this;
	}

	public enum Space
	{
		DEFAULT, PRESERVE;

		private String str;

		private Space()
		{
			str = name().toLowerCase();
		}

		@Override
		public String toString()
		{
			return str;
		}
	}

	/**
	 * Sets the {@code xml:space} attribute.
	 */
	public Xml setSpace(Space space)
	{
		attributes.set("xml:space", space.toString());
		return this;
	}

	/**
	 * Sets the {@code xml:base} attribute.
	 */
	public Xml setBase(String base)
	{
		attributes.set("xml:base", base);
		return this;
	}

	/**
	 * Sets the {@code xml:id} attribute.
	 */
	public Xml setId(String id)
	{
		attributes.set("xml:id", id);
		return this;
	}

	/**
	 * Sets the {@code xmlns} attribute.
	 */
	public Xml setXmlns(String uri, String prefix)
	{
		attributes.set(prefix != null && !prefix.isEmpty() ? "xmlns:" + prefix :  "xmlns", uri);
		return this;
	}

	/**
	 * Sets the {@code xmlns} attribute without prefix.
	 */
	public Xml setXmlns(String uri)
	{
		return setXmlns(uri, null);
	}

	/**
	 * Sets the {@code xmlns} attribute, considering {@link LanguageProperties#getXmlNamespace()}.
	 */
	public Xml setXmlns()
	{
		return setXmlns(properties.getXmlNamespace());
	}

	public ProcessingInstruction addProcessingInstruction(String target, String content)
	{
		ProcessingInstruction instr = ProcessingInstruction.create(target, content);
		if (instructions == null) {
			instructions = new ArrayList<ProcessingInstruction>();
		}
		instructions.add(instr);
		return instr;
	}

	public ProcessingInstruction addProcessingInstruction(String target)
	{
		return addProcessingInstruction(target, null);
	}

	public Xml disableLineBreak(boolean lineBreakDisabled)
	{
		return setOption(Option.LINE_BREAK_DISABLED, lineBreakDisabled);
	}

	public Xml disableLineBreak()
	{
		return disableLineBreak(true);
	}

	public Xml disableIndentation(boolean indentationDisabled)
	{
		return setOption(Option.INDENTATION_DISABLED, indentationDisabled);
	}

	public Xml disableIndentation()
	{
		return disableIndentation(true);
	}

	public Xml disableTextIndentation(boolean textIndentationDisabled)
	{
		return setOption(Option.TEXT_INDENTATION_DISABLED, textIndentationDisabled);
	}

	public Xml disableTextIndentation()
	{
		return disableTextIndentation(true);
	}

	/**
	 * Creates a string for {@code Content-Disposition} header field.
	 *
	 * Considers {@link LanguageProperties#getFilenameExtension()}.
	 *
	 * @param  filename      Eventually without extension.
	 * @param  addExtension  Whether the filename extension should be appended or not.
	 */
	public String getContentDispositionHeaderfield(String filename, boolean addExtension)
	{
		if (addExtension) {
			filename += "." + properties.getFilenameExtension();
		}
		return "Content-Disposition: attachment; filename=\"" + filename + "\"";
	}

	/**
	 * Creates a string for {@code Content-Disposition} header field.
	 *
	 * Considers {@link LanguageProperties#getFilenameExtension()}.
	 *
	 * @param  filename  Without extension!
	 */
	public String getContentDispositionHeaderfield(String filename)
	{
		return getContentDispositionHeaderfield(filename, true);
	}

	/**
	 * Creates a string for {@code Content-Type} header field.
	 *
	 * Considers {@link LanguageProperties#getMimeType()} and
	 * {@link LanguageProperties#getCharacterEncoding()}.
	 */
	public String getContentTypeHeaderfield()
	{
		return "Content-Type: " + properties.getMimeType()
				+ "; charset=" + properties.getCharacterEncoding();
	}

	public String getMarkup(String indentation, Options options)
	{
		String lineBr = options.getOption(this, Option.LINE_BREAK_DISABLED)
				? "" : properties.getLineBreak();
		String indent = options.getOption(this, Option.INDENTATION_DISABLED)
				? "" : properties.getIndentation();
		boolean textIndentDisabled = options.getOption(this, Option.TEXT_INDENTATION_DISABLED);
		if (lineBr == "") {
			indent = "";
		}
		String content = prepareContent(lineBr, indentation, textIndentDisabled);
		String markup = "";
		if (isRoot() && !sub) {
			markup += head(lineBr);
		}
		if (!children.isEmpty()) {
			markup += container(content, indentation, indent, lineBr, options);
		}
		else {
			markup += element(content, indentation, indent, lineBr);
		}
		return markup;
	}

	public String getMarkup()
	{
		return getMarkup("", new Options());
	}

	/**
	 * Shorthand method for {@code getRoot().getMarkup()}.
	 */
	@Override
	public String toString()
	{
		return getRoot().getMarkup();
	}

	public LanguageProperties getLanguageProperties()
	{
		return properties;
	}

	public Attributes getAttributes()
	{
		return attributes;
	}

	private String head(String lineBr)
	{
		String markup = "";
		if (properties.xmlDeclarationEnabled()) {
			markup += ProcessingInstruction.create("xml")
					.attrib("version", properties.getXmlVersion())
					.attrib("encoding", properties.getCharacterEncoding()) + lineBr;
		}
		if (instructions != null) {
			for (ProcessingInstruction instruction: instructions) {
				markup += instruction + lineBr;
			}
		}
		if (properties.getDoctype() != null && !properties.getDoctype().isEmpty()) {
			markup += properties.getDoctype() + lineBr;
		}
		return markup;
	}

	private String container(
			String content, String indentation, String indent, String lineBr, Options options)
	{
		String markup = "";
		boolean hasTags = name != null && !name.isEmpty();
		String newIndentation = indentation + indent;
		String tagsDependendIndentation = hasTags ? newIndentation : indentation;
		if (hasTags) {
			markup += indentation + openingTag(newIndentation + indent) + lineBr;
		}
		if (cdata) {
			markup += indentation + CDATA_START + lineBr;
		}
		if (content != null && !content.isEmpty()) {
			markup += tagsDependendIndentation + content + lineBr;
		}
		for (Xml child: children) {
			markup += child.getMarkup(tagsDependendIndentation, options) + lineBr;
		}
		if (cdata) {
			markup += indentation + CDATA_STOP + lineBr;
		}
		if (hasTags) {
			markup += indentation + closingTag() + lineBr;
		}
		return markup.substring(0, markup.lastIndexOf(lineBr)); // remove trailing line break;
	}

	private String element(String content, String indentation, String indent, String lineBr)
	{
		String markup = "";
		boolean hasTags = name != null && !name.isEmpty();
		boolean hasContent = !(content == null || (
				!properties.htmlModeEnabled() && content.isEmpty()));
		if (hasTags) {
			if (hasContent) {
				if (cdata) {
					content = CDATA_START + content + CDATA_STOP;
				}
				markup += indentation + openingTag(indentation + indent + indent)
						+ content + closingTag();
			}
			else {
				markup += indentation + standaloneTag(indentation + indent + indent);
			}
		}
		else if (hasContent) {
			markup += indentation + content;
		}
		return markup;
	}

	private String fullName()
	{
		return properties.getNamespacePrefix() + name;
	}

	private String openingTag(String indentation)
	{
		return "<" + fullName() + attributes.getMarkup(indentation) + ">";
	}

	private String closingTag()
	{
		return "</" + fullName() + ">";
	}

	private String standaloneTag(String indentation)
	{
		return "<" + fullName()
				+ attributes.getMarkup(indentation)
				+ (properties.htmlModeEnabled() ? ">" : "/>");
	}

	private String prepareContent(String lineBr, String indentation, boolean textIndentDisabled)
	{
		String content = this.content;
		if (content != null && !content.isEmpty() && !lineBr.isEmpty() && !textIndentDisabled) {
			content = content.replace(lineBr, lineBr + indentation);
		}
		return content;
	}

	private boolean isRoot()
	{
		return root == this;
	}

	private Xml setOption(Option name, boolean value)
	{
		if (options == null) {
			options = new LinkedHashMap<Option, Boolean>(3);
		}
		options.put(name, value);
		return this;
	}

	public enum Option
	{
		LINE_BREAK_DISABLED, INDENTATION_DISABLED, TEXT_INDENTATION_DISABLED
	}

	@SuppressWarnings("serial")
	class Options extends HashMap<Option, Boolean>
	{
		Options()
		{
			put(Option.LINE_BREAK_DISABLED, false);
			put(Option.INDENTATION_DISABLED, false);
			put(Option.TEXT_INDENTATION_DISABLED, false);
		}

		boolean getOption(Xml element, Option option)
		{
			if (element.options != null && element.options.containsKey(option)) {
				put(option, element.options.get(option));
			}
			return get(option);
		}
	}
}