package de.sloc.sspies.activesync.backend.mock;

import java.util.HashMap;
import java.util.Map;

import de.sloc.sspies.activesync.Constants.FolderType;
import de.sloc.sspies.activesync.backend.CollectionProvider;
import de.sloc.sspies.activesync.backend.Folder;
import de.sloc.sspies.activesync.types.ApplicationDataDocument.ApplicationData;

public class StaticContactsProvider implements CollectionProvider
{
	private static int i = 0;

	@Override
	public Folder[] getFolderHierarchy()
	{
		Folder contactsFolder = new Folder("CONTACTS", null, "contacts", FolderType.DEFAULT_CONTACTS_FOLDER);

		return new Folder[] { contactsFolder };
	}

	@Override
	public Map<String, String> getCollectionState(String serverId)
	{
		Map<String, String> result = new HashMap<String, String>();
		if (i++ % 2 == 0)
		{
			result.put("1", "1");
		}
		else
		{
			result.put("1", "2");
		}
		// result.put("2", "2");
		return result;
	}

	@Override
	public ApplicationData getItem(String collectionId, String itemId)
	{

		ApplicationData anItem = ApplicationData.Factory.newInstance();

		if (itemId.equals("1"))
		{
			anItem.addFirstName("Sebastian");
			anItem.addLastName("Spies" + i);
			anItem.addFileAs("Spies, Sebastian");
			anItem.addMobilePhoneNumber("+4915777830883");
		}
		return anItem;
	}


	@Override
	public boolean moveItem(String collectionOld, String collectionNew, String itemId)
	{
		return true;
	}
}
