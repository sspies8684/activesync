package de.sloc.sspies.activesync.backend;

import java.util.LinkedList;
import java.util.List;

import de.sloc.sspies.activesync.Constants;

public class Folder implements Constants
{
	private String serverId;
	private Folder parent;
	private List<Folder> children = new LinkedList<Folder>();
	private String name;
	private FolderType type;

	public Folder(String serverId, Folder parent, String name, FolderType type)
	{
		super();
		this.serverId = serverId;
		this.parent = parent;
		this.name = name;
		this.type = type;

		if (this.parent != null)
		{
			this.parent.addChild(this);
		}
	}

	public FolderType getType()
	{
		return type;
	}

	public CollectionProvider getCollectionProvider()
	{
		switch (getType())
		{
			case DEFAULT_CALENDAR_FOLDER:
			case USER_CALENDAR:
				return Providers.calendarProvider;
			case DEFAULT_CONTACTS_FOLDER:
			case USER_CONTACTS:
				return Providers.contactsProvider;
			case USER_TASKS:
			case DEFAULT_TASKS_FOLDER:
				return Providers.tasksProvider;
			case DEFAULT_JOURNAL_FOLDER:
			case USER_JOURNAL:
				return Providers.journalProvider;
			case RECIPIENT_INFORMATION_CACHE:
				return Providers.recipientInformationCacheProvider;
			case DEFAULT_NOTES_FOLDER:
			case USER_NOTES:
				return Providers.notesProvider;
			case GENERIC_FOLDER:
			case DEFAULT_DELETED_ITEMS_FOLDER:
			case DEFAULT_DRAFTS_FOLDER:
			case DEFAULT_INBOX:
			case DEFAULT_OUTBOX_FODLER:
			case DEFAULT_SENT_ITEMS_FOLDER:
			case USER_MAIL:
				return Providers.mailProvider;
			default:
				throw new InternalError("Could not lookup collection provider for folder type: " + getType());
		}
	}

	public String getServerId()
	{
		return serverId;
	}

	public Folder getParent()
	{
		return parent;
	}

	public String getName()
	{
		return name;
	}

	protected void addChild(Folder child)
	{
		children.add(child);
	}

	public List<Folder> getChildren()
	{
		return children;
	}

	@Override
	public int hashCode()
	{
		return serverId.hashCode();
	}

	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof Folder))
		{
			return false;
		}

		Folder otherFolder = (Folder) other;

		return serverId.equals(otherFolder.getServerId());
	}

	public boolean wasUpdated(Folder otherFolder)
	{
		return !((parent == null && otherFolder.getParent() == null) || parent.equals(otherFolder.getParent()) && name.equals(otherFolder.getName())
				&& type.equals(otherFolder.getType()));
	}

}