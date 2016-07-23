package com.lib16.java.xml.shared;

import com.lib16.java.xml.Xml;

public class MediaAttribute
{
	public enum Media
	{
		ALL, AURAL, BRAILLE, HANDHELD, PROJECTION, PRINT, SCREEN, TTY, TV;
		
		private String str;
		
		private Media()
		{
			str = name().toLowerCase();
		}
		
		@Override
		public String toString()
		{
			return str;
		}
	}

	public static void setMedia(Xml xml, Media... media)
	{
		for (Media m: media) {
			xml.getAttributes().setComplex("media", ",", true, m.toString());
		}
	}
}