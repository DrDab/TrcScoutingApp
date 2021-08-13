package trc492.trcscoutingcodegen.classgen;

public class CodeTemplates
{
    public static final String APPINFO_CLASS_TEMPLATE = "package trc3543.trcscoutingapp.data;\r\n"
        + "\r\n"
        + "import trc3543.trcscoutingapp.fragments.*;\r\n"
        + "\r\n"
        + "public class AppInfo\r\n"
        + "{\r\n"
        + "    //\r\n"
        + "    // General app settings.\r\n"
        + "    //\r\n"
        + "    public static final String DATA_FOLDER_NAME = \"TrcScoutingApp\";\r\n"
        + "    public static final String SETTINGS_FILENAME = \"app_settings.json\";\r\n"
        + "    public static final String CSV_HEADER = \"%s\";\r\n"
        + "    public static final String VERSION_NUMBER = \"1.4.0-frc\";\r\n"
        + "    public static final int YEAR_NUMBER = %d;\r\n"
        + "\r\n"
        + "    //\r\n"
        + "    // SetMatchInfo and child Fragment settings.\r\n"
        + "    //\r\n"
        + "    public static final int NUM_PAGES = %d;\r\n"
        + "    public static final String[] TAB_NAMES = %s;\r\n"
        + "    public static final Class<?>[] FRAGMENT_CLASSES = %s;\r\n"
        + "}";
    
