package trc492.trcscoutingcodegen.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import trc492.trcscoutingcodegen.CurSessionHandlerUtil;
import trc492.trcscoutingcodegen.classgen.AddMatchesClassGen;
import trc492.trcscoutingcodegen.classgen.AppInfoClassGen;
import trc492.trcscoutingcodegen.classgen.ClassGenerator;
import trc492.trcscoutingcodegen.classgen.FragmentClassGen;
import trc492.trcscoutingcodegen.classgen.MatchInfoClassGen;
import trc492.trcscoutingcodegen.data.AppInfoSettings;
import trc492.trcscoutingcodegen.data.Page;
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
            System.out
                .println("CSV header cannot be null! Use \"appinfo set csv_header <csv header>\" to set a CSV header.");
            return false;
        }

        List<ClassGenerator> classGens = new ArrayList<>();
        classGens.add(new AppInfoClassGen(util.sessionData));
        classGens.add(new AddMatchesClassGen(util.sessionData));
        classGens.add(new MatchInfoClassGen(util.sessionData));

        for (Page page : util.sessionData.pages)
        {
            classGens.add(new FragmentClassGen(page));
        }

        System.out.printf("Instantiated %d class generators.\n", classGens.size());

        int successCnt = 0;

        for (ClassGenerator classGen : classGens)
        {
            System.out.printf("Exporting %s... ", classGen.getExportFile());
            boolean success = classGen.exportToFile();
            successCnt += success ? 1 : 0;
            System.out.println(success ? "Success!" : "Failed!");
        }
        
        System.out.printf("Successfully exported %d files.\n", successCnt);

        return true;
    }

}
