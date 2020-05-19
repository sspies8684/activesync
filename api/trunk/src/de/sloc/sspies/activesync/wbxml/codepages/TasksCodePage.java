package de.sloc.sspies.activesync.wbxml.codepages;

public class TasksCodePage extends WbXmlCodePage
{

	public TasksCodePage()
	{
		super(9);

		addEntry("Categories", 0x08);
		addEntry("Category", 0x09);
		addEntry("Complete", 0x0A);
		addEntry("DateCompleted", 0x0B);
		addEntry("DueDate", 0x0C);
		addEntry("UTCDueDate", 0x0D);
		addEntry("Importance", 0x0E);
		addEntry("Recurrence", 0x0F);
		addEntry("Recurrence_Type", 0x10);
		addEntry("Recurrence_Start", 0x11);
		addEntry("Recurrence_Until", 0x12);
		addEntry("Recurrence_Occurrences", 0x13);
		addEntry("Recurrence_Interval", 0x14);
		addEntry("Recurrence_DayOfMonth", 0x15);
		addEntry("Recurrence_DayOfWeek", 0x16);
		addEntry("Recurrence_WeekOfMonth", 0x17);
		addEntry("Recurrence_MonthOfYear", 0x18);
		addEntry("Recurrence_Regenerate", 0x19);
		addEntry("Recurrence_DeadOccur", 0x1A);
		addEntry("ReminderSet", 0x1B);
		addEntry("ReminderTime", 0x1C);
		addEntry("Sensitivity", 0x1D);
		addEntry("StartDate", 0x1E);
		addEntry("UTCStartDate", 0x1F);
		addEntry("Subject", 0x20);
		addEntry("OrdinalDate", 0x22);
		addEntry("SubOrdinalDate", 0x23);
		addEntry("CalendarType", 0x24);
		addEntry("IsLeapMonth", 0x25);
		addEntry("FirstDayOfWeek", 0x26);
	}
}
