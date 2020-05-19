package de.sloc.sspies.activesync.context;

import java.util.HashMap;
import java.util.Map;

import de.sloc.sspies.activesync.MicrosoftLanguageId;
import de.sloc.sspies.activesync.context.RequestContextFactory.EncodingAnnotation;
import de.sloc.sspies.activesync.context.RequestContextFactory.EncodingType;

@EncodingAnnotation(EncodingType.PLAIN)
public class PlainContext extends RequestContext
{
	protected PlainContext()
	{
	}

	@Override
	public Map<String, String> toHeaders()
	{
		Map<String, String> headers = new HashMap<String, String>();
		headers.put(HTTP_HEADER_AS_PROTOCOL_VERSION, protocolVersion);

		if (languageId != null)
		{
			headers.put(HTTP_HEADER_ACCEPT_LANGUAGE, languageId.getMeaning());
		}

		if (policyKey != null)
		{
			headers.put(HTTP_HEADER_POLICY_KEY, policyKey);
		}

		if (acceptMultiPart != null)
		{
			headers.put(HTTP_HEADER_ACCEPT_MULTIPART, (acceptMultiPart ? "T" : "F"));
		}

		if (authUser != null && password != null)
		{
			headers.put(HTTP_HEADER_AUTHORIZATION, encodeBasicAuthParameters(authUser, password));
		}

		if (contentType != null)
		{
			headers.put(HTTP_HEADER_CONTENT_TYPE, contentType);
		}

		if (userAgent != null)
		{
			headers.put(HTTP_HEADER_USER_AGENT, userAgent);
		}

		// additional parameters
		addProtectedParameters(	headers, additionalParameters, HTTP_HEADER_AS_PROTOCOL_VERSION, HTTP_HEADER_ACCEPT_LANGUAGE, HTTP_HEADER_POLICY_KEY,
								HTTP_HEADER_ACCEPT_MULTIPART, HTTP_HEADER_AUTHORIZATION, HTTP_HEADER_CONTENT_TYPE, HTTP_HEADER_USER_AGENT);

		return headers;
	}

	private void appendParameter(StringBuffer buffer, String key, String value)
	{
		if (value != null)
		{
			buffer.append(key);
			buffer.append("=");
			buffer.append(value);
			buffer.append("&");
		}
	}

	private Map<String, String> decodeQueryString(String queryString)
	{
		Map<String, String> result = new HashMap<String, String>();
		for (String pair : queryString.split("&"))
		{
			String[] kv = pair.split("=");
			result.put(kv[0], kv[1]);
		}
		return result;
	}

	@Override
	public String toQueryString()
	{
		StringBuffer sb = new StringBuffer();

		appendParameter(sb, HTTP_PARAMETER_CMD, command);
		appendParameter(sb, HTTP_PARAMETER_DEVICE_ID, deviceId);
		appendParameter(sb, HTTP_PARAMETER_DEVICE_TYPE, deviceType);
		appendParameter(sb, HTTP_PARAMETER_USER_NAME, user);
		appendParameter(sb, HTTP_PARAMETER_ATTACHMENT_NAME, attachmentName);
		appendParameter(sb, HTTP_PARAMETER_COLLECTION_ID, collectionId);
		appendParameter(sb, HTTP_PARAMETER_ITEM_ID, itemId);
		appendParameter(sb, HTTP_PARAMETER_LONG_ID, longId);
		appendParameter(sb, HTTP_PARAMETER_OCCURENCE, occurence);
		if (saveInSent != null)
		{
			appendParameter(sb, HTTP_PARAMETER_SAVE_IN_SENT, saveInSent ? "T" : "F");
		}

		return sb.substring(0, sb.length() - 1);
	}

	@Override
	public Parser getParser()
	{
		return new Parser()
		{

			@Override
			public RequestContext parse(Map<String, String> headers, String queryString)
			{
				PlainContext context = new PlainContext();

				// parse well-known headers
				context.setProtocolVersion(resolveHeader(headers, HTTP_HEADER_AS_PROTOCOL_VERSION));
				context.setLanguageId(MicrosoftLanguageId.byMeaning(resolveHeader(headers, HTTP_HEADER_ACCEPT_LANGUAGE)));
				context.setPolicyKey(resolveHeader(headers, HTTP_HEADER_POLICY_KEY));
				if (headers.containsKey(HTTP_HEADER_ACCEPT_MULTIPART))
				{
					context.setAcceptMultiPart(resolveHeader(headers, HTTP_HEADER_ACCEPT_MULTIPART).equals("T"));
				}
				String[] credentials = decodeBasicAuthParameters(resolveHeader(headers, HTTP_HEADER_AUTHORIZATION));
				context.setAuthUser(credentials[0]);
				context.setPassword(credentials[1]);
				context.setUserAgent(resolveHeader(headers, HTTP_HEADER_USER_AGENT));

				// additional headers
				addProtectedParameters(	context.getAdditionalParameters(), headers, HTTP_HEADER_AS_PROTOCOL_VERSION, HTTP_HEADER_ACCEPT_LANGUAGE,
										HTTP_HEADER_POLICY_KEY, HTTP_HEADER_ACCEPT_MULTIPART, HTTP_HEADER_AUTHORIZATION, HTTP_HEADER_CONTENT_TYPE,
										HTTP_HEADER_USER_AGENT);

				// parse parameters
				Map<String, String> parameters = decodeQueryString(queryString);

				context.setCommand(parameters.get(HTTP_PARAMETER_CMD));
				context.setDeviceId(parameters.get(HTTP_PARAMETER_DEVICE_ID));
				context.setDeviceType(parameters.get(HTTP_PARAMETER_DEVICE_TYPE));
				context.setUser(parameters.get(HTTP_PARAMETER_USER_NAME));
				context.setAttachmentName(parameters.get(HTTP_PARAMETER_ATTACHMENT_NAME));
				context.setCollectionId(parameters.get(HTTP_PARAMETER_COLLECTION_ID));
				context.setItemId(parameters.get(HTTP_PARAMETER_ITEM_ID));
				context.setLongId(parameters.get(HTTP_PARAMETER_LONG_ID));
				context.setOccurence(parameters.get(HTTP_PARAMETER_OCCURENCE));
				if (parameters.containsKey(HTTP_PARAMETER_SAVE_IN_SENT))
				{
					context.setSaveInSent(parameters.get(HTTP_PARAMETER_ATTACHMENT_NAME).equalsIgnoreCase("t"));
				}

				return context;
			}
		};
	}

}
