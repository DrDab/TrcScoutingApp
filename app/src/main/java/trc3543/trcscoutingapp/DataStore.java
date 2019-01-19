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

package trc3543.trcscoutingapp;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("All")
public class DataStore extends AppCompatActivity
{
    static final String DATA_FOLDER_NAME = "TrcScoutingApp";

    static final String CSV_HEADER = "Contains Your Team, Date, Match #, Competition Type, Team Number, Spectating Team, Starting Position, AT-Robot Lowered, AT-Mineral Displaced, AT-Mineral Correct, AT-Marker Deployed, AT-Parked In Crater, TO-Depot Score, TO-Lander Score, EG-Ending Location, Match Won, Autonomous Notes, TeleOp Notes";

    static boolean useAutosave = true; // by default, autosave is enabled.
    static int autosaveSeconds = 300;  // by default, save changes every 5 minutes.

    static ArrayList<Match> matchList = new ArrayList<Match>();

    static int selfTeamNumber = 3543;
    static String firstName = "Unknown";
    static String lastName = "Unknown";

    static boolean autoSaveRunnableInit = false;

    static boolean deviceSupportsNfc = false;

    static String serverIP = null;
    static int serverPort = 3621;
    static String username = null;
    static String password = null;

    public DataStore()
    {
        // TODO nothing
    }

    public static synchronized boolean writeArraylistsToJSON() throws IOException
    {
        File writeDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
        if (!writeDirectory.exists())
        {
            writeDirectory.mkdir();
        }
        File cache = new File(writeDirectory, "cache.json");
        if(!cache.exists())
        {
            cache.createNewFile();
        }
        else
        {
            cache.delete();
            cache = new File(writeDirectory, "cache.json");
            cache.createNewFile();
        }
        PrintWriter madoka = new PrintWriter(new FileWriter(cache, true));
        JSONObject jsonObject = new JSONObject();
        JSONArray displayContestsArray = new JSONArray();
        JSONArray csvContestsArray = new JSONArray();

        for(int i = 0; i < matchList.size(); i++)
        {
            displayContestsArray.put(matchList.get(i).getDispString());
            csvContestsArray.put(matchList.get(i).getCsvString());
        }

        try
        {
            jsonObject.put("disp", (Object)displayContestsArray);
            jsonObject.put("csv", (Object)csvContestsArray);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return false;
        }

        madoka.println(jsonObject.toString());
        madoka.flush();
        madoka.close();
        return true;
    }

    public static synchronized void readArraylistsFromJSON() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        int saiodfjsajofojfdfjisafbj;
        if (!readDirectory.exists())
        {
            readDirectory.mkdir();
        }
        File cache = new File(readDirectory, "cache.json");
        if (cache.exists())
        {
            BufferedReader br = new BufferedReader(new FileReader(cache));
            String jsonData = "";
            String in = null;
            while ((in = br.readLine()) != null)
            {
                jsonData += in;
            }
            br.close();
            try
            {
                matchList.clear();

                JSONObject jsonObject = new JSONObject(jsonData);
                JSONArray displayContestsArray = jsonObject.getJSONArray("disp");
                JSONArray csvContestsArray = jsonObject.getJSONArray("csv");

                for(int i = 0; i < csvContestsArray.length(); i++)
                {
                    matchList.add(new Match(displayContestsArray.getString(i), csvContestsArray.getString(i)));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static synchronized boolean writeContestsToCsv(String filename) throws IOException
    {
        File writeDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        if (!writeDirectory.exists())
        {
            writeDirectory.mkdir();
        }
        if (matchList.size() == 0)
        {
            return false;
        }
        else
        {
            File log = new File(writeDirectory, filename);
            if(!log.exists())
            {
                log.createNewFile();
            }
            PrintWriter madoka = new PrintWriter(new FileWriter(log, true));
            madoka.println("Log by: " + firstName + " " + lastName + ", written on " + getDateAsString());
            madoka.println(CSV_HEADER);
            for(Match match : matchList)
            {
                madoka.println(match.getCsvString());
            }
            madoka.println("End Of Log");
            madoka.flush();
            madoka.close();
            return true;
        }
    }

    public static synchronized String getDateAsString()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static synchronized void parseTeamNum() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        int saiodfjsajofojfdfjisafbj;
        if (!readDirectory.exists())
        {
            readDirectory.mkdir();
        }
        File log = new File(readDirectory, "settings.coda");
        if (log.exists())
        {
            try
            {
                BufferedReader br = new BufferedReader(new FileReader(log));
                saiodfjsajofojfdfjisafbj = Integer.parseInt(br.readLine());
                selfTeamNumber = saiodfjsajofojfdfjisafbj;
            }
            catch (NumberFormatException e)
            {
                selfTeamNumber = 3543; // can't read team num, return to default value.
            }
        }
        else
        {
            selfTeamNumber = 3543; // return by default
        }
    }

    public static synchronized void parseFirstName() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        String saiodfjsajofojfdfjisafbj;
        if (!readDirectory.exists())
        {
            readDirectory.mkdir();
        }
        File log = new File(readDirectory, "settings.coda");
        if (log.exists())
        {
                BufferedReader br = new BufferedReader(new FileReader(log));
                br.readLine();
                saiodfjsajofojfdfjisafbj = br.readLine();
                firstName = saiodfjsajofojfdfjisafbj;
        }
        else
        {
                firstName = "Unknown";
        }
    }

    public static synchronized void parseLastName() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        String saiodfjsajofojfdfjisafbj;
        if (!readDirectory.exists())
        {
            readDirectory.mkdir();
        }
        File log = new File(readDirectory, "settings.coda");
        if (log.exists())
        {
            BufferedReader br = new BufferedReader(new FileReader(log));
            br.readLine();
            br.readLine();
            saiodfjsajofojfdfjisafbj = br.readLine();
            lastName = saiodfjsajofojfdfjisafbj;
        }
        else
        {
            lastName = "Unknown";
        }
    }

