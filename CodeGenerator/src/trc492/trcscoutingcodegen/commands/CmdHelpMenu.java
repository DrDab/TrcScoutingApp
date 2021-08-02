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

        String searchCmd = null;
        boolean searchSuccess = false;

        if (args.size() > 1)
        {
            searchCmd = args.get(1);
        }

        for (Command command : commands)
        {
            if (searchCmd != null)
            {
                if (searchCmd.equals(command.getName()))
                {
                    searchSuccess = true;
                }
                else
                {
                    continue;
                }
            }
            System.out.printf("%s - %s\n\t", command.getName(), command.getDescription());
            command.printSyntax();
        }
        
        if (searchCmd != null && !searchSuccess)
            System.out.printf("\"%s\" is not a valid command. Please type \"help\" for a list of commands.\n", searchCmd);

        return searchCmd == null ? true : searchSuccess;
    }

}
