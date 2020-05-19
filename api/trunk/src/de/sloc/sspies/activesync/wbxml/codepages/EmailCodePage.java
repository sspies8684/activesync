package de.sloc.sspies.activesync.wbxml.codepages;

public class EmailCodePage extends WbXmlCodePage
{

	public EmailCodePage()
	{
		super(2);

		addEntry("DateReceived", 0x0F);
		addEntry("DisplayTo", 0x11);
		addEntry("Importance", 0x12);
		addEntry("MessageClass", 0x13);
		addEntry("Subject", 0x14);
		addEntry("Read", 0x15);
		addEntry("To", 0x16);
		addEntry("CC", 0x17);
		addEntry("From", 0x18);
		addEntry("ReplyTo", 0x19);
		addEntry("AllDayEvent", 0x1A);
		addEntry("Categories", 0x1B);
		addEntry("Category", 0x1C);
		addEntry("DTStamp", 0x1D);
		addEntry("EndTime", 0x1E);
		addEntry("InstanceType", 0x1F);
		addEntry("BusyStatus", 0x20);
		addEntry("Location", 0x21);
		addEntry("MeetingRequest", 0x22);
		addEntry("Organizer", 0x23);
		addEntry("RecurrenceId", 0x24);
		addEntry("Reminder", 0x25);
		addEntry("ResponseRequested", 0x26);
		addEntry("Recurrences", 0x27);
		addEntry("Recurrence", 0x28);
		addEntry("Recurrence_Type", 0x29);
		addEntry("Recurrence_Until", 0x2A);
		addEntry("Recurrence_Occurrences", 0x2B);
		addEntry("Recurrence_Interval", 0x2C);
		addEntry("Recurrence_DayOfWeek", 0x2D);
		addEntry("Recurrence_DayOfMonth", 0x2E);
		addEntry("Recurrence_WeekOfMonth", 0x2F);
		addEntry("Recurrence_MonthOfYear", 0x30);
		addEntry("StartTime", 0x31);
		addEntry("Sensitivity", 0x32);
		addEntry("TimeZone", 0x33);
		addEntry("GlobalObjId", 0x34);
		addEntry("ThreadTopic", 0x35);
		addEntry("InternetCPID", 0x39);
		addEntry("Flag", 0x3A);
		addEntry("FlagStatus", 0x3B);
		addEntry("ContentClass", 0x3C);
		addEntry("FlagType", 0x3D);
		addEntry("CompleteTime", 0x3E);
		addEntry("DisallowNewTimeProposal", 0x3F);
	}

}
