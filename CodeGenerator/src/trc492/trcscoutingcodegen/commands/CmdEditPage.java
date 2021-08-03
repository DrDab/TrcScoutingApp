package trc492.trcscoutingcodegen.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import trc492.trcscoutingcodegen.CurSessionHandlerUtil;
import trc492.trcscoutingcodegen.data.Page;

public class CmdEditPage extends Command
{
    private CurSessionHandlerUtil util;

    public CmdEditPage(CurSessionHandlerUtil util)
    {
        super("page", "Adds a page, sets a property of a page or deletes a page.",
            "Usage: page add <tabname> <classname> <fragmentname> <pagenum>",
            "page set {tabname | classname | fragname | pagenum} <name | pagenum>", "page del <classname>");
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

        int size = args.size();
        if (size < 2)
        {
            super.printSyntax();
            return false;
        }

        String mode = args.get(1);

        switch (mode)
        {
            case "add":
                if (size < 6)
                {
                    System.out.println(super.getSyntax()[0]);
                    return false;
                }

                String tabName = args.get(2);
                String className = args.get(3);
                String fragName = args.get(4);
                int pageNum = -1;
                try
                {
                    pageNum = Integer.parseInt(args.get(5));
                }
                catch (NumberFormatException uwu)
                {
                    System.out.println("pagenum must be an integer.");
                    return false;
                }

                Page newPage = new Page(pageNum, tabName, className, fragName, new ArrayList<>());
                util.sessionData.pages.add(newPage);
                util.writeSessionData();
                System.out.printf("Added page %s.\n", newPage);
                return true;

            case "del":
                if (size < 3)
                {
                    System.out.println("Usage: " + super.getSyntax()[2]);
                    return false;
                }
                
                String deletionClassName = args.get(2);
                
                for (Page page : util.sessionData.pages)
                {
                    if (page.className.equals(deletionClassName))
                    {
                        util.sessionData.pages.remove(page);
                        System.out.printf("Deleted page %s\n", deletionClassName);
                        return true;
                    }
                }
                
                System.out.printf("Page %s doesn't exist!\n", deletionClassName);
                break;

            default:
                System.out.printf("Mode not recognized: %s\n", mode);
                break;
        }

        return false;
    }

}
