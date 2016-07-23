package com.lib16.java.xml;

import com.lib16.java.xml.Language;
import com.lib16.java.xml.Xml;

public class TLang implements Language
{
	Xml xml;

	private TLang(Xml xml)
	{
		this.xml = xml;
	}

	public static TLang createSub(String name)
	{
		return new TLang(Xml.createSub(new TProps().h()).append(name));
	}

	public static TLang createSub()
	{
		return new TLang(Xml.createSub(new TProps().h()));
	}
	
	public static TLang createSub(LanguageProperties properties)
	{
		return new TLang(Xml.createSub(properties));
	}
	
	public static TLang createSub(String name, LanguageProperties properties)
	{
		return new TLang(Xml.createSub(properties).append(name));
	}

	@Override
	public Xml getXml()
	{
		return xml;
	}
}
