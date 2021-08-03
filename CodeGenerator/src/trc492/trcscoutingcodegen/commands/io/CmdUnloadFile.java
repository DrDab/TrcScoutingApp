package trc492.trcscoutingcodegen.commands.io;

import java.io.IOException;
import java.util.List;

import trc492.trcscoutingcodegen.GeneratorTempData;
import trc492.trcscoutingcodegen.commands.Command;

public class CmdUnloadFile extends Command
{
    private GeneratorTempData util;

    public CmdUnloadFile(GeneratorTempData util)
    {
        super("unload", "Unloads the currently loaded session file, if any.", "Usage: unload");
        this.util = util;
    }

    @Override
    public boolean call(List<String> args) throws IOException
    {
        util.unloadSession();
        System.out.println("Session unloaded.");
        return true;
    }
}
