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
import java.util.List;
import java.util.Locale;

import static trc3543.trcscoutingapp.data.AppInfo.CSV_HEADER;
import static trc3543.trcscoutingapp.data.AppInfo.DATA_FOLDER_NAME;
import static trc3543.trcscoutingapp.data.AppInfo.YEAR_NUMBER;

@SuppressWarnings("All")
public class IOUtils extends AppCompatActivity
{
    public static boolean deviceSupportsNfc = false;

    public static String getTextFileContents(File file)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String curLine;
            while ((curLine = br.readLine()) != null)
            {
                contentBuilder.append(curLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static AppSettings readSettingsFromJSON(File settingsFile) throws IOException, JSONException
    {
        String fileContents = getTextFileContents(settingsFile);
        JSONObject obj = new JSONObject(fileContents);
        return AppSettings.fromJSONObject(obj);
    }

    public static void writeSettingsToJSON(AppSettings appSettings, File file) throws IOException, JSONException
    {
        if(!file.exists())
            file.createNewFile();
        PrintWriter pw = new PrintWriter(new FileWriter(file));
        pw.println(appSettings.toJSONObject().toString());
        pw.close();
    }

    public static void writeArraylistsToJSON(List<MatchInfo> matchList) throws IOException, JSONException
    {
        File writeDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        if (!writeDirectory.exists())
        {
            writeDirectory.mkdir();
        }
        File cache = new File(writeDirectory, "cache-" + YEAR_NUMBER + ".json");
        if (!cache.exists())
        {
            cache.createNewFile();
        }
        PrintWriter pw = new PrintWriter(new FileWriter(cache));
        JSONObject jsonObject = new JSONObject();
        JSONArray matchArray = GsonUtilz.MatchInfoListToJSONArray(matchList);
        jsonObject.put("matches", matchArray);
        pw.println(jsonObject.toString());
        pw.close();
    }

    public static List<MatchInfo> readArraylistsFromJSON(List<MatchInfo> matchList) throws IOException, JSONException
    {
        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);
        if (!readDirectory.exists())
        {
            readDirectory.mkdir();
        }
        File cache = new File(readDirectory, "cache-" + YEAR_NUMBER + ".json");
        if (cache.exists())
        {
            String jsonData = IOUtils.getTextFileContents(cache);
            JSONObject jsonCacheObject = new JSONObject(jsonData);
            return GsonUtilz.JSONArrayToMatchInfoList(jsonCacheObject.getJSONArray("matches"));
        }
        return new ArrayList<>();
    }

    public static boolean writeContestsToCsv(AppSettings appSettings, List<MatchInfo> matchList, String filename) throws IOException
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
            pw.println("Log by: " + appSettings.firstName + " " + appSettings.lastName + ", written on " + getDateAsString());
            pw.println(CSV_HEADER);
            for(MatchInfo match : matchList)
            {
                pw.println(match.getCsvString());
            }
            pw.close();
            return true;
        }
    }

    public static String getDateAsString()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
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
