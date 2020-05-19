package de.sloc.sspies.activesync.wbxml.codepages;

public class Email2CodePage extends WbXmlCodePage
{

	public Email2CodePage()
	{
		super(22);
		
		addEntry("UmCallerID", 0x05);
		addEntry("UmUserNotes", 0x06);
		addEntry("UmAttDuration", 0x07);
		addEntry("UmAttOrder", 0x08);
		addEntry("ConversationId", 0x09);
		addEntry("ConversationIndex", 0x0A);
		addEntry("LastVerbExecuted", 0x0B);
		addEntry("LastVerbExecutionTime", 0x0C);
		addEntry("ReceivedAsBcc", 0x0D);
		addEntry("Sender", 0x0E);
		addEntry("CalendarType", 0x0F);
		addEntry("IsLeapMonth", 0x10);
		addEntry("AccountId", 0x11);
		addEntry("FirstDayOfWeek", 0x12);
		addEntry("MeetingMessageType", 0x13);
	}


}
