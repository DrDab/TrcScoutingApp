package com.team3543;

import java.io.IOException;

public class CollectorMain
{
	public static final String CSV_HEADER = "Contains Your Team, Date, Match #, Competition Type, Team Number, Spectating Team, Starting Position, AT-Robot Lowered, AT-Mineral Displaced, AT-Mineral Correct, AT-Marker Deployed, AT-Parked In Crater, TO-Depot Score, TO-Lander Score, EG-Ending Location, Match Won, Autonomous Notes, TeleOp Notes";
	
	private static Stopwatch stp;

	public static void main(String[] args) 
	{
		stp = new Stopwatch();
		CollectorServer listener;
		LoginHandler loginHandler = new LoginHandler("logins.awoo");
		try 
		{
			listener = new CollectorServer(stp, loginHandler, 3621);
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
}
