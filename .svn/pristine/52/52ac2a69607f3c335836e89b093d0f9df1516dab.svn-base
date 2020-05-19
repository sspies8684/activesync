package de.sloc.sspies.activesync.wbxml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.sloc.sspies.activesync.wbxml.codepages.WbXmlCodePage;

public class WbXmlParser implements XMLStreamReader, XMLStreamConstants, WbXmlConstants
{
	private InputStream inputStream;
	private List<String[]> path = new LinkedList<String[]>();
	private String version;
	private int publicIdentifier;
	private Charset charset;

	private String currentContent;
	private String currentNamespace;
	private ContentType currentContentType = ContentType.START;
	private WbXmlCodePage currentCodePage;
	private boolean endDocument = false;
	private boolean switchedCP = true;
	private int currentEvent = START_DOCUMENT;
	private String rootSuffix;

	private enum ContentType
	{
		START, STRING, CDATA, START_TAG, EMPTY_START_TAG, END_TAG
	};

	public WbXmlParser(InputStream inputStream, String rootSuffix) throws IOException
	{
		this.inputStream = inputStream;
		this.currentCodePage = WbXmlCodePage.getCodePage(0);
		this.rootSuffix = rootSuffix;
		parseHeader();
	}

	private void parseHeader() throws IOException
	{
		this.version = "1." + inputStream.read();
		this.publicIdentifier = inputStream.read();
		int charsetValue = inputStream.read();

		if (charsetValue == UTF8)
		{
			this.charset = Charset.forName("UTF-8");
		}
		else
		{
			throw new UnsupportedCharsetException("Code: " + charsetValue);
		}

		// ignore table length
		int tableLength = inputStream.read();
		if (tableLength > 0)
		{
			throw new IOException("Wbxml tables are unsupported");
		}
	}

	@Override
	public Object getProperty(String name) throws IllegalArgumentException
	{
		if (name == null)
		{
			throw new IllegalArgumentException();
		}

		return null;
	}

	@Override
	public int next() throws XMLStreamException
	{
		switchedCP = false;
		if (currentContentType == ContentType.EMPTY_START_TAG)
		{
			currentContentType = ContentType.END_TAG;
			currentEvent = END_ELEMENT;
		}
		else
		{
			try
			{
				int token = inputStream.read();

				if (token == -1)
				{
					this.endDocument = true;
					currentEvent = END_DOCUMENT;
				}
				else
				{
					if (token == SWITCH_PAGE)
					{
						currentCodePage = WbXmlCodePage.getCodePage(inputStream.read());
						currentNamespace = currentCodePage.getCodePageName();
						token = inputStream.read();
						switchedCP = true;
					}

					if (token == STR_I)
					{
						currentContent = readString();
						currentContentType = ContentType.STRING;
						currentEvent = CHARACTERS;
					}
					else if (token == OPAQUE)
					{
						int length = readMbInt32();

						currentContent = readCdata(length);
						currentContentType = ContentType.CDATA;
						currentEvent = CDATA;
					}
					else if (token == END)
					{
						String[] lastPathElement = path.remove(path.size() - 1);
						currentContent = lastPathElement[0];
						currentNamespace = lastPathElement[1];
						currentEvent = END_ELEMENT;
					}
					else
					{
						boolean hasContent = (token & HAS_CONTENT) > 0;
						boolean hasAttributes = (token & HAS_ATTRIBUTES) > 0;
						token &= 0x3F;

						if (hasAttributes)
						{
							throw new XMLStreamException("Attributes unsupported");
						}

						currentContentType = hasContent ? ContentType.START_TAG : ContentType.EMPTY_START_TAG;

						currentContent = currentCodePage.getTagFromToken(token);

						if (rootSuffix != null)
						{
							currentContent += rootSuffix;
							rootSuffix = null;
						}

						currentNamespace = currentCodePage.getCodePageName();

						if (hasContent)
						{
							path.add(new String[] { currentNamespace, currentContent });
						}
						currentEvent = START_ELEMENT;
					}

				}
			}
			catch (IOException e)
			{
				throw new XMLStreamException(e);
			}
		}

		return currentEvent;

	}

	private String readString() throws IOException
	{
		int c;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((c = inputStream.read()) != 0)
		{
			baos.write(c);
		}
		return new String(baos.toByteArray(), charset);
	}

