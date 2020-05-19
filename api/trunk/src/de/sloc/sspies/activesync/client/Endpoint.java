package de.sloc.sspies.activesync.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.sloc.sspies.activesync.Constants;
import de.sloc.sspies.activesync.context.ReplyContext;
import de.sloc.sspies.activesync.context.RequestContext;

public interface Endpoint extends Constants
{ 
	public URL getUrl();

	public EndpointReply issuePOST(RequestContext context, BufferedInputStream bodyStream) throws IOException;
	
	public ReplyContext issueOPTIONS(RequestContext context) throws IOException;
	
	
	
	public class EndpointReply
	{
		protected InputStream bodyStream;
		protected ReplyContext replyContext;

		protected EndpointReply(InputStream bodyStream, ReplyContext replyContext)
		{
			super();
			this.bodyStream = bodyStream;
			this.replyContext = replyContext;
		}

		public InputStream getBodyStream()
		{
			return bodyStream;
		}

		public ReplyContext getReplyContext()
		{
			return replyContext;
		}

	}

}