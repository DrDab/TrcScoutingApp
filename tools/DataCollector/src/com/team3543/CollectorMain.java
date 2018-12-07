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
