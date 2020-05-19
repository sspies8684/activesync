package de.sloc.sspies.activesync.context;

import java.util.HashMap;
import java.util.Map;


public class AutodiscoverContext extends RequestContext
{

	protected AutodiscoverContext(String userName, String password)
	{
		this.setContentType(CONTENT_TYPE_TEXT_XML);

		this.setAuthUser(userName);
		this.setPassword(password);
	}

	protected AutodiscoverContext()
	{

	}

	@Override
	public Map<String, String> toHeaders()
	{
		Map<String, String> headers = new HashMap<String, String>();

		headers.put(HTTP_HEADER_AUTHORIZATION, encodeBasicAuthParameters(authUser, password));
		headers.put(HTTP_HEADER_CONTENT_TYPE, getContentType());

		return headers;
	}

	@Override
	public String toQueryString()
	{
		return "";
	}

	@Override
	public Parser getParser()
	{
		return new Parser()
		{
			@Override
			public RequestContext parse(Map<String, String> headers, String queryString)
			{
				AutodiscoverContext context = new AutodiscoverContext();

				String[] credentials = decodeBasicAuthParameters(headers.get(HTTP_HEADER_AUTHORIZATION));
				context.setAuthUser(credentials[0]);
				context.setPassword(credentials[1]);

				context.setContentType(headers.get(HTTP_HEADER_CONTENT_TYPE));

				addProtectedParameters(context.getAdditionalParameters(), headers, HTTP_HEADER_AUTHORIZATION, HTTP_HEADER_CONTENT_TYPE);

				return context;
			}
		};
	}

}
