package com.lib16.java.xml;

import java.util.LinkedHashMap;

public class XmlProperties implements LanguageProperties
{
	@Override
	public boolean htmlModeEnabled()
	{
		return false;
	}

	@Override
	public String getDoctype()
	{
		return null;
	}

	@Override
	public String getMimeType()
	{
		return "application/xml";
	}

	@Override
	public String getFilenameExtension()
	{
		return "xml";
	}

	@Override
	public String getXmlVersion()
	{
		return "1.0";
	}

	@Override
	public String getXmlNamespace()
	{
		return null;
	}

	@Override
	public LinkedHashMap<String, String> getMoreXmlNamespaces()
	{
		return null;
	}

	@Override
	public String getNamespacePrefix()
	{
		return "";
	}

	@Override
	public boolean xmlDeclarationEnabled()
	{
		return true;
	}

	@Override
	public boolean verticalAttributesEnabled()
	{
		return false;
	}

	@Override
	public String getCharacterEncoding()
	{
		return "UTF-8";
	}

	@Override
	public String getLineBreak()
	{
		return "\n";
	}

	@Override
	public String getIndentation()
	{
		return "\t";
	}
}
