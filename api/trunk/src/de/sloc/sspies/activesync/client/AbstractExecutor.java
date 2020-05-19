package de.sloc.sspies.activesync.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import javax.xml.stream.XMLStreamReader;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import de.sloc.sspies.activesync.Constants;
import de.sloc.sspies.activesync.client.Endpoint.EndpointReply;
import de.sloc.sspies.activesync.context.Preset;
import de.sloc.sspies.activesync.context.RequestContext;
import de.sloc.sspies.activesync.context.RequestContextFactory;
import de.sloc.sspies.activesync.context.RequestContextFactory.EncodingType;
import de.sloc.sspies.activesync.types.FolderSyncRequestDocument.FolderSyncRequest;
import de.sloc.sspies.activesync.types.FolderSyncResponseDocument.FolderSyncResponse;
import de.sloc.sspies.activesync.wbxml.WbXmlParser;
import de.sloc.sspies.activesync.wbxml.WbXmlSerializer;
import de.sloc.sspies.activesync.wbxml.WbXmlSerializerImpl;
import de.sloc.sspies.activesync.wbxml.codepages.CodepageLoader;

public abstract class AbstractExecutor implements Constants
{
	protected Endpoint endpoint;

	public AbstractExecutor(Endpoint endpoint)
	{
		this.endpoint = endpoint;
	}

	public XmlObject call(RequestContext context, XmlObject request) throws IOException, XmlException
	{
		try
		{
			XmlObject requestDocument = appendChildToDocument(request);

			System.out.println(requestDocument);
			
			// issue post command
			PipedInputStream pin = new PipedInputStream();
			PipedOutputStream pout = new PipedOutputStream(pin);

			WbXmlSerializer serializer = new WbXmlSerializerImpl(requestDocument.newXMLStreamReader(), pout);
			serializer.serialize();
			pout.close();

			EndpointReply reply = endpoint.issuePOST(context, new BufferedInputStream(pin));

			if (Integer.parseInt(reply.getReplyContext().getContentLength()) > 0)
			{
				XmlObject response = parseResponse(request, reply.getBodyStream());
				System.out.println(response);
				return response;
			}
			else
			{
				return null;
			}
		}
		catch (Exception e)
		{
			if (e instanceof IOException)
				throw (IOException) e;
			else if (e instanceof XmlException)
				throw (XmlException) e;

			e.printStackTrace();
			throw new InternalError(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	protected static XmlObject parseResponse(XmlObject request, InputStream inputStream) throws ClassNotFoundException, IllegalAccessException,
																						IllegalArgumentException, InvocationTargetException,
																						NoSuchMethodException, SecurityException
	{
		// parse stream to Response Document object
		Class<? extends XmlObject> responseClass = (Class<? extends XmlObject>) Class.forName(requestToResponseClassName(request.getClass().getName()));
		Class<? extends XmlObject> responseDocumentClass = (Class<? extends XmlObject>) Class.forName(responseClass.getName().replaceAll("\\$.*", ""));

		XmlObject responseDocument = parseByFactory(responseDocumentClass, inputStream);
		return (XmlObject) responseDocumentClass.getMethod(getGetterMethodName(responseClass)).invoke(responseDocument);
	}

	@SuppressWarnings("unchecked")
	protected static XmlObject appendChildToDocument(XmlObject child) throws ClassNotFoundException, IllegalAccessException,
																		IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
																		SecurityException
	{
		Class<? extends XmlObject> requestClass = child.getClass();
		Class<? extends XmlObject> requestInterface = (Class<? extends XmlObject>) requestClass.getInterfaces()[0];
		Class<? extends XmlObject> documentClass = findDocumentClass(requestClass);

		XmlObject document = createByFactory(documentClass);

		document.getClass().getMethod(getSetterMethodName(requestClass), requestInterface).invoke(document, child);

		return document;
	}

	private static String requestToResponseClassName(String className)
	{
		return className.replaceAll(COMMAND_TYPE_REQUEST, COMMAND_TYPE_RESPONSE);
	}

	private static String getSetterMethodName(Class<? extends XmlObject> requestOrResponseClass)
	{
		String name = requestOrResponseClass.getInterfaces()[0].getSimpleName().replaceAll("\\$.*", "");
		return "set" + name;
	}

	private static String getGetterMethodName(Class<? extends XmlObject> requestOrResponseClass)
	{
		String name = requestOrResponseClass.getInterfaces()[0].getSimpleName().replaceAll("\\$.*", "");
		return "get" + name;
	}

	private static XmlObject parseByFactory(Class<? extends XmlObject> documentClass, InputStream inputStream)
	{
		try
		{
			for (Class<?> klass : documentClass.getInterfaces()[0].getClasses())
			{
				if (klass.getSimpleName().equals(CLASS_NAME_FACTORY))
				{
					WbXmlParser parser = new WbXmlParser(inputStream, COMMAND_TYPE_RESPONSE);

					return (XmlObject) klass.getMethod(METHOD_NAME_PARSE, XMLStreamReader.class).invoke(null, parser);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private static XmlObject createByFactory(Class<? extends XmlObject> documentClass)
	{
		try
		{
			for (Class<?> klass : documentClass.getClasses())
			{
				if (klass.getSimpleName().equals(CLASS_NAME_FACTORY))
				{
					return (XmlObject) klass.getMethod(METHOD_NAME_NEWINSTANCE).invoke(null);
				}
			}
		}
		catch (Exception e)
		{

		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static Class<? extends XmlObject> findDocumentClass(Class<? extends XmlObject> requestOrResponseClass) throws ClassNotFoundException
	{
		String interfaceName = requestOrResponseClass.getInterfaces()[0].getName().replaceAll("\\$.*", "");
		return (Class<? extends XmlObject>) Class.forName(interfaceName);
	}

	public static void main(String[] args) throws InstantiationException, IOException, XmlException
	{
		CodepageLoader.loadCodepages();

		RequestContextFactory factory = RequestContextFactory.newInstance(EncodingType.PLAIN);
		RequestContext cxt = factory.createRequestContext(Preset.WBXML_DEFAULT("a", "b", VERSION_14_1, "aaaa", "SmartPhone"));

		Executor e = new Executor(new EndpointImpl(new URL("http://localhost/Microsoft-Server-ActiveSync")));

		FolderSyncRequest request = FolderSyncRequest.Factory.newInstance();
		request.setSyncKey(ZERO_SYNC_KEY);

		FolderSyncResponse response = e.folderSync(cxt, request);

		System.out.println(response);
	}

}
