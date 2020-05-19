package de.sloc.sspies.activesync.context;

import java.util.Map;

public class ReplyContext extends Context
{
	protected int statusCode;

	protected String cacheControl = null;
	protected String contentEncoding = null;
	protected String contentLength;
	protected String contentType = null;
	protected String msServerActiveSync = null;
	protected String msLocation = null;
	protected String msRp = null;
	protected String msProtocolCommands = null;
	protected String msProtocolVersions = null;

	protected ReplyContext()
	{
	}

	public static ReplyContext parse(Map<String, String> headers, int statusCode)
	{
		ReplyContext context = new ReplyContext();

		context.setStatusCode(statusCode);

		context.setCacheControl(resolveHeader(headers, HTTP_HEADER_CACHE_CONTROL));
		context.setContentEncoding(resolveHeader(headers, HTTP_HEADER_CONTENT_ENCODING));
		context.setContentLength(resolveHeader(headers, HTTP_HEADER_CONTENT_LENGTH));
		context.setContentType(resolveHeader(headers, HTTP_HEADER_CONTENT_TYPE));
		context.setMsServerActiveSync(resolveHeader(headers, HTTP_HEADER_MS_SERVER_AS));
		context.setMsLocation(resolveHeader(headers, HTTP_HEADER_MS_LOCATION));
		context.setMsRp(resolveHeader(headers, HTTP_HEADER_MS_RP));

		context.setMsProtocolCommands(resolveHeader(headers, HTTP_HEADER_PROTOCOL_COMMANDS));
		context.setMsProtocolVersions(resolveHeader(headers, HTTP_HEADER_PROTOCOL_VERSIONS));

		addProtectedParameters(	context.getAdditionalParameters(), headers, HTTP_HEADER_CACHE_CONTROL, HTTP_HEADER_CONTENT_ENCODING,
								HTTP_HEADER_CONTENT_LENGTH, HTTP_HEADER_CONTENT_TYPE, HTTP_HEADER_MS_SERVER_AS, HTTP_HEADER_MS_LOCATION,
								HTTP_HEADER_MS_RP, HTTP_HEADER_PROTOCOL_VERSIONS, HTTP_HEADER_PROTOCOL_COMMANDS);

		return context;
	}

	protected void setMsProtocolCommands(String msProtocolCommands)
	{
		this.msProtocolCommands = msProtocolCommands;
	}

	protected void setMsProtocolVersions(String msProtocolVersions)
	{
		this.msProtocolVersions = msProtocolVersions;
	}

	protected void setStatusCode(int statusCode)
	{
		this.statusCode = statusCode;
	}

	protected void setCacheControl(String cacheControl)
	{
		this.cacheControl = cacheControl;
	}

	protected void setContentEncoding(String contentEncoding)
	{
		this.contentEncoding = contentEncoding;
	}

	protected void setContentLength(String contentLength)
	{
		this.contentLength = contentLength;
	}

	protected void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	protected void setMsServerActiveSync(String msServerActiveSync)
	{
		this.msServerActiveSync = msServerActiveSync;
	}

	protected void setMsLocation(String msLocation)
	{
		this.msLocation = msLocation;
	}

	protected void setMsRp(String msRp)
	{
		this.msRp = msRp;
	}

	public int getStatusCode()
	{
		return statusCode;
	}

	public String getCacheControl()
	{
		return cacheControl;
	}

	public String getContentEncoding()
	{
		return contentEncoding;
	}

	public String getContentLength()
	{
		return contentLength;
	}

	public String getContentType()
	{
		return contentType;
	}

	public String getMsServerActiveSync()
	{
		return msServerActiveSync;
	}

	public String getMsLocation()
	{
		return msLocation;
	}

	public String getMsRp()
	{
		return msRp;
	}

	public String getMsProtocolCommands()
	{
		return msProtocolCommands;
	}

	public String getMsProtocolVersions()
	{
		return msProtocolVersions;
	}

}
