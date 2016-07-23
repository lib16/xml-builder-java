package com.lib16.java.xml.shared;

import com.lib16.java.xml.Xml;

/**
 * The XLink attributes, but only for {@code simple} links.
 */
public class XLink
{
	public static final String NAMESPACE = "http://www.w3.org/1999/xlink";
	public static final String PREFIX = "xlink";
	
	public static void setXmlns(Xml xml)
	{
		xml.setXmlns(NAMESPACE, PREFIX);
	}
	
	public static void setHref(Xml xml, String href)
	{
		set(xml, "href", href);
	}
	
	public static void setType(Xml xml)
	{
		set(xml, "type", "simple");
	}
	
	public static void setTitle(Xml xml, String title)
	{
		set(xml, "title", title);
	}
	
	public static void setShow(Xml xml, Show show)
	{
		set(xml, "show", show.toString());
	}
	
	public static void setActuate(Xml xml, Actuate actuate)
	{
		set(xml, "actuate", actuate.toString());
	}
	
	public static void setRole(Xml xml, String role)
	{
		set(xml, "role", role);
	}

	public static void setArcrole(Xml xml, String arcrole)
	{
		set(xml, "arcrole", arcrole);
	}
	
	private static void set(Xml xml, String name, String value)
	{
		xml.getAttributes().set(PREFIX + ":" + name, value);
	}
	
	public enum Show
	{
		EMBED, NEW, REPLACE, OTHER, NONE;
		
		private String str;
		
		private Show()
		{
			str = name().toLowerCase();
		}
		
		@Override
		public String toString()
		{
			return str;
		}
	}
	
	public enum Actuate
	{
		ON_LOAD("onLoad"), ON_REQUEST("onRequest"), OTHER("other"), NONE("none");
		
		private String str;
		
		private Actuate(String str)
		{
			this.str = str;
		}
		
		@Override
		public String toString()
		{
			return str;
		}
	}
}
