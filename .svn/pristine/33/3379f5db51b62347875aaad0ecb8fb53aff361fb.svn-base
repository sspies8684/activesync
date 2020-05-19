package de.sloc.sspies.activesync.backend;

import java.security.InvalidParameterException;
import java.util.List;

import de.sloc.sspies.activesync.Constants;
import de.sloc.sspies.activesync.types.EmailAddressesDocument.EmailAddresses;
import de.sloc.sspies.activesync.types.SettingsResponseDocument.SettingsResponse.UserInformation.Get;
import de.sloc.sspies.activesync.types.SettingsResponseDocument.SettingsResponse.UserInformation.Get.Accounts;

public abstract class UserInformation implements Constants
{
	/**
	 * >= 14.1
	 */
	public abstract List<Account> getAccounts();

	public static interface Account
	{
		public String getId();

		public String getName();

		public String getUserDisplayName();

		public boolean isSendDisabled();

		public String getPrimarySmtpAddress();

		public List<String> getSmtpAddresses();
	}

	/**
	 * < 14.1
	 */
	public abstract List<String> getSmtpAddresses();

	/**
	 * Export to version dependent Get element
	 * 
	 * @param version
	 * @return
	 */
	public Get toGetElement(String version)
	{
		Get getElement = Get.Factory.newInstance();

		if (version.equals(VERSION_14_1))
		{
			List<Account> accounts = getAccounts();
			if (!accounts.isEmpty())
			{
				Accounts accountsElement = getElement.addNewAccounts();

				for (Account account : getAccounts())
				{
					de.sloc.sspies.activesync.types.SettingsResponseDocument.SettingsResponse.UserInformation.Get.Accounts.Account accountElement = accountsElement.addNewAccount();
					accountElement.setAccountId(account.getId());
					accountElement.setAccountName(account.getName());
					accountElement.setSendDisabled(account.isSendDisabled() ? MS_TRUE : MS_FALSE);
					accountElement.setUserDisplayName(account.getUserDisplayName());

					EmailAddresses emailAddresses = accountElement.addNewEmailAddresses();

					emailAddresses.setPrimarySmtpAddress(account.getPrimarySmtpAddress());
					for (String smtpAddress : account.getSmtpAddresses())
					{
						emailAddresses.addSMTPAddress(smtpAddress);
					}
				}
			}
		}
		else if (version.equals(VERSION_14_0) || version.equals(VERSION_12_1))
		{
			List<String> smtpAddresses = getSmtpAddresses();

			if (!smtpAddresses.isEmpty())
			{
				EmailAddresses emailAddressesElement = getElement.addNewEmailAddresses();
				
				for (String smtpAddress : smtpAddresses)
				{
					emailAddressesElement.addSMTPAddress(smtpAddress);
				}
			}
		}
		else
		{
			throw new InvalidParameterException("Unsupported version: " + version);
		}

		return getElement;
	}
}