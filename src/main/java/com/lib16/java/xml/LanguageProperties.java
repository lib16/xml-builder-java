package com.lib16.java.xml;

import java.text.NumberFormat;

public interface LanguageProperties
{
	boolean htmlModeEnabled();

	String getDoctype();

	String getMimeType();

	String getFilenameExtension();

	String getXmlVersion();

	String getXmlNamespace();

	String getNamespacePrefix();

	boolean xmlDeclarationEnabled();

	boolean verticalAttributesEnabled();

	String getCharacterEncoding();

	String getLineBreak();

	String getIndentation();

	NumberFormat getNumberFormat();
}
