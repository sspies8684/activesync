package de.sloc.sspies.activesync.backend;

import java.util.Map;

import de.sloc.sspies.activesync.types.ApplicationDataDocument.ApplicationData;

public interface CollectionProvider
{
	public Folder[] getFolderHierarchy();

	public Map<String, String> getCollectionState(String collectionId);
	
	// TODO enforce calendar in UTC!!
	public ApplicationData getItem(String collectionId, String itemId);
	
	public boolean moveItem(String collectionOld, String collectionNew, String itemId);
}
