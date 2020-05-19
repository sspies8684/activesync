package de.sloc.sspies.activesync.backend;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import de.sloc.sspies.AntProperties;
import de.sloc.sspies.activesync.Constants;

public class Providers implements ServletContextListener, Constants
{

	/*
	 * Backend objects
	 */
	protected static AuthenticationProvider authenticationProvider;
	protected static SettingsProvider settingsProvider;

	protected static MailProvider mailProvider;

	protected static Store store;
	
	// 
	public static CollectionProvider tasksProvider;
	public static CollectionProvider calendarProvider;
	public static CollectionProvider contactsProvider;
	public static CollectionProvider journalProvider;
	public static CollectionProvider notesProvider;
	public static CollectionProvider recipientInformationCacheProvider;

	private static Object resolveProvider(AntProperties properties, String propertyName) throws ClassNotFoundException, InstantiationException,
																						IllegalAccessException
	{
		String providerClassName = properties.getProperty(propertyName);
		Class<?> providerClass = Class.forName(providerClassName);
		return providerClass.newInstance();
	}

	
	public static AuthenticationProvider getAuthenticationProvider()
	{
		return authenticationProvider;
	}
	
	public static SettingsProvider getSettingsProvider()
	{
		return settingsProvider;
	}
	
	public static MailProvider getMailProvider()
	{
		return mailProvider;
	}
	
	public static CollectionProvider getContactsProvider()
	{
		return contactsProvider;
	}
	
	public static Store getStore()
	{
		return store;
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		// intentionally void
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		try
		{
			AntProperties antProperties = new AntProperties();
			antProperties.load(new File(servletContextEvent.getServletContext().getRealPath(BACKEND_PROPERTY_FILE)));
			authenticationProvider = (AuthenticationProvider) resolveProvider(antProperties, PROPERTY_AUTHENTICATION_PROVIDER);
			mailProvider = (MailProvider) resolveProvider(antProperties, PROPERTY_MAIL_PROVIDER);
			contactsProvider = (CollectionProvider) resolveProvider(antProperties, PROPERTY_CONTACTS_PROVIDER);
			settingsProvider = (SettingsProvider) resolveProvider(antProperties, PROPERTY_SETTINGS_PROVIDER);
			store = (Store) resolveProvider(antProperties, PROPERTY_STORE);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("FATAL: " + e.getMessage());
		}
	}
}