    public static final String ADDMATCHES_CLASS_TEMPLATE = "/*\r\n"
        + " * Copyright (c) 2017-2019 Titan Robotics Club\r\n"
        + " *\r\n"
        + " * Permission is hereby granted, free of charge, to any person obtaining a copy\r\n"
        + " * of this software and associated documentation files (the \"Software\"), to deal\r\n"
        + " * in the Software without restriction, including without limitation the rights\r\n"
        + " * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\r\n"
        + " * copies of the Software, and to permit persons to whom the Software is\r\n"
        + " * furnished to do so, subject to the following conditions:\r\n"
        + " *\r\n"
        + " * The above copyright notice and this permission notice shall be included in all\r\n"
        + " * copies or substantial portions of the Software.\r\n"
        + " *\r\n"
        + " * THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\r\n"
        + " * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\r\n"
        + " * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\r\n"
        + " * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\r\n"
        + " * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\r\n"
        + " * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\r\n"
        + " * SOFTWARE.\r\n"
        + " */\r\n"
        + "\r\n"
        + "package trc3543.trcscoutingapp.activities;\r\n"
        + "\r\n"
        + "import android.Manifest;\r\n"
        + "import android.app.Activity;\r\n"
        + "import android.app.Dialog;\r\n"
        + "import android.content.Context;\r\n"
        + "import android.content.DialogInterface;\r\n"
        + "import android.content.Intent;\r\n"
        + "import android.content.pm.PackageManager;\r\n"
        + "import android.nfc.NfcAdapter;\r\n"
        + "import android.nfc.NfcManager;\r\n"
        + "import android.os.Bundle;\r\n"
        + "import android.os.Environment;\r\n"
        + "import com.google.android.material.floatingactionbutton.FloatingActionButton;\r\n"
        + "\r\n"
        + "import androidx.annotation.NonNull;\r\n"
        + "import androidx.core.app.ActivityCompat;\r\n"
        + "import androidx.appcompat.app.AlertDialog;\r\n"
        + "import androidx.appcompat.app.AppCompatActivity;\r\n"
        + "import androidx.appcompat.widget.Toolbar;\r\n"
        + "import trc3543.trcscoutingapp.data.AppInfo;\r\n"
        + "import trc3543.trcscoutingapp.data.AppSettings;\r\n"
        + "import trc3543.trcscoutingapp.threads.AutoSaveThread;\r\n"
        + "import trc3543.trcscoutingapp.data.IOUtils;\r\n"
        + "import trc3543.trcscoutingapp.data.MatchInfo;\r\n"
        + "import trc3543.trcscoutingapp.R;\r\n"
        + "\r\n"
        + "import android.util.Log;\r\n"
        + "import android.view.View;\r\n"
        + "import android.view.Menu;\r\n"
        + "import android.view.MenuItem;\r\n"
        + "import android.widget.AdapterView;\r\n"
        + "import android.widget.ArrayAdapter;\r\n"
        + "import android.widget.ListView;\r\n"
        + "\r\n"
        + "import org.json.JSONException;\r\n"
        + "\r\n"
        + "import java.io.File;\r\n"
        + "import java.io.IOException;\r\n"
        + "import java.util.ArrayList;\r\n"
        + "import java.util.List;\r\n"
        + "\r\n"
        + "import static trc3543.trcscoutingapp.data.AppInfo.DATA_FOLDER_NAME;\r\n"
        + "\r\n"
        + "@SuppressWarnings(\"all\")\r\n"
        + "public class AddMatches extends AppCompatActivity\r\n"
        + "{\r\n"
        + "    public static final String MODULE_NAME = \"AddMatches\";\r\n"
        + "\r\n"
        + "    // permission codes\r\n"
        + "    public static final int WRITE_EXT_STORAGE_PERM_CODE = 621;\r\n"
        + "\r\n"
        + "    // activity request code\r\n"
        + "    public static final int LAUNCH_SETMATCHINFO_REQUEST = 100;\r\n"
        + "    public static final int LAUNCH_SETTINGS_REQUEST = 101;\r\n"
        + "    public static final int LAUNCH_SENDER_REQUEST = 102;\r\n"
        + "\r\n"
        + "    private List<MatchInfo> matchList;\r\n"
        + "    private ArrayAdapter<MatchInfo> adapter;\r\n"
        + "    private ListView matchListView;\r\n"
        + "    private boolean elementsInitialized;\r\n"
        + "\r\n"
        + "    private AppSettings settings;\r\n"
        + "\r\n"
        + "    private Runnable autosaverunnable;\r\n"
        + "    private boolean autoSaveRunnableInit;\r\n"
        + "\r\n"
        + "    private boolean deviceSupportsNfc;\r\n"
        + "\r\n"
        + "    @Override\r\n"
        + "    protected void onCreate(Bundle savedInstanceState)\r\n"
        + "    {\r\n"
        + "        super.onCreate(savedInstanceState);\r\n"
        + "        setContentView(R.layout.activity_add_matches);\r\n"
        + "\r\n"
        + "        Log.d(MODULE_NAME, \"External Storage Directory: \" + Environment.getExternalStorageDirectory().toString());\r\n"
        + "\r\n"
        + "        NfcManager nfcmanager = (NfcManager) getApplicationContext().getSystemService(Context.NFC_SERVICE);\r\n"
        + "        NfcAdapter nfcadapter = nfcmanager.getDefaultAdapter();\r\n"
        + "        deviceSupportsNfc = nfcadapter != null && nfcadapter.isEnabled();\r\n"
        + "\r\n"
        + "        elementsInitialized = false;\r\n"
        + "        autoSaveRunnableInit = false;\r\n"
        + "\r\n"
        + "        // let's check if we have file permissions before running.\r\n"
        + "        if (verifySystemPermissions(this))\r\n"
        + "        {\r\n"
        + "            initializeAppSettings();\r\n"
        + "        }\r\n"
        + "        else\r\n"
        + "        {\r\n"
        + "            AlertDialog alertDialog = new AlertDialog.Builder(AddMatches.this).create();\r\n"
        + "            alertDialog.setTitle(\"Permission Request\");\r\n"
        + "            alertDialog.setMessage(\"Please enable external storage permission.\");\r\n"
        + "            alertDialog.setButton(Dialog.BUTTON_POSITIVE, \"OK\", new DialogInterface.OnClickListener()\r\n"
        + "            {\r\n"
        + "                @Override\r\n"
        + "                public void onClick(DialogInterface dialog, int which)\r\n"
        + "                {\r\n"
        + "                    spawnExternalMemoryPermsRequest();\r\n"
        + "                }\r\n"
        + "            });\r\n"
        + "            alertDialog.show();\r\n"
        + "        }\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    public void initializeAppSettings()\r\n"
        + "    {\r\n"
        + "        File writeDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);\r\n"
        + "        if (!writeDirectory.exists())\r\n"
        + "            writeDirectory.mkdir();\r\n"
        + "\r\n"
        + "        File settingsFile = new File(writeDirectory, AppInfo.SETTINGS_FILENAME);\r\n"
        + "\r\n"
        + "        if (settingsFile.exists())\r\n"
        + "        {\r\n"
        + "            try\r\n"
        + "            {\r\n"
        + "                settings = IOUtils.readSettingsFromJSON(settingsFile);\r\n"
        + "                initializeElements();\r\n"
        + "            }\r\n"
        + "            catch (Exception e)\r\n"
        + "            {\r\n"
        + "                e.printStackTrace();\r\n"
        + "            }\r\n"
        + "        }\r\n"
        + "        else\r\n"
        + "        {\r\n"
        + "            settings = new AppSettings();\r\n"
        + "            openSettingsPrompt();\r\n"
        + "        }\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    public void initializeElements()\r\n"
        + "    {\r\n"
        + "        Log.d(MODULE_NAME, \"Initializing elements.\");\r\n"
        + "        elementsInitialized = true;\r\n"
        + "\r\n"
        + "        try\r\n"
        + "        {\r\n"
        + "            matchList = IOUtils.readArraylistsFromJSON(matchList);\r\n"
        + "        }\r\n"
        + "        catch (IOException | JSONException e)\r\n"
        + "        {\r\n"
        + "            e.printStackTrace();\r\n"
        + "        }\r\n"
        + "\r\n"
        + "        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);\r\n"
        + "        setSupportActionBar(toolbar);\r\n"
        + "        matchListView = (ListView) findViewById(R.id.listView);\r\n"
        + "\r\n"
        + "        adapter = new ArrayAdapter<MatchInfo>(AddMatches.this, android.R.layout.simple_list_item_1, matchList);\r\n"
        + "\r\n"
        + "        matchListView.setAdapter(adapter);\r\n"
        + "        matchListView.setOnItemClickListener(new AdapterView.OnItemClickListener()\r\n"
        + "        {\r\n"
        + "            @Override\r\n"
        + "            public void onItemClick(AdapterView arg0, View arg1, int position, long arg3)\r\n"
        + "            {\r\n"
        + "                openMatchEditPrompt(true, position);\r\n"
        + "            }\r\n"
        + "        });\r\n"
        + "        matchListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()\r\n"
        + "        {\r\n"
        + "            @Override\r\n"
        + "            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l)\r\n"
        + "            {\r\n"
        + "                new AlertDialog.Builder(AddMatches.this)\r\n"
        + "                        .setTitle(\"Are you sure?\")\r\n"
        + "                        .setMessage(\"Are you sure you want to delete this element?\")\r\n"
        + "                        .setPositiveButton(\"YES\", new DialogInterface.OnClickListener()\r\n"
        + "                        {\r\n"
        + "                            public void onClick(DialogInterface dialog, int whichButton)\r\n"
        + "                            {\r\n"
        + "                                removeFromList(i);\r\n"
        + "                                try\r\n"
        + "                                {\r\n"
        + "                                    IOUtils.writeArraylistsToJSON(matchList);\r\n"
        + "                                }\r\n"
        + "                                catch (IOException | JSONException e)\r\n"
        + "                                {\r\n"
        + "                                    e.printStackTrace();\r\n"
        + "                                }\r\n"
        + "                                adapter.notifyDataSetChanged();\r\n"
        + "                            }\r\n"
        + "                        })\r\n"
        + "                        .setNegativeButton(\"NO\", new DialogInterface.OnClickListener()\r\n"
        + "                        {\r\n"
        + "                            public void onClick(DialogInterface dialog, int whichButton)\r\n"
        + "                            {\r\n"
        + "                            }\r\n"
        + "                        })\r\n"
        + "                        .show();\r\n"
        + "                return true;\r\n"
        + "            }\r\n"
        + "        });\r\n"
        + "\r\n"
        + "        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);\r\n"
        + "        fab.setOnClickListener(new View.OnClickListener()\r\n"
        + "        {\r\n"
        + "            @Override\r\n"
        + "            public void onClick(View view)\r\n"
        + "            {\r\n"
        + "                // if the floating plus button is clicked, open the prompt to add a match.\r\n"
        + "                openMatchEditPrompt(false, -1);\r\n"
        + "            }\r\n"
        + "        });\r\n"
        + "\r\n"
        + "        if (!autoSaveRunnableInit)\r\n"
        + "        {\r\n"
        + "            autosaverunnable = new Runnable()\r\n"
        + "            {\r\n"
        + "                @Override\r\n"
        + "                public void run()\r\n"
        + "                {\r\n"
        + "                    AutoSaveThread autosave = new AutoSaveThread(settings, matchList);\r\n"
        + "                    autosave.run();\r\n"
        + "                }\r\n"
        + "            };\r\n"
        + "            new Thread(autosaverunnable).start();\r\n"
        + "            autoSaveRunnableInit = true;\r\n"
        + "        }\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    @Override\r\n"
        + "    public boolean onCreateOptionsMenu(Menu menu)\r\n"
        + "    {\r\n"
        + "        getMenuInflater().inflate(R.menu.menu_add_competitions, menu);\r\n"
        + "        return true;\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    @Override\r\n"
        + "    public boolean onOptionsItemSelected(MenuItem item)\r\n"
        + "    {\r\n"
        + "        int id = item.getItemId();\r\n"
        + "\r\n"
        + "        switch (id)\r\n"
        + "        {\r\n"
        + "            case R.id.action_about:\r\n"
        + "                Intent intent = new Intent(this, About.class);\r\n"
        + "                intent.putExtra(\"deviceSupportsNfc\", deviceSupportsNfc);\r\n"
        + "                startActivity(intent);\r\n"
        + "                return true;\r\n"
        + "\r\n"
        + "            case R.id.action_makecsv:\r\n"
        + "                String filename = IOUtils.getFileName(settings.firstName + \"_\" + settings.lastName);\r\n"
        + "                try\r\n"
        + "                {\r\n"
        + "                    IOUtils.writeContestsToCsv(settings, matchList, filename);\r\n"
        + "                }\r\n"
        + "                catch (IOException e)\r\n"
        + "                {\r\n"
        + "                    // TODO Auto-generated catch block\r\n"
        + "                    e.printStackTrace();\r\n"
        + "                }\r\n"
        + "                new AlertDialog.Builder(this)\r\n"
        + "                        .setTitle(\"CSV Write Successful\")\r\n"
        + "                        .setMessage(\"Scouting data written to file \\\"\" + filename + \"\\\"\")\r\n"
        + "                        .setPositiveButton(\"OK\", new DialogInterface.OnClickListener()\r\n"
        + "                        {\r\n"
        + "                            public void onClick(DialogInterface dialog, int whichButton)\r\n"
        + "                            {\r\n"
        + "                            }\r\n"
        + "                        })\r\n"
        + "                        .show();\r\n"
        + "                return true;\r\n"
        + "\r\n"
        + "            case R.id.action_transmitresults:\r\n"
        + "                intent = new Intent(this, SendReport.class);\r\n"
        + "                intent.putExtra(\"matchList\", (ArrayList) matchList);\r\n"
        + "                intent.putExtra(\"appSettings\", settings);\r\n"
        + "                startActivityForResult(intent, LAUNCH_SENDER_REQUEST);\r\n"
        + "                return true;\r\n"
        + "\r\n"
        + "            case R.id.action_ac:\r\n"
        + "                new AlertDialog.Builder(this)\r\n"
        + "                        .setTitle(\"Are you sure?\")\r\n"
        + "                        .setMessage(\"Are you sure you want to delete all matches in this session?\")\r\n"
        + "                        .setPositiveButton(\"YES\", new DialogInterface.OnClickListener()\r\n"
        + "                        {\r\n"
        + "                            public void onClick(DialogInterface dialog, int whichButton)\r\n"
        + "                            {\r\n"
        + "                                matchList.clear();\r\n"
        + "                                try\r\n"
        + "                                {\r\n"
        + "                                    IOUtils.writeArraylistsToJSON(matchList);\r\n"
        + "                                }\r\n"
        + "                                catch (IOException | JSONException e)\r\n"
        + "                                {\r\n"
        + "                                    e.printStackTrace();\r\n"
        + "                                }\r\n"
        + "                                adapter.notifyDataSetChanged();\r\n"
        + "                            }\r\n"
        + "                        })\r\n"
        + "                        .setNegativeButton(\"NO\", new DialogInterface.OnClickListener()\r\n"
        + "                        {\r\n"
        + "                            public void onClick(DialogInterface dialog, int whichButton)\r\n"
        + "                            {\r\n"
        + "                            }\r\n"
        + "                        })\r\n"
        + "                        .show();\r\n"
        + "                return true;\r\n"
        + "\r\n"
        + "            case R.id.action_config:\r\n"
        + "                openSettingsPrompt();\r\n"
        + "                return true;\r\n"
        + "\r\n"
        + "            case R.id.action_qrsend:\r\n"
        + "                intent = new Intent(this, QrDataSender.class);\r\n"
        + "                intent.putExtra(\"matchList\", (ArrayList) matchList);\r\n"
        + "                intent.putExtra(\"appSettings\", settings);\r\n"
        + "                startActivity(intent);\r\n"
        + "                return true;\r\n"
        + "\r\n"
        + "            default:\r\n"
        + "                return super.onOptionsItemSelected(item);\r\n"
        + "        }\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    @Override\r\n"
        + "    public void onRequestPermissionsResult(int requestCode,\r\n"
        + "                                           @NonNull String[] permissions,\r\n"
        + "                                           @NonNull int[] grantResults)\r\n"
        + "    {\r\n"
        + "        super.onRequestPermissionsResult(requestCode, permissions, grantResults);\r\n"
        + "\r\n"
        + "        if (requestCode == WRITE_EXT_STORAGE_PERM_CODE)\r\n"
        + "        {\r\n"
        + "            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)\r\n"
        + "                initializeAppSettings();\r\n"
        + "            else\r\n"
        + "                spawnExternalMemoryPermsRequest();\r\n"
        + "        }\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    @Override\r\n"
        + "    protected void onActivityResult(int requestCode, int resultCode, Intent data)\r\n"
        + "    {\r\n"
        + "        super.onActivityResult(requestCode, resultCode, data);\r\n"
        + "\r\n"
        + "        if (resultCode == Activity.RESULT_OK)\r\n"
        + "        {\r\n"
        + "            switch (requestCode)\r\n"
        + "            {\r\n"
        + "                case LAUNCH_SETMATCHINFO_REQUEST:\r\n"
        + "                    Log.d(MODULE_NAME, \"SetMatchInfo activity result callback triggered with result.\");\r\n"
        + "                    boolean editExisting = data.getBooleanExtra(\"editExisting\", false);\r\n"
        + "                    int existingEntryIndex = data.getIntExtra(\"existingEntryIndex\", -1);\r\n"
        + "                    MatchInfo matchInfo = (MatchInfo) data.getSerializableExtra(\"matchInfo\");\r\n"
        + "\r\n"
        + "                    if (editExisting)\r\n"
        + "                    {\r\n"
        + "                        Log.d(MODULE_NAME, \"Resetting list entry \" + existingEntryIndex);\r\n"
        + "                        resetListItem(matchInfo, existingEntryIndex);\r\n"
        + "                        Log.d(MODULE_NAME, \"List entry \" + existingEntryIndex + \" reset.\");\r\n"
        + "                    }\r\n"
        + "                    else\r\n"
        + "                    {\r\n"
        + "                        Log.d(MODULE_NAME, \"Adding new entry to list.\");\r\n"
        + "                        addToList(matchInfo);\r\n"
        + "                    }\r\n"
        + "\r\n"
        + "                    try\r\n"
        + "                    {\r\n"
        + "                        Log.d(MODULE_NAME, \"Writing updated match list to JSON file.\");\r\n"
        + "                        IOUtils.writeArraylistsToJSON(matchList);\r\n"
        + "                    }\r\n"
        + "                    catch (IOException | JSONException e)\r\n"
        + "                    {\r\n"
        + "                        e.printStackTrace();\r\n"
        + "                    }\r\n"
        + "                    break;\r\n"
        + "\r\n"
        + "                case LAUNCH_SETTINGS_REQUEST:\r\n"
        + "                    Log.d(MODULE_NAME, \"SettingsActivity result callback triggered with result.\");\r\n"
        + "                    AppSettings newSettings = (AppSettings) data.getSerializableExtra(\"appSettings\");\r\n"
        + "\r\n"
        + "                    Log.d(MODULE_NAME, \"Got result from SettingsActivity.\");\r\n"
        + "                    if (!elementsInitialized)\r\n"
        + "                    {\r\n"
        + "                        initializeElements();\r\n"
        + "                    }\r\n"
        + "\r\n"
        + "                    File writeDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);\r\n"
        + "                    File settingsFile = new File(writeDirectory, AppInfo.SETTINGS_FILENAME);\r\n"
        + "\r\n"
        + "                    settings.copyFieldsFromOtherSettings(newSettings);\r\n"
        + "\r\n"
        + "                    try\r\n"
        + "                    {\r\n"
        + "                        Log.d(MODULE_NAME, \"Writing updated settings to JSON file.\");\r\n"
        + "                        IOUtils.writeSettingsToJSON(settings, settingsFile);\r\n"
        + "                    }\r\n"
        + "                    catch (IOException | JSONException e)\r\n"
        + "                    {\r\n"
        + "                        e.printStackTrace();\r\n"
        + "                    }\r\n"
        + "                    break;\r\n"
        + "            }\r\n"
        + "        }\r\n"
        + "        else if (resultCode == Activity.RESULT_CANCELED)\r\n"
        + "        {\r\n"
        + "            File writeDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);\r\n"
        + "            File settingsFile = new File(writeDirectory, AppInfo.SETTINGS_FILENAME);\r\n"
        + "            switch (requestCode)\r\n"
        + "            {\r\n"
        + "                case LAUNCH_SETTINGS_REQUEST:\r\n"
        + "                    if (!settingsFile.exists())\r\n"
        + "                        openSettingsPrompt();\r\n"
        + "                    break;\r\n"
        + "\r\n"
        + "                case LAUNCH_SENDER_REQUEST:\r\n"
        + "                    try\r\n"
        + "                    {\r\n"
        + "                        settings.copyFieldsFromOtherSettings(IOUtils.readSettingsFromJSON(settingsFile));\r\n"
        + "                    }\r\n"
        + "                    catch (IOException | JSONException exception)\r\n"
        + "                    {\r\n"
        + "                        exception.printStackTrace();\r\n"
        + "                    }\r\n"
        + "                    break;\r\n"
        + "\r\n"
        + "            }\r\n"
        + "        }\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    public void openMatchEditPrompt(boolean modifyingExisting, int option)\r\n"
        + "    {\r\n"
        + "        Intent intent = new Intent(this, SetMatchInfo.class);\r\n"
        + "        if (!modifyingExisting)\r\n"
        + "        {\r\n"
        + "            MatchInfo prev = matchList == null ? null :\r\n"
        + "                    matchList.size() > 0 ?\r\n"
        + "                            matchList.get(matchList.size() - 1) : null;\r\n"
        + "            intent.putExtra(\"EditOption\", -1);\r\n"
        + "            if (prev != null)\r\n"
        + "            {\r\n"
        + "                %s\n"
        + "            }\r\n"
        + "        }\r\n"
        + "        else\r\n"
        + "        {\r\n"
        + "            intent.putExtra(\"EditOption\", option);\r\n"
        + "            intent.putExtra(\"matchInfo\", matchList.get(option));\r\n"
        + "        }\r\n"
        + "\r\n"
        + "        startActivityForResult(intent, LAUNCH_SETMATCHINFO_REQUEST);\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    public void openSettingsPrompt()\r\n"
        + "    {\r\n"
        + "        Intent intent = new Intent(this, SettingsActivity.class);\r\n"
        + "        intent.putExtra(\"appSettings\", settings);\r\n"
        + "        startActivityForResult(intent, LAUNCH_SETTINGS_REQUEST);\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    public boolean verifySystemPermissions(Activity activity)\r\n"
        + "    {\r\n"
        + "        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    public void spawnExternalMemoryPermsRequest()\r\n"
        + "    {\r\n"
        + "        ActivityCompat.requestPermissions(AddMatches.this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, WRITE_EXT_STORAGE_PERM_CODE);\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    public void addToList(MatchInfo matchInfo)\r\n"
        + "    {\r\n"
        + "        matchList.add(matchInfo);\r\n"
        + "        adapter.notifyDataSetChanged();\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    public void resetListItem(MatchInfo matchInfo, int pos)\r\n"
        + "    {\r\n"
        + "        matchList.set(pos, matchInfo);\r\n"
        + "        adapter.notifyDataSetChanged();\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    public void removeFromList(int index)\r\n"
        + "    {\r\n"
        + "        matchList.remove(index);\r\n"
        + "        adapter.notifyDataSetChanged();\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    public boolean listEmpty()\r\n"
        + "    {\r\n"
        + "        return matchList.size() == 0;\r\n"
        + "    }\r\n"
        + "\r\n"
        + "}\r\n"
        + "";

