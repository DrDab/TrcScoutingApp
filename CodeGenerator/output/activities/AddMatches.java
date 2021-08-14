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

package trc3543.trcscoutingapp.activities;

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
import trc3543.trcscoutingapp.data.AppInfo;
import trc3543.trcscoutingapp.data.AppSettings;
import trc3543.trcscoutingapp.threads.AutoSaveThread;
import trc3543.trcscoutingapp.data.IOUtils;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static trc3543.trcscoutingapp.data.AppInfo.DATA_FOLDER_NAME;

@SuppressWarnings("all")
public class AddMatches extends AppCompatActivity
{
    public static final String MODULE_NAME = "AddMatches";

    // permission codes
    public static final int WRITE_EXT_STORAGE_PERM_CODE = 621;

    // activity request code
    public static final int LAUNCH_SETMATCHINFO_REQUEST = 100;
    public static final int LAUNCH_SETTINGS_REQUEST = 101;
    public static final int LAUNCH_SENDER_REQUEST = 102;

    private List<MatchInfo> matchList;
    private ArrayAdapter<MatchInfo> adapter;
    private ListView matchListView;
    private boolean elementsInitialized;

    private AppSettings settings;

    private Runnable autosaverunnable;
    private boolean autoSaveRunnableInit;

    private boolean deviceSupportsNfc;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matches);

        Log.d(MODULE_NAME, "External Storage Directory: " + Environment.getExternalStorageDirectory().toString());

        NfcManager nfcmanager = (NfcManager) getApplicationContext().getSystemService(Context.NFC_SERVICE);
        NfcAdapter nfcadapter = nfcmanager.getDefaultAdapter();
        deviceSupportsNfc = nfcadapter != null && nfcadapter.isEnabled();

        elementsInitialized = false;
        autoSaveRunnableInit = false;

        // let's check if we have file permissions before running.
        if (verifySystemPermissions(this))
        {
            initializeAppSettings();
        }
        else
        {
            AlertDialog alertDialog = new AlertDialog.Builder(AddMatches.this).create();
            alertDialog.setTitle("Permission Request");
            alertDialog.setMessage("Please enable external storage permission.");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    spawnExternalMemoryPermsRequest();
                }
            });
            alertDialog.show();
        }
    }

    public void initializeAppSettings()
    {
        File writeDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        if (!writeDirectory.exists())
            writeDirectory.mkdir();

        File settingsFile = new File(writeDirectory, AppInfo.SETTINGS_FILENAME);

        if (settingsFile.exists())
        {
            try
            {
                settings = IOUtils.readSettingsFromJSON(settingsFile);
                initializeElements();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            settings = new AppSettings();
            openSettingsPrompt();
        }
    }

    public void initializeElements()
    {
        Log.d(MODULE_NAME, "Initializing elements.");
        elementsInitialized = true;

        try
        {
            matchList = IOUtils.readArraylistsFromJSON(matchList);
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        matchListView = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter<MatchInfo>(AddMatches.this, android.R.layout.simple_list_item_1, matchList);

        matchListView.setAdapter(adapter);
        matchListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int position, long arg3)
            {
                openMatchEditPrompt(true, position);
            }
        });
        matchListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
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
                                    IOUtils.writeArraylistsToJSON(matchList);
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

        if (!autoSaveRunnableInit)
        {
            autosaverunnable = new Runnable()
            {
                @Override
                public void run()
                {
                    AutoSaveThread autosave = new AutoSaveThread(settings, matchList);
                    autosave.run();
                }
            };
            new Thread(autosaverunnable).start();
            autoSaveRunnableInit = true;
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
                intent.putExtra("deviceSupportsNfc", deviceSupportsNfc);
                startActivity(intent);
                return true;

            case R.id.action_makecsv:
                String filename = IOUtils.getFileName(settings.firstName + "_" + settings.lastName);
                try
                {
                    IOUtils.writeContestsToCsv(settings, matchList, filename);
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
                intent.putExtra("matchList", (ArrayList) matchList);
                intent.putExtra("appSettings", settings);
                startActivityForResult(intent, LAUNCH_SENDER_REQUEST);
                return true;

            case R.id.action_ac:
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure?")
                        .setMessage("Are you sure you want to delete all matches in this session?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                matchList.clear();
                                try
                                {
                                    IOUtils.writeArraylistsToJSON(matchList);
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
                openSettingsPrompt();
                return true;

            case R.id.action_qrsend:
                intent = new Intent(this, QrDataSender.class);
                intent.putExtra("matchList", (ArrayList) matchList);
                intent.putExtra("appSettings", settings);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
                initializeAppSettings();
            else
                spawnExternalMemoryPermsRequest();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case LAUNCH_SETMATCHINFO_REQUEST:
                    Log.d(MODULE_NAME, "SetMatchInfo activity result callback triggered with result.");
                    boolean editExisting = data.getBooleanExtra("editExisting", false);
                    int existingEntryIndex = data.getIntExtra("existingEntryIndex", -1);
                    MatchInfo matchInfo = (MatchInfo) data.getSerializableExtra("matchInfo");

                    if (editExisting)
                    {
                        Log.d(MODULE_NAME, "Resetting list entry " + existingEntryIndex);
                        resetListItem(matchInfo, existingEntryIndex);
                        Log.d(MODULE_NAME, "List entry " + existingEntryIndex + " reset.");
                    }
                    else
                    {
                        Log.d(MODULE_NAME, "Adding new entry to list.");
                        addToList(matchInfo);
                    }

                    try
                    {
                        Log.d(MODULE_NAME, "Writing updated match list to JSON file.");
                        IOUtils.writeArraylistsToJSON(matchList);
                    }
                    catch (IOException | JSONException e)
                    {
                        e.printStackTrace();
                    }
                    break;

                case LAUNCH_SETTINGS_REQUEST:
                    Log.d(MODULE_NAME, "SettingsActivity result callback triggered with result.");
                    AppSettings newSettings = (AppSettings) data.getSerializableExtra("appSettings");

                    Log.d(MODULE_NAME, "Got result from SettingsActivity.");
                    if (!elementsInitialized)
                    {
                        initializeElements();
                    }

                    File writeDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
                    File settingsFile = new File(writeDirectory, AppInfo.SETTINGS_FILENAME);

                    settings.copyFieldsFromOtherSettings(newSettings);

                    try
                    {
                        Log.d(MODULE_NAME, "Writing updated settings to JSON file.");
                        IOUtils.writeSettingsToJSON(settings, settingsFile);
                    }
                    catch (IOException | JSONException e)
                    {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED)
        {
            File writeDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
            File settingsFile = new File(writeDirectory, AppInfo.SETTINGS_FILENAME);
            switch (requestCode)
            {
                case LAUNCH_SETTINGS_REQUEST:
                    if (!settingsFile.exists())
                        openSettingsPrompt();
                    break;

                case LAUNCH_SENDER_REQUEST:
                    try
                    {
                        settings.copyFieldsFromOtherSettings(IOUtils.readSettingsFromJSON(settingsFile));
                    }
                    catch (IOException | JSONException exception)
                    {
                        exception.printStackTrace();
                    }
                    break;

            }
        }
    }

    public void openMatchEditPrompt(boolean modifyingExisting, int option)
    {
        Intent intent = new Intent(this, SetMatchInfo.class);
        if (!modifyingExisting)
        {
            MatchInfo prev = matchList == null ? null :
                    matchList.size() > 0 ?
                            matchList.get(matchList.size() - 1) : null;
            intent.putExtra("EditOption", -1);
            if (prev != null)
            {
                intent.putExtra("PrevMatch", prev.matchNumber);
		intent.putExtra("PrevAlliance", prev.alliance);
		intent.putExtra("PrevMatchType", prev.matchtype);
		
            }
        }
        else
        {
            intent.putExtra("EditOption", option);
            intent.putExtra("matchInfo", matchList.get(option));
        }

        startActivityForResult(intent, LAUNCH_SETMATCHINFO_REQUEST);
    }

    public void openSettingsPrompt()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("appSettings", settings);
        startActivityForResult(intent, LAUNCH_SETTINGS_REQUEST);
    }

    public boolean verifySystemPermissions(Activity activity)
    {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void spawnExternalMemoryPermsRequest()
    {
        ActivityCompat.requestPermissions(AddMatches.this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, WRITE_EXT_STORAGE_PERM_CODE);
    }

    public void addToList(MatchInfo matchInfo)
    {
        matchList.add(matchInfo);
        adapter.notifyDataSetChanged();
    }

    public void resetListItem(MatchInfo matchInfo, int pos)
    {
        matchList.set(pos, matchInfo);
        adapter.notifyDataSetChanged();
    }

    public void removeFromList(int index)
    {
        matchList.remove(index);
        adapter.notifyDataSetChanged();
    }

    public boolean listEmpty()
    {
        return matchList.size() == 0;
    }

}

