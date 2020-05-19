package de.sloc.sspies.activesync.context;

import static de.sloc.sspies.Utility.integerToOneByte;
import static de.sloc.sspies.Utility.integerToTwoBytes;

import java.io.ByteArrayOutputStream;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.xmlbeans.impl.util.Base64;

import de.sloc.sspies.Utility;
import de.sloc.sspies.UtilityException;
import de.sloc.sspies.activesync.MicrosoftLanguageId;
import de.sloc.sspies.activesync.context.RequestContextFactory.EncodingAnnotation;
import de.sloc.sspies.activesync.context.RequestContextFactory.EncodingType;

@EncodingAnnotation(EncodingType.BASE64)
public class Base64Context extends RequestContext
{
	protected Base64Context()
	{

	}

	@Override
	public Map<String, String> toHeaders()
	{
		Map<String, String> headers = new HashMap<String, String>();

		headers.put(HTTP_HEADER_AUTHORIZATION, encodeBasicAuthParameters(authUser, password));

		if (contentType != null)
		{
			headers.put(HTTP_HEADER_CONTENT_TYPE, contentType);
		}

		if (userAgent != null)
		{
			headers.put(HTTP_HEADER_USER_AGENT, userAgent);
		}

		// add all additional parameters, but prevent overwriting of existing
		// semantics
		addProtectedParameters(	headers, additionalParameters, HTTP_HEADER_AS_PROTOCOL_VERSION, HTTP_HEADER_ACCEPT_LANGUAGE, HTTP_HEADER_POLICY_KEY,
								HTTP_HEADER_ACCEPT_MULTIPART);

		return headers;
	}

	private void writeEncodedParameter(ByteArrayOutputStream baos, CommandParameter commandParameter, String value) throws Exception
	{
		if (value != null)
		{
			baos.write(integerToOneByte(commandParameter.getCode()));
			baos.write(integerToOneByte(value.length()));
			baos.write(value.getBytes());
		}
	}

	@Override
	public String toQueryString()
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try
		{
			baos.write(integerToOneByte(Integer.parseInt(protocolVersion.replaceFirst("\\.", ""))));
			baos.write(integerToOneByte(CommandCode.lookupByName(command).getCode()));

			if (languageId == null)
			{
				throw new IllegalStateException("Language id is unset");
			}
			baos.write(integerToTwoBytes(languageId.getValue()));

			baos.write(integerToOneByte(deviceId.length()));
			baos.write(deviceId.getBytes());

			if (policyKey == null)
			{
				baos.write(0x00);
			}
			else
			{
				baos.write(integerToOneByte(policyKey.length()));
				baos.write(policyKey.getBytes());
			}

			baos.write(integerToOneByte(deviceType.length()));
			baos.write(deviceType.getBytes());

			writeEncodedParameter(baos, CommandParameter.USER, user);
			writeEncodedParameter(baos, CommandParameter.ATTACHMENT_NAME, attachmentName);
			writeEncodedParameter(baos, CommandParameter.COLLECTION_ID, collectionId);
			writeEncodedParameter(baos, CommandParameter.ITEM_ID, itemId);
			writeEncodedParameter(baos, CommandParameter.LONG_ID, longId);
			writeEncodedParameter(baos, CommandParameter.OCCURENCE, occurence);
			
			if (saveInSent != null || acceptMultiPart != null)
			{
				byte optionsBitmask = integerToOneByte((saveInSent != null && saveInSent ? 0x01 : 0x00)
						| (acceptMultiPart != null && acceptMultiPart ? 0x02 : 0x00));
				writeEncodedParameter(baos, CommandParameter.OPTIONS, new String(new byte[] { optionsBitmask }));
			}

		}
		catch (Exception e)
		{
			throw new InternalError(e.getMessage());
		}

