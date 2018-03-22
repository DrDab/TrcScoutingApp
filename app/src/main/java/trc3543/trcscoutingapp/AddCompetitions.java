package trc3543.trcscoutingapp;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@SuppressWarnings("all")
public class AddCompetitions extends AppCompatActivity
{
    /**
     *
     *  Copyright (c) 2018 Titan Robotics Club, _c0da_ (Victor Du)
     *
     *	Permission is hereby granted, free of charge, to any person obtaining a copy
     *	of this software and associated documentation files (the "Software"), to deal
     *	in the Software without restriction, including without limitation the rights
     *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
     *	copies of the Software, and to permit persons to whom the Software is
     *	furnished to do so, subject to the following conditions:
     *
     *	The above copyright notice and this permission notice shall be included in all
     *	copies or substantial portions of the Software.
     *
     *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
     *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
     *	SOFTWARE.
     */

    public static final boolean MAKE_CHANGES_READ_ONLY = false;

    static ArrayAdapter<String> adapter;
    static ListView contestList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_competitions);

        // verifyStoragePermissions(this);

        Log.d("FileIO","External Storage Directory: " + Environment.getExternalStorageDirectory().toString());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contestList = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter < String >
                (AddCompetitions.this, android.R.layout.simple_list_item_1,
                        DataStore.contests);

        contestList.setAdapter(adapter);
        contestList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int position, long arg3)
            {
                // position = position selected
                AlertDialog alertDialog = new AlertDialog.Builder(AddCompetitions.this).create();
                alertDialog.setTitle("Game Information");
                if (DataStore.CsvFormattedContests.size() >= 1)
                {
                    if (MAKE_CHANGES_READ_ONLY)
                    {
                        String s = DataStore.CsvFormattedContests.get(position);
                        alertDialog.setMessage(s);
                        alertDialog.show();
                    }
                    else
                    {
                        openCompNamePrompt(true, position);
                    }
                }
                else
                {
                    alertDialog.setMessage("No Games Yet");
                    alertDialog.show();
                }

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // place a message
                Snackbar.make(view, "Enter competition information please", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                openCompNamePrompt(false, -1);

            }
        });

        if (DataStore.contests.size() == 0)
        {
            addToList("No Entries Yet");
        }

        // check if user information is saved. if not, open the settings window.
        boolean openSettingsCondition = false;
        if (!DataStore.existsSave())
        {
            File writeDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
            if (!writeDirectory.exists())
            {
                Log.d("FileIO", "Creating write directory: " + writeDirectory.toString());
                writeDirectory.mkdir();
            }
            File log = new File(writeDirectory, "settings.coda");
            if(!log.exists())
            {
                try {
                    Log.d("FileIO", "Creating settings file: " + log.toString());
                    log.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }
        else
        {
            // if file exists, check that all data is entered.
            File writeDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
            if (!writeDirectory.exists())
            {
                writeDirectory.mkdir();
            }
            File log = new File(writeDirectory, "settings.coda");
            if(!log.exists())
            {
                try
                {
                    Log.d("FileIO", "Creating settings file: " + log.toString());
                    log.createNewFile();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            BufferedReader br = null;
            try
            {
                br = new BufferedReader(new FileReader(log));
            }
            catch (FileNotFoundException e)
            {
                openSettingsCondition = true;
            }
            try
            {
                for(int i = 0; i < 6; i++)
                {
                    if (br.readLine() == null)
                    {
                        openSettingsCondition = true;
                    }
                }
            }
            catch (IOException e)
            {
                openSettingsCondition = true;
            }
        }
        // if all user data is not entered, open a settings screen. (This is if older versions are upgraded)
        if (openSettingsCondition)
        {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }

        // start another thread to automatically save.
        try
        {
            DataStore.parseAutoSaveBoolean();
            DataStore.parseAutoSaveTime();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Runnable autosaverunnable = new Runnable()
        {
            @Override
            public void run()
            {
                AutoSaveThread autosave = new AutoSaveThread();
                autosave.run();
            }
        };
        new Thread(autosaverunnable).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_competitions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about)
        {
            // popup about screen
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_makecsv)
        {
            String filename = DataStore.FIRST_NAME+"_"+DataStore.LAST_NAME+"_results.csv";
            try
            {
                DataStore.writeContestsToCsv(filename);
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else if (id == R.id.action_mailcsv)
        {
            // mail the CSV
            try
            {
                final String filename = DataStore.FIRST_NAME+"_"+DataStore.LAST_NAME+"_results.csv";
                final String[] recipient = {""};
                final EditText txtUrl = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle("Mail results")
                        .setMessage("Please enter the recipient's email.")
                        .setView(txtUrl)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                recipient[0] = txtUrl.getText().toString();
                                sendEmailWithCSV(filename, recipient[0]);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
            }
            catch (Exception arg0)
            {
                // TODO Auto-generated catch block
                arg0.printStackTrace();
            }
        }
        else if (id == R.id.action_ac)
        {
            // clear the screen
            // ask for user confirmation
            new AlertDialog.Builder(this)
                    .setTitle("Are you sure?")
                    .setMessage("Are you sure you want to clear the contest history?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                           DataStore.CsvFormattedContests.clear();
                           DataStore.contests.clear();
                           adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })
                    .show();

        }
        else if (id == R.id.action_config)
        {
            // popup settings window
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_autosave_set)
        {
            // popup automatic save settings window
            Intent intent = new Intent(this, AutoSaveSettings.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void addToList(String s)
    {
        if (DataStore.contests.contains("No Entries Yet"))
        {
            removeFromList("No Entries Yet");
        }
        DataStore.contests.add(s);
        adapter.notifyDataSetChanged();
    }

    public static void resetListItem(String s, int pos)
    {
        DataStore.contests.set(pos, s);
        adapter.notifyDataSetChanged();
    }

    public static void removeFromList(String s)
    {
        DataStore.contests.remove(s);
        adapter.notifyDataSetChanged();
    }

    public void openCompNamePrompt(boolean modifyingExisting, int option)
    {
        Intent intent = null;
        if (!modifyingExisting)
        {
            intent = new Intent(this, SetCompetitionName.class);
            intent.putExtra("EditOption", -1 + "");
        }
        else
        {
            intent = new Intent(this, SetCompetitionName.class);
            intent.putExtra("EditOption", option + "");
        }
        startActivity(intent);
    }
    public void sendEmailWithCSV(String filename0, String target)
    {
        try
        {
            File writeDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
            if (!writeDirectory.exists())
            {
                writeDirectory.mkdir();
            }
            File filelocation = new File(writeDirectory, filename0);
            Uri path = Uri.fromFile(filelocation);
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("vnd.android.cursor.dir/email");
            String to[] = {target};
            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
            emailIntent.putExtra(Intent.EXTRA_STREAM, path);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Robotics Scouting Results");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity)
    {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Log.d("FileIO", "Checking File I/O Permissions...");
        if (permission != PackageManager.PERMISSION_GRANTED)
        {
            // We don't have permission so prompt the user
            Log.d("FileIO", "File permissions insufficient, requesting privileges...");
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}

