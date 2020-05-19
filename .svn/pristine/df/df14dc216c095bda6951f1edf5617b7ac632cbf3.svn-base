package de.sloc.sspies.activesync.web;

import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLStreamReader;

import org.apache.xmlbeans.XmlObject;

import de.sloc.sspies.activesync.Constants;
import de.sloc.sspies.activesync.types.FolderCreateRequestDocument;
import de.sloc.sspies.activesync.types.FolderCreateResponseDocument;
import de.sloc.sspies.activesync.types.FolderDeleteRequestDocument;
import de.sloc.sspies.activesync.types.FolderDeleteResponseDocument;
import de.sloc.sspies.activesync.types.FolderSyncRequestDocument;
import de.sloc.sspies.activesync.types.FolderSyncResponseDocument;
import de.sloc.sspies.activesync.types.FolderUpdateRequestDocument;
import de.sloc.sspies.activesync.types.FolderUpdateResponseDocument;
import de.sloc.sspies.activesync.types.GetItemEstimateRequestDocument;
import de.sloc.sspies.activesync.types.GetItemEstimateResponseDocument;
import de.sloc.sspies.activesync.types.ItemOperationsRequestDocument;
import de.sloc.sspies.activesync.types.ItemOperationsResponseDocument;
import de.sloc.sspies.activesync.types.MeetingResponseRequestDocument;
import de.sloc.sspies.activesync.types.MeetingResponseResponseDocument;
import de.sloc.sspies.activesync.types.MoveItemsRequestDocument;
import de.sloc.sspies.activesync.types.MoveItemsResponseDocument;
import de.sloc.sspies.activesync.types.PingRequestDocument;
import de.sloc.sspies.activesync.types.PingResponseDocument;
import de.sloc.sspies.activesync.types.ProvisionRequestDocument;
import de.sloc.sspies.activesync.types.ProvisionResponseDocument;
import de.sloc.sspies.activesync.types.ResolveRecipientsRequestDocument;
import de.sloc.sspies.activesync.types.ResolveRecipientsResponseDocument;
import de.sloc.sspies.activesync.types.SearchRequestDocument;
import de.sloc.sspies.activesync.types.SearchResponseDocument;
import de.sloc.sspies.activesync.types.SendMailRequestDocument;
import de.sloc.sspies.activesync.types.SendMailResponseDocument;
import de.sloc.sspies.activesync.types.SettingsRequestDocument;
import de.sloc.sspies.activesync.types.SettingsResponseDocument;
import de.sloc.sspies.activesync.types.SmartForwardRequestDocument;
import de.sloc.sspies.activesync.types.SmartForwardResponseDocument;
import de.sloc.sspies.activesync.types.SmartReplyRequestDocument;
import de.sloc.sspies.activesync.types.SmartReplyResponseDocument;
import de.sloc.sspies.activesync.types.SyncRequestDocument;
import de.sloc.sspies.activesync.types.SyncResponseDocument;
import de.sloc.sspies.activesync.types.ValidateCertRequestDocument;
import de.sloc.sspies.activesync.types.ValidateCertResponseDocument;
import de.sloc.sspies.activesync.wbxml.codepages.CodepageLoader;

public abstract class AbstractServlet extends HttpServlet implements Constants
{
	private static final long serialVersionUID = 1L;

	protected static Map<String, Class<? extends XmlObject>> COMMANDS_TO_REQUEST_XMLOBJECTS = new HashMap<String, Class<? extends XmlObject>>();
	protected static Map<String, Class<? extends XmlObject>> COMMANDS_TO_RESPONSE_XMLOBJECTS = new HashMap<String, Class<? extends XmlObject>>();
	
	static
	{
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_FOLDER_CREATE, FolderCreateRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_FOLDER_DELETE, FolderDeleteRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_FOLDER_SYNC, FolderSyncRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_FOLDER_UPDATE, FolderUpdateRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_GET_ATTACHMENT, null);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_GET_ITEM_ESTIMATE, GetItemEstimateRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_ITEM_OPERATIONS, ItemOperationsRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_MEETING_RESPONSE, MeetingResponseRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_MOVE_ITEMS, MoveItemsRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_PING, PingRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_PROVISION, ProvisionRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_RESOLVE_RECIPIENTS, ResolveRecipientsRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_SEARCH, SearchRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_SEND_MAIL, SendMailRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_SETTINGS, SettingsRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_SMART_FORWARD, SmartForwardRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_SMART_REPLY, SmartReplyRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_SYNC, SyncRequestDocument.class);
		COMMANDS_TO_REQUEST_XMLOBJECTS.put(COMMAND_VALIDATE_CERT, ValidateCertRequestDocument.class);

		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_FOLDER_CREATE, FolderCreateResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_FOLDER_DELETE, FolderDeleteResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_FOLDER_SYNC, FolderSyncResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_FOLDER_UPDATE, FolderUpdateResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_GET_ATTACHMENT, null);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_GET_ITEM_ESTIMATE, GetItemEstimateResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_ITEM_OPERATIONS, ItemOperationsResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_MEETING_RESPONSE, MeetingResponseResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_MOVE_ITEMS, MoveItemsResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_PING, PingResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_PROVISION, ProvisionResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_RESOLVE_RECIPIENTS, ResolveRecipientsResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_SEARCH, SearchResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_SEND_MAIL, SendMailResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_SETTINGS, SettingsResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_SMART_FORWARD, SmartForwardResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_SMART_REPLY, SmartReplyResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_SYNC, SyncResponseDocument.class);
		COMMANDS_TO_RESPONSE_XMLOBJECTS.put(COMMAND_VALIDATE_CERT, ValidateCertResponseDocument.class);
		
	}
	public AbstractServlet()
	{
		CodepageLoader.loadCodepages();
	}

	protected static XmlObject parseXmlDocument(XMLStreamReader streamReader, Class<?> factoryClass) throws InvalidParameterException
	{
		try
		{
			Method parseMethod = factoryClass.getMethod(METHOD_NAME_PARSE, XMLStreamReader.class);
			return (XmlObject) parseMethod.invoke(null, streamReader);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new InvalidParameterException(e.getMessage());
		}
	}

	protected static Class<?> getXmlObjectFactoryForCommand(String command, String commandType)
	{
		Class<? extends XmlObject> documentClass = null;

		if (commandType.equals(COMMAND_TYPE_REQUEST))
		{
			if (!COMMANDS_TO_REQUEST_XMLOBJECTS.containsKey(command))
			{
				throw new IllegalArgumentException("Unknown request command: " + command);
			}

			documentClass = COMMANDS_TO_REQUEST_XMLOBJECTS.get(command);
		}
		else if (commandType.equals(COMMAND_TYPE_RESPONSE))
		{
			if (!COMMANDS_TO_RESPONSE_XMLOBJECTS.containsKey(command))
			{
				throw new IllegalArgumentException("Unknown request command: " + command);
			}

			documentClass = COMMANDS_TO_RESPONSE_XMLOBJECTS.get(command);
		}
		else
		{
			throw new IllegalArgumentException("Unknown command type: " + commandType);
		}

		if (documentClass == null)
		{
			return null;
		}

		for (Class<?> klass : documentClass.getClasses())
		{
			if (klass.getSimpleName().equals(CLASS_NAME_FACTORY))
			{
				return klass;
			}
		}

		throw new InternalError("Could not find factory for " + command);
	}
	
	protected Map<String, String> createHeaderMapFromRequest(HttpServletRequest request)
	{
		Map<String, String> result= new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements())
		{
			String headerName = headerNames.nextElement();
			result.put(headerName, request.getHeader(headerName));
		}
		return result;
	}


}
