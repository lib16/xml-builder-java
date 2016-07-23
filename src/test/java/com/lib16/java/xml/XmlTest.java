package com.lib16.java.xml;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lib16.java.xml.Xml.Space;

public class XmlTest extends TestClass
{
	public static final String DOCTYPE = "<!DOCTYPE txml>";
	public static final String XML_DECL = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
	public static final String NAMESPACE = "http://example.com/baz";
	public static final String MULTILINE = "lorem\nipsum\ndolor\nsit";

	@DataProvider(name = "provider")
	public static Object[][] provider()
	{
		return new Object[][] {
			// createSub()
			{Xml.createSub(null).append("e", "content"), "<e>content</e>"},

			// append() !htmlMode
			{sub().append(null), ""},
			{sub().append(""), ""},
			{sub().append(null, "content"), "content"},
			{sub().append("", "content"), "content"},
			{sub().append("e"), "<e/>"},
			{sub().append("e", ""), "<e/>"},
			{sub().append("e", "content"), "<e>content</e>"},
			{sub().append("e"), "<e/>"},
			{sub().append("e", new String[] {}), "<e/>"},
			// append() htmlMode
			{sub(new TProps().h()).append(null), ""},
			{sub(new TProps().h()).append(""), ""},
			{sub(new TProps().h()).append(null, "content"), "content"},
			{sub(new TProps().h()).append("", "content"), "content"},
			{sub(new TProps().h()).append("e"), "<e>"},
			{sub(new TProps().h()).append("e", ""), "<e></e>"},
			{sub(new TProps().h()).append("e", "content"), "<e>content</e>"},
			// append() multiple content
			{
				sub(new TProps().l(""))
						.append("e", "lorem", "ipsum", "dolor")
						.setAttributes(new Attributes().set("foo", "bar")),
				"<e foo=\"bar\">lorem</e><e>ipsum</e><e>dolor</e>"
			},
			// append(): indentation
			{sub().append("").append("e"), "<e/>"},
			{
				(ReturnXml) () -> {
					Xml xml = sub();
					xml.append(null, "content 1");
					xml.append("e", "content 2");
					xml.append(null, "content 3");
					xml.append("e", "content 4");
					xml.append("e", "content 5");
					return xml;
				},
				"content 1\n<e>content 2</e>\ncontent 3\n<e>content 4</e>\n<e>content 5</e>"
			},
			{
				(ReturnXml) () -> {
					Xml xml = sub();
					xml.append("e").append("e", "1.1");
					xml.append("e").append("e", "2.1").getParent().append("e", "2.2");
					return xml;
				},
				"<e>\n\t<e>1.1</e>\n</e>\n<e>\n\t<e>2.1</e>\n\t<e>2.2</e>\n</e>"
			},
			{
				sub()
						.append("e", "1")
						.append("e", "1.1").getParent()
						.append("e", "1.2"),
				"<e>\n\t1\n\t<e>1.1</e>\n\t<e>1.2</e>\n</e>"
			},

			// appendText(), comment()
			{
				sub()
						.append("e").getParent()
						.comment("comment").getParent()
						.append("f", "content 1").appendText("content 2"),
				"<e/>\n<!-- comment -->\n<f>\n\tcontent 1\n\tcontent 2\n</f>"
			},
			{sub().append("e", "").appendText("content"), "<e>\n\tcontent\n</e>"},
			{sub("e").appendText("one").appendText("two"), "<e>\n\tone\n\ttwo\n</e>"},
			{
				(ReturnXml) () -> {
					Xml e = sub();
					e.append("p", MULTILINE);
					e.append("p").appendText(MULTILINE);
					return e;
				},
				"<p>lorem\nipsum\ndolor\nsit</p>\n<p>\n\tlorem\n\tipsum\n\tdolor\n\tsit\n</p>"
			},

			// inject()
			{
				Xml.createRoot("e", new TProps().h())
						.setAttributes(new Attributes().setBoolean("a", true))
						.inject(Xml.createRoot("f", new TProps().n(NAMESPACE))
								.append("g").getRoot()),
				"<e a>\n\t<f xmlns=\"" + NAMESPACE + "\">\n\t\t<g/>\n\t</f>\n</e>"
			},
			{
				Xml.createRoot("e", new TProps().h())
						.setAttributes(new Attributes().setBoolean("a", true))
						.inject(sub().append("f").getRoot()),
				"<e a>\n\t<f/>\n</e>"
			},

			// setLang()
			{sub("e").setLang("fr"), "<e xml:lang=\"fr\"/>"},
			// setSpace()
			{sub("e").setSpace(Space.DEFAULT), "<e xml:space=\"default\"/>"},
			{sub("e").setSpace(Space.PRESERVE), "<e xml:space=\"preserve\"/>"},
			// setBase()
			{sub("e").setBase("http://example.com"), "<e xml:base=\"http://example.com\"/>"},
			// setId()
			{sub("e").setId("foo"), "<e xml:id=\"foo\"/>"},

			// getChild()
			{
				(ReturnStr) () -> {
					Xml p = sub("p");
					p.append("c", "one", "two", "three");
					return p.getChild(1).getMarkup();
				},
				"<c>two</c>"
			},
			
			// countChildElements()
			{
				(ReturnStr) () -> {
					Xml p = sub("p");
					p.append("c", "one", "two", "three");
					return "count: " + p.countChildElements(); 
				},
				"count: 3"
			},

			// getParent()
			{
				sub("e", new TProps().l(""))
						.append("f").append("g").getParent().getMarkup(),
				"<f><g/></f>"
			},
			{
				sub("e", new TProps().l(""))
						.append("f").append("g").append("h").getParent(2).getMarkup(),
				"<f><g><h/></g></f>"
			},
			{
				sub("e", new TProps().l(""))
						.append("f").append("g").append("h").getParent(3).getMarkup(),
				"<e><f><g><h/></g></f></e>"
			},
			{
				sub("e", new TProps().l(""))
						.append("f").append("g").append("h").getParent(10),
				null
			},
			{
				sub("e", new TProps().l(""))
						.append("f").inject(
								sub("g", new TProps().l("")).append("h").getParent())
						.getParent(2)
						.getMarkup(),
				"<e><f><g><h/></g></f></e>"
			},

			// getRoot()
			{
				sub("e", new TProps().l(""))
						.append("f").append("g").append("h").getRoot().getMarkup(),
				"<e><f><g><h/></g></f></e>"
			},
			{
				(ReturnStr) () -> {
					Xml e1 = sub("r1", new TProps()).disableLineBreak().append("e1");
					Xml e2 = sub("r2", new TProps().h()).append("e2");
					e1.inject(e2.getRoot());
					return e2.getRoot().getMarkup();
				},
				"<r1><e1><r2><e2></r2></e1></r1>"
			},

			// setXmlns()
			{
				sub("r").setXmlns("http://example.com/foo", "foo"),
				"<r xmlns:foo=\"http://example.com/foo\"/>"
			},
			{
				sub("r").setXmlns("http://example.com/foo", ""),
				"<r xmlns=\"http://example.com/foo\"/>"
			},
			{
				sub("r").setXmlns("http://example.com/foo"),
				"<r xmlns=\"http://example.com/foo\"/>"
			},
			{
				sub("r").setXmlns(),
				"<r/>"
			},
			{
				sub(new TProps().n(NAMESPACE)).append("r").setXmlns(),
				"<r xmlns=\"" + NAMESPACE + "\"/>"
			},

			// cdata()
			{sub().append("e").cdata(), "<e/>"},
			{sub().append("e", "content").cdata(), "<e><![CDATA[content]]></e>"},
			{
				sub().append("e").cdata().append("f", "content"),
				"<e>\n<![CDATA[\n\t<f>content</f>\n]]>\n</e>"
			},

			// XML Head, addProcessingInstruction()
			{Xml.createRoot("txml", new TProps().h()), "<txml>"},
			{
				(ReturnXml) () -> {
					Xml xml = Xml.createRoot(
							"txml", new TProps().x().d(DOCTYPE).n(NAMESPACE));
					xml.addProcessingInstruction("target1", "content");
					xml.addProcessingInstruction("target2").attrib("attrib", "value");
					return xml;
				},
				XML_DECL
						+ "\n<?target1 content ?>\n<?target2 attrib=\"value\" ?>\n"
						+ DOCTYPE
						+ "\n<txml xmlns=\"" + NAMESPACE + "\"/>"
			},
			{sub().addProcessingInstruction("target", "").getMarkup(), "<?target ?>"},
			{Xml.createRoot("e", null), XML_DECL + "\n<e/>"},
			{Xml.createRoot("e", new TProps().d("")), "<e/>"},

			// disableLineBreak(), disableIndentation(), disableTextIndentation()
			{
				sub("e").disableLineBreak().append("f").disableLineBreak(false).append("g"),
				"<e><f>\n\t<g/>\n</f></e>"
			},
			{
				sub("e").disableIndentation().append("f").disableIndentation(false).append("g"),
				"<e>\n<f>\n\t<g/>\n</f>\n</e>"
			},
			{
				sub("e").disableLineBreak().append("f").append("g", "a", "b"),
				"<e><f><g>a</g><g>b</g></f></e>"
			},
			{
				sub()
						.append("e").appendText(MULTILINE).getRoot()
						.append("f").disableTextIndentation().appendText(MULTILINE),
				"<e>\n\tlorem\n\tipsum\n\tdolor\n\tsit\n</e>\n"
				+ "<f>\n\tlorem\nipsum\ndolor\nsit\n</f>"
			},
			{
				sub("e")
						.append("f", MULTILINE).getParent()
						.append("g", MULTILINE).disableTextIndentation(),
				"<e>\n\t<f>lorem\n\tipsum\n\tdolor\n\tsit</f>\n"
				+ "\t<g>lorem\nipsum\ndolor\nsit</g>\n</e>"
			},
			{ // combined
				sub("e")
						.disableTextIndentation()
						.disableIndentation()
						.append("f", MULTILINE),
				"<e>\n<f>lorem\nipsum\ndolor\nsit</f>\n</e>"
			},
			
			// getContentDispositionHeaderfield(), getContentTypeHeaderfield()
			{
				TLang.createSub().getXml().getContentDispositionHeaderfield("test"),
				"Content-Disposition: attachment; filename=\"test.xml\""
			},
			{
				TLang.createSub().getXml().getContentDispositionHeaderfield("test.kml", false),
				"Content-Disposition: attachment; filename=\"test.kml\""
			},
			{
				TLang.createSub().getXml().getContentTypeHeaderfield(),
				"Content-Type: application/xml; charset=UTF-8"
			},
			
			// getAttributes()
			{
				(ReturnXml) () -> {
					Xml e = sub("e");
					e.getAttributes().set("a", "lorem ipsum");
					return e;
				},
				"<e a=\"lorem ipsum\"/>"
			},
			
			// getContent()
			{sub().append("e", "one").getContent(), "one"},
			{sub().append("e", "one", "two").getContent(), "one"},
		};
	}

	@Test(dataProvider = "provider")
	public void test(Object actual, String expected)
	{
		assertEquals(actual, expected);
	}

	public static Xml sub(String name, LanguageProperties properties)
	{
		if (properties == null) {
			properties = new TProps();
		}
		Xml xml = Xml.createSub(properties);
		return name != null ? xml.append(name) : xml;
	}

	public static Xml sub(String name)
	{
		return sub(name, null);
	}

	public static Xml sub(LanguageProperties properties)
	{
		return sub(null, properties);
	}

	public static Xml sub()
	{
		return sub(null, null);
	}
}
