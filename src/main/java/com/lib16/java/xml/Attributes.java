package com.lib16.java.xml;

import java.util.LinkedHashMap;
import java.util.Map;

import com.lib16.java.utils.NumberFormatWrapper;
import com.lib16.java.utils.Unit;

/**
 * Manages and displays the attributes of an XML element.
 * You gain access to these with {@link Xml#getAttributes()}.
 */
public class Attributes
{
	private LinkedHashMap<String, String> attributes;
	private LanguageProperties properties;

	public Attributes(LanguageProperties properties)
	{
		this.properties = properties;
		attributes = new LinkedHashMap<String, String>();
	}

	public Attributes()
	{
		this(null);
	}

	/**
	 * Sets an attribute.
	 */
	public Attributes set(String name, String value)
	{
		if (name != null) {
			attributes.put(name, value);
		}
		return this;
	}

	/**
	 * Sets an boolean attribute (like {@code selected} in HTML).
	 *
	 * @param  isset  Whether the attribute is set or not.
	 */
	public Attributes setBoolean(String name, boolean isset)
	{
		return set(name, isset ? name : null);
	}

	/**
	 * Sets a boolean attribute by comparing one or more values
	 * with the value of another attribute.
	 *
	 * Helpful for attributes like {@code selected} or {@code checked} in HTML.
	 *
	 * @param  comparisonAttribute  Name of the attribute to compare with.
	 * @param  values               Values to compare.
	 */
	public Attributes setBoolean(String name, String comparisonAttribute, String... values)
	{
		String compareTo = attributes.get(comparisonAttribute);
		for (String value: values) {
			if (value == compareTo)
				return setBoolean(name, true);
		}
		return setBoolean(name, false);
	}

	/**
	 * Sets or appends to a composable attribute like
	 * {@code class} (HTML) or {@code points} (SVG).
	 *
	 * @param  delimiter  The boundary string.
	 * @param  check      Whether multiple entries shall be accepted or not.
	 * @param  parts      Strings to append to the current attribute value.
	 */
	public Attributes setComplex(String name, String delimiter, boolean check, String... parts)
	{
		String value = attributes.containsKey(name) && attributes.get(name) != null
				? attributes.get(name) : "";
		for (String part: parts) {
			if (part == null) {
				continue;
			}
			if (!check || (value != part
					&& !value.startsWith(part + delimiter)
					&& !value.endsWith(delimiter + part)
					&& !value.contains(delimiter + part + delimiter))) {
				value = !value.isEmpty() ? value + delimiter + part : part;
			}
		}
		set(name, value.isEmpty() ? null : value);
		return this;
	}

	/**
	 * Sets a number attribute like {@code width} (SVG).
	 */
	public Attributes setNumber(String name, Number value, NumberFormatWrapper wrapper, Unit unit)
	{
		set(name, numberToString(value, wrapper, unit));
		return this;
	}

	/**
	 * Sets a number attribute.
	 */
	public Attributes setNumber(String name, Number value, NumberFormatWrapper wrapper)
	{
		return setNumber(name, value, wrapper, null);
	}

	/**
	 * Sets a number attribute which accepts multiple values.
	 *
	 * @param  delimiter  The boundary string.
	 */
	public Attributes setNumber(String name, String delimiter,
			NumberFormatWrapper wrapper, Unit unit, Number... numbers)
	{
		for (Number number: numbers) {
			setComplex(name, delimiter, false, numberToString(number, wrapper, unit));
		}
		return this;
	}

	/**
	 * Sets a number attribute which accepts multiple values.
	 *
	 * @param  delimiter  The boundary string.
	 */
	public Attributes setNumber(String name, String delimiter,
			NumberFormatWrapper wrapper, Number... numbers)
	{
		return setNumber(name, delimiter, wrapper, null, numbers);
	}

	public Attributes setNull(String... names)
	{
		for (String name: names) {
			set(name, null);
		}
		return this;
	}

	/**
	 * Converts a number to a string.
	 */
	public static String numberToString(Number value, NumberFormatWrapper wrapper, Unit unit)
	{
		String string = wrapper.format(value);
		if (value != null && unit != null) {
			string += unit;
		}
		return string;
	}

	/**
	 * Converts a number to a string.
	 */
	public static String numberToString(Number value, NumberFormatWrapper wrapper)
	{
		return numberToString(value, wrapper, null);
	}

	/**
	 * Replaces previous attribute list, but retains existing {@code LanguageProperties} instance.
	 */
	public Attributes setAttributes(Attributes attributes)
	{
		this.attributes = attributes.attributes;
		return this;
	}

	/**
	 * Called by the {@code Xml} method {@code getMarkup()}.
	 */
	public String getMarkup(String indentation)
	{
		String whitespace = " ";
		if (properties != null && properties.verticalAttributesEnabled()) {
			whitespace = properties.getLineBreak() + indentation;
		}
		String markup = "";
		for (Map.Entry<String, String> entry: attributes.entrySet()) {
			markup += buildAttribStr(entry.getKey(), entry.getValue(), whitespace);
		}
		return markup;
	}

	@Override
	public String toString()
	{
		return getMarkup("");
	}

	private String buildAttribStr(String name, String value, String whitespace)
	{
		if (value == null)
			return "";
		if (value == name && properties != null && properties.htmlModeEnabled())
			return whitespace + name;
		return whitespace + name + "=\"" + value + "\"";
	}
}