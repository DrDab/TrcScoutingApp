package trc492.trcscoutingcodegen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import trc492.trcscoutingcodegen.data.Field;
import trc492.trcscoutingcodegen.data.Page;

public class Main
{

    public static void main(String[] args)
    {
        System.out.println("TRC Scouting App Code Generator");
        System.out.println("(C) 2021 Titan Robotics Club");

        Scanner sc = new Scanner(System.in);
        FileIOUtil util = new FileIOUtil(sc);

        while (true)
        {
            System.out.print("codegen>");
            String command = sc.nextLine();
            if (command == null)
                System.out.println("Invalid command");
            else
            {
                List<String> commandParts = splitCommand(command);
                try
                {
                    processCommand(commandParts, util);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void processCommand(List<String> commandParts, FileIOUtil util) throws IOException
    {
        if (commandParts == null || commandParts.size() == 0)
            return;

        String root = commandParts.get(0);

        switch (root)
        {
            case "create":
                if (commandParts.size() < 2)
                {
                    System.out.println("Usage: create <filename>");
                    return;
                }

                String filename = commandParts.get(1);

                if (util.fileExists(filename))
                {
                    if (util.promptYN("File already exists. Would you like to overwrite file %s? (Y/N)", filename))
                    {
                        util.createNewSession(filename);
                        System.out.printf("Overwrote and loaded file %s\n", filename);
                    }
                }
                else
                {
                    util.createNewSession(filename);
                    System.out.printf("Created and loaded file %s\n", filename);
                }

                break;

            case "load":
                if (commandParts.size() < 2)
                {
                    System.out.println("Usage: load <filename>");
                    return;
                }

                filename = commandParts.get(1);

                if (!util.fileExists(filename))
                {
                    System.out.printf("Error: File %s doesn't exist.\n", filename);
                    break;
                }

                if (util.loadSessionData(filename))
                {
                    System.out.printf("Loaded file %s\n", filename);
                }
                else
                {
                    System.out.printf("Did not load file %s successfully\n", filename);
                }

                break;

            case "pages":
                if (!util.sessionLoaded())
                {
                    System.out.println("No session loaded!");
                    break;
                }

                System.out.printf("%d Pages:\n", util.sessionData.pages.size());
                for (Page page : util.sessionData.pages)
                {
                    System.out.println(page);
                }
                System.out.println("---");
                break;

            case "fields":
                if (!util.sessionLoaded())
                {
                    System.out.println("No session loaded!");
                    break;
                }
                
                System.out.printf("%d Fields:\n", util.sessionData.fields.size());
                for (Field field : util.sessionData.fields)
                {
                    System.out.println(field);
                }
                System.out.println("---");
                break;
                
            case "unload":
                util.unloadSession();
                System.out.println("Session unloaded.");
                break;
                
            case "setfield":
                break;
                
            case "delfield":
                break;
                
            case "mapcol":
                break;
                
            case "delcol":
                break;
                
            case "newpage":
                break;
                
            case "pagetabname":
                break;
                
            case "setpage":
                break;
                
            case "delpage":
                break;
                
            case "elements":
                break;
                
            case "typefields":
                break;
                
            case "addelement":
                break;
                
            case "mapelement":
                break;
                
            case "delelement":
                break;
                
            case "exportcode":
                break;

            case "exit":
                System.out.println("Bye");
                System.exit(0);
                break;

            default:
                break;
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
