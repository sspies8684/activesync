package de.sloc.sspies;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utility class for convenient access to raw data.
 * 
 * @author sspies
 * 
 */
public class Utility
{

	/**
	 * Converts an integer to one byte
	 * 
	 * @param value
	 *            the integer value to convert
	 * @return the computed byte
	 * @throws UtilityException
	 *             if Integer value is larger than 2^15
	 */
	public static final byte integerToOneByte(int value) throws UtilityException
	{
		if ((value > Math.pow(2, 15)) || (value < 0))
			throw new UtilityException("Integer value " + value + " is larger than 2^15");
		return (byte) (value & 0xFF);
	}

	/**
	 * Converts an integer to two bytes
	 * 
	 * @param value
	 *            the integer value to convert
	 * @return the computed byte-array
	 * @throws UtilityException
	 *             if Intger value is larger than 2^16-1
	 */
	public static final byte[] integerToTwoBytes(int value) throws UtilityException
	{
		byte[] result = new byte[2];
		if ((value > Math.pow(2, 16) - 1) || (value < 0))
			throw new UtilityException("Integer value " + value + " is larger than 2^16-1");
		result[0] = (byte) ((value >>> 8) & 0xFF);
		result[1] = (byte) (value & 0xFF);
		return result;
	}

	/**
	 * Converts a long to four bytes
	 * 
	 * @param value
	 *            the long value to convert
	 * @return the computed byte-array
	 * @throws UtilityException
	 *             just for completeness
	 */
	public static final byte[] longToFourBytes(long value) throws UtilityException
	{
		byte[] result = new byte[4];
		result[0] = (byte) ((value >>> 24) & 0xFF);
		result[1] = (byte) ((value >>> 16) & 0xFF);
		result[2] = (byte) ((value >>> 8) & 0xFF);
		result[3] = (byte) (value & 0xFF);
		return result;
	}

	/**
	 * Converts a long to six bytes
	 * 
	 * @param value
	 *            the long value to convert
	 * @return the computed byte-array
	 * @throws UtilityException
	 *             just for completeness
	 */
	public static final byte[] longToSixBytes(long value) throws UtilityException
	{
		byte[] result = new byte[6];
		result[0] = (byte) ((value >>> 40) & 0xFF);
		result[1] = (byte) ((value >>> 32) & 0xFF);
		result[2] = (byte) ((value >>> 24) & 0xFF);
		result[3] = (byte) ((value >>> 16) & 0xFF);
		result[4] = (byte) ((value >>> 8) & 0xFF);
		result[5] = (byte) (value & 0xFF);
		return result;
	}

	/**
	 * Converts one byte to an integer
	 * 
	 * @param value
	 *            the byte to convert to an integer
	 * @return the computed integer value
	 * @throws UtilityException
	 *             just for completeness
	 */
	public static final int oneByteToInteger(byte value)
	{
		return (int) value & 0xFF;
	}

	/**
	 * Converts two bytes to an integer
	 * 
	 * @param value
	 *            the byte-array to convert to an integer (MSB left)
	 * @return the computed integer value
	 * @throws UtilityException
	 *             if byte array is too short
	 */
	public static final int twoBytesToInteger(byte[] value) throws UtilityException
	{
		if (value.length < 2)
			throw new UtilityException("Byte array too short!");

		int temp0 = value[0] & 0xFF;
		int temp1 = value[1] & 0xFF;
		return ((temp0 << 8) + temp1);
	}

