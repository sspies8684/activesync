package de.sloc.sspies.activesync.wbxml.codepages;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class WbXmlCodePage
{

	private static final String CODE_PAGE_CLASS_SUFFIX = "CodePage";

	protected static Map<Integer, WbXmlCodePage> registry = new HashMap<Integer, WbXmlCodePage>();
	protected static Map<String, WbXmlCodePage> namespaceURIToCodePage = new HashMap<String, WbXmlCodePage>();

	private int codePageId;
	private String codePageName;
	
	private Set<String> opaqueElements= new HashSet<String>(); 
	private Map<String, Integer> tagToToken = new HashMap<String, Integer>();
	private Map<Integer, String> tokenToTag = new HashMap<Integer, String>();

	public WbXmlCodePage(int codePageId)
	{
		this.codePageId = codePageId;
		this.codePageName = this.getClass().getSimpleName().replaceFirst(CODE_PAGE_CLASS_SUFFIX, "");
		if (registry.containsKey(codePageId))
		{
//			throw new IllegalArgumentException("code page " + codePageId + " already existent: " + registry.get(codePageId));
			return;
		}
		registry.put(codePageId, this);
		namespaceURIToCodePage.put(this.codePageName, this);
	}

	protected void addEntry(String tagName, int token, boolean...encodeOpaque)
	{
		String lcTagName = tagName.toLowerCase();
		tagToToken.put(lcTagName, token);
		tokenToTag.put(token, tagName);
		
		if(encodeOpaque.length > 0 && encodeOpaque[0])
		{
			opaqueElements.add(lcTagName);
		}
	}

	public int getCodePageId()
	{
		return codePageId;
	}

	public String getCodePageName()
	{
		return codePageName;
	}

	public int getTokenFromTag(String tag)
	{
		String lcTagName = tag.toLowerCase();
		return tagToToken.get(lcTagName);
	}

	public String getTagFromToken(int token)
	{
		return tokenToTag.get(token);
	}
	
	public boolean isOpaque(String elementName)
	{
		return opaqueElements.contains(elementName.toLowerCase());
	}

	public static WbXmlCodePage getCodePage(int codePageId)
	{
		return registry.get(codePageId);
	}

	public static WbXmlCodePage getCodePage(String namespaceURI)
	{
		return namespaceURIToCodePage.get(namespaceURI);
	}

	
	
	@Override
	public String toString()
	{
		return "WbxmlCodePage: " + getClass().getSimpleName() + " codePageId: " + codePageId;
	}

}
