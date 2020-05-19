package de.sloc.sspies.activesync.backend.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.sloc.sspies.activesync.backend.SettingsProvider;
import de.sloc.sspies.activesync.backend.UserInformation;

public class DefaultSettingsProvider implements SettingsProvider
{

	@Override
	public UserInformation getUserInformation(String user)
	{
		return new UserInformation()
		{

			@Override
			public List<String> getSmtpAddresses()
			{
				List<String> result = new ArrayList<String>();
				result.add("a@b.de");
				result.add("d@e.de");
				return result;
			}

			@Override
			public List<Account> getAccounts()
			{
				Account account = new Account()
				{

					@Override
					public boolean isSendDisabled()
					{
						return false;
					}

					@Override
					public String getUserDisplayName()
					{
						return "Sebastian Spies";
					}

					@Override
					public List<String> getSmtpAddresses()
					{
						return Collections.singletonList("sspies@sloc.de");
					}

					@Override
					public String getPrimarySmtpAddress()
					{
						return "sspies@sloc.de";
					}

					@Override
					public String getName()
					{
						return "Main Account";
					}

					@Override
					public String getId()
					{
						return "ssp-01203";
					}
				};
				
				return Collections.singletonList(account);
			}
		};
	}
	
	@Override
	public int getMaxHeartbeatInterval()
	{
		return 3600;
	}
	
	@Override
	public int getMinHeartbeatInterval()
	{
		return 0;
	}

}
