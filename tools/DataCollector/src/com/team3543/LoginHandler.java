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