	/**
	 * Converts four bytes to a long
	 * 
	 * @param value
	 *            the byte-array to convert to an long (MSB left)
	 * @return the computed long value
	 * @throws UtilityException
	 *             if byte array is too short
	 */
	public static final long fourBytesToLong(byte[] value) throws UtilityException
	{
		if (value.length < 4)
			throw new UtilityException("Byte array too short!");

		int temp0 = value[0] & 0xFF;
		int temp1 = value[1] & 0xFF;
		int temp2 = value[2] & 0xFF;
		int temp3 = value[3] & 0xFF;
		return (((long) temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
	}

	/**
	 * Converts six bytes to a long
	 * 
	 * @param value
	 *            the byte-array to convert to a long (MSB left)
	 * @return the computer long value
	 * @throws UtilityException
	 *             if byte array is too short
	 */
	public static final long sixBytesToLong(byte[] value) throws UtilityException
	{
		if (value.length < 6)
		{
			throw new UtilityException("Byte array too short!");
		}
		int temp0 = value[0] & 0xFF;
		int temp1 = value[1] & 0xFF;
		int temp2 = value[2] & 0xFF;
		int temp3 = value[3] & 0xFF;
		int temp4 = value[4] & 0xFF;
		int temp5 = value[5] & 0xFF;
		return ((((long) temp0) << 40) + (((long) temp1) << 32) + (((long) temp2) << 24) + (temp3 << 16) + (temp4 << 8) + temp5);
	}

	/**
	 * Dump bytes from byte-array
	 * 
	 * @param data
	 *            the byte-array
	 * @return the byte-array in 0x.. notation
	 */
	public static final String dumpBytes(byte[] data)
	{
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (byte b : data)
		{
			i++;
			sb.append(String.valueOf(b));
			if (i < data.length)
				sb.append(", ");
			if ((i % 15) == 0)
				sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Truncate number of bytes from the left of a byte-array
	 * 
	 * @param b1
	 *            the byte-array
	 * @param bytes
	 *            the number of bytes to truncate
	 * @return the resulting new byte-array (new length is b1.length - bytes)
	 */
	public static byte[] truncateLeft(byte[] b1, int bytes)
	{
		int restBytesLength = b1.length - bytes;
		byte[] b2 = new byte[restBytesLength];
		System.arraycopy(b1, bytes, b2, 0, restBytesLength);
		return b2;
	}

	/**
	 * Shifts a byte-array a number of bytes to the left
	 * 
	 * @param b1
	 *            the byte-array
	 * @param bytes
	 *            the number of shifts (shifts 0 from right)
	 * @return the resulting new byte-array
	 */
	public static byte[] shiftLeft(byte[] b1, int bytes)
	{
		byte[] result = new byte[b1.length];
		System.arraycopy(b1, bytes, result, 0, bytes);
		return result;
	}

	/**
	 * Converts a hex String to a byte-array "440f0ac0a8ffef01" -> new byte[] {
	 * 0x44, 0x0F, 0xAC, 0x0A, 0x8F, 0xEF, 0x01 }
	 * 
	 * @param s
	 *            the hex string
	 * @return the resulting byte-array
	 */
	public static byte[] hexStringToByteArray(String s)
	{
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2)
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));

		return data;
	}

	public static String byteArrayToHexString(byte[] bytes)
	{
		BigInteger bi = new BigInteger(1, bytes);
		return bi.toString(16);
	}

	/**
	 * Concatenates two byte arrays
	 * 
	 * @param b1
	 *            byte-array one
	 * @param b2
	 *            byte-array two
	 * @return the resulting new byte array (new length is b1.length +
	 *         b2.length)
	 */
	public static byte[] concatenateTwoByteArrays(byte[] b1, byte[] b2)
	{
		return concatenateByteArrays(b1, b2);
	}

	public static byte[] concatenateByteArrays(byte[]... byteArrays)
	{
		return concatenateArrays(byteArrays);
	}

	@SuppressWarnings("unchecked")
	public static <T> T concatenateArrays(T... arrays)
	{
		if (arrays.length == 0)
		{
			throw new IllegalArgumentException("You need to supply at least one argument");
		}

		if (!arrays[0].getClass().isArray())
		{
			throw new IllegalArgumentException("You need to supply arrays");
		}

		int length = 0;
		for (T a : arrays)
		{
			length += lengthOfArray(a);
		}

		T result = (T) createArray(arrays[0].getClass(), length);

		int offset = 0;
		for (T a : arrays)
		{
			int aLength = lengthOfArray(a);
			System.arraycopy(a, 0, result, offset, aLength);
			offset += aLength;
		}

		return result;
	}

	private static int lengthOfArray(Object array)
	{
		if (!array.getClass().isArray())
		{
			throw new IllegalArgumentException("Can only calculate the length of an array");
		}

		Class<?> componentType = array.getClass().getComponentType();
		int result;

		if (componentType.isPrimitive())
		{
			if (componentType.isAssignableFrom(byte.class))
			{
				result = ((byte[]) array).length;
			}
			else if (componentType.isAssignableFrom(short.class))
			{
				result = ((short[]) array).length;
			}
			else if (componentType.isAssignableFrom(int.class))
			{
				result = ((int[]) array).length;
			}
			else if (componentType.isAssignableFrom(long.class))
			{
				result = ((long[]) array).length;
			}
			else if (componentType.isAssignableFrom(float.class))
			{
				result = ((float[]) array).length;
			}
			else if (componentType.isAssignableFrom(double.class))
			{
				result = ((double[]) array).length;
			}
			else if (componentType.isAssignableFrom(char.class))
			{
				result = ((char[]) array).length;
			}
			else
			// if (componentType.isInstance(boolean.class))
			{
				result = ((boolean[]) array).length;
			}
		}
		else
		{
			result = ((Object[]) array).length;
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private static <T> T createArray(Class<T> klass, int length)
	{
		T result;

		if (klass.getComponentType().isPrimitive())
		{
			if (klass.isAssignableFrom(byte[].class))
			{
				result = (T) new byte[length];
			}
			else if (klass.isAssignableFrom(short[].class))
			{
				result = (T) new short[length];
			}
			else if (klass.isAssignableFrom(int[].class))
			{
				result = (T) new int[length];
			}
			else if (klass.isAssignableFrom(long[].class))
			{
				result = (T) new long[length];
			}
			else if (klass.isAssignableFrom(float[].class))
			{
				result = (T) new float[length];
			}
			else if (klass.isAssignableFrom(double[].class))
			{
				result = (T) new double[length];
			}
			else if (klass.isAssignableFrom(char[].class))
			{
				result = (T) new char[length];
			}
			else
			// if (klass.isInstance(boolean[].class))
			{
				result = (T) new boolean[length];
			}
		}
		else
		{
			result = (T) Array.newInstance(klass.getComponentType(), length);
		}

		return result;
	}

	/**
	 * Generates number of random bytes
	 * 
	 * @param num
	 *            the number of random bytes to generate
	 * @return an array containing random bytes
	 */
	public static byte[] generateRandomBytes(int num)
	{
		Random rand = new Random();
		byte randomBytes[] = new byte[num];
		rand.nextBytes(randomBytes);
		return randomBytes;
	}

	public static final byte[] bigIntegerToUnsignedByteArray(BigInteger a, int len) throws NumberFormatException
	{
		byte[] buf = new byte[len];

		if (a.bitLength() > len * 8)
			throw new NumberFormatException("BigInteger doesn't fit into array");

		byte[] b = a.toByteArray();

		if (b.length == len + 1)
			if (b[0] == 0)
				for (int i = 0; i < buf.length; i++)
					buf[i] = b[i + 1];
			else
				throw new NumberFormatException("Internal Error");
		else if (b.length <= len)
			for (int i = 0; i < b.length; i++)
				buf[buf.length - 1 - i] = b[b.length - 1 - i];
		else
			throw new NumberFormatException("Internal Error");

		return buf;
	}

	/**
	 * Read an integer from a file (e.g., for pid-files)
	 * 
	 * @param file
	 *            the file to read the integer from
	 * @return the integer read from the file
	 * @throws IOException
	 *             if there's some {@link IOException} talking to the file
	 * @throws NumberFormatException
	 *             if the file does not contain only a single integer
	 */
	public static int readIntFromFile(File file) throws IOException, NumberFormatException
	{
		StringBuffer sb = new StringBuffer();
		FileInputStream fis = new FileInputStream(file);

		for (;;)
		{
			byte[] buf = new byte[10];

			if (fis.read(buf) == -1)
				break;

			sb.append(new String(buf));
		}

		return Integer.parseInt(sb.toString().trim());
	}

	/**
	 * Read an integer from a script
	 * 
	 * @param file
	 *            the script to run and expect one integer from
	 * @return the integer value the script sent to stdout
	 * @throws IOException
	 *             if there's some {@link IOException} talking to the script
	 * @throws NumberFormatException
	 *             if the script didn't output only a single integer
	 */
	public static int readIntFromScript(File file, String... params) throws IOException, NumberFormatException
	{
		String param = "";
		for (String p : params)
			param += p + " ";

		StringBuffer buffer = new StringBuffer();

		Runtime rt = Runtime.getRuntime();

		Process proc;
		try
		{
			proc = rt.exec(file.getAbsolutePath() + " " + param);
		}
		catch (IOException e)
		{
			return -1;
		}

		InputStream is = proc.getInputStream();

		byte[] readBuffer = new byte[5];

		for (;;)
		{
			int bytesRead;
			try
			{
				if ((bytesRead = is.read(readBuffer)) == -1)
					break;
			}
			catch (IOException e)
			{
				return -1;
			}
			buffer.append(new String(readBuffer, 0, bytesRead));
		}

		return (int) Float.parseFloat(buffer.toString().trim());
	}

	/**
	 * Read a boolean from a script
	 * 
	 * @param file
	 *            the script to run and expect one boolean from
	 * @return the boolean value the script sent to stdout (1 is true, 0 is
	 *         false)
	 * @throws IOException
	 *             if there's some {@link IOException} talking to the script
	 */
	public static boolean readBooleanFromScript(File file, String... params) throws IOException
	{
		String param = "";
		for (String p : params)
			param += p + " ";

		Runtime rt = Runtime.getRuntime();

		Process proc = rt.exec(file.getAbsolutePath() + " " + param);

		InputStream is = proc.getInputStream();

		return is.read() == 49;
	}

	/**
	 * Enumeration of network classes (see <a
	 * href="http://tools.ietf.org/html/rfc791">RFC791</a>)
	 * 
	 * @author sspies
	 * 
	 */
	public enum IP_CLASS
	{
		A(8), B(16), C(24), D(0), E(0);

		private int prefixLength;

		private IP_CLASS(int prefixLength)
		{
			this.prefixLength = prefixLength;
		}

		public int getPrefixLength()
		{
			return prefixLength;
		}
	}

	/**
	 * Gets the class of an address
	 * 
	 * @param address
	 *            the address to find the class for
	 * @return the resulting {@link IP_CLASS} of the address
	 */
	public static IP_CLASS getRangeClassOfAddress(InetAddress address)
	{
		byte interestingByte = address.getAddress()[0];
		if ((interestingByte & 0x80) == 0)
			return IP_CLASS.A;
		else if ((interestingByte & 0xC0) == 0x80)
			return IP_CLASS.B;
		else if ((interestingByte & 0xE0) == 0xC0)
			return IP_CLASS.C;
		else if ((interestingByte & 0xF0) == 0xE0)
			return IP_CLASS.D;
		else if ((interestingByte & 0xF0) == 0xF0)
			return IP_CLASS.E;

		// not reached
		return null;
	}

	public static List<Class<?>> getClassesForPackage(String pckgname) throws ClassNotFoundException
	{
		// This will hold a list of directories matching the pckgname.
		// There may be more than one if a package is split over multiple
		// jars/paths
		List<Class<?>> classes = new ArrayList<Class<?>>();
		ArrayList<File> directories = new ArrayList<File>();
		try
		{
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if (cld == null)
			{
				throw new ClassNotFoundException("Can't get class loader.");
			}
			// Ask for all resources for the path
			Enumeration<URL> resources = cld.getResources(pckgname.replace('.', '/'));
			while (resources.hasMoreElements())
			{
				URL res = resources.nextElement();
				if (res.getProtocol().equalsIgnoreCase("jar"))
				{
					JarURLConnection conn = (JarURLConnection) res.openConnection();
					JarFile jar = conn.getJarFile();
					for (JarEntry e : Collections.list(jar.entries()))
					{

						if (e.getName().startsWith(pckgname.replace('.', '/')) && e.getName().endsWith(".class") && !e.getName().contains("$"))
						{
							String className = e.getName().replace("/", ".").substring(0, e.getName().length() - 6);
							classes.add(Class.forName(className));
						}
					}
				}
				else
					directories.add(new File(URLDecoder.decode(res.getPath(), "UTF-8")));
			}
		}
		catch (NullPointerException x)
		{
			throw new ClassNotFoundException(pckgname + " does not appear to be " + "a valid package (Null pointer exception)");
		}
		catch (UnsupportedEncodingException encex)
		{
			throw new ClassNotFoundException(pckgname + " does not appear to be " + "a valid package (Unsupported encoding)");
		}
		catch (IOException ioex)
		{
			throw new ClassNotFoundException("IOException was thrown when trying " + "to get all resources for " + pckgname);
		}

		// For every directory identified capture all the .class files
		for (File directory : directories)
		{
			if (directory.exists())
			{
				// Get the list of the files contained in the package
				String[] files = directory.list();
				for (String file : files)
					// we are only interested in .class files
					if (file.endsWith(".class"))
						// removes the .class extension
						classes.add(Class.forName(pckgname + '.' + file.substring(0, file.length() - 6)));
			}
			else
				throw new ClassNotFoundException(pckgname + " (" + directory.getPath() + ") does not appear to be a valid package");
		}
		return classes;
	}

	public static List<Class<?>> getClassessOfInterface(String thePackage, Class<?> theInterface)
	{
		List<Class<?>> classList = new ArrayList<Class<?>>();
		try
		{
			for (Class<?> discovered : getClassesForPackage(thePackage))
				if (theInterface.isAssignableFrom(discovered))
					classList.add(discovered);
		}
		catch (ClassNotFoundException ex)
		{

		}

		return classList;
	}

	public static boolean isProcessRunning(String name, int pid) throws UtilityException
	{
		try
		{
			Process proc = Runtime.getRuntime().exec("ps -p " + pid);
			return proc.waitFor() == 0;
		}
		catch (Exception e)
		{
			throw new UtilityException("Don't know if process " + name + " is running");
		}
	}

	public static long inetAddressStringToLong(String inetAddress) throws UtilityException
	{
		String[] addressArray = inetAddress.split("\\.");
		try
		{
			// enforce unsigned bytes by casts ;-)
			return fourBytesToLong(new byte[] { (byte) Integer.parseInt(addressArray[0]), (byte) Integer.parseInt(addressArray[1]),
												(byte) Integer.parseInt(addressArray[2]), (byte) Integer.parseInt(addressArray[3]) });
		}
		catch (Exception e)
		{
			throw new UtilityException("Invalid address format: " + inetAddress + " " + e.getMessage());
		}
	}

	public static InetAddress inetAddressLongToString(long inetAddress) throws UtilityException
	{
		try
		{
			return InetAddress.getByAddress(longToFourBytes(inetAddress));
		}
		catch (UnknownHostException e)
		{
			throw new UtilityException(e.getMessage());
		}
	}

	public static long dottedAS4ToLong(String as4Num)
	{
		String[] as4Parts = as4Num.split("\\.");

		if (as4Parts.length != 2)
		{
			throw new NumberFormatException("Does not contain exactly one dot");
		}

		long[] as4PartsLong = { Integer.parseInt(as4Parts[0]), Integer.parseInt(as4Parts[1]) };

		return as4PartsLong[0] * 0x010000 + as4PartsLong[1];
	}

	public static boolean equalByteArray(byte[] b1, byte[] b2)
	{
		if (b1.length != b2.length)
		{
			return false;
		}

		for (int i = 0; i < b1.length; i++)
		{
			if (b1[i] != b2[i])
			{
				return false;
			}
		}

		return true;

	}

	public static int[] unbox(Integer[] box)
	{
		int[] result = new int[box.length];
		int i = 0;

		for (int val : box)
		{
			result[i++] = val;
		}

		return result;
	}

	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Random rnd = new Random();

	public static String randomString(int len)
	{
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	// Missing operators
	public static boolean imply(boolean a, boolean b)
	{
		return !a || (a && b);
	}

	public static boolean xor(boolean a, boolean b)
	{
		return (a && !b) || (!a && b);
	}

	public static String arrayToString(Object[] array)
	{
		StringBuffer result = new StringBuffer();

		result.append("[ ");
		int i = 0;
		for (Object o : array)
		{

			result.append(o.toString());
			if (++i < array.length)
			{
				result.append(", ");
			}
		}

		result.append(" ]");
		return result.toString();
	}

	public static StringBuilder indent(StringBuilder sb, int numTabs, String indentString)
	{
		for (int i = 0; i < numTabs; i++)
		{
			sb.append(indentString);
		}
		return sb;
	}

	public static StringBuilder indentSmall(StringBuilder sb, int numTabs)
	{
		return indent(sb, numTabs, "  ");
	}

	protected static final Pattern dottedAS4Pattern = Pattern.compile("\\d+\\.\\d+");

	public static String sanitizeASPath(String asPath)
	{
		asPath = asPath.trim();
		asPath = asPath.replaceAll("\\s+", " ");

		Matcher matcher = dottedAS4Pattern.matcher(asPath);
		while (matcher.find())
		{
			String match = matcher.group();
			asPath = asPath.replace(match, Utility.dottedAS4ToLong(match) + "");
		}

		return asPath;
	}

	private static String[] splitLine(String line)
	{
		String[] splittedLine = line.split("\\s*,\\s*");
		for (int i = 0; i < splittedLine.length; i++)
		{
			splittedLine[i] = splittedLine[i].replaceAll("\\s*\"\\s*", "");
		}
		return splittedLine;
	}

	public static List<Map<String, String>> parseCSV(File file) throws UtilityException
	{
		List<Map<String, String>> csvData = new LinkedList<Map<String, String>>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));

			// read first lines as has keys
			String[] keys = splitLine(br.readLine());

			String line;
			while ((line = br.readLine()) != null)
			{
				String[] splittedLine = splitLine(line);
				Map<String, String> csvMap = new HashMap<String, String>();

				for (int i = 0; i < splittedLine.length; i++)
				{
					csvMap.put(keys[i], splittedLine[i]);
				}

				csvData.add(csvMap);
			}
		}
		catch (Exception e)
		{
			throw new UtilityException(e.getMessage());
		}

		return csvData;
	}

	public static String findRelativePath(String base, String path) throws IOException
	{
		String a = new File(base).getCanonicalFile().toURI().getPath();
		String b = new File(path).getCanonicalFile().toURI().getPath();
		String[] basePaths = a.split("/");
		String[] otherPaths = b.split("/");
		int n = 0;
		for (; n < basePaths.length && n < otherPaths.length; n++)
		{
			if (!basePaths[n].equals(otherPaths[n]))
			{
				break;
			}
		}

		StringBuffer tmp = new StringBuffer("");

		for (int m = n; m < basePaths.length - 1; m++)
		{
			tmp.append("../");
		}

		for (int m = n; m < otherPaths.length; m++)
		{
			tmp.append(otherPaths[m]);
			tmp.append("/");
		}

		return tmp.subSequence(0, tmp.length() - 1).toString();
	}

	public static boolean deleteRecursive(File path) throws FileNotFoundException
	{
		if (!path.exists())
			throw new FileNotFoundException(path.getAbsolutePath());
		boolean ret = true;
		if (path.isDirectory())
		{
			for (File f : path.listFiles())
			{
				ret = ret && deleteRecursive(f);
			}
		}
		return ret && path.delete();
	}

	public static void copyFile(File rpkiFile, String target)
	{
		String targetBasePath = target.subSequence(0, target.lastIndexOf("/")).toString();
		new File(targetBasePath).mkdirs();

		try
		{
			InputStream in = new FileInputStream(rpkiFile);
			OutputStream out = new FileOutputStream(target);
			int len;
			byte[] buf = new byte[1024];
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
		catch (Exception e)
		{

		}

	}

	public static String nextSyncKey()
	{
		return randomString(16);
	}

	public static boolean isEqualNode(Node node1, Node node2)
	{
		boolean check = nullEquality(node1.getNodeName(), node2.getNodeName());
		check = check && nullEquality(node1.getNamespaceURI(), node2.getNamespaceURI());
		check = check && nullEquality(node1.getLocalName(), node2.getLocalName());
		check = check && nullEquality(node1.getPrefix(), node2.getPrefix());
		check = check && nullEquality(node1.getNodeValue(), node2.getNodeValue());
		check = check && namedNodeMapEquality(node1.getAttributes(), node2.getAttributes());
		check = check && nodeListEquality(node1.getChildNodes(), node2.getChildNodes());
		return check;

	}

	public static boolean nullEquality(Object obj1, Object obj2)
	{
		return (obj1 == null && obj2 == null) || obj1.equals(obj2);
	}

	public static boolean namedNodeMapEquality(NamedNodeMap map1, NamedNodeMap map2)
	{
		boolean bothNull = map1 == null && map2 == null;
		boolean sameLength = false;
		boolean equalNodes = true;

		if (!bothNull)
		{
			sameLength = map1.getLength() == map2.getLength();

			if (sameLength)
				for (int i = 0; i < map1.getLength(); i++)
					equalNodes = equalNodes && isEqualNode(map1.item(i), map2.item(i));
		}
		return bothNull || (sameLength && equalNodes);
	}

	public static boolean nodeListEquality(NodeList nodeList1, NodeList nodeList2)
	{
		boolean bothNull = nodeList1 == null && nodeList2 == null;
		boolean sameLength = false;
		boolean equalNodes = true;

		if (!bothNull)
		{
			sameLength = nodeList1.getLength() == nodeList2.getLength();

			if (sameLength)
				for (int i = 0; i < nodeList1.getLength(); i++)
					equalNodes = equalNodes && isEqualNode(nodeList1.item(i), nodeList2.item(i));
		}

		return bothNull || (sameLength && equalNodes);
	}

	public static void flow(InputStream is, OutputStream os, int bufSize) throws IOException
	{
		int numRead;
		byte[] buf = new byte[bufSize];

		while ((numRead = is.read(buf)) >= 0)
		{
			os.write(buf, 0, numRead);
		}
	}

	public static String inputStreamToString(InputStream is) throws IOException
	{
		int numRead;
		StringBuffer result = new StringBuffer();
		byte[] buf = new byte[1024];

		while ((numRead = is.read(buf)) >= 0)
		{
			result.append(new String(buf, 0, numRead));
		}

		return result.toString();
	}

	public static String genericToString(Object o)
	{
		StringBuffer result = new StringBuffer("[");

		Class<?> c = o.getClass();
		result.append(c.getSimpleName());
		result.append(" ");

		Method[] methods = c.getDeclaredMethods();

		for (Method m : methods)
		{
			if ((m.getModifiers() & Modifier.PUBLIC) > 0 && m.getParameterTypes().length == 0 && !m.getName().equals("toString"))
			{
				try
				{
					Object value = m.invoke(o);
					if (value != null)
					{
						result.append(m.getName());
						result.append("=");
						result.append(m.invoke(o));
						result.append(" ");
					}
				}
				catch (Exception e)
				{
					continue;
				}

			}
		}

		result.append("]");
		return result.toString();
	}
	
	public static byte[] longToMbUInt32(long integer)
	{
		List<Byte> multiByteInteger = new ArrayList<Byte>();
		int mask = 0x7F;

		while (integer > 0)
		{
			byte sevenBits = (byte) (integer & mask);
			multiByteInteger.add(sevenBits);
			integer >>= 7;
		}
		Collections.reverse(multiByteInteger);
		byte[] result = new byte[multiByteInteger.size()];

		for (int i = 0; i < result.length; i++)
		{
			byte value;
			if (i < result.length - 1)
			{
				value = (byte) (multiByteInteger.get(i) | 0x80);
			}
			else
			{
				value = (byte) multiByteInteger.get(i);
			}
			result[i] = value;
		}

		return result;
	}
}
