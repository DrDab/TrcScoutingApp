package trc492.trcscoutingcodegen.commands.io;

import java.io.IOException;
import java.util.List;

import trc492.trcscoutingcodegen.GeneratorUtil;
import trc492.trcscoutingcodegen.commands.Command;

public class CmdCreateFile extends Command
{
    private GeneratorUtil util;
    
    public CmdCreateFile(GeneratorUtil util)
    {
        super("create", "Creates a data file with a given filename.", "Usage: create <filename>");
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

        if (util.fileExists(filename))
        {
            if (util.promptYN("File already exists. Would you like to overwrite file %s? (Y/N)", filename))
            {
                util.createNewSession(filename);
                System.out.printf("Overwrote and loaded file %s.\n", filename);
            }
        }
        else
        {
            util.createNewSession(filename);
            System.out.printf("Created and loaded file %s.\n", filename);
        }
        
        return true;
    }

}