    public static final String MATCHINFO_CLASS_TEMPLATE =
        "package trc3543.trcscoutingapp.data;\r\n"
        + "\r\n"
        + "import java.io.Serializable;\r\n"
        + "import java.util.Iterator;\r\n"
        + "\r\n"
        + "import org.json.JSONException;\r\n"
        + "import org.json.JSONObject;\r\n"
        + "\r\n"
        + "import com.google.gson.Gson;\r\n"
        + "import com.google.gson.annotations.SerializedName;\r\n"
        + "\r\n"
        + "public class MatchInfo implements Serializable\r\n"
        + "{\r\n"
        + "    private static final long serialVersionUID = 1L;\r\n"
        + "\r\n"
        + "    // misc. variables.\r\n"
        + "    @SerializedName(\"uuid\")\r\n"
        + "    public String uuid;\r\n"
        + "\r\n"
        + "%s\n"
        + "\r\n"
        + "    public JSONObject toJSONObject() throws JSONException\r\n"
        + "    {\r\n"
        + "        return new JSONObject(new Gson().toJson(this));\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    public static MatchInfo fromMultipleJSONObjects(JSONObject... fragmentJSONObjects)\r\n"
        + "            throws JSONException\r\n"
        + "    {\r\n"
        + "        JSONObject head = new JSONObject();\r\n"
        + "\r\n"
        + "        for (JSONObject fragmentJSONObject : fragmentJSONObjects)\r\n"
        + "        {\r\n"
        + "            if (fragmentJSONObject == null)\r\n"
        + "                continue;\r\n"
        + "            Iterator<String> keyIterator = fragmentJSONObject.keys();\r\n"
        + "            while (keyIterator.hasNext()) {\r\n"
        + "                String key = keyIterator.next();\r\n"
        + "                Object value = fragmentJSONObject.get(key);\r\n"
        + "                head.put(key, value);\r\n"
        + "            }\r\n"
        + "        }\r\n"
        + "\r\n"
        + "        Gson gson = new Gson();\r\n"
        + "        MatchInfo toReturn = gson.fromJson(head.toString(), MatchInfo.class);\r\n"
        + "        return toReturn;\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    private boolean checkAnyNull(Object... args)\r\n"
        + "    {\r\n"
        + "        for (Object cur : args)\r\n"
        + "        {\r\n"
        + "            if (cur == null)\r\n"
        + "            {\r\n"
        + "                return true;\r\n"
        + "            }\r\n"
        + "        }\r\n"
        + "        return false;\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    public boolean allNeededFieldsPopulated()\r\n"
        + "    {\r\n"
        + "        return !checkAnyNull(matchNumber, teamNumber);\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    public String getDisplayString()\r\n"
        + "    {\r\n"
        + "        return %s\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    public String getCsvString()\r\n"
        + "    {\r\n"
        + "        %s"
        + "        return csvOrder.csvString;\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    @Override\r\n"
        + "    public String toString()\r\n"
        + "    {\r\n"
        + "        return getDisplayString();\r\n"
        + "    }\r\n"
        + "\r\n"
        + "    private class CsvOrder\r\n"
        + "    {\r\n"
        + "        public String csvString;\r\n"
        + "        public CsvOrder(Object... params)\r\n"
        + "        {\r\n"
        + "            this.csvString = \"\";\r\n"
        + "            for (Object s : params)\r\n"
        + "            {\r\n"
        + "                if (s != null)\r\n"
        + "                {\r\n"
        + "                    boolean isStr = s instanceof String;\r\n"
        + "                    if (isStr)\r\n"
        + "                    {\r\n"
        + "                        this.csvString += \"\\\"\";\r\n"
        + "                    }\r\n"
        + "                    this.csvString += s.toString();\r\n"
        + "                    if (isStr)\r\n"
        + "                    {\r\n"
        + "                        this.csvString += \"\\\"\";\r\n"
        + "                    }\r\n"
        + "                }\r\n"
        + "                this.csvString += \",\";\r\n"
        + "            }\r\n"
        + "        }\r\n"
        + "    }\r\n"
        + "}\r\n";
    
