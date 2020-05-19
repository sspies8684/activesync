package de.sloc.sspies.activesync.wbxml.codepages;

public class AirSyncCodePage extends WbXmlCodePage
{

	public AirSyncCodePage()
	{
		super(0);

		addEntry("Sync", 0x05);
		addEntry("Responses", 0x06);
		addEntry("Add", 0x07);
		addEntry("Change", 0x08);
		addEntry("Delete", 0x09);
		addEntry("Fetch", 0x0A);
		addEntry("SyncKey", 0x0B);
		addEntry("ClientId", 0x0C);
		addEntry("ServerId", 0x0D);
		addEntry("Status", 0x0E);
		addEntry("Collection", 0x0F);
		addEntry("Class", 0x10);
		addEntry("CollectionId", 0x12);
		addEntry("GetChanges", 0x13);
		addEntry("MoreAvailable", 0x14);
		addEntry("WindowSize", 0x15);
		addEntry("Commands", 0x16);
		addEntry("Options", 0x17);
		addEntry("FilterType", 0x18);
		addEntry("Conflict", 0x1B);
		addEntry("Collections", 0x1C);
		addEntry("ApplicationData", 0x1D);
		addEntry("DeletesAsMoves", 0x1E);
		addEntry("Supported", 0x20);
		addEntry("SoftDelete", 0x21);
		addEntry("MIMESupport", 0x22);
		addEntry("MIMETruncation", 0x23);
		addEntry("Wait", 0x24);
		addEntry("Limit", 0x25);
		addEntry("Partial", 0x26);
		addEntry("ConversationMode", 0x27);
		addEntry("MaxItems", 0x28);
		addEntry("HeartbeatInterval", 0x29);
	}

}
