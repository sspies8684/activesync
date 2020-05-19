package de.sloc.sspies.activesync.backend;

import static de.sloc.sspies.Utility.nextSyncKey;
import static de.sloc.sspies.activesync.backend.Providers.authenticationProvider;
import static de.sloc.sspies.activesync.backend.Providers.contactsProvider;
import static de.sloc.sspies.activesync.backend.Providers.mailProvider;
import static de.sloc.sspies.activesync.backend.Providers.settingsProvider;
import static de.sloc.sspies.activesync.backend.Providers.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.sloc.sspies.Utility;
import de.sloc.sspies.activesync.Constants;
import de.sloc.sspies.activesync.types.ApplicationDataDocument.ApplicationData;
import de.sloc.sspies.activesync.types.SyncRequestDocument.SyncRequest.Collections.Collection.Commands.Fetch;
import de.sloc.sspies.activesync.types.SyncResponseDocument.SyncResponse.Collections.Collection;
import de.sloc.sspies.activesync.types.SyncResponseDocument.SyncResponse.Collections.Collection.Commands;
import de.sloc.sspies.activesync.types.SyncResponseDocument.SyncResponse.Collections.Collection.Commands.Add;
import de.sloc.sspies.activesync.types.SyncResponseDocument.SyncResponse.Collections.Collection.Commands.Change;
import de.sloc.sspies.activesync.types.SyncResponseDocument.SyncResponse.Collections.Collection.Commands.Delete;
import de.sloc.sspies.activesync.types.SyncResponseDocument.SyncResponse.Collections.Collection.Responses;


public class User implements Constants
{

	private String userName;
	private String password;

	public User()
	{

	}

