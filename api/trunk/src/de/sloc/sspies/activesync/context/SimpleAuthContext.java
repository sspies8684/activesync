package de.sloc.sspies.activesync.context;

import java.util.HashMap;
import java.util.Map;


public class SimpleAuthContext extends RequestContext
{

	protected SimpleAuthContext(String userName, String password)
	{
		this.setAuthUser(userName);
		this.setPassword(password);
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
		return null;
	}

}
