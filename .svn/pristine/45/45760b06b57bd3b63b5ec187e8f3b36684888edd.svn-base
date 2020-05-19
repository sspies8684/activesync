package de.sloc.sspies.activesync.context;

import java.util.Map;

import org.apache.xmlbeans.impl.util.Base64;

import de.sloc.sspies.Utility;
import de.sloc.sspies.activesync.Constants;
import de.sloc.sspies.activesync.MicrosoftLanguageId;

public abstract class RequestContext extends Context
{
	public String protocolVersion; // 14.1, 14.0, 12.1

	public String command;

	public MicrosoftLanguageId languageId;

	public String deviceId;

	public String policyKey = null; // optional

	public String deviceType;

	// command parameters, all optional
	public String attachmentName = null;
	public String collectionId = null;
	public String itemId = null;
	public String longId = null;
	public String occurence = null;
	public Boolean saveInSent = null;
	public Boolean acceptMultiPart = null;
	public String user = null;
	// end of command parameters

	public String authUser;
	public String password;
	public String contentType = null; // should be null, when no body exists
	public String userAgent = null; // optional


	// abstract methods
	public abstract Map<String, String> toHeaders();

	public abstract String toQueryString();

	public abstract Parser getParser();

	// parses

	public interface Parser extends Constants
	{
		public RequestContext parse(Map<String, String> headers, String queryString);
	}

	public void setProtocolVersion(String protocolVersion)
	{
		this.protocolVersion = protocolVersion;
	}

	public void setCommand(String command)
	{
		this.command = command;
	}

	public void setLanguageId(MicrosoftLanguageId languageId)
	{
		this.languageId = languageId;
	}

	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

	public void setPolicyKey(String policyKey)
	{
		this.policyKey = policyKey;
	}

	public void setDeviceType(String deviceType)
	{
		this.deviceType = deviceType;
	}

	public void setAttachmentName(String attachmentName)
	{
		this.attachmentName = attachmentName;
	}

	public void setCollectionId(String collectionId)
	{
		this.collectionId = collectionId;
	}

	public void setItemId(String itemId)
	{
		this.itemId = itemId;
	}

	public void setLongId(String longId)
	{
		this.longId = longId;
	}

	public void setOccurence(String occurence)
	{
		this.occurence = occurence;
	}

	public void setSaveInSent(Boolean saveInSent)
	{
		this.saveInSent = saveInSent;
	}

	public void setAcceptMultiPart(Boolean acceptMultiPart)
	{
		this.acceptMultiPart = acceptMultiPart;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}

	public void setAuthUser(String authUser)
	{
		this.authUser = authUser;
	}




	public static String encodeBasicAuthParameters(String username, String password)
	{
		String both = username + ":" + password;
		String base64Auth = new String(Base64.encode(both.getBytes()));
		return HTTP_HEADER_AUTHORIZATION_BASIC_PREFIX + base64Auth;
	}

	public static String[] decodeBasicAuthParameters(String base64String)
	{
		String decodedString = new String(Base64.decode(base64String.replaceFirst(HTTP_HEADER_AUTHORIZATION_BASIC_PREFIX, "").getBytes()));
		String[] result = decodedString.split(":");

		if (result.length != 2)
		{
			throw new IllegalArgumentException(HTTP_HEADER_AUTHORIZATION + " is not well-formed: " + base64String);
		}

		return result;
	}



	public String getProtocolVersion()
	{
		return protocolVersion;
	}

	public String getCommand()
	{
		return command;
	}

	public MicrosoftLanguageId getLanguageId()
	{
		return languageId;
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public String getPolicyKey()
	{
		return policyKey;
	}

	public String getDeviceType()
	{
		return deviceType;
	}

	public String getAttachmentName()
	{
		return attachmentName;
	}

	public String getCollectionId()
	{
		return collectionId;
	}

	public String getItemId()
	{
		return itemId;
	}

	public String getLongId()
	{
		return longId;
	}

	public String getOccurence()
	{
		return occurence;
	}

	public Boolean getSaveInSent()
	{
		return saveInSent;
	}

	public Boolean getAcceptMultiPart()
	{
		return acceptMultiPart;
	}

	public String getUser()
	{
		return user;
	}

	public String getAuthUser()
	{
		return authUser;
	}

	public String getPassword()
	{
		return password;
	}

	public String getContentType()
	{
		return contentType;
	}

	public String getUserAgent()
	{
		return userAgent;
	}



	public static RequestContext parse(String queryString, Map<String, String> headers)
	{
		RequestContext result = null;
		Parser parser = null;
		if (queryString.contains("&"))
		{
			// plain
			parser = new PlainContext().getParser();
		}
		else
		{
			// base64
			parser = new Base64Context().getParser();
		}
		result = parser.parse(headers, queryString);

		return result;
	}



	@Override
	public String toString()
	{
		return Utility.genericToString(this);
	}
}
