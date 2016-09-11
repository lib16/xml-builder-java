package com.lib16.java.xml.shared;

import com.lib16.java.xml.Xml;

public class TargetAttribute
{
	public enum Target
	{
		BLANK, SELF, PARENT, TOP;

		private String str;

		private Target()
		{
			str = "_" + name().toLowerCase();
		}

		@Override
		public String toString() {
			return str;
		}
	}

	public static void setTarget(Xml xml, String target)
	{
		xml.getAttributes().set("target", target);
	}

	public static void setTarget(Xml xml, Target target)
	{
		xml.getAttributes().setEnum("target", target);
	}
}
