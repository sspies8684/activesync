package de.sloc.sspies.activesync.context;

import de.sloc.sspies.activesync.Constants;

public abstract class Preset implements Constants
{
	public abstract void initialize(RequestContext requestContext);

	public static Preset WBXML_DEFAULT(final String user, final String password, final String protocolVersion, final String deviceId,
										final String deviceType)
	{
		return new Preset()
		{
			@Override
			public void initialize(RequestContext requestContext)
			{
				requestContext.setContentType(CONTENT_TYPE_MS_WBXML);
				requestContext.setProtocolVersion(protocolVersion);
				requestContext.setUser(user);
				requestContext.setAuthUser(user);
				requestContext.setPassword(password);
				requestContext.setDeviceId(deviceId);
				requestContext.setDeviceType(deviceType);
			}
		};
	}

	public static Preset POLICY_KEY(final String policyKey)
	{
		return new Preset()
		{
			@Override
			public void initialize(RequestContext requestContext)
			{
				requestContext.setPolicyKey(policyKey);
			}
		};
	}

	public static Preset USER_AGENT(final String userAgent)
	{
		return new Preset()
		{
			@Override
			public void initialize(RequestContext requestContext)
			{
				requestContext.setUserAgent(userAgent);
			}
		};
	}

}
