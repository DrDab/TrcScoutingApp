package trc3543.trcscoutingapp;

import android.os.Environment;
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

    static boolean useAutosave = true; // by default, autosave is enabled.
    static int autosaveSeconds = 300;  // by default, save changes every 5 minutes.

    static ArrayList<String> contests = new ArrayList<String>();

    static ArrayList<String> CsvFormattedContests = new ArrayList<String>();

    static int selfTeamNumber = 3543;
    static String firstName = "Unknown";
    static String lastName = "Unknown";

    static boolean USE_DIRECT_SAVE = false; // don't use direct save by default

    public DataStore()
    {
        // TODO nothing
    }


    public static boolean writeContestsToCsv(String filename) throws IOException
    {
        File writeDirectory = new File(Environment.getExternalStorageDirectory(), "TrcScoutingApp");
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
            madoka.println("Log by: " + firstName + " " + lastName + ", written on " + getDateAsString());
            madoka.println("Contains Your Team, Date, Match #, Competition Type, Team Number, Spectating Team, Starting Position, AT-Robot Lowered, AT-Mineral Displaced, AT-Mineral Correct, AT-Marker Deployed, TO-Depot Score, TO-Lander Score, EG-Ending Location, Match Won, Autonomous Notes, TeleOp Notes");
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
                firstName = saiodfjsajofojfdfjisafbj;
        }
        else
        {
                firstName = "Unknown";
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
            lastName = saiodfjsajofojfdfjisafbj;
        }
        else
        {
            lastName = "Unknown";
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
