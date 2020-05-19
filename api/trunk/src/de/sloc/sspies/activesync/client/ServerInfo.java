package de.sloc.sspies.activesync.client;

import java.net.URL;

import de.sloc.sspies.activesync.Constants;
import de.sloc.sspies.activesync.MicrosoftLanguageId;

public interface ServerInfo extends Constants
{

	public abstract URL getActiveSyncUrl();

	public abstract String getDisplayName();

	public abstract String getEmailAddress();

	public abstract MicrosoftLanguageId getCulture();

	public abstract String getServerUrl();

	public abstract String getServerName();

	public abstract String getServerType();

}