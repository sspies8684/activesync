package de.sloc.sspies.activesync.client;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;

import de.sloc.sspies.Utility;
import de.sloc.sspies.activesync.MicrosoftLanguageId;
import de.sloc.sspies.activesync.client.Endpoint.EndpointReply;
import de.sloc.sspies.activesync.context.RequestContext;
import de.sloc.sspies.activesync.context.RequestContextFactory;
import de.sloc.sspies.activesync.types.AutodiscoverDocument;
import de.sloc.sspies.activesync.types.AutodiscoverDocument2;
import de.sloc.sspies.activesync.types.AutodiscoverDocument2.Autodiscover.Response;
import de.sloc.sspies.activesync.types.AutodiscoverDocument2.Autodiscover.Response.Action.Settings.Server;
import de.sloc.sspies.activesync.types.AutodiscoverDocument2.Autodiscover.Response.User;
import de.sloc.sspies.activesync.types.RequestType;

public class Autodiscover implements ServerInfo
{
	protected URL activeSyncUrl;
	protected String displayName;
	protected String emailAddress;
	protected MicrosoftLanguageId culture;
	protected String serverUrl;
	protected String serverName;
	protected String serverType;

	protected Autodiscover(URL activeSyncUrl, String displayName, String emailAddress, MicrosoftLanguageId culture, String serverUrl,
							String serverName, String serverType)
	{
		super();
		this.activeSyncUrl = activeSyncUrl;
		this.displayName = displayName;
		this.emailAddress = emailAddress;
		this.culture = culture;
		this.serverUrl = serverUrl;
		this.serverName = serverName;
		this.serverType = serverType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sloc.sspies.activesync.client.IAutodiscover#getActiveSyncUrl()
	 */
	@Override
	public URL getActiveSyncUrl()
	{
		return activeSyncUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sloc.sspies.activesync.client.IAutodiscover#getDisplayName()
	 */
	@Override
	public String getDisplayName()
	{
		return displayName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sloc.sspies.activesync.client.IAutodiscover#getEmailAddress()
	 */
	@Override
	public String getEmailAddress()
	{
		return emailAddress;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sloc.sspies.activesync.client.IAutodiscover#getCulture()
	 */
	@Override
	public MicrosoftLanguageId getCulture()
	{
		return culture;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sloc.sspies.activesync.client.IAutodiscover#getServerUrl()
	 */
	@Override
	public String getServerUrl()
	{
		return serverUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sloc.sspies.activesync.client.IAutodiscover#getServerName()
	 */
	@Override
	public String getServerName()
	{
		return serverName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sloc.sspies.activesync.client.IAutodiscover#getServerType()
	 */
	@Override
	public String getServerType()
	{
		return serverType;
	}

	public static ServerInfo discover(String eMailAddress, String password)
	{

		String[] autodiscoverHostnames = deriveAutodiscoverHostnames(eMailAddress);
		AutodiscoverDocument requestDocument = createAutodiscoverRequestDocument(eMailAddress);
		RequestContext context = RequestContextFactory.newInstance(null).createAutodiscoverContext(eMailAddress, password);

		for (String autodiscoverHostname : autodiscoverHostnames)
		{
			URL autodiscoverURL = deriveAutodiscoverURL(autodiscoverHostname);
			Endpoint endpoint = new EndpointImpl(autodiscoverURL);

			try
			{
				EndpointReply reply = endpoint.issuePOST(context, new BufferedInputStream(requestDocument.newInputStream()));
				
				if (!reply.getReplyContext().getContentType().equalsIgnoreCase(CONTENT_TYPE_TEXT_XML))
				{
					continue;
				}
				
				InputStream replyStream = reply.getBodyStream();

				// repair namespace
				String replyString = Utility.inputStreamToString(replyStream);
				replyString = replyString.replaceFirst(AUTODISCOVER_GENERIC_RESPONSE_NAMESPACE, AUTODISCOVER_RESPONSE_NAMESPACE);
				// end of repair namespace

				// parse document body
				AutodiscoverDocument2 replyDocument = AutodiscoverDocument2.Factory.parse(replyString);
				Response response = replyDocument.getAutodiscover().getResponse();
				User user = response.getUser();
				Server[] servers = response.getAction().getSettings().getServerArray();
				for (Server server : servers)
				{
					if (!server.getType().equals(AUTODISCOVER_SERVER_TYPE_MOBILE_SYNC))
					{
						continue;
					}

					return new Autodiscover(new URL(server.getUrl() + AUTODISCOVER_SERVER_URL_PATH), user.getDisplayName(), user.getEMailAddress(),
											resolveLanguageId(response.getCulture()), server.getUrl(), server.getName(), server.getType());
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
				continue;
			}
		}

		return null;
	}

	protected static MicrosoftLanguageId resolveLanguageId(String culture)
	{
		return MicrosoftLanguageId.byMeaning(culture.replace(":", "-"));
	}

	protected static AutodiscoverDocument createAutodiscoverRequestDocument(String eMailAddress)
	{
		AutodiscoverDocument requestDocument = de.sloc.sspies.activesync.types.AutodiscoverDocument.Factory.newInstance();

		de.sloc.sspies.activesync.types.AutodiscoverDocument.Autodiscover autodiscover = requestDocument.addNewAutodiscover();
		RequestType request = autodiscover.addNewRequest();
		request.setEMailAddress(eMailAddress);
		request.setAcceptableResponseSchema(AUTODISCOVER_RESPONSE_NAMESPACE);

		return requestDocument;
	}

	protected static String[] deriveAutodiscoverHostnames(String eMailAddress)
	{
		Matcher matcher = EMAIL_PATTERN.matcher(eMailAddress);

		if (!matcher.find())
		{
			throw new IllegalArgumentException("Invalid E-Mail address: " + eMailAddress);
		}

		String[] autodiscoverHostnames = new String[] { matcher.group(2), AUTODISCOVER_HOSTNAME_PREFIX + matcher.group(2) };

		return autodiscoverHostnames;
	}

	protected static URL deriveAutodiscoverURL(String host)
	{
		try
		{
			return new URL(PROTOCOL_HTTPS, host, AUTODISCOVER_FILEPATH);
		}
		catch (MalformedURLException e)
		{
			throw new InternalError(e.getMessage());
		}
	}

	@Override
	public String toString()
	{
		return Utility.genericToString(this);
	}

	public static void main(String[] args)
	{
		System.out.println(discover("giveme223@hotmail.com", "0t7rndn"));
	}

}
