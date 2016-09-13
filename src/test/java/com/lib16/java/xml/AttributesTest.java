package com.lib16.java.xml;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lib16.java.utils.NumberFormatter;
import com.lib16.java.utils.Unit;
import com.lib16.java.xml.shared.MediaAttribute.Media;

public class AttributesTest
{
	@DataProvider(name = "provider")
	public static Object[][] provider()
	{
		NumberFormatter fw = new NumberFormatter(4);

		return new Object[][] {
			// set()
			{new Attributes().set("a", "foo"),  " a=\"foo\""},
			{new Attributes().set("a", ""),     " a=\"\""},
			{new Attributes().set("a", null),   ""},
			{new Attributes().set(null, "foo"), ""},

			// setBoolean()
			{new Attributes().setBoolean("a", true), " a=\"a\""},
			{new Attributes().setBoolean("a", false), ""},
			{new Attributes(new TProps()).setBoolean("a", true), " a=\"a\""},
			{new Attributes(new TProps().h()).setBoolean("a", true), " a"},
			{new Attributes(new TProps().h()).setBoolean("a", false), ""},
			{
				new Attributes()
						.set("a1", "foo")
						.setBoolean("a2", "a1", "foo"),
				" a1=\"foo\" a2=\"a2\""
			},
			{
				new Attributes()
						.set("a1", "bar")
						.setBoolean("a2", "a1", "foo"),
				" a1=\"bar\""
			},
			{
				new Attributes()
						.set("a1", null)
						.setBoolean("a2", "a1", "foo"),
				""
			},
			{
				new Attributes()
						.set("a1", "foo")
						.setBoolean("a2", "a1", new String[] {"bar", "foo"}),
				" a1=\"foo\" a2=\"a2\""
			},
			{
				new Attributes()
						.set("a1", "foo")
						.setBoolean("a2", "a1", "bar", "baz"),
				" a1=\"foo\""
			},
			{
				new Attributes()
						.set("a1", "foo")
						.setBoolean("a2", "a1"),
				" a1=\"foo\""
			},

			// setComplex()
			{
				new Attributes()
				.set("a", "foo,bar")
				.setComplex("a", ",", false, "baz", "foo", "baz"),
				" a=\"foo,bar,baz,foo,baz\""
			},
			{
				new Attributes()
						.set("a", "foo,bar")
						.setComplex("a", ",", true, "baz", "foo", "baz"),
				" a=\"foo,bar,baz\""
			},
			{
				new Attributes()
						.set("a", "foo")
						.setComplex("a", ",", true, "foo"),
				" a=\"foo\""
			},
			{
				new Attributes()
						.set("a", "foo,baz")
						.setComplex("a", ",", true, "foo"),
				" a=\"foo,baz\""
			},
			{
				new Attributes()
						.set("a", "baz,foo")
						.setComplex("a", ",", true, "foo"),
				" a=\"baz,foo\""
			},
			{
				new Attributes()
						.set("a", "baz,foo,baz")
						.setComplex("a", ",", true, "foo"),
				" a=\"baz,foo,baz\""
			},
			{
				new Attributes()
						.set("a", null)
						.setComplex("a", ",", true, "foo"),
				" a=\"foo\""
			},
			{
				new Attributes()
						.setComplex("a", ",", true, "foo", null, "baz"),
				" a=\"foo,baz\""
			},

			// setNumber()
			{new Attributes().setNumber("a", 12.3456789, fw), " a=\"12.3457\""},
			{new Attributes().setNumber("a", 12.3456789f, fw), " a=\"12.3457\""},
			{new Attributes().setNumber("a", 12.3, fw), " a=\"12.3\""},
			{new Attributes().setNumber("a", 12, fw), " a=\"12\""},
			{new Attributes().setNumber("a", 16, fw, Unit.PX), " a=\"16px\""},
			{new Attributes().setNumber("a", 50, fw, Unit.PERCENT), " a=\"50%\""},
			{new Attributes().setNumber("a", 1.5, fw, Unit.NONE), " a=\"1.5\""},
			{new Attributes().setNumber("a", 1.5, fw, null), " a=\"1.5\""},
			{new Attributes().setNumber("a", null, fw), ""},
			{
				new Attributes()
						.setNumber("a", " ", fw, 10.5, 5.25)
						.setNumber("a", " ", fw, 10.5)
						.setNumber("a", " ", fw),
				" a=\"10.5 5.25 10.5\""
			},
			{new Attributes().setNumber("a", " ", fw), ""},
			{new Attributes().setNumber("a", " ", fw, Unit.PX), ""},
			{
				new Attributes()
						.setNumber("a", " ", fw, Unit.PX, 10.00001, 5)
						.setNumber("a", " ", fw, Unit.PX, 10)
						.setNumber("a", " ", fw),
				" a=\"10px 5px 10px\""
			},

			// setEnum()
			{
				new Attributes().setEnum("a", Media.ALL),
				" a=\"all\""
			},
			{
				new Attributes().setEnum("a", null),
				""
			},
			{
				new Attributes().setEnum("a", " ", Media.SCREEN, Media.PRINT),
				" a=\"screen print\""
			},
			{
				new Attributes().setEnum("a", " ", Media.SCREEN, null, Media.PRINT),
				" a=\"screen print\""
			},

			// numberToString()
			{
				new Attributes().set("a", Attributes.numberToString(0.99999, fw)),
				" a=\"1\""
			},
			{
				new Attributes().set("a", Attributes.numberToString(50.55555, fw, Unit.PX)),
				" a=\"50.5555px\""
			},

			// vertical alignment
			{
				new Attributes(new TProps().v()).set("a1", "value 1").set("a2", "value 2"),
				"\n\t\ta1=\"value 1\"\n\t\ta2=\"value 2\""
			}
		};
	}

	@Test(dataProvider = "provider")
	public void test(Attributes attributes, String expected)
	{
		Assert.assertEquals(attributes.getMarkup("\t\t"), expected);
	}
}
