package de.sloc.sspies.activesync.backend;

import java.util.HashMap;
import java.util.Map;

import de.sloc.sspies.activesync.Constants;

public class FolderState implements Constants
{
	private String syncKey = ZERO_SYNC_KEY;
	
	private String folderId;
	private Map<String, String> itemState = new HashMap<String, String>();
	
	
	public FolderState(String folderId)
	{
		this.folderId = folderId;
	}
	
	
	public String getFolderId()
	{
		return folderId;
	}
	
	public String getSyncKey()
	{
		return syncKey;
	}
	
	public void setSyncKey(String syncKey)
	{
		this.syncKey = syncKey;
	}
	
	public Map<String, String> getItemState()
	{
		return itemState;
	}
	
	public void setItemState(Map<String, String> itemState)
	{
		this.itemState = itemState;
	}
	
	
	
}
