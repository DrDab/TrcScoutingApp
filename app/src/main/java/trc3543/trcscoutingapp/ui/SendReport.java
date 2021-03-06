/*
 * Copyright (c) 2017-2019 Titan Robotics Club
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

package trc3543.trcscoutingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import trc3543.trcscoutingapp.data.DataStore;
import trc3543.trcscoutingapp.R;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SendReport extends AppCompatActivity
{
    private EditText ipBox = null;
    private EditText portBox = null;
    private EditText usernameBox = null;
    private EditText passwordBox = null;
    private TextView statusBox = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_report);
        setTitle("Send Reports");

        ipBox = (EditText) findViewById(R.id.serverIPForm);
        portBox = (EditText) findViewById(R.id.serverPortForm);
        usernameBox = (EditText) findViewById(R.id.usernameForm);
        passwordBox = (EditText) findViewById(R.id.passwordForm);
        statusBox = (TextView) findViewById(R.id.uploaderStatusBox);

        if (DataStore.serverIP != null && DataStore.username != null && DataStore.password != null)
        {
            ipBox.setText(DataStore.serverIP);
            portBox.setText(DataStore.serverPort + "");
            usernameBox.setText(DataStore.username);
            passwordBox.setText(DataStore.password);
        }
    }

    public void onSendClicked(View vue)
    {
        // save the login data and then upload it.

        try
        {
            final String ip = ipBox.getText().toString();
            final int port = Integer.parseInt(portBox.getText().toString());
            final String username = usernameBox.getText().toString();
            final String password = passwordBox.getText().toString();
            saveCredentials(ip, port, username, password);

            //
            // after this, send the data.
            //

            final Thread sendThread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        final Socket sock = new Socket();
                        sock.connect(new InetSocketAddress(ip, port), 5000);
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
                        InputStream istream = sock.getInputStream();
                        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
                        boolean isclosed = false;
                        String receiveMessage = null;

                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                try
                                {
                                    Thread.sleep(5000);
                                    if (!sock.isClosed())
                                    {
                                        sock.close();
                                    }
                                }
                                catch (InterruptedException ie)
                                {
                                    ie.printStackTrace();
                                }
                                catch (IOException ioe)
                                {
                                    ioe.printStackTrace();
                                }
                            }
                        }
                        ).start();

                        for(;;)
                        {
                            updateStatusDisplay(String.format("Connecting to server %s:%d...", ip, port));
                            if (sock.isClosed())
                            {
                                isclosed = true;
                                break;
                            }
                            if (isclosed)
                            {
                                break;
                            }
                            receiveMessage = receiveRead.readLine();
                            if (receiveMessage != null)
                            {
                                if (receiveMessage.equals("reqauth"))
                                {
                                    bw.write("login " + username + " " + password + "\n");
                                    bw.flush();
                                }
                                else if (receiveMessage.equals("auth_successful"))
                                {
                                    JSONArray csvContestsArray = new JSONArray();
                                    for(int i = 0; i < DataStore.matchList.size(); i++)
                                    {
                                        csvContestsArray.put(DataStore.matchList.get(i).getCsvString());
                                    }
                                    bw.write(csvContestsArray.toString() + "\n");
                                    bw.flush();
                                }
                                else if (receiveMessage.equals("auth_failed"))
                                {
                                    updateStatusDisplay("[FAIL] Incorrect login.\nPlease check your username and password, or contact the sysadmin.");
                                    isclosed = true;
                                }
                                else if (receiveMessage.equals("json_success"))
                                {
                                    updateStatusDisplay(String.format("[SUCCESS] %d entries sent successfully!", DataStore.matchList.size()));
                                    isclosed = true;
                                }
                                else
                                {
                                    updateStatusDisplay("[FAIL] JSON data is malformed, or an error happened.");
                                    isclosed = true;
                                }

                                if (sock.isClosed())
                                {
                                    isclosed = true;
                                    break;
                                }
                                if (isclosed)
                                {
                                    break;
                                }
                            }
                        }
                        if (isclosed)
                        {
                            receiveRead.close();
                            istream.close();
                            bw.close();
                            sock.close();
                        }
                    }
                    catch (final Exception e)
                    {
                        updateStatusDisplay(e.toString());
                    }
                }
            });

            sendThread.start();

        }
        catch (Exception e)
        {
            statusBox.setText(e.toString());
        }
    }

    public void updateStatusDisplay(final String status)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                statusBox.setText(status);
            }
        });
    }

    public void onSaveClicked(View vue)
    {
        // save the login data to disk
        try
        {
            String ip = ipBox.getText().toString();
            int port = Integer.parseInt(portBox.getText().toString());
            String username = usernameBox.getText().toString();
            String password = passwordBox.getText().toString();
            saveCredentials(ip, port, username, password);
        }
        catch (Exception e)
        {
            statusBox.setText(e.toString());
        }
    }

    public synchronized void saveCredentials(String ip, int port, String username, String password)
    {
        DataStore.serverIP = ip;
        DataStore.serverPort = port;
        DataStore.username = username;
        DataStore.password = password;
        try
        {
            DataStore.writeServerLoginData();
            statusBox.setText("*** Login saved. ***");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
