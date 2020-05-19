package de.sloc.sspies.activesync.backend.mock;

import java.util.HashMap;
import java.util.Map;

import de.sloc.sspies.activesync.backend.Store;
import de.sloc.sspies.activesync.backend.User;
import de.sloc.sspies.activesync.backend.User.Device;
import de.sloc.sspies.activesync.types.ApplicationDataDocument.ApplicationData;

public class MemoryStore implements Store
{
	private static Device device = null;
	private static Map<String, ApplicationData> items = new HashMap<String, ApplicationData>();

	@Override
	public Device loadDevice(User user, String deviceId)
	{
		return device;
	}

	@Override
	public void saveDevice(User user, Device createdDevice)
	{
		device = createdDevice;
	}

	@Override
	public ApplicationData getItem(String deviceId, String collectionId, String itemId)
	{
		return items.get(deviceId + ":" + collectionId + ":" + itemId);
	}

	@Override
	public void saveItem(String deviceId, String collectionId, String itemId, ApplicationData data)
	{
		items.put(deviceId + ":" + collectionId + ":" + itemId, data);
	}
	
	@Override
	public void removeItem(String deviceId, String collectionId, String itemId)
	{
		items.remove(deviceId + ":" + collectionId + ":" + itemId);
	}

}
