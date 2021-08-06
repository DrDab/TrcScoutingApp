package trc492.trcscoutingcodegen.commands;

import java.io.IOException;
import java.util.List;

import trc492.trcscoutingcodegen.CurSessionHandlerUtil;
import trc492.trcscoutingcodegen.classgen.AppInfoClassGen;

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
        
        // generate AppInfo
        AppInfoClassGen a = new AppInfoClassGen(util.sessionData);
        System.out.println(a.generateCode());

        return false;
    }

}
