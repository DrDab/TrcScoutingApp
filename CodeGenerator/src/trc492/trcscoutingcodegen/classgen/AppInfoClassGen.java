package trc492.trcscoutingcodegen.classgen;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import trc492.trcscoutingcodegen.data.AppInfoSettings;
import trc492.trcscoutingcodegen.data.Page;
import trc492.trcscoutingcodegen.data.SessionData;

public class AppInfoClassGen
{
    private SessionData sessionData;

    public AppInfoClassGen(SessionData sessionData)
    {
        this.sessionData = sessionData;
    }
    
    public String generateCode()
    {
        String codeTemp = "package trc3543.trcscoutingapp.data;\r\n"
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
        
        AppInfoSettings settings = sessionData.appInfoSettings;
        List<Page> pages = sessionData.pages;
        Collections.sort(pages, new Comparator<Page>() {

            @Override
            public int compare(Page arg0, Page arg1)
            {
                return Integer.compare(arg0.pageNum, arg1.pageNum);
            }
            
        });
        
        String csvHeaderStr = settings.csvHeader;
        Integer yearNum = settings.yearNumber;
        int numPages = pages.size();
        String tabNamesStr = "{";
        String fragClassesStr = "{";
        
        for (int i = 0; i < pages.size(); i++)
        {
            Page page = pages.get(i);
            tabNamesStr += page.tabName;
            fragClassesStr += page.className + ".class";
            if (i != pages.size() - 1)
            {
                tabNamesStr += ",";
                fragClassesStr += ",";
            }
        }
        
        tabNamesStr += "}";
        fragClassesStr += "}";
        
        String code = String.format(codeTemp, csvHeaderStr, yearNum, numPages, tabNamesStr, fragClassesStr);
        return code;
    }

}