	private String readCdata(int length) throws IOException
	{
		byte[] cdata = new byte[length];
		int offset = 0;

		while ((offset += inputStream.read(cdata, offset, length - offset)) < length)
			;

		return new String(cdata, charset);
	}

	private int readMbInt32() throws IOException
	{

		int result = 0;
		int currentByte = 0;
		int power = 0;
		do
		{
			currentByte = inputStream.read();
			result *= Math.pow(2, 7 * power++);
			result += currentByte & 0x7F;
		}
		while ((currentByte & 0x80) > 0);
		return result;
	}

	@Override
	public void require(int type, String namespaceURI, String localName) throws XMLStreamException
	{
		if (this.currentEvent == type && this.currentNamespace.equals(namespaceURI) && this.currentContent.equals(localName))
		{

		}
		else
		{
			throw new XMLStreamException("Requirement not met");
		}

	}

	@Override
	public String getElementText() throws XMLStreamException
	{
		if (getEventType() != START_ELEMENT)
		{
			throw new XMLStreamException("parser must be on START_ELEMENT to read next text", getLocation());
		}
		int eventType = next();
		StringBuffer content = new StringBuffer();
		while (eventType != END_ELEMENT)
		{
			if (eventType == CHARACTERS || eventType == CDATA || eventType == SPACE || eventType == ENTITY_REFERENCE)
			{
				content.append(getText());
			}
			else if (eventType == PROCESSING_INSTRUCTION || eventType == COMMENT)
			{
				// skipping
			}
			else if (eventType == END_DOCUMENT)
			{
				throw new XMLStreamException("unexpected end of document when reading element text content");
			}
			else if (eventType == START_ELEMENT)
			{
				throw new XMLStreamException("element text content may not contain START_ELEMENT", getLocation());
			}
			else
			{
				throw new XMLStreamException("Unexpected event type " + eventType, getLocation());
			}
			eventType = next();
		}
		return content.toString();
	}

	@Override
	public int nextTag() throws XMLStreamException
	{
		int eventType = next();
		while ((eventType == XMLStreamConstants.CHARACTERS && isWhiteSpace()) // skip
																				// whitespace
				|| (eventType == XMLStreamConstants.CDATA && isWhiteSpace())
				// skip whitespace
				|| eventType == XMLStreamConstants.SPACE
				|| eventType == XMLStreamConstants.PROCESSING_INSTRUCTION
				|| eventType == XMLStreamConstants.COMMENT)
		{
			eventType = next();
		}
		if (eventType != XMLStreamConstants.START_ELEMENT && eventType != XMLStreamConstants.END_ELEMENT)
		{
			throw new XMLStreamException("expected start or end tag", getLocation());
		}
		return eventType;
	}

	@Override
	public boolean hasNext() throws XMLStreamException
	{
		return !endDocument;
	}

	@Override
	public void close() throws XMLStreamException
	{
	}

	@Override
	public String getNamespaceURI(String prefix)
	{
		if (prefix == null)
		{
			throw new IllegalArgumentException("prefix may not be null");
		}

		if (prefix.equals("xml"))
		{
			return "http://www.w3.org/XML/1998/namespace";
		}
		else if (prefix.equals("xmlns"))
		{
			return "http://www.w3.org/2000/xmlns/";
		}

		return null;
	}

	@Override
	public boolean isStartElement()
	{
		return currentContentType == ContentType.START_TAG || currentContentType == ContentType.EMPTY_START_TAG;
	}

	@Override
	public boolean isEndElement()
	{
		return currentContentType == ContentType.END_TAG;
	}

	@Override
	public boolean isCharacters()
	{
		return currentContentType == ContentType.STRING || currentContentType == ContentType.CDATA;
	}

	@Override
	public boolean isWhiteSpace()
	{
		return false;
	}

	@Override
	public String getAttributeValue(String namespaceURI, String localName)
	{
		if (currentContentType == ContentType.START_TAG)
		{
			return null;
		}

		throw new IllegalStateException("Attributes are not supported");
	}

	@Override
	public int getAttributeCount()
	{
		return 0;
	}

	@Override
	public QName getAttributeName(int index)
	{
		return null;
	}

	@Override
	public String getAttributeNamespace(int index)
	{
		return null;
	}

