package de.sloc.sspies.activesync.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.sloc.sspies.Utility;
import de.sloc.sspies.activesync.Constants;

public abstract class Context implements Constants
{
	public Map<String, String> additionalParameters = new HashMap<String, String>();
		
	public Map<String, String> getAdditionalParameters()
	{
		return additionalParameters;
	}
	
	public void addAdditionalParameter(String key, String value)
	{
		this.additionalParameters.put(key, value);
	}
	
	public static boolean containsParameters(String parameterName, String... parameters)
	{
		for (String aParameter : parameters)
		{
			if (aParameter.equalsIgnoreCase(parameterName))
			{
				return true;
			}
		}
		return false;
	}
	
	public static void addProtectedParameters(Map<String, String> target, Map<String, String> parameters, String...protectedParameters)
	{
		for (Entry<String, String> header : parameters.entrySet())
		{
			String key = header.getKey();
			String value = header.getValue();

			if (!containsParameters(key, protectedParameters))
			{
				target.put(key, value);
			}
		}
	}
	
	public static String resolveHeader(Map<String, String> headers, String key)
	{
		String result = null;

		result = headers.get(key);

		if (result == null)
			result = headers.get(key.toLowerCase());

		return result;
	}

	@Override
	public String toString()
	{
		return Utility.genericToString(this);
	}
}
