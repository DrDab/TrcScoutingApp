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

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.os.Environment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import trc3543.trcscoutingapp.threads.AutoSaveThread;
import trc3543.trcscoutingapp.data.DataStore;
import trc3543.trcscoutingapp.data.MatchInfo;
import trc3543.trcscoutingapp.R;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@SuppressWarnings("all")
public class AddMatches extends AppCompatActivity
{
    public static final int WRITE_EXT_STORAGE_PERM_CODE = 621;

    static ArrayAdapter<MatchInfo> adapter;
    static ListView contestList;

    private Runnable autosaverunnable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matches);

        Log.d("FileIO", "External Storage Directory: " + Environment.getExternalStorageDirectory().toString());

        NfcManager nfcmanager = (NfcManager) getApplicationContext().getSystemService(Context.NFC_SERVICE);
        NfcAdapter nfcadapter = nfcmanager.getDefaultAdapter();
        DataStore.deviceSupportsNfc = nfcadapter != null && nfcadapter.isEnabled();

        // let's check if we have file permissions before running.
        if (verifySystemPermissions(this))
        {
            initializeAppElements();
        }
        else
        {
            AlertDialog alertDialog1 = new AlertDialog.Builder(AddMatches.this).create();
            alertDialog1.setTitle("Permission Request");
            alertDialog1.setMessage("Please enable external storage permission.");
            alertDialog1.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(AddMatches.this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, WRITE_EXT_STORAGE_PERM_CODE);
                }
            });
            alertDialog1.show();
        }
    }

    public void initializeAppElements()
    {
        try
        {
            DataStore.readArraylistsFromJSON();
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contestList = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter<MatchInfo>(AddMatches.this, android.R.layout.simple_list_item_1, DataStore.matchList);

        contestList.setAdapter(adapter);
        contestList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int position, long arg3)
            {
                openMatchEditPrompt(true, position);
            }
        });
        contestList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l)
            {
                new AlertDialog.Builder(AddMatches.this)
                        .setTitle("Are you sure?")
                        .setMessage("Are you sure you want to delete this element?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                removeFromList(i);
                                try
                                {
                                    DataStore.writeArraylistsToJSON();
                                }
                                catch (IOException | JSONException e)
                                {
                                    e.printStackTrace();
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                            }
                        })
                        .show();
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // if the floating plus button is clicked, open the prompt to add a match.
                openMatchEditPrompt(false, -1);
            }
        });

        // check if user information is saved. if not, open the settings window.
        boolean openSettingsCondition = false;
        if (!DataStore.existsSave())
        {
            File writeDirectory = new File(Environment.getExternalStorageDirectory(), DataStore.DATA_FOLDER_NAME);
            if (!writeDirectory.exists())
            {
                Log.d("FileIO", "Creating write directory: " + writeDirectory.toString());
                writeDirectory.mkdir();
            }
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }
        else
        {
            // if file exists, check that all data is entered.
            File writeDirectory = new File(Environment.getExternalStorageDirectory(), DataStore.DATA_FOLDER_NAME);
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
                for(int i = 0; i < 3; i++)
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
        else
        {
            try
            {
                DataStore.parseAutoSaveInfo();
                DataStore.parseUserInfoGeneral();
                DataStore.parseServerLoginData();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if (!DataStore.autoSaveRunnableInit)
        {
            autosaverunnable = new Runnable()
            {
                @Override
                public void run()
                {
                    AutoSaveThread autosave = new AutoSaveThread();
                    autosave.run();
                }
            };
            new Thread(autosaverunnable).start();
            DataStore.autoSaveRunnableInit = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_add_competitions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_about:
                Intent intent = new Intent(this, About.class);
                startActivity(intent);
                return true;

            case R.id.action_makecsv:
                String filename = DataStore.getFileName(DataStore.firstName + "_" + DataStore.lastName);
                try
                {
                    DataStore.writeContestsToCsv(filename);
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                new AlertDialog.Builder(this)
                        .setTitle("CSV Write Successful")
                        .setMessage("Scouting data written to file \"" + filename + "\"")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                            }
                        })
                        .show();
                return true;

            case R.id.action_transmitresults:
                intent = new Intent(this, SendReport.class);
                startActivity(intent);
                return true;

            case R.id.action_ac:
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure?")
                        .setMessage("Are you sure you want to delete all matches in this session?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                DataStore.matchList.clear();
                                try
                                {
                                    DataStore.writeArraylistsToJSON();
                                }
                                catch (IOException | JSONException e)
                                {
                                    e.printStackTrace();
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                            }
                        })
                        .show();
                return true;

            case R.id.action_config:
                intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;

            case R.id.action_autosave_set:
                intent = new Intent(this, AutoSaveSettings.class);
                startActivity(intent);
                return true;

            case R.id.action_qrsend:
                intent = new Intent(this, QrDataSender.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static synchronized void addToList(MatchInfo matchInfo)
    {
        synchronized (DataStore.matchList)
        {
            DataStore.matchList.add(matchInfo);
            adapter.notifyDataSetChanged();
        }
    }

    public static synchronized void resetListItem(MatchInfo matchInfo, int pos)
    {
        synchronized (DataStore.matchList)
        {
            DataStore.matchList.set(pos, matchInfo);
            adapter.notifyDataSetChanged();
        }
    }

    public static synchronized void removeFromList(int index)
    {
        synchronized (DataStore.matchList)
        {
            DataStore.matchList.remove(index);
            adapter.notifyDataSetChanged();
        }
    }

    public static synchronized boolean listEmpty()
    {
        synchronized (DataStore.matchList)
        {
            if (DataStore.matchList.size() == 0)
            {
                return true;
            }
        }

        return false;
    }

    public void openMatchEditPrompt(boolean modifyingExisting, int option)
    {
        Intent intent = null;
        if (!modifyingExisting)
        {
            MatchInfo prev = DataStore.matchList == null ? null :
                    DataStore.matchList.size() > 0 ?
                            DataStore.matchList.get(DataStore.matchList.size() - 1) : null;
            intent = new Intent(this, SetMatchInfo.class);
            intent.putExtra("EditOption", -1);
            if (prev != null)
            {
                intent.putExtra("PrevMatch", prev.matchNumber);
                intent.putExtra("PrevAlliance", prev.alliance);
                intent.putExtra("PrevMatchType", prev.matchType);
            }
        }
        else
        {
            intent = new Intent(this, SetMatchInfo.class);
            intent.putExtra("EditOption", option);
        }
        startActivity(intent);
    }

    public static boolean verifySystemPermissions(Activity activity)
    {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == WRITE_EXT_STORAGE_PERM_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                initializeAppElements();
            else
                return;
        }
    }

}

