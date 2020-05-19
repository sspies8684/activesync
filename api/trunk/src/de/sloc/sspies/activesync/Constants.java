package de.sloc.sspies.activesync;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public interface Constants
{
	public static final int MS_TRUE = 1;
	public static final int MS_FALSE = 0;

	public static final String SUPPORTED_COMMANDS = "Sync,SendMail,SmartForward,SmartReply,GetAttachment,GetHierarchy,CreateCollection,DeleteCollection,MoveCollection,FolderSync,FolderCreate,FolderDelete,FolderUpdate,MoveItems,GetItemEstimate,MeetingResponse,Search,Settings,Ping,ItemOperations,Provision,ResolveRecipients,ValidateCert";
	public static final String SUPPORTED_VERSIONS = "12.1,14.0,14.1";
	public static final String VERSION_14_1 = "14.1";
	public static final String VERSION_14_0 = "14.0";
	public static final String VERSION_12_1 = "12.1";

	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_TYPE_MS_WBXML = "application/vnd.ms-sync.wbxml";
	public static final String CONTENT_TYPE_TEXT_XML = "text/xml";

	public static final String CLASS_NAME_FACTORY = "Factory";
	public static final String METHOD_NAME_NEWINSTANCE = "newInstance";
	public static final String METHOD_NAME_PARSE = "parse";
	public static final String EXECUTE_PREFIX = "execute";

	public static final int HTTP_CODE_OK = 200;
	public static final int HTTP_CODE_BAD_REQUEST = 400;
	public static final int HTTP_CODE_UNAUTHORIZED = 401;
	public static final int HTTP_CODE_SERVER_REDIRECT = 451;
	public static final int HTTP_CODE_SERVER_ERROR = 500;
	public static final int HTTP_CODE_NOT_IMPLEMENTED = 501;

	public static final String HTTP_PARAMETER_CMD = "Cmd";
	public static final String HTTP_PARAMETER_USER_NAME = "User";
	public static final String HTTP_PARAMETER_DEVICE_ID = "DeviceId";
	public static final String HTTP_PARAMETER_DEVICE_TYPE = "DeviceType";
	public static final String HTTP_PARAMETER_ATTACHMENT_NAME = "AttachmentName";
	public static final String HTTP_PARAMETER_COLLECTION_ID = "CollectionId";
	public static final String HTTP_PARAMETER_ITEM_ID = "ItemId";
	public static final String HTTP_PARAMETER_LONG_ID = "LongId";
	public static final String HTTP_PARAMETER_OCCURENCE = "Occurence";
	public static final String HTTP_PARAMETER_SAVE_IN_SENT = "SaveInSent";
	public static final String HTTP_HEADER_ACCEPT_LANGUAGE = "Accept-Language";
	public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
	public static final String HTTP_HEADER_AUTHORIZATION_BASIC_PREFIX = "Basic ";
	public static final String HTTP_HEADER_ACCEPT_MULTIPART = "MS-ASAcceptMultiPart";
	public static final String HTTP_HEADER_AS_PROTOCOL_VERSION = "MS-ASProtocolVersion";
	public static final String HTTP_HEADER_POLICY_KEY = "X-MS-PolicyKey";
	public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HTTP_HEADER_USER_AGENT = "User-Agent";
	public static final String HTTP_HEADER_CACHE_CONTROL = "Cache-Control";
	public static final String HTTP_HEADER_CONTENT_ENCODING = "Content-Encoding";
	public static final String HTTP_HEADER_CONTENT_LENGTH = "Content-Length";
	public static final String HTTP_HEADER_MS_SERVER_AS = "MS-Server-ActiveSync";
	public static final String HTTP_HEADER_MS_LOCATION = "X-MS-Location";
	public static final String HTTP_HEADER_MS_RP = "X-MS-RP";
	public static final String HTTP_HEADER_PROTOCOL_VERSIONS = "MS-ASProtocolVersions";
	public static final String HTTP_HEADER_PROTOCOL_COMMANDS = "MS-ASProtocolCommands";

	public static final String COMMAND_TYPE_REQUEST = "Request";
	public static final String COMMAND_TYPE_RESPONSE = "Response";

	public static final String COMMAND_FOLDER_CREATE = "FolderCreate";
	public static final String COMMAND_FOLDER_DELETE = "FolderDelete";
	public static final String COMMAND_FOLDER_SYNC = "FolderSync";
	public static final String COMMAND_FOLDER_UPDATE = "FolderUpdate";
	public static final String COMMAND_GET_ATTACHMENT = "GetAttachment";
	public static final String COMMAND_GET_ITEM_ESTIMATE = "GetItemEstimate";
	public static final String COMMAND_ITEM_OPERATIONS = "ItemOperations";
	public static final String COMMAND_MEETING_RESPONSE = "MeetingResponse";
	public static final String COMMAND_MOVE_ITEMS = "MoveItems";
	public static final String COMMAND_PING = "Ping";
	public static final String COMMAND_PROVISION = "Provision";
	public static final String COMMAND_RESOLVE_RECIPIENTS = "ResolveRecipients";
	public static final String COMMAND_SEARCH = "Search";
	public static final String COMMAND_SEND_MAIL = "SendMail";
	public static final String COMMAND_SETTINGS = "Settings";
	public static final String COMMAND_SMART_FORWARD = "SmartForward";
	public static final String COMMAND_SMART_REPLY = "SmartReply";
	public static final String COMMAND_SYNC = "Sync";
	public static final String COMMAND_VALIDATE_CERT = "ValidateCert";

	public static final String COMMAND_PARAMETER_OPTIONS = "Options";
	public static final String COMMAND_PARAMETER_USER = "User";

	public static final int OPTIONS_BITMASK_FLAG_SAVE_IN_SENT = 0x01;
	public static final int OPTIONS_BITMASK_FLAG_ACCEPT_MULTI_PART = 0x02;

	public static final String BACKEND_PROPERTY_FILE = "WEB-INF/conf/backend-configuration.properties";
	public static final String PROPERTY_AUTHENTICATION_PROVIDER = "de.sloc.sspies.activesync.backend.AuthenticationProvider";
	public static final String PROPERTY_MAIL_PROVIDER = "de.sloc.sspies.activesync.backend.MailProvider";
	public static final String PROPERTY_CONTACTS_PROVIDER = "de.sloc.sspies.activesync.backend.ContactsProvider";
	public static final String PROPERTY_SETTINGS_PROVIDER = "de.sloc.sspies.activesync.backend.SettingsProvider";
	public static final String PROPERTY_STORE = "de.sloc.sspies.activesync.backend.Store";
	public static final String PROPERTY_MAX_HEARTBEAT_INTERVAL = "de.sloc.sspies.activesync.backend.maxHeartbeatInterval";
	public static final String PROPERTY_MIN_HEARTBEAT_INTERVAL = "de.sloc.sspies.activesync.backend.minHeartbeatInterval";

	public static final short STATUS_CODE_SETTINGS_SUCCESS = 1;
	public static final short STATUS_CODE_SETTINGS_PROTOCOL_ERROR = 2;
	public static final short STATUS_CODE_SETTINGS_ACCESS_DENIED = 3;
	public static final short STATUS_CODE_SETTINGS_REQUIRED_PARAMETERS_OMITTED = 3;
	public static final short STATUS_CODE_SETTINGS_SERVER_UNAVAILABLE = 4;
	public static final short STATUS_CODE_SETTINGS_INVALID_ARGUMENTS = 5;
	public static final short STATUS_CODE_SETTINGS_HEARTBEAT_OUTSIDE_ALLOWED_INTERVAL = 5;
	public static final short STATUS_CODE_SETTINGS_CONFLICTING_ARGUMENTS = 6;
	public static final short STATUS_CODE_SETTINGS_DENIED_BY_POLICY = 7;

	public static final short STATUS_CODE_FOLDER_SYNC_SUCCESS = 1;
	public static final short STATUS_CODE_FOLDER_SYNC_SERVER_ERROR = 6;
	public static final short STATUS_CODE_FOLDER_SYNC_SYNC_KEY_MISMATCH = 9;
	public static final short STATUS_CODE_FOLDER_SYNC_INCORRECTLY_FORMATTED_REQUEST = 10;
	public static final short STATUS_CODE_FOLDER_SYNC_UNKNOWN_ERROR = 11;
	public static final short STATUS_CODE_FOLDER_SYNC_CODE_UNKNOWN = 12;

	public static final short STATUS_CODE_SYNC_SUCCESS = 1;
	public static final short STATUS_CODE_SYNC_INVALID_SYNCHRONIZATION_KEY = 3;
	public static final short STATUS_CODE_SYNC_PROTOCOL_ERROR = 4;
	public static final short STATUS_CODE_SYNC_FOLDER_HIERARCHY_HAS_CHANGED = 12;

	public static final String INIT_SYNC_KEY = "INIT";
	public static final String ZERO_SYNC_KEY = "0";

	public static final short BODY_TYPE_PLAIN_TEXT = 1;
	public static final short BODY_TYPE_HTML = 2;
	public static final short BODY_TYPE_RTF = 3;
	public static final short BODY_TYPE_MIME = 4;

	public static final short FILTER_TYPE_ALL = 0;
	public static final short FILTER_TYPE_DAY_ONE_BACK = 1;
	public static final short FILTER_TYPE_DAY_THREE_BACK = 2;
	public static final short FILTER_TYPE_WEEK_ONE_BACK = 3;
	public static final short FILTER_TYPE_WEEK_TWO_BACK = 4;
	public static final short FILTER_TYPE_MONTH_ONE_BACK = 5;
	public static final short FILTER_TYPE_MONTH_THREE_BACK = 6;
	public static final short FILTER_TYPE_MONTH_SIX_BACK = 7;
	public static final short FILTER_TYPE_INCOMPLETE_TASKS = 8;

	public static final String AUTODISCOVER_HOSTNAME_PREFIX = "autodiscover.";
	public static final String AUTODISCOVER_FILEPATH = "/autodiscover/autodiscover.xml";
	public static final String AUTODISCOVER_RESPONSE_NAMESPACE = "http://schemas.microsoft.com/exchange/autodiscover/mobilesync/responseschema/2006";
	public static final String AUTODISCOVER_GENERIC_RESPONSE_NAMESPACE = "http://schemas.microsoft.com/exchange/autodiscover/responseschema/2006";
	public static final String AUTODISCOVER_SERVER_TYPE_MOBILE_SYNC = "MobileSync";
	public static final String AUTODISCOVER_SERVER_URL_PATH = "/Microsoft-Server-ActiveSync";

	public static final String PROTOCOL_HTTPS = "https";

	// MISC
	public static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))$");

	public static enum FolderType
	{
		GENERIC_FOLDER(1, "Email"), DEFAULT_INBOX(2, "Email"), DEFAULT_DRAFTS_FOLDER(3, "Email"), DEFAULT_DELETED_ITEMS_FOLDER(4, "Email"),
		DEFAULT_SENT_ITEMS_FOLDER(5, "Email"), DEFAULT_OUTBOX_FODLER(6, "Email"), DEFAULT_TASKS_FOLDER(7, "Email"),
		DEFAULT_CALENDAR_FOLDER(8, "Calendar"), DEFAULT_CONTACTS_FOLDER(9, "Contacts"), DEFAULT_NOTES_FOLDER(10, "Notes"),
		DEFAULT_JOURNAL_FOLDER(11, "Notes"), USER_MAIL(12, "Email"), USER_CALENDAR(13, "Calendar"), USER_CONTACTS(14, "Contacts"),
		USER_TASKS(15, "Tasks"), USER_JOURNAL(16, "Notes"), USER_NOTES(17, "Notes"), UNKNOWN_FOLDER_TYPE(18, ""),
		RECIPIENT_INFORMATION_CACHE(19, "Contacts");

		private static Map<Integer, FolderType> valueToType = new HashMap<Integer, FolderType>();

		static
		{
			for (FolderType type : EnumSet.allOf(FolderType.class))
			{
				valueToType.put(type.getValue(), type);
			}
		}

		private int value;
		private String class1;

		private FolderType(int value, String class1)
		{
			this.value = value;
			this.class1 = class1;

		}

		public int getValue()
		{
			return value;
		}

		public String getClass1()
		{
			return class1;
		}

	}
}
