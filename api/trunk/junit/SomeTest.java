import java.io.IOException;
import java.net.URL;

import javax.xml.stream.XMLStreamException;

import org.apache.xmlbeans.XmlException;

import de.sloc.sspies.activesync.Constants;
import de.sloc.sspies.activesync.MicrosoftLanguageId;
import de.sloc.sspies.activesync.client.Autodiscover;
import de.sloc.sspies.activesync.client.Endpoint;
import de.sloc.sspies.activesync.client.EndpointImpl;
import de.sloc.sspies.activesync.client.Executor;
import de.sloc.sspies.activesync.client.ServerInfo;
import de.sloc.sspies.activesync.context.Preset;
import de.sloc.sspies.activesync.context.RequestContext;
import de.sloc.sspies.activesync.context.RequestContextFactory;
import de.sloc.sspies.activesync.context.RequestContextFactory.EncodingType;
import de.sloc.sspies.activesync.types.ApplicationDataDocument.ApplicationData;
import de.sloc.sspies.activesync.types.Folder;
import de.sloc.sspies.activesync.types.FolderSyncRequestDocument.FolderSyncRequest;
import de.sloc.sspies.activesync.types.FolderSyncResponseDocument.FolderSyncResponse;
import de.sloc.sspies.activesync.types.OptionsDocument.Options;
import de.sloc.sspies.activesync.types.Preference;
import de.sloc.sspies.activesync.types.SendMailRequestDocument.SendMailRequest;
import de.sloc.sspies.activesync.types.SendMailResponseDocument.SendMailResponse;
import de.sloc.sspies.activesync.types.SettingsRequestDocument.SettingsRequest;
import de.sloc.sspies.activesync.types.SettingsRequestDocument.SettingsRequest.UserInformation;
import de.sloc.sspies.activesync.types.SettingsResponseDocument.SettingsResponse;
import de.sloc.sspies.activesync.types.SyncRequestDocument.SyncRequest;
import de.sloc.sspies.activesync.types.SyncRequestDocument.SyncRequest.Collections.Collection;
import de.sloc.sspies.activesync.types.SyncResponseDocument.SyncResponse;
import de.sloc.sspies.activesync.wbxml.codepages.CodepageLoader;

public class SomeTest implements Constants
{

