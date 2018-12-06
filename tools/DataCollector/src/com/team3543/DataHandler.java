package com.team3543;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;

public class DataHandler
{
	private PrintWriter pw;
	private Stopwatch st;
	
	public DataHandler(Stopwatch st)
	{
		System.out.printf("[%.3f] Initializing DataHandler...\n", st.elapsedTime());
		this.st = st;
		System.out.printf("[%.3f] DataHandler init complete!\n", st.elapsedTime());
	}
	
	public DataHandler(Stopwatch st, String fileName)
	{
		this.st = st;
		System.out.printf("[%.3f] Initializing DataHandler with file %s...\n", st.elapsedTime(), fileName);
		try 
		{
			pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("[%.3f] DataHandler init complete!\n", st.elapsedTime());
	}
	
	public boolean setFile(String fileName)
	{
		System.out.printf("[%.3f] Setting DataHandler to file %s...\n", st.elapsedTime(), fileName);
		try 
		{
			pw.close();
			pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
			System.out.printf("[%.3f] Changed DataHandler file name to %s.\n", st.elapsedTime());
			return true;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean appendHeader(String header)
	{
		if (header != null && pw != null)
		{
			System.out.printf("[%.3f] Writing header \"%s\" to CSV File...\n", st.elapsedTime(), header);
			try
			{
				pw.println(header);
				pw.flush();
				System.out.printf("[%.3f] Header appended.\n", st.elapsedTime());
				return true;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean writeToCsvFile(JSONArray lines)
	{
		if (lines != null && pw != null)
		{
			System.out.printf("[%.3f] Writing %d entries to CSV File...\n", st.elapsedTime(), lines.length());
			try
			{
				for (Object line : lines)
				{
					System.out.printf("[%.3f] Wrote : \"%s\"\n", st.elapsedTime(), line.toString());
					pw.println(line.toString());
				}
				pw.flush();
				System.out.printf("[%.3f] %d CSV entries written!.\n", st.elapsedTime(), lines.length());
				return true;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public void close()
	{
		if (pw != null)
		{
			pw.close();
		}
	}
	
	public static String getTimeStamp(String format)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		return dateFormat.format(new Date());
	}

	public static String getFileName(String username)
	{
		return username + "_" + getTimeStamp("yyyyMMdd@HHmmss") + ".csv";
	}
}
