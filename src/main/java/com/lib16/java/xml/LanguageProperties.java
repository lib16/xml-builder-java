package com.lib16.java.xml;

import java.util.LinkedHashMap;

public interface LanguageProperties
{
	boolean htmlModeEnabled();

	String getDoctype();

	String getMimeType();

	String getFilenameExtension();

	String getXmlVersion();

	String getXmlNamespace();

	LinkedHashMap<String, String> getMoreXmlNamespaces();

	String getNamespacePrefix();

	boolean xmlDeclarationEnabled();

	boolean verticalAttributesEnabled();

	String getCharacterEncoding();

	String getLineBreak();

	String getIndentation();
}
