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
import java.util.HashMap;

public class LoginHandler 
{
	private HashMap<String, String> loginMap = new HashMap<String, String>();
	private BufferedReader br = null;
	
	public LoginHandler(String fileName)
	{
		try
		{
			br = new BufferedReader(new FileReader(fileName));
			String input = null;
			while ((input = br.readLine()) != null)
			{
				String[] parts = input.split(" ");
				loginMap.put(parts[0], parts[1]);
			}
			br.close();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized boolean setFile(String fileName)
	{
		try 
		{
			br.close();
			br = new BufferedReader(new FileReader(fileName));
			return true;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	} 
	
	public synchronized boolean isValid(String username, String password)
	{
		if (loginMap.containsKey(username))
		{
			if (loginMap.get(username).equals(password))
			{
				return true;
			}
		}
		return false;
	}
}
