package de.sloc.sspies.activesync.wbxml.codepages;

public class SearchCodePage extends WbXmlCodePage
{

	public SearchCodePage()
	{
		super(15);
		
		addEntry("Search", 0x05);
		addEntry("Store", 0x07);
		addEntry("Name", 0x08);
		addEntry("Query", 0x09);
		addEntry("Options", 0x0A);
		addEntry("Range", 0x0B);
		addEntry("Status", 0x0C);
		addEntry("Response", 0x0D);
		addEntry("Result", 0x0E);
		addEntry("Properties", 0x0F);
		addEntry("Total", 0x10);
		addEntry("EqualTo", 0x11);
		addEntry("Value", 0x12);
		addEntry("And", 0x13);
		addEntry("Or", 0x14);
		addEntry("FreeText", 0x15);
		addEntry("DeepTraversal", 0x17);
		addEntry("LongId", 0x18);
		addEntry("RebuildResults", 0x19);
		addEntry("LessThan", 0x1A);
		addEntry("GreaterThan", 0x1B);
		addEntry("UserName", 0x1E);
		addEntry("Password", 0x1F);
		addEntry("ConversationId", 0x20);
		addEntry("Picture", 0x21);
		addEntry("MaxSize", 0x22);
		addEntry("MaxPictures", 0x23);
	}



}
