package trc492.trcscoutingcodegen.commands;

import java.io.IOException;
import java.util.List;

import trc492.trcscoutingcodegen.CurSessionHandlerUtil;
import trc492.trcscoutingcodegen.classgen.AddMatchesClassGen;
import trc492.trcscoutingcodegen.classgen.AppInfoClassGen;
import trc492.trcscoutingcodegen.classgen.MatchInfoClassGen;
import trc492.trcscoutingcodegen.data.AppInfoSettings;
import trc492.trcscoutingcodegen.data.SessionData;

public class CmdGenerateCode extends Command
{
    private CurSessionHandlerUtil util;

    public CmdGenerateCode(CurSessionHandlerUtil util)
    {
        super("generatecode", "Generates code from variables", "OwO");
        this.util = util;
    }

    @Override
    public boolean call(List<String> args) throws IOException
    {
        if (!util.sessionLoaded())
        {
            System.out.println("No session loaded!");
            return false;
        }
        
        // check all necessary info is filled
        SessionData data = util.sessionData;
        AppInfoSettings appInfoSettings = data.appInfoSettings;
        
        if (appInfoSettings.csvHeader == null)
        {
            System.out.println("CSV header cannot be null! Use \"appinfo set csv_header <csv header>\" to set a CSV header.");
            return false;
        }
        
        // generate AppInfo
        AppInfoClassGen a = new AppInfoClassGen(util.sessionData);
        System.out.println(a.generateCode());
        
        AddMatchesClassGen b = new AddMatchesClassGen(util.sessionData);
        System.out.println(b.generateCode());
        
        MatchInfoClassGen c = new MatchInfoClassGen(util.sessionData);
        System.out.println(c.generateCode());

        return false;
    }

}