		return new String(Base64.encode(baos.toByteArray()));
	}

	public Parser getParser()
	{
		return new Parser()
		{
			private void parseHeaders(Base64Context context, Map<String, String> headers)
			{
				for (Entry<String, String> entry : headers.entrySet())
				{
					String key = entry.getKey();
					String value = entry.getValue();

					if (key.equalsIgnoreCase(HTTP_HEADER_AUTHORIZATION))
					{
						String[] credentials = decodeBasicAuthParameters(value);
						context.setAuthUser(credentials[0]);
						context.setPassword(credentials[1]);
					}
					else if (key.equalsIgnoreCase(HTTP_HEADER_CONTENT_TYPE))
					{
						context.setContentType(value);
					}
					else if (key.equalsIgnoreCase(HTTP_HEADER_USER_AGENT))
					{
						context.setUserAgent(value);
					}
					else
					{
						context.addAdditionalParameter(key, value);
					}
				}
			}

			@Override
			public RequestContext parse(Map<String, String> headers, String queryString)
			{
				Base64Context context = new Base64Context();

				parseHeaders(context, headers);

				try
				{
					byte[] decodedBytes = Base64.decode(queryString.getBytes());
					int offset = 0;

					{ // protocol version
						byte protocolVersion = decodedBytes[0];
						context.setProtocolVersion(Integer.toString(Utility.oneByteToInteger(protocolVersion)).replaceFirst("(\\d+)(\\d)", "$1.$2"));
					}

					{ // command
						byte commandCode = decodedBytes[1];
						int commandCodeValue = Utility.oneByteToInteger(commandCode);
						CommandCode commandCodeType = CommandCode.lookupByCode(commandCodeValue);
						if (commandCodeType == null)
						{
							throw new InvalidParameterException("Invalid command code: " + commandCodeValue);
						}
						context.setCommand(commandCodeType.getName());
					}

					{ // Language Id
						byte[] localeBytes = new byte[2];
						System.arraycopy(decodedBytes, 2, localeBytes, 0, 2);
						int localeId = Utility.twoBytesToInteger(localeBytes);
						MicrosoftLanguageId languageId = MicrosoftLanguageId.byValue(localeId);

						// if (languageId == MicrosoftLanguageId.UNKNOWN)
						// {
						// throw new
						// InvalidParameterException("Invalid locale id: " +
						// localeId);
						// }
						context.setLanguageId(languageId);
					}

					{ // DeviceId
						int deviceIdLength = Utility.oneByteToInteger(decodedBytes[4]);
						byte[] deviceIdBytes = new byte[deviceIdLength];
						System.arraycopy(decodedBytes, 5, deviceIdBytes, 0, deviceIdLength);
						context.setDeviceId(new String(deviceIdBytes));
						offset = 5 + deviceIdLength;
					}

					{ // Policy key
						int policyKeyLength = Utility.oneByteToInteger(decodedBytes[offset]);
						if (policyKeyLength > 0)
						{
							byte[] policyKeyBytes = new byte[policyKeyLength];
							System.arraycopy(decodedBytes, offset + 1, policyKeyBytes, 0, policyKeyLength);
							context.setPolicyKey(new String(policyKeyBytes));
						}
						offset += 1 + policyKeyLength;
					}

					{ // DeviceType
						int deviceTypeLength = Utility.oneByteToInteger(decodedBytes[offset]);
						byte[] deviceTypeBytes = new byte[deviceTypeLength];
						System.arraycopy(decodedBytes, offset + 1, deviceTypeBytes, 0, deviceTypeLength);
						context.setDeviceType(new String(deviceTypeBytes));
						offset += 1 + deviceTypeLength;
					}

					// command parameters
					while (offset < decodedBytes.length)
					{
						// undecoded parameters
						int tag = Utility.oneByteToInteger(decodedBytes[offset]);
						int length = Utility.oneByteToInteger(decodedBytes[offset + 1]);
						byte[] valueBytes = new byte[length];
						System.arraycopy(decodedBytes, offset + 2, valueBytes, 0, length);
						CommandParameter parameter = CommandParameter.lookupByCode(tag);

						// options
						if (parameter == CommandParameter.OPTIONS)
						{
							byte bitmask = valueBytes[0];
							boolean saveInSent = (bitmask & OPTIONS_BITMASK_FLAG_SAVE_IN_SENT) > 0;
							boolean acceptMultiPart = (bitmask & OPTIONS_BITMASK_FLAG_ACCEPT_MULTI_PART) > 0;
							context.setSaveInSent(saveInSent);
							context.setAcceptMultiPart(acceptMultiPart);
						}
						else
						// String
						{
							String value = new String(valueBytes);

							if (parameter == CommandParameter.USER)
							{
								context.setUser(value);
							}
							else
							{
								context.addAdditionalParameter(parameter.getName(), value);
							}
						}

						offset += 2 + length;
					}

				}
				catch (UtilityException e)
				{
					throw new InvalidParameterException("Could not decode base64 query string: " + queryString + " cause: " + e.getMessage());
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					throw new InvalidParameterException("Malformed encoded parameter");
				}

				return context;

			}

		};
	}

	private enum CommandCode
	{

		SYNC(0, "Sync"), SEND_MAIL(1, "SendMail"), SMART_FORWARD(2, "SmartForward"), SMART_REPLY(3, "SmartReply"),
		GET_ATTACHMENT(4, "GetAttachment"), FOLDER_SYNC(9, "FolderSync"), FOLDER_CREATE(10, "FolderCreate"), FOLDER_DELETE(11, "FolderDelete"),
		FOLDER_UPDATE(12, "FolderUpdate"), MOVE_ITEMS(13, "MoveItems"), GET_ITEM_ESTIMATE(14, "GetItemEstimate"),
		MEETING_RESPONSE(15, "MeetingResponse"), SEARCH(16, "Search"), SETTINGS(17, "Settings"), PING(18, "Ping"),
		ITEM_OPERATIONS(19, "ItemOperations"), PROVISION(20, "Provision"), RESOLVE_RECIPIENTS(21, "ResolveRecipients"),
		VALIDATE_CERT(22, "ValidateCert");

		private static Map<String, CommandCode> nameToType = new HashMap<String, CommandCode>();
		private static Map<Integer, CommandCode> codeToType = new HashMap<Integer, CommandCode>();

		static
		{
			for (CommandCode commandCode : CommandCode.values())
			{
				nameToType.put(commandCode.getName(), commandCode);
				codeToType.put(commandCode.getCode(), commandCode);
			}
		}

		public static CommandCode lookupByName(String name)
		{
			return nameToType.get(name);
		}

		public static CommandCode lookupByCode(int code)
		{
			return codeToType.get(code);
		}

		private String name;
		private int code;

		private CommandCode(int code, String name)
		{
			this.name = name;
			this.code = code;
		}

		public int getCode()
		{
			return this.code;
		}

		public String getName()
		{
			return this.name;
		}
	}

	private enum CommandParameter
	{

		ATTACHMENT_NAME(0, "AttachmentName"), COLLECTION_ID(1, "CollectionId"), ITEM_ID(3, "ItemId"), LONG_ID(4, "LongId"),
		OCCURENCE(6, "Occurrence"), OPTIONS(7, "Options"), USER(8, "User");

		private static Map<String, CommandParameter> nameToType = new HashMap<String, CommandParameter>();
		private static Map<Integer, CommandParameter> codeToType = new HashMap<Integer, CommandParameter>();

		static
		{
			for (CommandParameter commandCode : CommandParameter.values())
			{
				nameToType.put(commandCode.getName(), commandCode);
				codeToType.put(commandCode.getCode(), commandCode);
			}
		}

		@SuppressWarnings("unused")
		public static CommandParameter lookupByName(String name)
		{
			return nameToType.get(name);
		}

		public static CommandParameter lookupByCode(int code)
		{
			return codeToType.get(code);
		}

		private String name;
		private int code;

		private CommandParameter(int code, String name)
		{
			this.name = name;
			this.code = code;
		}

		public int getCode()
		{
			return this.code;
		}

		public String getName()
		{
			return this.name;
		}
	}

	public static void main(String[] args)
	{
		/*
		 * RequestContext cxt = new
		 * Base64Context().getParser().parse(Collections.<String, String>
		 * emptyMap(), "jQAEBwNCTEEFYmx1cHAKU21hcnRQaG9uZQgGc3NwaWVzBwEB");
		 * 
		 * RequestContext c = new PlainContext(); c.setProtocolVersion("14.1");
		 * c.setCommand("Sync"); c.setLanguageId(MicrosoftLanguageId.DE_DE);
		 * c.setDeviceId("BLA"); c.setPolicyKey("blupp");
		 * c.setDeviceType("SmartPhone"); c.setUser("sspies");
		 * c.setSaveInSent(true); c.setAuthUser("sspies");
		 * c.setPassword("blabla111!"); c.setContentType("text/xml");
		 * c.setUserAgent("Java");
		 * 
		 * 
		 * System.out.println(cxt); System.out.println(c);
		 */

		RequestContext cxt2 = new Base64Context().getParser().parse(Collections.<String, String> emptyMap(),
																	"jAkECQJtZQAKU21hcnRQaG9uZQgTc3NwaWVzODY4NEBsaXZlLmNvbQ==");

		System.out.println(cxt2);
	}
}
