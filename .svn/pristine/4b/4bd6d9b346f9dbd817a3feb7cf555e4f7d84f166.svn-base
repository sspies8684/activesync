import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map.Entry;

import de.sloc.sspies.Utility;

public class EncodingTest
{

	public static void main(String[] args) throws UnsupportedEncodingException
	{
		byte[] something = Utility.hexStringToByteArray("616161C396616161");
		
		for(Entry<String,Charset> c : Charset.availableCharsets().entrySet())
		{
			System.out.println(c.getKey());
			System.out.println(new String(something, c.getKey()));
		}
		System.out.println();
		System.out.println(Charset.defaultCharset().toString());
	}
}
