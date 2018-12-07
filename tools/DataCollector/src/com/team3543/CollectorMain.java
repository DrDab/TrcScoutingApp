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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;

public class CollectorMain
{
	public static int port = 3621;
	public static String csvHeader = "";
	
	private static Stopwatch stp;

	public static void main(String[] args) 
	{
		System.out.println("TRC Scouting App - Data Collecter v1.0 (C) Victor Du 2018");
		stp = new Stopwatch();
		parseConfiguration("config.json");
		CollectorServer listener;
		LoginHandler loginHandler = new LoginHandler("logins.awoo");
		try 
		{
			listener = new CollectorServer(stp, loginHandler, port);
			new Thread(new Runnable() 
			{
				public void run() 
				{
					listener.run();
				}
			}
			).start();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static boolean parseConfiguration(String fileName)
	{
		try
		{
			String accuminput = "";
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			System.out.printf("[%.3f] Opened configuration file %s...\n", stp.elapsedTime(), fileName);
			String input = null;
			while ((input = br.readLine()) != null)
			{
				accuminput += (input + "\n");
			}
			br.close();
			JSONObject confObj = new JSONObject(accuminput);
			port = confObj.getInt("port");
			csvHeader = confObj.getString("header");
			System.out.printf("[%.3f] Successfully parsed settings from %s.\n", stp.elapsedTime(), fileName);
			System.out.printf("[%.3f] Port set to: %d\n[%.3f] Excel Header:\"%s\"\n", stp.elapsedTime(), port, stp.elapsedTime(), csvHeader);
			return true;
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
