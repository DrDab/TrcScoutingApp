package trc492.trcscoutingcodegen.classgen;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import trc492.trcscoutingcodegen.data.AppInfoSettings;
import trc492.trcscoutingcodegen.data.CodeTemplates;
import trc492.trcscoutingcodegen.data.Page;
import trc492.trcscoutingcodegen.data.SessionData;

public class AppInfoClassGen extends ClassGenerator
{
    private SessionData sessionData;

    public AppInfoClassGen(SessionData sessionData)
    {
        super("output", "data", "AppInfo.java");
        this.sessionData = sessionData;
    }
    
    @Override
    public String generateCode()
    {
        String codeTemp = CodeTemplates.APPINFO_CLASS_TEMPLATE;
        
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
            tabNamesStr += "\"" + page.tabName + "\"";
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
