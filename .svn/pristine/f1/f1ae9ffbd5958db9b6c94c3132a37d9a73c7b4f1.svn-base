package de.sloc.sspies.activesync.backend;

import org.junit.Before;
import org.junit.Test;

import de.sloc.sspies.activesync.Constants;
import de.sloc.sspies.activesync.types.SyncRequestDocument.SyncRequest.Collections.Collection;

public class FolderTest implements Constants
{

	private Folder singleFolder;

	@Before
	public void setUp()
	{
		singleFolder = new Folder("INBOX", null, "Inbox", FolderType.DEFAULT_INBOX);
	}

	@Test
	public void testInitializeSynchronization()
	{
		// test get changes
		Collection collection = Collection.Factory.newInstance();

		collection.setSyncKey(ZERO_SYNC_KEY);
		collection.setGetChanges(MS_TRUE);

		de.sloc.sspies.activesync.types.SyncResponseDocument.SyncResponse.Collections.Collection resultCollection = de.sloc.sspies.activesync.types.SyncResponseDocument.SyncResponse.Collections.Collection.Factory.newInstance();
		System.out.println(collection);
		// assertEquals(STATUS_CODE_SYNC_SUCCESS,
		// singleFolder.initializeSynchronization(collection,
		// resultCollection));
		//
		// collection.unsetGetChanges();
		// assertEquals(STATUS_CODE_SYNC_SUCCESS,
		// singleFolder.initializeSynchronization(collection,
		// resultCollection));
		//
		// collection.setGetChanges(true);
		// assertEquals(STATUS_CODE_SYNC_PROTOCOL_ERROR,
		// singleFolder.initializeSynchronization(collection,
		// resultCollection));

	}

}
