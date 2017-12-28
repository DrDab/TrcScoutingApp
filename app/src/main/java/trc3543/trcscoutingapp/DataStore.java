package trc3543.trcscoutingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
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
    static ArrayList<String> contests = new ArrayList<String>();
    /**
     * The ArrayList "CsvFormattedContests" should be in the format:
     * "Team Contained Status, Date, Match #, Competition Name, Competition Type, Red Alliance 1, Red Alliance 2, Blue Alliance 1, Blue Alliance 2, SampleCondition1, SampleCond2"
     */
    static ArrayList<String> CsvFormattedContests = new ArrayList<String>();

    public DataStore()
    {
        // TODO nothing
    }


    public static boolean writeContestsToCsv(String filename) throws IOException
    {
        File writeDirectory = new File("/sdcard/TrcScoutingApp/");
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
            madoka.println("Contains Your Team, Date, Match #, Competition Name, Competition Type, Red Alliance 1, Red Alliance 2, Blue Alliance 1, Blue Alliance 2, Condition 1, Condition 2");
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

}
