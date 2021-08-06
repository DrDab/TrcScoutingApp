package trc492.trcscoutingcodegen.commands;

import java.io.IOException;
import java.util.List;

public class CmdClearTerm extends Command
{
    public CmdClearTerm()
    {
        super("cls", "Clears the terminal history.", "Usage: cls");
    }

    @Override
    public boolean call(List<String> args) throws IOException
    {
        for (int i = 0; i < 1000; i++)
        {
            System.out.println();
        }
        
        return true;
    }

}
