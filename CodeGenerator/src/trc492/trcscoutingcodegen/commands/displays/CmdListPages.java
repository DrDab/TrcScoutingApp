package trc492.trcscoutingcodegen.commands.displays;

import java.io.IOException;
import java.util.List;

import trc492.trcscoutingcodegen.GeneratorUtil;
import trc492.trcscoutingcodegen.commands.Command;
import trc492.trcscoutingcodegen.data.Page;

public class CmdListPages extends Command
{
    private GeneratorUtil util;
    
    public CmdListPages(GeneratorUtil util)
    {
        super("pages", "Lists the current pages in the editor session.", "Usage: pages");
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

        System.out.printf("%d Pages:\n", util.sessionData.pages.size());
        for (Page page : util.sessionData.pages)
        {
            System.out.println(page);
        }
        System.out.println("---");
        
        return true;
    }

}
