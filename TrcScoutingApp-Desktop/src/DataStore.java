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

@SuppressWarnings("all")
public class DataStore 
{
    static ArrayList<String> contests = new ArrayList<String>();
    /**
     * The ArrayList "CsvFormattedContests" should be in the format:
     * "Team Contained Status, Date, Match #, Competition Name, Competition Type, Red Alliance 1, Red Alliance 2, Blue Alliance 1, Blue Alliance 2, SampleCondition1, SampleCond2"
     */
    static ArrayList<String> CsvFormattedContests = new ArrayList<String>();

    static int SELF_TEAM_NUMBER = 3543;
    static String FIRST_NAME = "Unknown";
    static String LAST_NAME = "Unknown";

    static boolean hasAdded = false;
    
    static String HOME_DIR_DESKTOP = System.getProperty("user.home")+"/Desktop/";
    
    public static boolean writeContestsToCsv(String filename) throws IOException
    {
        File writeDirectory = new File(HOME_DIR_DESKTOP+"TrcScoutingApp/");
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
            madoka.println("Log by: " + FIRST_NAME + " " + LAST_NAME + ", written on " + getDateAsString());
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

    public static void parseTeamNum() throws IOException
    {
        File readDirectory = new File(HOME_DIR_DESKTOP+"TrcScoutingApp/");
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
        File readDirectory = new File(HOME_DIR_DESKTOP+"TrcScoutingApp/");
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
        File readDirectory = new File(HOME_DIR_DESKTOP+"TrcScoutingApp/");
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
    public static void writeSettingsToFile(String firstname, String lastname, int teamNum) throws IOException
    {
        File writeDirectory = new File(HOME_DIR_DESKTOP+"TrcScoutingApp/");
        if (!writeDirectory.exists())
        {
            writeDirectory.mkdir();
        }
        else
        {
            File log = new File(writeDirectory, "settings.coda");
            if(!log.exists())
            {
                log.createNewFile();
            }
            PrintWriter waffleryebread = new PrintWriter(new FileWriter(log));
            waffleryebread.println(teamNum);
            waffleryebread.println(firstname);
            waffleryebread.println(lastname);
            waffleryebread.println("\n");
            waffleryebread.flush();
            waffleryebread.close();
        }
        DataStore.parseTeamNum();
        DataStore.parseFirstName();
        DataStore.parseLastName();
    }
   
}
