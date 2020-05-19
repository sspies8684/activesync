package de.sloc.sspies.activesync.client;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;

import de.sloc.sspies.activesync.Constants;
import de.sloc.sspies.activesync.context.RequestContext;
import de.sloc.sspies.activesync.types.FolderSyncRequestDocument.FolderSyncRequest;
import de.sloc.sspies.activesync.types.FolderSyncResponseDocument.FolderSyncResponse;
import de.sloc.sspies.activesync.types.PingRequestDocument.PingRequest;
import de.sloc.sspies.activesync.types.PingResponseDocument.PingResponse;
import de.sloc.sspies.activesync.types.SendMailRequestDocument.SendMailRequest;
import de.sloc.sspies.activesync.types.SendMailResponseDocument.SendMailResponse;
import de.sloc.sspies.activesync.types.SettingsRequestDocument.SettingsRequest;
import de.sloc.sspies.activesync.types.SettingsResponseDocument.SettingsResponse;
import de.sloc.sspies.activesync.types.SyncRequestDocument.SyncRequest;
import de.sloc.sspies.activesync.types.SyncResponseDocument.SyncResponse;

public class Executor extends AbstractExecutor implements Constants
{

	public Executor(Endpoint endpoint)
	{
		super(endpoint);
	}

	public SettingsResponse settings(RequestContext context, SettingsRequest request) throws IOException, XmlException
	{
		context.setCommand(COMMAND_SETTINGS);
		return (SettingsResponse) call(context, request);
	}
	
	public FolderSyncResponse folderSync(RequestContext context, FolderSyncRequest request) throws IOException, XmlException
	{
		context.setCommand(COMMAND_FOLDER_SYNC);
		return (FolderSyncResponse) call(context, request);
	}

	public SyncResponse sync(RequestContext context, SyncRequest syncRequest) throws IOException, XmlException
	{
		context.setCommand(COMMAND_SYNC);
		return (SyncResponse) call(context, syncRequest);
	}
	
	public PingResponse ping(RequestContext context, PingRequest pingRequest) throws IOException, XmlException
	{
		context.setCommand(COMMAND_PING);
		return (PingResponse) call(context, pingRequest);
	}
	
	public SendMailResponse sendMail(RequestContext context, SendMailRequest sendMailRequest) throws IOException, XmlException
	{
		context.setCommand(COMMAND_SEND_MAIL);
//		context.setSaveInSent(true);
		return (SendMailResponse) call(context, sendMailRequest);
	}

}
