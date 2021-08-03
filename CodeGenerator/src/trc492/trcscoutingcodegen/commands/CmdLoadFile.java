package trc492.trcscoutingcodegen.commands;

import java.io.IOException;
import java.util.List;

import trc492.trcscoutingcodegen.CurSessionHandlerUtil;

public class CmdLoadFile extends Command
{
    private CurSessionHandlerUtil util;

    public CmdLoadFile(CurSessionHandlerUtil util)
    {
        super("load", "Loads a data file given the filename.", "Usage: load <filename>");
        this.util = util;
    }

    @Override
    public boolean call(List<String> args) throws IOException
    {
        if (args.size() < 2)
        {
            super.printSyntax();
            return false;
        }

        String filename = args.get(1);

        if (!util.fileExists(filename))
        {
            System.out.printf("Error: File %s doesn't exist.\n", filename);
            return true;
        }

        if (util.loadSessionData(filename))
        {
            System.out.printf("Loaded file %s\n", filename);
        }
        else
        {
            System.out.printf("Did not load file %s successfully\n", filename);
        }
        
        return true;
    }

}
