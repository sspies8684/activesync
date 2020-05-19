package de.sloc.sspies.activesync.wbxml.codepages;

public class MoveCodePage extends WbXmlCodePage
{

	public MoveCodePage()
	{
		super(5);
		addEntry("MoveItems", 0x05);
		addEntry("Move", 0x06);
		addEntry("SrcMsgId", 0x07);
		addEntry("SrcFldId", 0x08);
		addEntry("DstFldId", 0x09);
		addEntry("Response", 0x0A);
		addEntry("Status", 0x0B);
		addEntry("DstMsgId", 0x0C);
	}

}
