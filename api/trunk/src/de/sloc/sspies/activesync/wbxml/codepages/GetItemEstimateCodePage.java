package de.sloc.sspies.activesync.wbxml.codepages;

public class GetItemEstimateCodePage extends WbXmlCodePage
{

	public GetItemEstimateCodePage()
	{
		super(6);
		addEntry("GetItemEstimate", 0x05);
		addEntry("Version", 0x06);
		addEntry("Collections", 0x07);
		addEntry("Collection", 0x08);
		addEntry("Class", 0x09);
		addEntry("CollectionId", 0x0A);
		addEntry("DateTime", 0x0B);
		addEntry("Estimate", 0x0C);
		addEntry("Response", 0x0D);
		addEntry("Status", 0x0E);
	}


}
