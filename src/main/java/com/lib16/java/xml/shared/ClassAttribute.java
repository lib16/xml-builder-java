package com.lib16.java.xml.shared;

import com.lib16.java.xml.Xml;

public class ClassAttribute
{
	public static void setClass(Xml xml, String... classes)
	{
		xml.getAttributes().setComplex("class", " ", true, classes);
	}

	/**
	 * Adds alternating classes to child elements.
	 */
	public static void stripes(Xml xml, int column, String... classes)
	{
		int n = classes.length;
		int index = -1;
		Xml prev = null;
		for (int i = 0; i < xml.countChildElements(); i++) {
			Xml row = xml.getChild(i);
			if (column < 0) {
				index = ++index % n;
			}
			else {
				for (int k = 0; k < row.countChildElements(); k++) {
					Xml cell = row.getChild(k);
					if (k > column) {
						break;
					}
					if (i == 0 || cell.getContent() != prev.getChild(k).getContent()) {
						index = ++index % n;
						break;
					}
				}
				prev = row;
			}
			setClass(xml.getChild(i), classes[index]);
		}
	}

	public static void stripes(Xml xml, String... classes)
	{
		stripes(xml, -1, classes);
	}
}