	@Override
	public String getAttributeLocalName(int index)
	{
		return null;
	}

	@Override
	public String getAttributePrefix(int index)
	{
		return null;
	}

	@Override
	public String getAttributeType(int index)
	{
		return null;
	}

	@Override
	public String getAttributeValue(int index)
	{
		return null;
	}

	@Override
	public boolean isAttributeSpecified(int index)
	{
		return false;
	}

	@Override
	public int getNamespaceCount()
	{
		return switchedCP ? 1 : 0;
	}

	@Override
	public String getNamespacePrefix(int index)
	{
		return currentNamespace;
	}

	@Override
	public String getNamespaceURI(int index)
	{
		return currentNamespace;
	}

	@Override
	public NamespaceContext getNamespaceContext()
	{
		throw new IllegalStateException("namespace contextes are unsupported");
	}

	@Override
	public int getEventType()
	{
		return currentEvent;
	}

	@Override
	public String getText()
	{
		return currentContentType == ContentType.STRING || currentContentType == ContentType.CDATA ? currentContent : null;
	}

	@Override
	public char[] getTextCharacters()
	{
		return currentContentType == ContentType.STRING || currentContentType == ContentType.CDATA ? currentContent.toCharArray() : new char[0];
	}

	@Override
	public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException
	{
		throw new UnsupportedOperationException("Unsupported");
	}

	@Override
	public int getTextStart()
	{
		if (currentContentType != ContentType.STRING && currentContentType != ContentType.CDATA)
		{
			throw new IllegalStateException("Not at text element");
		}
		return 0;
	}

	@Override
	public int getTextLength()
	{
		if (currentContentType != ContentType.STRING && currentContentType != ContentType.CDATA)
		{
			throw new IllegalStateException("Not at text element");
		}
		return currentContent.length();
	}

	@Override
	public String getEncoding()
	{
		return charset.displayName();
	}

	@Override
	public boolean hasText()
	{
		if (currentContentType == ContentType.STRING || currentContentType == ContentType.CDATA)
		{
			return true;
		}
		else if (currentContentType == ContentType.START_TAG)
		{
			inputStream.mark(50);
			try
			{
				int content = inputStream.read();
				inputStream.reset();

				if (content == STR_I)
				{
					return true;
				}
			}
			catch (IOException e)
			{
				return false;
			}
		}

		return false;

	}

	@Override
	public Location getLocation()
	{
		return new Location()
		{

			@Override
			public String getSystemId()
			{
				return null;
			}

			@Override
			public String getPublicId()
			{
				return null;
			}

			@Override
			public int getLineNumber()
			{
				return -1;
			}

			@Override
			public int getColumnNumber()
			{
				return -1;
			}

			@Override
			public int getCharacterOffset()
			{
				return -1;
			}
		};
	}

	@Override
	public QName getName()
	{
		if (currentContentType == ContentType.START_TAG || currentContentType == ContentType.END_TAG
				|| currentContentType == ContentType.EMPTY_START_TAG)
		{
			return new QName(currentNamespace, currentContent);
		}
		else
		{
			throw new IllegalStateException("Not at START_ELEMENT or END_ELEMENT");
		}
	}

	@Override
	public String getLocalName()
	{
		return getName().getLocalPart();
	}

	@Override
	public boolean hasName()
	{
		return currentContentType == ContentType.START_TAG || currentContentType == ContentType.END_TAG;
	}

	@Override
	public String getNamespaceURI()
	{
		if (currentContentType == ContentType.START_TAG || currentContentType == ContentType.END_TAG)
		{
			return currentNamespace;
		}
		else
		{
			return null;
		}
	}

	@Override
	public String getPrefix()
	{
		return null;
	}

	@Override
	public String getVersion()
	{
		return "1.0";
	}

	@Override
	public boolean isStandalone()
	{
		return false;
	}

	@Override
	public boolean standaloneSet()
	{
		return false;
	}

	@Override
	public String getCharacterEncodingScheme()
	{
		return charset.displayName();
	}

	@Override
	public String getPITarget()
	{
		return null;
	}

	@Override
	public String getPIData()
	{
		return null;
	}

	public int getPublicIdentifier()
	{
		return publicIdentifier;
	}

	public String getWbxmlVersion()
	{
		return version;
	}

}
