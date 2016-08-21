package com.lib16.java.xml.shared;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lib16.java.xml.TLang;
import com.lib16.java.xml.TestClass;
import com.lib16.java.xml.Xml;
import com.lib16.java.xml.shared.MediaAttribute.Media;
import com.lib16.java.xml.shared.TargetAttribute.Target;

public class SharedAttributeTest extends TestClass
{
	static Xml xml;

	@BeforeMethod
	public void before()
	{
		xml = TLang.createSub("e").getXml();
	}

	@DataProvider(name = "provider")
	public static Object[][] provider()
	{
		return new Object[][] {
			// ClassAttribute.setClass()
			{
				(ReturnXml) () -> {
					ClassAttribute.setClass(xml, "lorem", "ipsum", "dolores");
					ClassAttribute.setClass(xml, "dolor", "ipsum", "lorem");
					return xml;
				},
				"<e class=\"lorem ipsum dolores dolor\">"
			},
			// ClassAttribute.stripes()
			{
				(ReturnXml) () -> {
					xml = table();
					ClassAttribute.stripes(xml, 0, "a-1st", "a-2nd");
					ClassAttribute.stripes(xml, 1, null, "b-2nd", "b-3rd");
					ClassAttribute.stripes(xml, null, "c-2nd");
					return xml;
				},
				"<table>"
				+ "\n\t<tr class=\"a-1st\">"
				+ "\n\t\t<td>Foo</td>"
				+ "\n\t\t<td>Berlin</td>"
				+ "\n\t\t<td>20</td>"
				+ "\n\t</tr>"
				+ "\n\t<tr class=\"a-1st c-2nd\">"
				+ "\n\t\t<td>Foo</td>"
				+ "\n\t\t<td>Berlin</td>"
				+ "\n\t\t<td>12</td>"
				+ "\n\t</tr>"
				+ "\n\t<tr class=\"a-1st b-2nd\">"
				+ "\n\t\t<td>Foo</td>"
				+ "\n\t\t<td>Cologne</td>"
				+ "\n\t\t<td>12</td>"
				+ "\n\t</tr>"
				+ "\n\t<tr class=\"a-2nd b-3rd c-2nd\">"
				+ "\n\t\t<td>Bar</td>"
				+ "\n\t\t<td>Cologne</td>"
				+ "\n\t\t<td>12</td>"
				+ "\n\t</tr>"
				+ "\n\t<tr class=\"a-2nd\">"
				+ "\n\t\t<td>Bar</td>"
				+ "\n\t\t<td>Hamburg</td>"
				+ "\n\t\t<td>15</td>"
				+ "\n\t</tr>"
				+ "\n\t<tr class=\"a-2nd c-2nd\">"
				+ "\n\t\t<td>Bar</td>"
				+ "\n\t\t<td>Hamburg</td>"
				+ "\n\t\t<td>15</td>"
				+ "\n\t</tr>"
				+ "\n</table>"
			},
			{
				(ReturnXml) () -> {
					xml = list();
					ClassAttribute.stripes(xml, null, "a-2nd");
					return xml;
				},
				"<ul>\n\t<li>Berlin</li>\n\t<li class=\"a-2nd\">Hamburg</li>\n\t<li>Munich</li>\n</ul>"
			},

			// MediaAttribute.setMedia()
			{
				(ReturnXml) () -> {
					MediaAttribute.setMedia(xml, Media.SCREEN,Media.PRINT);
					MediaAttribute.setMedia(xml);
					return xml;
				},
				"<e media=\"screen,print\">"
			},
			{
				(ReturnXml) () -> {
					MediaAttribute.setMedia(xml);
					return xml;
				},
				"<e>"
			},

			// TargetAttribute.setTarget()
			{
				(ReturnXml) () -> {
					TargetAttribute.setTarget(xml, Target.TOP);
					return xml;
				},
				"<e target=\"_top\">"
			},
			{
				(ReturnXml) () -> {
					TargetAttribute.setTarget(xml, (Target) null);
					return xml;
				},
				"<e>"
			},
			{
				(ReturnXml) () -> {
					TargetAttribute.setTarget(xml, "foo.html");
					return xml;
				},
				"<e target=\"foo.html\">"
			},
		};
	}

	@Test(dataProvider = "provider")
	public void testAttribute(Object actual, String expected)
	{
		assertEquals(actual, expected);
	}

	static Xml table()
	{
		Xml table = TLang.createSub("table").getXml();
		Xml tr = table.append("tr");
		tr.append("td", "Foo");
		tr.append("td", "Berlin");
		tr.append("td", "20");
		tr = table.append("tr");
		tr.append("td", "Foo");
		tr.append("td", "Berlin");
		tr.append("td", "12");
		tr = table.append("tr");
		tr.append("td", "Foo");
		tr.append("td", "Cologne");
		tr.append("td", "12");
		tr = table.append("tr");
		tr.append("td", "Bar");
		tr.append("td", "Cologne");
		tr.append("td", "12");
		tr = table.append("tr");
		tr.append("td", "Bar");
		tr.append("td", "Hamburg");
		tr.append("td", "15");
		tr = table.append("tr");
		tr.append("td", "Bar");
		tr.append("td", "Hamburg");
		tr.append("td", "15");
		return table;
	}

	static Xml list()
	{
		Xml list = TLang.createSub("ul").getXml();
		list.append("li", "Berlin");
		list.append("li", "Hamburg");
		list.append("li", "Munich");
		return list;
	}
}