	public static void main(String[] args) throws IOException, XMLStreamException, InstantiationException, XmlException, InterruptedException
	{
		CodepageLoader.loadCodepages();

		ServerInfo serverInfo = Autodiscover.discover("somebody441@hotmail.de", "dnstuff");
		System.out.println(serverInfo);

		/*
		 * RequestContextFactory factory =
		 * RequestContextFactory.newInstance(EncodingType.PLAIN); RequestContext
		 * cxt = factory.createOptionsContext("sspies8684@live.com", "dnstuff");
		 * 
		 * Endpoint e = new EndpointImpl(/* serverInfo.getActiveSyncUrl() new
		 * URL("https://m.hotmail.com/Microsoft-Server-ActiveSync")); Executor
		 * exe = new Executor(e);
		 * 
		 * System.out.println(e.issueOPTIONS(cxt));
		 */

		RequestContextFactory factory = RequestContextFactory.newInstance(EncodingType.PLAIN);
		Endpoint e = new EndpointImpl(new URL("https://col-m.hotmail.com/Microsoft-Server-ActiveSync"));
		Executor exe = new Executor(e);

		RequestContext wbxmlContext = factory.createRequestContext(Preset.WBXML_DEFAULT("somebody441@hotmail.de", "dnstuff", VERSION_14_0, "me2",
																						"SmartPhone"));
		// RequestContext wbxmlContext =
		// factory.createRequestContext(Preset.WBXML_DEFAULT("sspies8684@live.com",
		// "dnstuff", VERSION_14_0, "me2",
		// "SmartPhone"));
		wbxmlContext.setLanguageId(MicrosoftLanguageId.EN_US);
		System.out.println(wbxmlContext);

		// SETTINGS REQUEST

		SettingsRequest settingsRequest = SettingsRequest.Factory.newInstance();
		UserInformation ui = settingsRequest.addNewUserInformation();
		ui.addNewGet();
		SettingsResponse sResponse = exe.settings(wbxmlContext, settingsRequest);

		System.out.println();
		System.out.println("SETTINGS REQUEST");
		System.out.println(settingsRequest);
		System.out.println("SETTINGS REPLY");
		System.out.println(sResponse);

		FolderSyncRequest fsr = FolderSyncRequest.Factory.newInstance();
		fsr.setSyncKey(ZERO_SYNC_KEY);

		FolderSyncResponse fsresponse = exe.folderSync(wbxmlContext, fsr);
		System.out.println();
		System.out.println("FOLDER SYNC");
		System.out.println(fsr);
		System.out.println("FOLDER SYNC REPLY");
		System.out.println(fsresponse);

		SyncRequest sr = SyncRequest.Factory.newInstance();
		de.sloc.sspies.activesync.types.SyncRequestDocument.SyncRequest.Collections collections = sr.addNewCollections();
		for (Folder add : fsresponse.getChanges().getAddArray())
		{
			if (add.getType().intValue() == 2)
			{
				Collection c = collections.addNewCollection();
				c.setSyncKey(ZERO_SYNC_KEY);
				c.setCollectionId(add.getServerId());

				Options o = c.addNewOptions();
				o.addMIMESupport((short) 2);
				o.addMIMETruncation((short) 8);
				o.addFilterType(FILTER_TYPE_ALL);

				Preference p = o.addNewBodyPreference();
				p.setType(BODY_TYPE_PLAIN_TEXT);
			}

			if (add.getType().intValue() == 9)
			{
				Collection c = collections.addNewCollection();
				c.setSyncKey(ZERO_SYNC_KEY);
				c.setCollectionId(add.getServerId());

				Options o = c.addNewOptions();
				o.addFilterType(FILTER_TYPE_ALL);
			}
		}

		SyncResponse sresponse = exe.sync(wbxmlContext, sr);
		System.out.println();
		System.out.println("REQUEST 1");
		System.out.println(sr);
		System.out.println("REPLY 1");
		System.out.println(sresponse);

		sr.getCollections().getCollectionArray(0).setSyncKey(sresponse.getCollections().getCollectionArray(0).getSyncKeyArray(0));
		sr.getCollections().getCollectionArray(0).removeOptions(0);
		sr.getCollections().getCollectionArray(1).setSyncKey(sresponse.getCollections().getCollectionArray(1).getSyncKeyArray(0));
		sr.getCollections().getCollectionArray(1).removeOptions(0);

		SyncResponse sresponse2 = exe.sync(wbxmlContext, sr);

		System.out.println();
		System.out.println("REQUEST 2");
		System.out.println(sr);
		System.out.println("REPLY 2");
		System.out.println(sresponse2);

		// System.out.println(new
		// String(sresponse2.getCollections().getCollectionArray(0).getCommandsArray(0).getAddArray(6).getApplicationData().getBodyArray(0).getData().getBytes(),
		// Charset.forName("UTF-16")));

		sr.getCollections().getCollectionArray(0).setSyncKey(sresponse2.getCollections().getCollectionArray(0).getSyncKeyArray(0));
		sr.getCollections().getCollectionArray(1).setSyncKey(sresponse2.getCollections().getCollectionArray(1).getSyncKeyArray(0));

		de.sloc.sspies.activesync.types.SyncRequestDocument.SyncRequest.Collections.Collection.Commands.Add addContact = sr.getCollections()
																															.getCollectionArray(1)
																															.addNewCommands()
																															.addNewAdd();
		addContact.setClientId("blui");
		ApplicationData data = addContact.addNewApplicationData();
		data.addFirstName("Me");
		data.addLastName("N÷T");
		data.addPagerNumber("+491234");
		data.addFileAs("me, n÷t");

		System.out.println();
		System.out.println("REQUEST 3");
		System.out.println(sr);
		SyncResponse sresponse3 = exe.sync(wbxmlContext, sr);
		System.out.println("REPLY 3");
		System.out.println(sresponse3);

		sr.getCollections().getCollectionArray(1).setSyncKey(sresponse3.getCollections().getCollectionArray(0).getSyncKeyArray(0));
		sr.getCollections().getCollectionArray(1).unsetCommands();

		System.out.println();
		System.out.println("REQUEST 4");
		System.out.println(sr);
		SyncResponse sresponse4 = exe.sync(wbxmlContext, sr);
		System.out.println("REPLY 4");
		System.out.println(sresponse4);

		sr.getCollections().getCollectionArray(1).setSyncKey(sresponse4.getCollections().getCollectionArray(0).getSyncKeyArray(0));

		System.out.println();
		System.out.println("REQUEST 5");
		System.out.println(sr);
		SyncResponse sresponse5 = exe.sync(wbxmlContext, sr);
		System.out.println("REPLY 5");
		System.out.println(sresponse5);

		sr.getCollections().getCollectionArray(1).setSyncKey(sresponse5.getCollections().getCollectionArray(0).getSyncKeyArray(0));

		System.out.println();
		System.out.println("REQUEST 6");
		System.out.println(sr);
		SyncResponse sresponse6 = exe.sync(wbxmlContext, sr);
		System.out.println("REPLY 6");
		System.out.println(sresponse6);

		sr.getCollections().getCollectionArray(1).setSyncKey(sresponse6.getCollections().getCollectionArray(0).getSyncKeyArray(0));

		System.out.println();
		System.out.println("REQUEST 7");
		System.out.println(sr);
		SyncResponse sresponse7 = exe.sync(wbxmlContext, sr);
		System.out.println("REPLY 7");
		System.out.println(sresponse7);

		System.out.println("sleep...");
		// Thread.sleep(60000L);

		/*
		 * PingRequest pr = PingRequest.Factory.newInstance();
		 * pr.setHeartbeatInterval(BigInteger.valueOf(600)); Folders fldrs =
		 * pr.addNewFolders(); Folder f = fldrs.addNewFolder();
		 * f.setId(sr.getCollections().getCollectionArray(0).getCollectionId());
		 * f.setClass1(Folder.Class.EMAIL);
		 * 
		 * System.out.println(); System.out.println("PING REQUEST");
		 * System.out.println(pr); PingResponse presponse =
		 * exe.ping(wbxmlContext, pr); System.out.println("PING REPLY");
		 * System.out.println(presponse);
		 */

		SendMailRequest smr = SendMailRequest.Factory.newInstance();
		smr.setClientId("neasdaswu");
		smr.addNewSaveInSentItems();

		smr.setMime("From: somebody441@hotmail.de\n" + "Content-type: text/plain; charset=\"us-ascii\"\n" + "Message-id: <Fasdfasdfasdddd@sloc.de>\n"
				+ "Return-Path: <somebody441@hotmail.de>\n" + "Date: Fri, 22 Jun 2012 01:09:13 +0200\n" + "Content-transfer-encoding: 7bit\n"
				+ "Mime-version: 1.0 (1.0)\n" + "To: <spam@sloc.de>\n" + "Subject: testtest?\n\n"
				+ "Das habe ich gerade von meiner App geschickt\n\n");

		System.out.println();
		System.out.println("SENDMAIL request");
		System.out.println(smr);
		SendMailResponse smresponse = exe.sendMail(wbxmlContext, smr);
		System.out.println("SENDMAIL response");
		System.out.println(smresponse);

		System.exit(0);
		//
		//
		// SyncRequestDocument syncRequestDocument =
		// SyncRequestDocument.Factory.newInstance();
		// SyncRequest syncRequest = syncRequestDocument.addNewSyncRequest();
		// Collections collections = syncRequest.addNewCollections();
		// Collection collection = collections.addNewCollection();
		// collection.setCollectionId("0");
		// collection.setSyncKey(ZERO_SYNC_KEY);
		// Options options = collection.addNewOptions();
		// options.addMaxItems(BigInteger.valueOf(100));
		// Preference bodyPreference = options.addNewBodyPreference();
		// bodyPreference.setType(BODY_TYPE_PLAIN_TEXT);
		// bodyPreference.setTruncationSize(100);
		// options.addFilterType(FILTER_TYPE_WEEK_TWO_BACK);
		//
		// System.out.println(syncRequestDocument);
		// URL endpoint = new
		// URL("https://m.hotmail.com/Microsoft-Server-ActiveSync");
		//
		// HttpURLConnection optionsConnection = (HttpURLConnection)
		// endpoint.openConnection();
		// // optionsConnection.setDoInput(true);
		//
		// optionsConnection.setRequestMethod("OPTIONS");
		// optionsConnection.setUseCaches(false);
		// optionsConnection.setRequestProperty( HTTP_HEADER_AUTHORIZATION,
		// HTTP_HEADER_AUTHORIZATION_BASIC_PREFIX
		// + new
		// String(Base64.encode("sspies8684@live.com:dnstuff".getBytes())));
		// optionsConnection.connect();
		//
		// for (Entry<String, List<String>> headers :
		// optionsConnection.getHeaderFields().entrySet())
		// {
		// System.out.println(headers.getKey() + ": " +
		// headers.getValue().get(0));
		// }
		//
		// endpoint = new
		// URL("https://m.hotmail.com/Microsoft-Server-ActiveSync?Cmd=Sync&User=sspies8684@live.com&DeviceId=MyPhone&DeviceType=SM");
		// URLConnection connection = endpoint.openConnection();
		//
		// connection.addRequestProperty("Content-Type",
		// "application/vnd.ms-sync.wbxml");
		// connection.addRequestProperty(HTTP_HEADER_AS_PROTOCOL_VERSION,
		// Constants.VERSION_14_1);
		// connection.setRequestProperty( HTTP_HEADER_AUTHORIZATION,
		// HTTP_HEADER_AUTHORIZATION_BASIC_PREFIX + new
		// String(Base64.encode("sspies8684@live.com:dnstuff".getBytes())));
		//
		// connection.setDoOutput(true);
		// connection.setDoInput(true);
		//
		// OutputStream os = connection.getOutputStream();
		// CodepageLoader.loadCodepages();
		// WbXmlSerializer serializer = new
		// WbXmlSerializerImpl(syncRequestDocument.newXMLStreamReader(), os);
		// serializer.serialize();
		// os.close();
		//
		// System.out.println(Utility.inputStreamToString(connection.getInputStream()));
		//
		// // connection.connect();
		//
	}
}
