package de.sloc.sspies.activesync.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.sloc.sspies.Utility;
import de.sloc.sspies.activesync.context.ReplyContext;
import de.sloc.sspies.activesync.context.RequestContext;

public class EndpointImpl implements Endpoint
{
	protected URL url;

	public EndpointImpl(URL url)
	{
		super();
		this.url = url;
	}

	@Override
	public URL getUrl()
	{
		return url;
	}

	@Override
	public EndpointReply issuePOST(RequestContext context, BufferedInputStream bodyStream) throws IOException
	{
		URL modifiedUrl;
		String queryString = context.toQueryString();
		if (queryString != null && !queryString.equals(""))
		{
			modifiedUrl = new URL(getUrl().toString() + "?" + context.toQueryString());
		}
		else
		{
			modifiedUrl = getUrl();
		}

		URLConnection connection = modifiedUrl.openConnection();
		connection.setConnectTimeout(1500);

		connection.setDoInput(true);
		connection.setDoOutput(true);

		Map<String, String> headers = context.toHeaders();
		for (Entry<String, String> header : headers.entrySet())
		{
			connection.addRequestProperty(header.getKey(), header.getValue());
		}

		OutputStream outputStream = connection.getOutputStream();
		bodyStream.mark(4096);
		Utility.flow(bodyStream, outputStream, 1024);
		outputStream.close();

		Map<String, String> resultHeaders = new HashMap<String, String>();
		for (Entry<String, List<String>> headerEntry : connection.getHeaderFields().entrySet())
		{
			// multi-headers have no meaning for ActiveSync
			resultHeaders.put(headerEntry.getKey(), headerEntry.getValue().get(0));
		}

		ReplyContext replyContext = ReplyContext.parse(resultHeaders, ((HttpURLConnection) connection).getResponseCode());

		EndpointReply reply;
		if(! isRedirected(replyContext))
		{
			reply = new EndpointReply(connection.getInputStream(), replyContext);
		}
		else
		{
			System.out.println("451 - Redirected to " + url);
			// TODO log and redo, check optionsContext
			ReplyContext optionsContext = issueOPTIONS(context);
			optionsContext.getMsProtocolVersions();
			bodyStream.reset();
			reply = issuePOST(context, bodyStream);
		}

		return reply;
	}

	protected boolean isRedirected(ReplyContext replyContext) throws MalformedURLException
	{
		if (replyContext.getStatusCode() == HTTP_CODE_SERVER_REDIRECT)
		{
			String redirectUrl = replyContext.getMsLocation();
			if (redirectUrl != null)
			{
				this.url = new URL(redirectUrl);
			}
			else
			{
				throw new MalformedURLException("Header X-MS-Location not set while status code is 451!");
			}
			return true;
		}
		else
			return false;
	}

	@Override
	public ReplyContext issueOPTIONS(RequestContext context) throws IOException
	{
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setRequestMethod("OPTIONS");
		connection.setUseCaches(false);

		connection.setRequestProperty(HTTP_HEADER_AUTHORIZATION, context.toHeaders().get(HTTP_HEADER_AUTHORIZATION));
		connection.connect();

		Map<String, String> resultHeaders = new HashMap<String, String>();
		for (Entry<String, List<String>> headerEntry : connection.getHeaderFields().entrySet())
		{
			// multi-headers have no meaning for ActiveSync
			resultHeaders.put(headerEntry.getKey(), headerEntry.getValue().get(0));
		}

		return ReplyContext.parse(resultHeaders, connection.getResponseCode());
	}

}
