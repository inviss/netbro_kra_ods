package kr.co.netbro.kra.socket.maker;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class Util {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.(E)", Locale.KOREAN);
	private static final SimpleDateFormat munjaFormat = new SimpleDateFormat("yyyyMMdd");
	private static Properties p;
	
	public static String format(long number)
	{
		if (number < 1000L) {
			return "" + number;
		}
		return format(number / 1000L) + ',' + new StringBuilder().append(number % 1000L + 1000L).append("").toString().substring(1);
	}

	public static String checkNull(String s)
	{
		if (s == null) {
			return "0";
		}
		return s;
	}

	public static String removeSpace(String s)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if (c != ' ') {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static Properties loadProperties(String file)
	{
		//BufferedInputStream bis = null;
		InputStream bis = null;
		p = new Properties();
		try {
			//bis = new BufferedInputStream(new FileInputStream(file));
			bis = ClassLoader.class.getResourceAsStream(file);
			p.load(bis);

			return p;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return p;
	}

	public static String getValidString(boolean[] v)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 9; i++) {
			if (v[i] != true) {   // ##############################################
				sb.append(i);
			} else {
				sb.append('-');
			}
		}
		return sb.toString();
	}

	public static char replaceZero(char a)
	{
		return a == '0' ? ' ' : a;
	}

	public static String addZero(String s)
	{
		return s.length() == 1 ? "0" + s : s;
	}

	public static String addSpace(String s)
	{
		return s.length() == 1 ? " " + s : s;
	}

	public static int indexOf(String[] array, String v)
	{
		if ((array == null) || (v == null)) {
			return -1;
		}
		for (int i = 0; i < array.length; i++) {
			if ((array[i] != null) && (v.equals(array[i]))) {
				return i;
			}
		}
		return -1;
	}

	public static List<String> readFile(File file)
	{
		List<String> list = new ArrayList<String>();
		try
		{
			BufferedReader r = new BufferedReader(new FileReader(file));
			String str;
			while ((str = r.readLine()) != null) {
				list.add(str);
			}
			r.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public static String getDateString()
	{
		return dateFormat.format(new Date());
	}

	public static String getLastDate(String date)
	{
		try
		{
			Date d = munjaFormat.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			int dow = c.get(7);
			int beforeDays;
			if ((dow == 1) || (dow == 7)) {
				beforeDays = 1;
			} else {
				beforeDays = dow - 1;
			}
			c.add(5, -beforeDays);
			return munjaFormat.format(c.getTime());
		}
		catch (Exception ex) {}
		return date;
	}

	public static byte[] readBytes(File file)
			throws Exception
	{
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try
		{
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			int size = (int)file.length();
			byte[] data = new byte[size];

			return data;
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				bis.close();
				fis.close();
			} catch (Exception ex) {
				throw ex;
			}
		}
	}
	
	public static byte[] toBytes(char[] chars) {
	    CharBuffer charBuffer = CharBuffer.wrap(chars);
	    ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
	    byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
	            byteBuffer.position(), byteBuffer.limit());
	    Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
	    Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
	    return bytes;
	}
}
