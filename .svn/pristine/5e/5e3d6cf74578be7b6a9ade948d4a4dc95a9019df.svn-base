package de.sloc.sspies.activesync.backend;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import de.sloc.sspies.activesync.types.FolderSyncResponseDocument.FolderSyncResponse.Changes;
import de.sloc.sspies.activesync.types.FolderSyncResponseDocument.FolderSyncResponse.Changes.Delete;

public class FolderHistory
{
	private List<Entry> history = new LinkedList<FolderHistory.Entry>();

	public void addFolder(Folder folder)
	{
		this.history.add(new Entry(EntryType.ADD, folder));
	}

	public void deleteFolder(Folder folder)
	{
		this.history.add(new Entry(EntryType.DELETE, folder));
	}

	public void updateFolder(Folder folder)
	{
		this.history.add(new Entry(EntryType.UPDATE, folder));
	}

	public List<Entry> getHistory()
	{
		return history;
	}

	public void reset()
	{
		history.clear();
	}
	
	public boolean isEmpty()
	{
		return this.history.size() == 0;
	}

	public static class Entry
	{
		private EntryType entryType;
		private Folder folder;

		public Entry(EntryType entryType, Folder folder)
		{
			super();
			this.entryType = entryType;
			this.folder = folder;
		}

		public EntryType getEntryType()
		{
			return entryType;
		}

		public Folder getFolder()
		{
			return folder;
		}

	}

	public static enum EntryType
	{
		UPDATE, DELETE, ADD
	}

	public Changes toChangesElement()
	{
		Changes changes = Changes.Factory.newInstance();
		changes.setCount(history.size());

		for (Entry entry : history)
		{
			Folder folder = entry.getFolder();

			switch (entry.getEntryType())
			{
				case ADD:
					de.sloc.sspies.activesync.types.Folder add = changes.addNewAdd();
					add.setServerId(folder.getServerId());
					add.setDisplayName(folder.getName());
					add.setParentId(folder.getParent() == null ? "0" : folder.getParent().getServerId());
					add.setType(BigInteger.valueOf(folder.getType().getValue()));
					break;
				case DELETE:
					Delete delete = changes.addNewDelete();
					delete.setServerId(folder.getServerId());
					break;
				case UPDATE:
					de.sloc.sspies.activesync.types.Folder update = changes.addNewUpdate();
					update.setServerId(folder.getServerId());
					update.setParentId(folder.getParent() == null ? "0" : folder.getParent().getServerId());
					update.setDisplayName(folder.getName());
					update.setType(BigInteger.valueOf(folder.getType().getValue()));
					break;
			}
		}

		return changes;
	}

}
