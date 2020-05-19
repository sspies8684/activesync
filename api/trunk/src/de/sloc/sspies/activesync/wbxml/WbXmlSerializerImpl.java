package de.sloc.sspies.activesync.wbxml;

import static de.sloc.sspies.Utility.longToMbUInt32;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.sloc.sspies.activesync.wbxml.codepages.WbXmlCodePage;

public class WbXmlSerializerImpl implements WbXmlSerializer, WbXmlConstants
{
	private XMLStreamReader streamReader;
	private OutputStream outputStream;
	private WbXmlCodePage currentElementCodePage;

	public WbXmlSerializerImpl(XMLStreamReader streamReader, OutputStream outputStream)
	{
		this.streamReader = streamReader;
		this.outputStream = outputStream;
		this.currentElementCodePage = WbXmlCodePage.getCodePage(0);
	}

	public void serialize() throws XMLStreamException
	{
		try
		{
			// write version number 1.3
			outputStream.write(3);

			// write unknown public identifier
			outputStream.write(1);

			// write charset = utf-8
			outputStream.write(UTF8);

			// no string table
			outputStream.write(0);

			String elementName = null;

			outer: for (;;)
			{
				int eventType = streamReader.getEventType();

				switch (eventType)
				{

					case XMLStreamConstants.START_DOCUMENT:
						streamReader.next();
						continue outer;
					case XMLStreamConstants.START_ELEMENT:
						String namespaceURI = streamReader.getNamespaceURI();
						elementName = streamReader.getLocalName();
						elementName = elementName.replaceAll("(Request|Response)$", "");

						boolean hasAttributes = streamReader.getAttributeCount() > 0;
						WbXmlCodePage elementCodePage = WbXmlCodePage.getCodePage(namespaceURI);

						// switch codepage if changed
						if (elementCodePage != currentElementCodePage)
						{
							currentElementCodePage = elementCodePage;

							outputStream.write(SWITCH_PAGE);
							outputStream.write(currentElementCodePage.getCodePageId());

						}

						int tokenValue = currentElementCodePage.getTokenFromTag(elementName);

						if (hasAttributes)
						{
							tokenValue |= HAS_ATTRIBUTES;
						}
						int nextTag = streamReader.nextTag();
						boolean hasContent = nextTag != XMLStreamConstants.END_ELEMENT;
						if (hasContent)
						{
							tokenValue |= HAS_CONTENT;
						}

						outputStream.write(tokenValue);

						if (hasAttributes)
						{
							// alert, unexpected
							throw new XMLStreamException("Did not expect to see attributes");
						}

						streamReader.next();

						continue outer;
					case XMLStreamConstants.END_ELEMENT:
						outputStream.write(END);
						streamReader.next();
						continue outer;
					case XMLStreamConstants.CHARACTERS:
						if(currentElementCodePage.isOpaque(elementName))
						{
							byte[] opaqueBytes = streamReader.getText().getBytes(Charset.forName("UTF-8"));
							outputStream.write(OPAQUE);
							outputStream.write(longToMbUInt32(opaqueBytes.length));
							outputStream.write(opaqueBytes);
						}
						else
						{
							outputStream.write(STR_I);
							outputStream.write(streamReader.getText().getBytes(Charset.forName("UTF-8")));
							outputStream.write(0);
						}

						streamReader.next();
						continue outer;
					case XMLStreamConstants.END_DOCUMENT:
						break outer;
					default:
						throw new XMLStreamException("Unknown eventType=" + eventType);

				}
			}

			outputStream.flush();
		}
		catch (IOException e)
		{
			throw new XMLStreamException(e);
		}

	}


}
