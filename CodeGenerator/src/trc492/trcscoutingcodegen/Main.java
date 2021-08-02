package trc492.trcscoutingcodegen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import trc492.trcscoutingcodegen.commands.CmdCreateFile;
import trc492.trcscoutingcodegen.commands.CmdExit;
import trc492.trcscoutingcodegen.commands.CmdListFields;
import trc492.trcscoutingcodegen.commands.CmdListPages;
import trc492.trcscoutingcodegen.commands.CmdLoadFile;
import trc492.trcscoutingcodegen.commands.CmdUnloadFile;
import trc492.trcscoutingcodegen.commands.Command;

public class Main
{
    public static List<Command> commands;

    public static void main(String[] args)
    {
        System.out.println("TRC Scouting App Code Generator");
        System.out.println("(C) 2021 Titan Robotics Club");

        Scanner sc = new Scanner(System.in);
        GeneratorUtil util = new GeneratorUtil(sc);

        loadCommands(util);

        while (true)
        {
            System.out.print("codegen>");
            String command = sc.nextLine();
            if (command == null)
                System.out.println("Invalid command");
            else
            {
                List<String> cmdArgs = splitCommand(command);
                try
                {
                    processCommand(cmdArgs, util);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void loadCommands(GeneratorUtil util)
    {
        commands = new ArrayList<>();
        commands.add(new CmdCreateFile(util));
        commands.add(new CmdLoadFile(util));
        commands.add(new CmdUnloadFile(util));
        commands.add(new CmdListFields(util));
        commands.add(new CmdListPages(util));
        commands.add(new CmdExit());
    }

    public static void processCommand(List<String> cmdArgs, GeneratorUtil util) throws IOException
    {
        if (cmdArgs == null || cmdArgs.size() == 0)
            return;

        String root = cmdArgs.get(0);
        
        for (Command cmd : commands)
        {
            if (cmd.getName().equals(root))
                cmd.call(cmdArgs);
        }
    }

    public static List<String> splitCommand(String command)
    {
        if (command == null)
            return null;

        List<String> matchList = new ArrayList<String>();
        Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
        Matcher regexMatcher = regex.matcher(command);
        while (regexMatcher.find())
        {
            if (regexMatcher.group(1) != null)
            {
                // Add double-quoted string without the quotes
                matchList.add(regexMatcher.group(1));
            }
            else if (regexMatcher.group(2) != null)
            {
                // Add single-quoted string without the quotes
                matchList.add(regexMatcher.group(2));
            }
            else
            {
                // Add unquoted word
                matchList.add(regexMatcher.group());
            }
        }
        return matchList;
    }
}
