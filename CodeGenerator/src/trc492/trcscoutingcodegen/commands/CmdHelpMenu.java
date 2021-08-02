package trc492.trcscoutingcodegen.commands;

import java.io.IOException;
import java.util.List;

public class CmdHelpMenu extends Command
{
    private List<Command> commands;
    
    public CmdHelpMenu(List<Command> commands)
    {
        super("help", "Lists syntax and usage all commands, or of optional given command.", "Usage: help [command]");
        this.commands = commands;
    }

    @Override
    public boolean call(List<String> args) throws IOException
    {
        if (commands == null)
        {
            System.out.println("No commands to list!");
            return false;
        }
        
        for (Command command : commands)
        {
            System.out.printf("%s - %s\n\t", command.getName(), command.getDescription());
            command.printSyntax();
        }
        
        return true;
    }

}
