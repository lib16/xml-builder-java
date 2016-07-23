package com.lib16.java.xml.shared;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lib16.java.xml.TLang;
import com.lib16.java.xml.TestClass;
import com.lib16.java.xml.Xml;
import com.lib16.java.xml.shared.XLink.Actuate;
import com.lib16.java.xml.shared.XLink.Show;

public class XLinkTest extends TestClass
{
	static Xml xml;
	
	@BeforeMethod
	public void before()
	{
		xml = TLang.createSub("a").getXml();
	}
	
	@DataProvider(name = "provider")
	public static Object[][] classProvider()
	{
		return new Object[][] {
			{
				(ReturnXml) () -> {
					XLink.setXmlns(xml);
					return xml;
				},
				"<a xmlns:xlink=\"http://www.w3.org/1999/xlink\">"
			},
			{
				(ReturnXml) () -> {
					XLink.setHref(xml, "https://example.com");
					return xml;
				},
				"<a xlink:href=\"https://example.com\">"
			},
			{
				(ReturnXml) () -> {
					XLink.setType(xml);
					return xml;
				},
				"<a xlink:type=\"simple\">"
			},
			{
				(ReturnXml) () -> {
					XLink.setTitle(xml, "Foo Bar");
					return xml;
				},
				"<a xlink:title=\"Foo Bar\">"
			},
			{
				(ReturnXml) () -> {
					XLink.setShow(xml, Show.NEW);
					return xml;
				},
				"<a xlink:show=\"new\">"
			},
			{
				(ReturnXml) () -> {
					XLink.setActuate(xml, Actuate.ON_REQUEST);
					return xml;
				},
				"<a xlink:actuate=\"onRequest\">"
			},
			{
				(ReturnXml) () -> {
					XLink.setRole(xml, "https://example.com");
					return xml;
				},
				"<a xlink:role=\"https://example.com\">"
			},
			{
				(ReturnXml) () -> {
					XLink.setArcrole(xml, "https://example.com");
					return xml;
				},
				"<a xlink:arcrole=\"https://example.com\">"
			}
		};
	}

	@Test(dataProvider = "provider")
	public void testClassAttribute(Object actual, String expected)
	{
		assertEquals(actual, expected);
	}
}
