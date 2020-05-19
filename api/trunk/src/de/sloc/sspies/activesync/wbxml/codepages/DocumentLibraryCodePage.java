package de.sloc.sspies.activesync.wbxml.codepages;

public class DocumentLibraryCodePage extends WbXmlCodePage
{

	public DocumentLibraryCodePage()
	{
		super(19);
		
		addEntry("LinkId", 0x05);
		addEntry("DisplayName", 0x06);
		addEntry("IsFolder", 0x07);
		addEntry("CreationDate", 0x08);
		addEntry("LastModifiedDate", 0x09);
		addEntry("IsHidden", 0x0A);
		addEntry("ContentLength", 0x0B);
		addEntry("ContentType", 0x0C);
	}



}
