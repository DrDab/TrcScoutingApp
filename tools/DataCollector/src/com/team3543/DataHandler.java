/*
 * Copyright (c) 2018 Victor Du, Titan Robotics Club
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
