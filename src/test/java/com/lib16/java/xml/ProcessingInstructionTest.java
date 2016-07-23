package com.lib16.java.xml;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lib16.java.xml.ProcessingInstruction;
import com.lib16.java.xml.Xml;

public class ProcessingInstructionTest
{
	@DataProvider(name = "provider")
	public static Object[][] provider()
	{
		return new Object[][] {
			{
				ProcessingInstruction.create("target", "content"),
				"<?target content ?>"
			},
			{
				ProcessingInstruction.create("target")
						.attrib("attrib1", "value1")
						.attrib("attrib2", "value2"),
				"<?target attrib1=\"value1\" attrib2=\"value2\" ?>"
			},
		};
	}

	@Test(dataProvider = "provider")
	public void test(Object actual, String expected)
	{
		if (actual instanceof FuncI) {
			actual = ((FuncI) actual).gm().getMarkup();
		}
		Assert.assertEquals(actual.toString(), expected);
	}

	interface FuncI
	{
		public Xml gm();
	}

}
