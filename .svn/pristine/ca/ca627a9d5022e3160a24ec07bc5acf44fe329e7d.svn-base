package de.sloc.sspies.activesync.backend.mock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import de.sloc.sspies.activesync.Constants.FolderType;
import de.sloc.sspies.activesync.backend.Folder;
import de.sloc.sspies.activesync.backend.MailProvider;
import de.sloc.sspies.activesync.types.ApplicationDataDocument.ApplicationData;
import de.sloc.sspies.activesync.types.BodyDocument.Body;

public class StaticMailboxProvider implements MailProvider
{
	private static int i = 0;

	@Override
	public Folder[] getFolderHierarchy()
	{
		Folder defaultInbox = new Folder("INBOX", null, "INBOX", FolderType.DEFAULT_INBOX);

		/*
		 * if (i++ % 2 == 0) {
		 */
		// new Folder("subbox", defaultInbox, "subbox",
		// Type.DEFAULT_DRAFTS_FOLDER);
		// }

		return new Folder[] { defaultInbox };
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

			anItem.addTo("Sebastian Spies <sspies@sloc.de>");
			anItem.addFrom("Kerstin Kockler <kk@sloc.de>");
			anItem.addSubject("Spatzl!");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(1334321269203L));
			cal.setTimeZone(TimeZone.getTimeZone("UTC"));
			anItem.addDateReceived(cal);
			anItem.addRead(i % 2);
			Body body = anItem.addNewBody();
			body.setType((short) 1);
			body.setData("Hello");

		}
		return anItem;
	}

	private static String readFileAsString(String filePath) throws java.io.IOException
	{
		StringBuffer fileData = new StringBuffer(1000);

		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1)
		{
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}

	@Override
	public boolean moveItem(String collectionOld, String collectionNew, String itemId)
	{
		return true;
	}
}