    public static final String FRAGMENT_CLASS_TEMPLATE =
        "package trc3543.trcscoutingapp.fragments;\r\n"
        + "\r\n"
        + "import android.view.LayoutInflater;\r\n"
        + "import android.view.ViewGroup;\r\n"
        + "import android.widget.*;\r\n"
        + "\r\n"
        + "import org.json.JSONException;\r\n"
        + "import org.json.JSONObject;\r\n"
        + "\r\n"
        + "import trc3543.trcscoutingapp.R;\r\n"
        + "\r\n"
        + "public class %s extends AbstractPageFragment\r\n"
        + "{\r\n"
        + "%s"
        + "\r\n"
        + "    @Override\r\n"
        + "    public void instantiateViews(LayoutInflater inflater, ViewGroup container)\r\n"
        + "    {\r\n"
        + "        view = inflater.inflate(R.layout.%s, container, false);\r\n"
        + "%s"
        + "    }\r\n"
        + "\r\n"
        + "    @Override\r\n"
        + "    public void setFields(JSONObject fieldData) throws JSONException\r\n"
        + "    {\r\n"
        + "%s"
        + "    }\r\n"
        + "\r\n"
        + "    @Override\r\n"
        + "    public JSONObject getFields()\r\n"
        + "    {\r\n"
        + "        try\r\n"
        + "        {\r\n"
        + "            JSONObject data = new JSONObject();\r\n"
        + "%s"
        + "            return data;\r\n"
        + "        }\r\n"
        + "        catch (Exception e)\r\n"
        + "        {\r\n"
        + "            e.printStackTrace();\r\n"
        + "            return null;\r\n"
        + "        }"
        + "    }\r\n"
        + "}";
    
}
