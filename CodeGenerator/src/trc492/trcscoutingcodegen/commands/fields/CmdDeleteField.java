package trc492.trcscoutingcodegen.commands.fields;

import java.io.IOException;
import java.util.List;

import trc492.trcscoutingcodegen.commands.Command;

public class CmdDeleteField extends Command
{

    public CmdDeleteField(String name, String description, String... syntax)
    {
        super(name, description, syntax);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean call(List<String> args) throws IOException
    {
        // TODO Auto-generated method stub
        return false;
    }

}
