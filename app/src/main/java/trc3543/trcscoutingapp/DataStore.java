package trc3543.trcscoutingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

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

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by citrus on 12/26/17.
 */

@SuppressWarnings("All")
public class DataStore extends AppCompatActivity
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

    static boolean USE_AUTOSAVE = true; // by default, autosave is enabled.
    static int AUTOSAVE_SECONDS = 300;  // by default, save changes every 5 minutes.

    static ArrayList<String> contests = new ArrayList<String>();
    /**
     * The ArrayList "CsvFormattedContests" should be in the format:
     * "Team Contained Status, Date, Match #, Competition Name, Competition Type, Spectating Team Number, Spectating Team Type, Conditions"
     */
    static ArrayList<String> CsvFormattedContests = new ArrayList<String>();

    static int SELF_TEAM_NUMBER = 3543;
    static String FIRST_NAME = "Unknown";
    static String LAST_NAME = "Unknown";

    static boolean USE_DIRECT_SAVE = false; // don't use direct save by default

    public DataStore()
    {
        // TODO nothing
    }


    public static boolean writeContestsToCsv(String filename) throws IOException
    {
        File writeDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
        // File writeDirectory = new File("/sdcard/TrcScoutingApp/");
        if (!writeDirectory.exists())
        {
            writeDirectory.mkdir();
        }
        if (CsvFormattedContests.size() == 0)
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
            madoka.println("Log by: " + FIRST_NAME + " " + LAST_NAME + ", written on " + getDateAsString());                    // auto                                                                                                                                        // teleop                                                                                                                                                                                                                                                                                                       // endgame                                                                       // penalties
            madoka.println("Contains Your Team, Date, Match #, Competition Type, Team Number, Spectating Team, Starting Position, AT-Crossed Auto Line, AT-# Cubes Placed on Scale, AT-# Cubes Attempted on Scale, AT-# Cubes Placed on Switch, AT-# Cubes Attempted on Switch, TO-# Cubes Placed Far Switch, TO-# Cubes Attempted Far Switch, TO-# Cubes Placed Near Switch, TO-# Cubes Attempted Near Switch, TO-# Cubes Placed on Scale, TO-# Cubes Attempted on Scale, TO-# Cubes Placed in Exchange, TO-# Cubes Attempted in Exchange, TO-Cube Pickup at Portal, TO-Cube Pickup at Ground, EG-Climb Attempt, EG-Successful Climb, EG-Park on Platform, EG-Robot Breakdown, PN-Herding Pen., PN-Scale Contact Pen., PN-Pinning Pen., PN-Zone Contact Pen., PN-Other, PN-Total Penalty Points, Autonomous Notes, Teleoperated Notes");
            for(String sk : CsvFormattedContests)
            {
                madoka.println(sk);
            }
            madoka.println("End Of Log");
            madoka.flush();
            madoka.close();
            return true;
        }
    }

    public static String getDateAsString()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static void parseTeamNum() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
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
                SELF_TEAM_NUMBER = saiodfjsajofojfdfjisafbj;
            }
            catch (NumberFormatException e)
            {
                SELF_TEAM_NUMBER = 3543; // can't read team num, return to default value.
            }
        }
        else
        {
            SELF_TEAM_NUMBER = 3543; // return by default
        }
    }
    public static void parseFirstName() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
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
                FIRST_NAME = saiodfjsajofojfdfjisafbj;
        }
        else
        {
                FIRST_NAME = "Unknown";
        }
    }
    public static void parseLastName() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
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
            LAST_NAME = saiodfjsajofojfdfjisafbj;
        }
        else
        {
            LAST_NAME = "Unknown";
        }
    }
    public static void parseDirectSave() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
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
            br.readLine();
            saiodfjsajofojfdfjisafbj = br.readLine();
            if (saiodfjsajofojfdfjisafbj.matches("y"))
            {
                USE_DIRECT_SAVE = true;
            }
            else
            {
                USE_DIRECT_SAVE = false;
            }
        }
        else
        {
            USE_DIRECT_SAVE = false;
        }
    }
    public static void parseAutoSaveBoolean() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
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
                USE_AUTOSAVE = true;
            }
            else
            {
                USE_AUTOSAVE = false;
            }
        }
        else
        {
            USE_AUTOSAVE = true;
        }
    }
    public static void parseAutoSaveTime() throws IOException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
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
                AUTOSAVE_SECONDS = Integer.parseInt(saiodfjsajofojfdfjisafbj);
            }
            catch (NumberFormatException e)
            {
                AUTOSAVE_SECONDS = 300;
                e.printStackTrace();
            }
        }
        else
        {
            AUTOSAVE_SECONDS = 300;
        }
    }
    public static boolean existsSave()
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
        File log = new File(readDirectory, "settings.coda");
        if (!log.exists())
        {
            return false;
        }
        return true;
    }
}
