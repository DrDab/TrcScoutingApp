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

package trc3543.trcscoutingapp.data;

import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;

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
    // Begin season-specific info.
    public static final String DATA_FOLDER_NAME = "TrcScoutingApp";
    public static final String CSV_HEADER = "matchNum, teamNum, matchType, alliance, initLineCrossed, autonomousLowerCells, autonomousOuterCells, autonomousInnerCells, autonomousMissedCells, teleopLowerCells, teleopOuterCells, teleopInnerCells, teleopMissedCells, shieldStage1, shieldStage2, shieldStage3, controlPanelRotated, controlPanelPositioned, generatorSwitchParked, generatorSwitchHanging, generatorSwitchSupportingMechanism, generatorSwitchLevel, notes";
    public static final String VERSION_NUMBER = "1.3.3-frc-INDEV";
    public static final int YEAR_NUMBER = 2020;
    // End season-specific info.


    // The following variables are not supposed to be changed.
    // DO NOT CHANGE THESE VARIABLES.
    public static boolean useAutosave = true;
    public static int autosaveSeconds = 7200;

    public static ArrayList<MatchInfo> matchList = new ArrayList<MatchInfo>();

    public static int selfTeamNumber = 492;
    public static String firstName = "Unknown";
    public static String lastName = "Unknown";

    public static boolean autoSaveRunnableInit = false;

    public static boolean deviceSupportsNfc = false;

    public static String serverIP = null;
    public static int serverPort = 3621;
    public static String username = null;
    public static String password = null;


    private static String getTextFileContents(File file)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static synchronized void writeArraylistsToJSON() throws IOException, JSONException
    {
        File writeDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
        if (!writeDirectory.exists())
        {
            writeDirectory.mkdir();
        }
        File cache = new File(writeDirectory, "cache-" + YEAR_NUMBER + ".json");
        if(!cache.exists())
        {
            cache.createNewFile();
        }
        PrintWriter pw = new PrintWriter(new FileWriter(cache));
        JSONObject jsonObject = new JSONObject();
        JSONArray matchArray = GsonUtilz.MatchInfoArrayListToJSONArray(matchList);
        jsonObject.put("matches", matchArray);
        pw.println(jsonObject.toString());
        pw.close();
    }

    public static synchronized void readArraylistsFromJSON() throws IOException, JSONException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        if (!readDirectory.exists())
        {
            readDirectory.mkdir();
        }
        File cache = new File(readDirectory, "cache-" + YEAR_NUMBER + ".json");
        if (cache.exists())
        {
            String jsonData = getTextFileContents(cache);
            JSONObject jsonCacheObject = new JSONObject(jsonData);
            matchList = GsonUtilz.JSONArrayToMatchInfoArrayList(jsonCacheObject.getJSONArray("matches"));
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
            PrintWriter pw = new PrintWriter(new FileWriter(log, true));
            pw.println("Log by: " + firstName + " " + lastName + ", written on " + getDateAsString());
            pw.println(CSV_HEADER);
            for(MatchInfo match : matchList)
            {
                pw.println(match.getCsvString());
            }
            pw.close();
            return true;
        }
    }

    public static synchronized String getDateAsString()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static synchronized void parseUserInfoGeneral() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        if (!readDirectory.exists())
        {
            readDirectory.mkdir();
        }
        File log = new File(readDirectory, "settings.coda");
        if (log.exists())
        {
            BufferedReader br = new BufferedReader(new FileReader(log));
            try
            {
                selfTeamNumber = Integer.parseInt(br.readLine());
            }
            catch (NumberFormatException e)
            {
                selfTeamNumber = 492; // can't read team num, return to default value.
            }
            firstName = br.readLine();
            lastName = br.readLine();
        }
        else
        {
            selfTeamNumber = 492; // return by default
        }
    }

    public static synchronized void parseAutoSaveInfo() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        if (!readDirectory.exists())
        {
            readDirectory.mkdir();
        }
        File log = new File(readDirectory, "autosave.coda");
        if (log.exists())
        {
            BufferedReader br = new BufferedReader(new FileReader(log));
            String yn = br.readLine();
            if (yn.contains("y"))
            {
                useAutosave = true;
            }
            else
            {
                useAutosave = false;
            }
            try
            {
                autosaveSeconds = Integer.parseInt(br.readLine());
            }
            catch (NumberFormatException e)
            {
                autosaveSeconds = 300;
                e.printStackTrace();
            }
        }
    }


    public static synchronized void parseServerLoginData() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
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
        PrintWriter pw = new PrintWriter(new FileWriter(log));
        pw.println(serverIP);
        pw.println(serverPort);
        pw.println(username);
        pw.println(password);
        pw.flush();
        pw.close();
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