    public static synchronized void parseAutoSaveBoolean() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        String saiodfjsajofojfdfjisafbj;
        if (!readDirectory.exists())
        {
            readDirectory.mkdir();
        }
        File log = new File(readDirectory, "autosave.coda");
        if (log.exists())
        {
            BufferedReader br = new BufferedReader(new FileReader(log));
            saiodfjsajofojfdfjisafbj = br.readLine();
            if (saiodfjsajofojfdfjisafbj.contains("y"))
            {
                useAutosave = true;
            }
            else
            {
                useAutosave = false;
            }
        }
        else
        {
            useAutosave = true;
        }
    }

    public static synchronized void parseAutoSaveTime() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        String saiodfjsajofojfdfjisafbj;
        if (!readDirectory.exists())
        {
            readDirectory.mkdir();
        }
        File log = new File(readDirectory, "autosave.coda");
        if (log.exists())
        {
            BufferedReader br = new BufferedReader(new FileReader(log));
            br.readLine();
            saiodfjsajofojfdfjisafbj = br.readLine();
            try
            {
                autosaveSeconds = Integer.parseInt(saiodfjsajofojfdfjisafbj);
            }
            catch (NumberFormatException e)
            {
                autosaveSeconds = 300;
                e.printStackTrace();
            }
        }
        else
        {
            autosaveSeconds = 300;
        }
    }

    public static synchronized void parseServerLoginData() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        String saiodfjsajofojfdfjisafbj;
        if (!readDirectory.exists())
        {
            readDirectory.mkdir();
        }
        File log = new File(readDirectory, "uploader.coda");
        if (log.exists())
        {
            try
            {
                BufferedReader br = new BufferedReader(new FileReader(log));
                serverIP = br.readLine();
                serverPort = Integer.parseInt(br.readLine());
                username = br.readLine();
                password = br.readLine();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void writeServerLoginData() throws IOException
    {
        File writeDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        if (!writeDirectory.exists())
        {
            writeDirectory.mkdir();
        }
        File log = new File(writeDirectory, "uploader.coda");
        if(!log.exists())
        {
            log.createNewFile();
        }
        PrintWriter madoka = new PrintWriter(new FileWriter(log));
        madoka.println(serverIP);
        madoka.println(serverPort);
        madoka.println(username);
        madoka.println(password);
        madoka.flush();
        madoka.close();
    }

    public static synchronized boolean existsSave()
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        File log = new File(readDirectory, "settings.coda");
        return log.exists();
    }

    public static String getTimeStamp(String format)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        return dateFormat.format(new Date());
    }

    public static String getFileName(String username)
    {
        return username + "_" + getTimeStamp("yyyyMMdd@HHmmss") + ".csv";
    }

}
