package de.sloc.sspies.activesync.wbxml.codepages;

public class PingCodePage extends WbXmlCodePage
{

	public PingCodePage()
	{
		super(13);
		
		addEntry("Ping", 0x05);
		addEntry("AutdState", 0x06);
		addEntry("Status", 0x07);
		addEntry("HeartbeatInterval", 0x08);
		addEntry("Folders", 0x09);
		addEntry("Folder", 0x0A);
		addEntry("Id", 0x0B);
		addEntry("Class", 0x0C);
		addEntry("MaxFolders", 0x0D);
	}


}