	protected User(String userName, String password)
	{
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName()
	{
		return userName;
	}

	public String getPassword()
	{
		return password;
	}

	public static User authenticate(String userName, String password)
	{
		if (authenticationProvider.authenticate(userName, password))
		{
			return new User(userName, password);
		}

		return null;
	}

	public Device resolveDevice(String deviceId)
	{
		return store.loadDevice(this, deviceId);
	}

	public Device createDevice(String deviceId, String deviceType, String syncKey)
	{
		Device createdDevice = new Device(deviceId, deviceType, syncKey);
		store.saveDevice(this, createdDevice);
		return createdDevice;
	}

	public class Device
	{
		private String deviceId;
		private String deviceType;
		private String folderHierarchySyncKey;
		private Integer interval = null;

		private Map<String, FolderState> folderStates = new HashMap<String, FolderState>();

		// save current folder hierarchy
		private Folder[] rootFolders;
		private Map<String, Folder> serverIdToFolders = new HashMap<String, Folder>();

		public Device(String deviceId, String deviceType, String folderHierarchySyncKey)
		{
			this.deviceId = deviceId;
			this.deviceType = deviceType;
			this.folderHierarchySyncKey = folderHierarchySyncKey;
		}

		public String getDeviceId()
		{
			return deviceId;
		}

		public String getFolderHierarchySyncKey()
		{
			return folderHierarchySyncKey;
		}

		public String getDeviceType()
		{
			return deviceType;
		}

		public void setInterval(Integer interval)
		{
			this.interval = interval;
		}

		public Integer getInterval()
		{
			return interval;
		}

		private Folder[] getFolderHierarchy()
		{
			List<Folder> allFolders = new ArrayList<Folder>();
			Collections.addAll(allFolders, mailProvider.getFolderHierarchy());
			Collections.addAll(allFolders, contactsProvider.getFolderHierarchy());
			return allFolders.toArray(new Folder[0]);
		}

		public FolderHistory syncFolderHierarchy(String requestSyncKey)
		{
			FolderHistory history;

			if (requestSyncKey.equals(ZERO_SYNC_KEY))
			{
				// full sync
				setFolders(getFolderHierarchy());

				history = new FolderHistory();

				for (Folder folder : serverIdToFolders.values())
				{
					history.addFolder(folder);
				}

				this.folderHierarchySyncKey = nextSyncKey();

			}
			else if (this.folderHierarchySyncKey.equals(requestSyncKey))
			{
				// provide deltas
				Folder[] newFolderTree = getFolderHierarchy();

				// generate incremental folder history
				history = computeFolderDeltas(this.rootFolders, newFolderTree);
				setFolders(newFolderTree);

				if (!history.isEmpty())
				{
					this.folderHierarchySyncKey = nextSyncKey();
				}
			}
			else
			{
				// return nothing, because we have no incremental update
				// available
				history = null;
			}

			store.saveDevice(User.this, this);

			return history;
		}

		private void setFolders(Folder[] newFolderTree)
		{
			this.rootFolders = newFolderTree;

			List<Folder> flatFolders = flattenFolderTrees(newFolderTree);
			for (Folder folder : flatFolders)
				serverIdToFolders.put(folder.getServerId(), folder);
		}

		// assume, there is no cycle
		protected List<Folder> flattenFolderTree(Folder currentFolder)
		{
			List<Folder> currentFolders = new LinkedList<Folder>();
			currentFolders.add(currentFolder);

			for (Folder child : currentFolder.getChildren())
			{
				currentFolders.addAll(flattenFolderTree(child));
			}

			return currentFolders;
		}

		protected List<Folder> flattenFolderTrees(Folder[] rootFolders)
		{
			List<Folder> flatFolders = new ArrayList<Folder>();
			for (Folder rootFolder : rootFolders)
			{
				flatFolders.addAll(flattenFolderTree(rootFolder));
			}
			return flatFolders;
		}

		protected FolderHistory computeFolderDeltas(Folder[] oldTrees, Folder[] newTrees)
		{
			FolderHistory result = new FolderHistory();
			List<Folder> flatNewFolders = flattenFolderTrees(newTrees);
			List<Folder> flatOldFolders = flattenFolderTrees(oldTrees);

			// for every new folder, check if it exists in the old folders list
			for (Folder newFolder : flatNewFolders)
			{
				if (!flatOldFolders.contains(newFolder))
				{
					// found a new folder for the hierarchy
					result.addFolder(newFolder);
				}
				else
				{
					// check, if an update to the folder appeared

					// first get the corresponding old folder
					Folder oldFolder = flatOldFolders.get(flatOldFolders.indexOf(newFolder));

					// make an update check
					if (newFolder.wasUpdated(oldFolder))
					{
						result.updateFolder(newFolder);
					}
				}
			}

			// for every old folder, check if it exists in the new folders list
			// to compute deletions
			for (Folder oldFolder : flatOldFolders)
			{
				if (!flatNewFolders.contains(oldFolder))
				{
					result.deleteFolder(oldFolder);
				}
			}

			return result;
		}

		public Folder findFolder(String serverId) throws IllegalArgumentException
		{
			if (serverIdToFolders.containsKey(serverId))
			{
				return serverIdToFolders.get(serverId);
			}
			else
			{
				throw new IllegalArgumentException("Unknown folder: " + serverId);
			}
		}

		public synchronized short synchronize(	de.sloc.sspies.activesync.types.SyncRequestDocument.SyncRequest.Collections.Collection requestCollection,
												Collection responseCollection)
		{
			short statusCode;

			String requestFolderId = requestCollection.getCollectionId();
			String requestSyncKey = requestCollection.getSyncKey();

			Folder folder = findFolder(requestCollection.getCollectionId());

			CollectionProvider provider = folder.getCollectionProvider();
			responseCollection.addCollectionId(requestFolderId);

			if (requestSyncKey.equals(ZERO_SYNC_KEY))
			{
				statusCode = initializeSynchronization(requestCollection, responseCollection);
			}
			else
			{
				FolderState folderState = folderStates.get(requestFolderId);

				if (folderState == null || !requestSyncKey.equals(folderState.getSyncKey()))
				{
					statusCode = STATUS_CODE_SYNC_INVALID_SYNCHRONIZATION_KEY;
				}
				else
				{
					statusCode = differentialSynchronization(requestCollection, responseCollection);
				}

			}

			// process commands

			if (requestCollection.isSetCommands())
			{
				de.sloc.sspies.activesync.types.SyncRequestDocument.SyncRequest.Collections.Collection.Commands requestCommands = requestCollection.getCommands();

				Responses responses = responseCollection.addNewResponses();
				for (Fetch fetch : requestCommands.getFetchArray())
				{
					de.sloc.sspies.activesync.types.SyncResponseDocument.SyncResponse.Collections.Collection.Responses.Fetch fetchResult = responses.addNewFetch();
					fetchResult.setServerId(fetch.getServerId());
					fetchResult.setStatus(STATUS_CODE_SYNC_SUCCESS);
					fetchResult.setApplicationData(provider.getItem(folder.getServerId(), fetch.getServerId()));
				}
			}

			return statusCode;

		}

		public short initializeSynchronization(	de.sloc.sspies.activesync.types.SyncRequestDocument.SyncRequest.Collections.Collection requestCollection,
												Collection responseCollection)
		{
			if (requestCollection.getGetChanges() == MS_TRUE)
			{
				return STATUS_CODE_SYNC_PROTOCOL_ERROR;
			}
			String folderId = requestCollection.getCollectionId();

			FolderState folderState = new FolderState(folderId);
			folderStates.put(folderId, folderState);

			String nextFolderSynckey = INIT_SYNC_KEY;

			folderState.setSyncKey(nextFolderSynckey);
			responseCollection.addSyncKey(nextFolderSynckey);

			return STATUS_CODE_SYNC_SUCCESS;
		}

		public short differentialSynchronization(	de.sloc.sspies.activesync.types.SyncRequestDocument.SyncRequest.Collections.Collection requestCollection,
													Collection responseCollection)
		{
			String folderId = requestCollection.getCollectionId();
			Folder folder = findFolder(folderId);
			FolderState folderState = folderStates.get(folderId);

			if (requestCollection.getGetChanges() == MS_TRUE)
			{
				Commands commands = updateFolderState(folder);
				if (commands != null)
				{
					responseCollection.setCommandsArray(new Commands[] { commands });
				}
			}

			responseCollection.setSyncKeyArray(new String[] { folderState.getSyncKey() });

			return STATUS_CODE_SYNC_SUCCESS;
		}

		protected Commands updateFolderState(Folder folder)
		{
			Commands result = Commands.Factory.newInstance();
			CollectionProvider provider = folder.getCollectionProvider();
			Store store = Providers.getStore();
			String folderId = folder.getServerId();
			FolderState folderState = folderStates.get(folderId);

			// do a full sync, by comparing these two
			Map<String, String> currentFolderItemState = folderState.getItemState();
			Map<String, String> newFolderItemState = provider.getCollectionState(folder.getServerId());

			// if there is no previous folder state, just create adds
			if (folderState.getItemState() == null)
			{
				folderState.setItemState(new HashMap<String, String>());
			}

			// make diffs between old and new folder state

			// check for new messages in folder
			for (Entry<String, String> newItemState : newFolderItemState.entrySet())
			{
				String itemId = newItemState.getKey();
				if (!currentFolderItemState.containsKey(itemId))
				{
					// new message found
					Add add = result.addNewAdd();

					// get item from backend
					ApplicationData data = provider.getItem(folderId, itemId);

					// save it to cache
					store.saveItem(deviceId, folderId, itemId, data);

					add.setApplicationData(data);
					add.setServerId(itemId);
				}
				else if (!currentFolderItemState.get(itemId).equals(newItemState.getValue()))
				{
					// message hash has changed

					// get old message from cache
					ApplicationData oldData = store.getItem(deviceId, folderId, itemId);

					// get new message from backend
					ApplicationData changedData = provider.getItem(folderId, itemId);

					Change change = result.addNewChange();
					change.setServerId(itemId);

					// TODO set class depending on version
					// change.setClass1(folder.getType().getClass1());

					diffApplicationData(change, oldData, changedData);

					// save new message to cache
					store.saveItem(deviceId, folderId, itemId, changedData);
				}
				else
				{ /* no change, no command */
				}
			}

			// check for deleted messages
			for (String oldItemId : currentFolderItemState.keySet())
			{
				if (!newFolderItemState.containsKey(oldItemId))
				{
					// deleted message found
					Delete delete = result.addNewDelete();
					delete.setServerId(oldItemId);
					delete.setClass1(folder.getType().getClass1());

					store.removeItem(deviceId, folderId, oldItemId);

					// TODO check against FilterType or SyncOptions to issue
					// SoftDelete

					// TODO <15,29> Section 2.2.3.42.2: The Delete and Class
					// elements are not returned in the Sync response for an
					// SMS deletion when the MS-ASProtocolVersion header is
					// set to 14.0.
				}
			}

			folderState.setItemState(newFolderItemState);

			if (result.getDomNode().hasChildNodes())
			{
				folderState.setSyncKey(Utility.nextSyncKey());
				return result;
			}
			else
			{
				return null;
			}

		}

		protected void diffApplicationData(Change change, ApplicationData fromData, ApplicationData toData)
		{
			ApplicationData data = change.addNewApplicationData();

			NodeList children = toData.getDomNode().getChildNodes();
			for (int i = 0; i < children.getLength(); i++)
			{
				Node child = children.item(i);

				XmlObject[] foundInFromData = fromData.selectChildren(new QName(child.getNamespaceURI(), child.getLocalName()));

				Node oldValue = foundInFromData[0].getDomNode();
				Node newValue = child;

				if (foundInFromData.length == 0 || !Utility.isEqualNode(oldValue, newValue))
				{
					Node importedNode = data.getDomNode().getOwnerDocument().importNode(child, true);
					data.getDomNode().appendChild(importedNode);
				}
			}
		}

		protected String compareValues(String oldValue, String newValue)
		{
			return oldValue.equals(newValue) ? null : newValue;
		}

		@Override
		public String toString()
		{
			return String.format("[Device deviceId=%s, deviceType=%s, folderHierarchySyncKey=%s]", deviceId, deviceType, folderHierarchySyncKey);
		}

	}

	public UserInformation getUserInformation()
	{
		return settingsProvider.getUserInformation(this.userName);
	}

	@Override
	public String toString()
	{
		return String.format("[User name=%s]", userName);
	}

}
