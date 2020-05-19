package de.sloc.sspies.activesync.wbxml.codepages;

public class MeetingResponseCodePage extends WbXmlCodePage
{

	public MeetingResponseCodePage()
	{
		super(8);
		
		addEntry("CalendarId", 0x05);
		addEntry("CollectionId", 0x06);
		addEntry("MeetingResponse", 0x07);
		addEntry("RequestId", 0x08);
		addEntry("Request", 0x09);
		addEntry("Result", 0x0A);
		addEntry("Status", 0x0B);
		addEntry("UserResponse", 0x0C);
		addEntry("InstanceId", 0x0E);
	}


}
