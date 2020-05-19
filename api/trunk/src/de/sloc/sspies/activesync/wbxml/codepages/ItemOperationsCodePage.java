package de.sloc.sspies.activesync.wbxml.codepages;

public class ItemOperationsCodePage extends WbXmlCodePage
{

	public ItemOperationsCodePage()
	{
		super(20);
		addEntry("ItemOperations", 0x05);
		addEntry("Fetch", 0x06);
		addEntry("Store", 0x07);
		addEntry("Options", 0x08);
		addEntry("Range", 0x09);
		addEntry("Total", 0x0A);
		addEntry("Properties", 0x0B);
		addEntry("Data", 0x0C);
		addEntry("Status", 0x0D);
		addEntry("Response", 0x0E);
		addEntry("Version", 0x0F);
		addEntry("Schema", 0x10);
		addEntry("Part", 0x11);
		addEntry("EmptyFolderContents", 0x12);
		addEntry("DeleteSubFolders", 0x13);
		addEntry("UserName", 0x14);
		addEntry("Password", 0x15);
		addEntry("Move", 0x16);
		addEntry("DstFldId", 0x17);
		addEntry("ConversationId", 0x18);
		addEntry("MoveAlways", 0x19);
	}


}
