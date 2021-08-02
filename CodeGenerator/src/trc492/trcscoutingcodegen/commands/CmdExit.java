package trc492.trcscoutingcodegen.commands;

import java.io.IOException;
import java.util.List;

public class CmdExit extends Command
{
    public CmdExit()
    {
        super("exit", "Exits the code generator utility.", "Usage: exit");
    }

    @Override
    public boolean call(List<String> args) throws IOException
    {
        System.out.println("Bye");
        System.exit(0);
        return true;
    }

}
