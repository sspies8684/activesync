package de.sloc.sspies.activesync.backend;

import de.sloc.sspies.activesync.backend.User.Device;
import de.sloc.sspies.activesync.types.ApplicationDataDocument.ApplicationData;

public interface Store
{
	public Device loadDevice(User user, String deviceId);
	public void saveDevice(User user, Device createdDevice);
	
	public void saveItem(String deviceId, String collectionId, String itemId, ApplicationData data);
	public void removeItem(String deviceId, String collectionId, String itemId);
	public ApplicationData getItem(String deviceId, String collectionId, String itemId);
	

}
