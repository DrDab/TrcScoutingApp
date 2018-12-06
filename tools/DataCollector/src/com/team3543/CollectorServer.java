package com.team3543;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;

public class CollectorServer
{
	private Stopwatch st;
	private DataHandler dh;
	private LoginHandler lh;
	private ServerSocket ssock;
	
	public CollectorServer(Stopwatch st, DataHandler dh, LoginHandler lh, int port) throws IOException
	{
		this.st = st;
		this.dh = dh;
		this.lh = lh;
		System.out.printf("[%.3f] Collector Server initialized.\n", st.elapsedTime());
		ssock = new ServerSocket(port);
	}
	
	public void run()
	{
		System.out.printf("[%.3f] Collector Server Listening on port %d...\n", st.elapsedTime(), ssock.getLocalPort());
	    while (true) 
	    {
	    	Socket sock = null;
			try 
			{
				sock = ssock.accept();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			System.out.printf("[%.3f] Sensor COwOnnected UwU, IP: %s\n", st.elapsedTime(), sock.getRemoteSocketAddress().toString());
	        new Thread(new CollectorServerThread(sock, st, dh, lh)).start();
	    }
	}
	
}
class CollectorServerThread implements Runnable
{
	private Socket sock;
	private Stopwatch st;
	private DataHandler dh;
	private LoginHandler lh;
	private BufferedWriter bw;
	
	boolean isclosed = false;
	
	public CollectorServerThread(Socket sock, Stopwatch st, DataHandler dh, LoginHandler lh)
	{
		this.sock = sock;
		this.st = st;
		this.dh = dh;
		this.lh = lh;
	}

	@Override
	public void run() 
	{
		try
		{
			StringTokenizer stok = null;
			String receiveMessage;
			bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			InputStream istream = sock.getInputStream();
			BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
			boolean isSatisfied = false;
			String username = "";
			String password = "";
			bw.write("reqauth\r\n");
			bw.flush();
			for(;;)
			{
				try
				{	
					if (sock.isClosed())
					{
						isclosed = true;
						break;
					}
					if (isclosed)
					{
						break;
					}
					receiveMessage = null;
					receiveMessage = receiveRead.readLine();
					if (receiveMessage != null)
					{
						if (sock.isClosed())
						{
							isclosed = true;
							break;
						}
						if (isclosed)
						{
							break;
						}
						if (receiveMessage.indexOf("login") != -1 && !isSatisfied)
						{
							stok = new StringTokenizer(receiveMessage);
							try
							{
								stok.nextToken();
								username = stok.nextToken();
								password = stok.nextToken();
								System.out.printf("[%.3f] Login attempted from IP %s: Username: %s Password: %s\n", st.elapsedTime(), sock.getRemoteSocketAddress().toString(), username, password);
								if (lh.isValid(username, password))
								{
									System.out.printf("[%.3f] Successful login from IP %s\n", st.elapsedTime(), sock.getRemoteSocketAddress().toString());
									isSatisfied = true;
									bw.write("auth_successful\r\n");
									bw.flush();
								}
								else
								{
									System.out.printf("[%.3f] Incorrect login from IP %s\n", st.elapsedTime(), sock.getRemoteSocketAddress().toString());
									bw.write("auth_failed\r\n");
									bw.flush();
								}
							}
							catch (Exception e)
							{
								System.out.printf("[%.3f] Login authentication error from IP %s: %s\n", st.elapsedTime(), sock.getRemoteSocketAddress().toString(), e);
								bw.write("auth_failed\r\n");
								bw.flush();
							}
						}
						if (!isSatisfied)
						{
							bw.write("reqauth\r\n");;
							bw.flush();
						}
						else
						{
							if (receiveMessage.equals("bye"))
							{
								isclosed = true;
							}
							else if (receiveMessage.indexOf("[") != -1)
							{
								try
								{
									JSONArray jsonData = new JSONArray(receiveMessage);
									System.out.printf("[%.3f] Began receiving JSON data from device %s, Username: %s, Password: %s\n", st.elapsedTime(), sock.getRemoteSocketAddress().toString(), username, password);
									if (dh.writeToCsvFile(jsonData))
									{
										System.out.printf("[%.3f] Downloading JSON data from %s successful, Username: %s, Password: %s\n", st.elapsedTime(), sock.getRemoteSocketAddress().toString(), username, password);
										bw.write("json_success\r\n");
									}
									else
									{
										System.out.printf("[%.3f] Error while receiving JSON data from device %s, Username: %s, Password: %s\n", st.elapsedTime(), sock.getRemoteSocketAddress().toString(), username, password);
										bw.write("json_failure\r\n");
									}
									bw.flush();
								}
								catch (JSONException jse0)
								{
									System.out.printf("[%.3f] Received malformed JSON data from device %s, Username: %s, Password: %s\n", st.elapsedTime(), sock.getRemoteSocketAddress().toString(), username, password);
									bw.write("json_failure\r\n");
									bw.flush();
								}
							}
						}
					}
					else
					{
						isclosed = true;
						break;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					System.out.printf("[%.3f] CollectorServerThread was interrupted: %s\n", st.elapsedTime(), e);
					isclosed = true;
					break;
				}
			}
			if (isclosed)
			{
				System.out.printf("[%.3f] Disconnecting device, IP: %s\n", st.elapsedTime(), sock.getRemoteSocketAddress().toString());
				sock.close();
			}
		}
		catch (IOException ioe)
		{
			System.out.printf("[%.3f] CollectorServerThread failed: %s\n", st.elapsedTime(), ioe);
		}
		
	}
}