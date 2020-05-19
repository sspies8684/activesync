package de.sloc.sspies.activesync.backend.mock;

import de.sloc.sspies.activesync.backend.AuthenticationProvider;

public class AllowAllAuthenticationProvider implements AuthenticationProvider
{

	@Override
	public boolean authenticate(String userName, String password)
	{
		return true;
	}

	
	

}
