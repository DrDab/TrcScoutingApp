package trc3543.trcscoutingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.Buffer;

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

            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        Socket sock = new Socket(ip, port);
                        sock.setSoTimeout(5000);
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
                        InputStream istream = sock.getInputStream();
                        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
                        boolean isclosed = false;
                        String receiveMessage = null;
                        for(;;)
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
                                    runOnUiThread(new Runnable()
                                    {

                                        @Override
                                        public void run()
                                        {
                                            statusBox.setText("[FAIL] Incorrect login.\nPlease check your username and password, or contact the sysadmin.");
                                        }
                                    });
                                    isclosed = true;
                                }
                                else if (receiveMessage.equals("json_success"))
                                {
                                    runOnUiThread(new Runnable()
                                    {

                                        @Override
                                        public void run()
                                        {
                                            statusBox.setText(String.format("[SUCCESS] %d entries sent successfully!", DataStore.matchList.size()));
                                        }
                                    });
                                    isclosed = true;
                                }
                                else
                                {
                                    runOnUiThread(new Runnable()
                                    {

                                        @Override
                                        public void run()
                                        {
                                            statusBox.setText("[FAIL] JSON data is malformed, or an error happened.");
                                        }
                                    });
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
                            sock.close();
                        }
                    }
                    catch (final Exception e)
                    {
                        runOnUiThread(new Runnable()
                        {

                            @Override
                            public void run()
                            {
                                statusBox.setText(e.toString());
                            }
                        });
                    }
                }
            }).start();

        }
        catch (Exception e)
        {
            statusBox.setText(e.toString());
        }
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

    public void saveCredentials(String ip, int port, String username, String password)
    {

    }
}
