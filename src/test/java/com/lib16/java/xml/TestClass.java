package com.lib16.java.xml;

import org.testng.Assert;

public class TestClass {

	public void assertEquals(Object actual, String expected)
	{
		if (actual instanceof ReturnXml) {
			actual = ((ReturnXml) actual).getXml().toString();
		}
		else if (actual instanceof ReturnStr) {
			actual = ((ReturnStr) actual).getString();
		}
		else if (actual instanceof Xml) {
			actual = actual.toString();
		}
		else if (actual instanceof Language) {
			actual = ((Language) actual).getXml().toString();
		}
		Assert.assertEquals(actual, expected);
	}

	public interface ReturnXml
	{
		public Xml getXml();
	}

	public interface ReturnStr
	{
		public String getString();
	}
}
