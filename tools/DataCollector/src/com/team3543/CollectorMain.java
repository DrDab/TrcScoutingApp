package com.team3543;

import java.io.IOException;

public class CollectorMain
{
	public static final String CSV_NAME = "compiled.csv";
	
	private static Stopwatch stp;

	public static void main(String[] args) 
	{
		stp = new Stopwatch();
		CollectorServer listener;
		DataHandler dataHandler = new DataHandler(stp, CSV_NAME);
		LoginHandler loginHandler = new LoginHandler("logins.awoo");
		dataHandler.appendHeader("Contains Your Team, Date, Match #, Competition Type, Team Number, Spectating Team, Starting Position, AT-Robot Lowered, AT-Mineral Displaced, AT-Mineral Correct, AT-Marker Deployed, AT-Parked In Crater, TO-Depot Score, TO-Lander Score, EG-Ending Location, Match Won, Autonomous Notes, TeleOp Notes");
		try 
		{
			listener = new CollectorServer(stp, dataHandler, loginHandler, 3621);
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
