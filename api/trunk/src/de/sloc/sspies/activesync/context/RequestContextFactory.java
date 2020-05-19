package de.sloc.sspies.activesync.context;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import de.sloc.sspies.Utility;

public class RequestContextFactory
{
	protected EncodingType encodingType;
	protected Class<? extends RequestContext> instantiationClass;

	protected RequestContextFactory(EncodingType encodingType)
	{
		this.encodingType = encodingType;
		this.instantiationClass = resolveInstantiationClass(encodingType);
	}

	@SuppressWarnings("unchecked")
	protected Class<? extends RequestContext> resolveInstantiationClass(EncodingType encodingType)
	{
		for (Class<?> contextClass : Utility.getClassessOfInterface(getClass().getPackage().getName(), RequestContext.class))
		{
			EncodingAnnotation annotation = contextClass.getAnnotation(EncodingAnnotation.class);
			if (annotation != null && annotation.value() == encodingType)
			{
				return (Class<? extends RequestContext>) contextClass;
			}
		}

		if (encodingType == null)
		{
			return null;
		}
		else
			// backup
			switch (encodingType)
			{
				case BASE64:
					return Base64Context.class;
				case PLAIN:
					return PlainContext.class;
				default:
					throw new IllegalStateException("No class implements encoding type: " + encodingType);
			}
	}

	public static RequestContextFactory newInstance(EncodingType encodingType)
	{
		return new RequestContextFactory(encodingType);
	}

	public RequestContext createRequestContext(Preset... presets) throws InstantiationException
	{
		RequestContext requestContext;
		try
		{
			requestContext = instantiationClass.newInstance();

			// apply all presets
			for (Preset preset : presets)
			{
				preset.initialize(requestContext);
			}

			return requestContext;
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
			throw new InstantiationException(e.getMessage());
		}
	}

	public RequestContext createAutodiscoverContext(String username, String password)
	{
		return new AutodiscoverContext(username, password);
	}

	public RequestContext createOptionsContext(String username, String password)
	{
		return new SimpleAuthContext(username, password);
	}

	@Retention(RetentionPolicy.RUNTIME)
	public @interface EncodingAnnotation
	{
		EncodingType value();
	}

	public enum EncodingType
	{
		PLAIN, BASE64; // , AUTODISCOVER;
	}
}
