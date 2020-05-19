package de.sloc.sspies.activesync.web;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.InvalidParameterException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import org.apache.xmlbeans.XmlObject;

import de.sloc.sspies.activesync.backend.FolderHistory;
import de.sloc.sspies.activesync.backend.Providers;
import de.sloc.sspies.activesync.backend.SettingsProvider;
import de.sloc.sspies.activesync.backend.User;
import de.sloc.sspies.activesync.backend.User.Device;
import de.sloc.sspies.activesync.context.RequestContext;
import de.sloc.sspies.activesync.types.EmptyTag;
import de.sloc.sspies.activesync.types.FolderSyncRequestDocument;
import de.sloc.sspies.activesync.types.FolderSyncRequestDocument.FolderSyncRequest;
import de.sloc.sspies.activesync.types.FolderSyncResponseDocument;
import de.sloc.sspies.activesync.types.FolderSyncResponseDocument.FolderSyncResponse;
import de.sloc.sspies.activesync.types.PingRequestDocument;
import de.sloc.sspies.activesync.types.PingRequestDocument.PingRequest;
import de.sloc.sspies.activesync.types.PingResponseDocument;
import de.sloc.sspies.activesync.types.PingResponseDocument.PingResponse;
import de.sloc.sspies.activesync.types.SettingsRequestDocument;
import de.sloc.sspies.activesync.types.SettingsRequestDocument.SettingsRequest;
import de.sloc.sspies.activesync.types.SettingsResponseDocument;
import de.sloc.sspies.activesync.types.SettingsResponseDocument.SettingsResponse;
import de.sloc.sspies.activesync.types.SettingsResponseDocument.SettingsResponse.UserInformation;
import de.sloc.sspies.activesync.types.SyncRequestDocument;
import de.sloc.sspies.activesync.types.SyncRequestDocument.SyncRequest;
import de.sloc.sspies.activesync.types.SyncRequestDocument.SyncRequest.Collections;
import de.sloc.sspies.activesync.types.SyncRequestDocument.SyncRequest.Collections.Collection;
import de.sloc.sspies.activesync.types.SyncResponseDocument;
import de.sloc.sspies.activesync.types.SyncResponseDocument.SyncResponse;
import de.sloc.sspies.activesync.wbxml.WbXmlParser;
import de.sloc.sspies.activesync.wbxml.WbXmlSerializer;
import de.sloc.sspies.activesync.wbxml.WbXmlSerializerImpl;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/Microsoft-Server-ActiveSync")
public class MainServlet extends AbstractServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet()
	{
		super();

	}

	private User authenticate(RequestContext asContext)
	{
		if (!asContext.getAuthUser().equals(asContext.getUser()))
		{
			return null;
		}

		return User.authenticate(asContext.getAuthUser(), asContext.getUser());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{

			RequestContext requestContext = RequestContext.parse(request.getQueryString(), createHeaderMapFromRequest(request));

			if (request.getContentType().equals(CONTENT_TYPE_MS_WBXML))
			{
				/* XmlObject xmlObject = */parseXmlDocument(new WbXmlParser(request.getInputStream(), COMMAND_TYPE_REQUEST),
															getXmlObjectFactoryForCommand(requestContext.getCommand(), COMMAND_TYPE_REQUEST));

			}
		}
		catch (InvalidParameterException e)
		{
			response.setStatus(HTTP_CODE_BAD_REQUEST);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			// EXTRACT PARAMETERS
			RequestContext requestContext = RequestContext.parse(request.getQueryString(), createHeaderMapFromRequest(request));

			// AUTHENTICATION
			User authenticatedUser = authenticate(requestContext);
			if (authenticatedUser == null)
			{
				response.setStatus(HTTP_CODE_UNAUTHORIZED);
				return;
			}

			// RESOLVE DEVICE
			Device device = authenticatedUser.resolveDevice(requestContext.getDeviceId());
			if (device == null)
			{
				device = authenticatedUser.createDevice(requestContext.getDeviceId(), requestContext.getDeviceType(), "0");
			}

			log("POST command " + requestContext.getCommand() + " device: " + device + " user: " + authenticatedUser);

			// CHECK CONTENT TYPE
			if (request.getContentType().equals(CONTENT_TYPE_MS_WBXML))
			{
				// PARSE WBXML DOCUMENT
				XmlObject requestDocument = parseXmlDocument(	new WbXmlParser(request.getInputStream(), COMMAND_TYPE_REQUEST),
																getXmlObjectFactoryForCommand(requestContext.getCommand(), COMMAND_TYPE_REQUEST));

				log("request document\n" + requestDocument);

				// SET RESPONSE CONTENT TYPE
				response.setContentType(CONTENT_TYPE_MS_WBXML);

				// EXECUTE COMMAND AND CREATE RESPONSE DOCUMENT
				XmlObject responseDocument = executeCommand(requestContext.getCommand(), authenticatedUser, device, requestDocument,
															requestContext.getProtocolVersion());

				log("reply document\n" + responseDocument);
				WbXmlSerializer serializer = new WbXmlSerializerImpl(responseDocument.newXMLStreamReader(), response.getOutputStream());
				serializer.serialize();
			}
			else if (request.getContentType().equals(CONTENT_TYPE_TEXT_XML))
			{
				// Autodiscover
			}
			else
			{

			}
		}
		catch (UnsupportedOperationException e)
		{
			response.setStatus(HTTP_CODE_NOT_IMPLEMENTED);
		}
		catch (InvalidParameterException e)
		{
			response.setStatus(HTTP_CODE_BAD_REQUEST);
		}
		catch (XMLStreamException e)
		{
			response.setStatus(HTTP_CODE_SERVER_ERROR);

		}
	}

	/**
	 * @see HttpServlet#doOptions(HttpServletRequest, HttpServletResponse)
	 */
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setHeader(HTTP_HEADER_PROTOCOL_COMMANDS, SUPPORTED_COMMANDS);
		response.setHeader(HTTP_HEADER_PROTOCOL_VERSIONS, SUPPORTED_VERSIONS);
	}

	// command handling
	/**
	 * Command dispatcher
	 * 
	 * @param command
	 * @param requestDocument
	 * @return
	 */
	protected XmlObject executeCommand(String command, User user, Device device, XmlObject requestDocument, String version)
																															throws UnsupportedOperationException
	{
		try
		{
			Method commandMethod = getClass().getDeclaredMethod(EXECUTE_PREFIX + command, User.class, Device.class, XmlObject.class, String.class);
			return (XmlObject) commandMethod.invoke(this, user, device, requestDocument, version);
		}
		catch (Exception e)
		{
			log("Error while executing method for command: " + command, e);
			// TODO create meaningful responseDocument
			throw new UnsupportedOperationException("Command error for " + command + ": " + e.getMessage());
		}
	}

	protected XmlObject executeFolderSync(User user, Device device, XmlObject requestDocument, String version)
	{
		FolderSyncRequestDocument folderSyncRequestDocument = (FolderSyncRequestDocument) requestDocument;
		FolderSyncRequest folderSyncRequest = folderSyncRequestDocument.getFolderSyncRequest();

		FolderSyncResponseDocument responseDocument = FolderSyncResponseDocument.Factory.newInstance();
		FolderSyncResponse response = responseDocument.addNewFolderSyncResponse();

		FolderHistory folderHistory = device.syncFolderHierarchy(folderSyncRequest.getSyncKey());

		// set status
		if (folderHistory != null)
		{
			response.setStatus(STATUS_CODE_FOLDER_SYNC_SUCCESS);

			if (!folderHistory.isEmpty())
			{
				response.setChanges(folderHistory.toChangesElement());
			}

			// set new sync key
			response.setSyncKey(device.getFolderHierarchySyncKey());
		}
		else
		{
			/*
			 * TODO STATUS_CODE_SERVER_ERROR,
			 * STATUS_CODE_INCORRECTLY_FORMATTED_REQUEST,
			 * STATUS_CODE_UNKNOWN_ERROR, STATUS_CODE_CODE_UNKNOWN
			 */

			response.setStatus(STATUS_CODE_FOLDER_SYNC_SYNC_KEY_MISMATCH);
		}

		return responseDocument;
	}

	protected XmlObject executeSettings(User user, Device device, XmlObject requestDocument, String version)
	{
		SettingsRequestDocument settingsRequestDocument = (SettingsRequestDocument) requestDocument;
		SettingsRequest settingsRequest = settingsRequestDocument.getSettingsRequest();

		SettingsResponseDocument settingsResponseDocument = SettingsResponseDocument.Factory.newInstance();
		SettingsResponse settingsResponse = settingsResponseDocument.addNewSettingsResponse();

		if (settingsRequest.isSetUserInformation() && settingsRequest.getUserInformation().getGet() instanceof EmptyTag)
		{
			UserInformation userInformationBean = settingsResponse.addNewUserInformation();
			userInformationBean.setGet(user.getUserInformation().toGetElement(version));

			// TODO STATUS_CODE_PROTOCOL_ERROR
			userInformationBean.setStatus(STATUS_CODE_SETTINGS_SUCCESS);
		}

		/*
		 * TODO: STATUS_CODE_PROTOCOL_ERROR, STATUS_CODE_ACCESS_DENIED,
		 * STATUS_CODE_SERVER_UNAVAILABLE, STATUS_CODE_INVALID_ARGUMENTS,
		 * STATUS_CODE_CONFLICTING_ARGUMENTS, STATUS_CODE_DENIED_BY_POLICY
		 */
		settingsResponse.setStatus(STATUS_CODE_SETTINGS_SUCCESS);
		return settingsResponseDocument;
	}

	protected XmlObject executePing(User user, Device device, XmlObject requestDocument, String version)
	{
		SettingsProvider settingsProvider = Providers.getSettingsProvider();
		PingRequestDocument pingRequestDocument = (PingRequestDocument) requestDocument;
		PingRequest pingRequest = pingRequestDocument.getPingRequest();
		PingResponseDocument pingResponseDocument = PingResponseDocument.Factory.newInstance();
		PingResponse pingResponse = pingResponseDocument.addNewPingResponse();

		// INTERVALS
		BigInteger heartbeatInterval = pingRequest.isSetHeartbeatInterval() ? pingRequest.getHeartbeatInterval()
				: BigInteger.valueOf(device.getInterval());

		// no interval set or previously saved
		if (heartbeatInterval == null)
		{
			pingResponse.setStatus(STATUS_CODE_SETTINGS_REQUIRED_PARAMETERS_OMITTED);
			return pingResponseDocument;
		}

		// check interval value
		int minInterval = settingsProvider.getMinHeartbeatInterval();
		int maxInterval = settingsProvider.getMaxHeartbeatInterval();
		boolean hbBelowLimit = BigInteger.valueOf(minInterval).compareTo(heartbeatInterval) > 0;
		boolean hbAboveLimit = BigInteger.valueOf(maxInterval).compareTo(heartbeatInterval) < 0;
		if (hbBelowLimit || hbAboveLimit)
		{
			pingResponse.setHeartbeatInterval(BigInteger.valueOf(hbBelowLimit ? minInterval : maxInterval));
			pingResponse.setStatus(STATUS_CODE_SETTINGS_HEARTBEAT_OUTSIDE_ALLOWED_INTERVAL);
			return pingResponseDocument;
		}

		// interval value ok, save to device
		// int value is okay, as we can only save int values for min and max
		int heartbeatIntervalInt = heartbeatInterval.intValue();
		device.setInterval(heartbeatIntervalInt);

		// MONITORED FOLDERS

		// if(heartbeatInterval.compareTo(BigInteger.valueOf(sett)))

		pingResponse.setStatus(STATUS_CODE_SETTINGS_SUCCESS);
		return pingResponseDocument;
	}

	protected XmlObject executeSync(User user, Device device, XmlObject requestDocument, String version)
	{
		SyncRequestDocument syncRequestDocument = (SyncRequestDocument) requestDocument;
		SyncRequest syncRequest = syncRequestDocument.getSyncRequest();
		Collections requestCollections = syncRequest.getCollections();

		SyncResponseDocument syncResponseDocument = SyncResponseDocument.Factory.newInstance();
		SyncResponse syncResponse = syncResponseDocument.addNewSyncResponse();
		de.sloc.sspies.activesync.types.SyncResponseDocument.SyncResponse.Collections responseCollections = syncResponse.addNewCollections();
		short statusCode = STATUS_CODE_SETTINGS_SUCCESS;

		for (Collection requestCollection : requestCollections.getCollectionArray())
		{
			// check if collectionId is existent
			String requestCollectionId = requestCollection.getCollectionId();
			try
			{
				device.findFolder(requestCollectionId);
			}
			catch (IllegalArgumentException e)
			{
				de.sloc.sspies.activesync.types.SyncResponseDocument.SyncResponse.Collections.Collection responseCollection = responseCollections.addNewCollection();
				responseCollection.addCollectionId(requestCollectionId);
				responseCollection.addStatus(STATUS_CODE_SYNC_FOLDER_HIERARCHY_HAS_CHANGED);
				continue;
			}

			// collectionId is existent and requestFolder is not null

			de.sloc.sspies.activesync.types.SyncResponseDocument.SyncResponse.Collections.Collection responseCollection = responseCollections.addNewCollection();

			short collectionStatusCode = device.synchronize(requestCollection, responseCollection);
			responseCollection.addStatus(collectionStatusCode);
		}

		syncResponse.setStatus(statusCode);

		return syncResponseDocument;
	}

	// protected XmlObject executeMoveItems(User user, Device device, XmlObject
	// requestDocument, String version)
	// {
	// MoveItemsRequestDocument moveItemsRequestDocument =
	// (MoveItemsRequestDocument) requestDocument;
	// MoveItemsRequest moveItemsRequest =
	// moveItemsRequestDocument.getMoveItemsRequest();
	//
	//
	//
	//
	// }

}
