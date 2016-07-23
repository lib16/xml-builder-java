package com.lib16.java.xml;

import java.text.NumberFormat;
import java.util.Locale;

public class XmlProperties implements LanguageProperties
{
	private NumberFormat numberFormat;

	public XmlProperties()
	{
		numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
		numberFormat.setMaximumFractionDigits(4);
	}

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

	@Override
	public NumberFormat getNumberFormat()
	{
		return numberFormat;
	}
}
