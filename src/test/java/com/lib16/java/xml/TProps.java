package com.lib16.java.xml;

public class TProps extends XmlProperties
{
	Xml xml;

	public boolean htmlMode = false;
	public boolean vertical = false;
	public boolean xmlDecl = false;
	public String doctype = null;
	public String prefix = "";
	public String lineBr = "\n";
	public String indent = "\t";
	public String namespace = null;

	@Override
	public boolean htmlModeEnabled()
	{
		return htmlMode;
	}

	@Override
	public boolean verticalAttributesEnabled()
	{
		return vertical;
	}

	@Override
	public boolean xmlDeclarationEnabled()
	{
		return xmlDecl;
	}

	@Override
	public String getDoctype()
	{
		return doctype;
	}

	@Override
	public String getNamespacePrefix()
	{
		return prefix;
	}

	@Override
	public String getLineBreak()
	{
		return lineBr;
	}

	@Override
	public String getIndentation()
	{
		return indent;
	}

	@Override
	public String getXmlNamespace()
	{
		return namespace;
	}

	public TProps h()
	{
		htmlMode = true;
		return this;
	}

	public TProps v()
	{
		vertical = true;
		return this;
	}

	public TProps x()
	{
		xmlDecl = true;
		return this;
	}

	public TProps d(String doctype)
	{
		this.doctype = doctype;
		return this;
	}

	public TProps p(String prefix)
	{
		this.prefix = prefix;
		return this;
	}

	public TProps l(String lineBr)
	{
		this.lineBr = lineBr;
		return this;
	}

	public TProps i(String indent)
	{
		this.indent = indent;
		return this;
	}

	public TProps n(String namespace)
	{
		this.namespace = namespace;
		return this;
	}
}
