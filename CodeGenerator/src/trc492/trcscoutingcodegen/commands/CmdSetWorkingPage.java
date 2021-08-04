package trc492.trcscoutingcodegen.commands;

import java.io.IOException;
import java.util.List;

import trc492.trcscoutingcodegen.CurSessionHandlerUtil;
import trc492.trcscoutingcodegen.data.Page;

public class CmdSetWorkingPage extends Command
{
    private CurSessionHandlerUtil util;

    public CmdSetWorkingPage(CurSessionHandlerUtil util)
    {
        super("wp", "Sets, unsets, or updates an attribute of the current working page.", "Usage: wp set <classname>",
            "wp update {tabname | classname | fragname | pagenum} <name | pagenum>", "wp unset");
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
            case "set":
                if (size < 3)
                {
                    System.out.println(super.getSyntax()[0]);
                    return false;
                }
                
                String wpClassname = args.get(2);
                
                for (Page page : util.sessionData.pages)
                {
                    if (page.className.equals(wpClassname))
                    {
                        util.curWorkingPage = page;
                        System.out.printf("Set current working page to page %s.\n", wpClassname);
                        return true;
                    }
                }
                
                System.out.printf("No page with class name %s exists.\n", wpClassname);
                break;
                
            case "update":
                if (size < 4)
                {
                    System.out.println("Usage: " + super.getSyntax()[1]);
                    return false;
                }
                
                Page curWorkingPage = util.curWorkingPage;
                
                if (curWorkingPage == null)
                {
                    System.out.println("No current working page!");
                }

                String pageFieldToUpdate = args.get(2);
                switch (pageFieldToUpdate)
                {
                    case "tabname":
                        curWorkingPage.tabName = args.get(3);
                        util.writeSessionData();
                        System.out.printf("Set attribute %s of working page to %s.\n", pageFieldToUpdate, args.get(3));
                        return true;

                    case "classname":
                        curWorkingPage.className = args.get(3);
                        util.writeSessionData();
                        System.out.printf("Set attribute %s of working page to %s.\n", pageFieldToUpdate, args.get(3));
                        return true;

                    case "fragname":
                        curWorkingPage.fragmentName = args.get(3);
                        util.writeSessionData();
                        System.out.printf("Set attribute %s of working page to %s.\n", pageFieldToUpdate, args.get(3));
                        return true;

                    case "pagenum":
                        try
                        {
                            curWorkingPage.pageNum = Integer.parseInt(args.get(3));
                            util.writeSessionData();
                            System.out.printf("Set attribute %s of working page to %s.\n", pageFieldToUpdate, args.get(3));
                        }
                        catch (NumberFormatException uwu)
                        {
                            System.out.println("pagenum must be an integer.");
                            return false;
                        }
                        return true;

                    default:
                        System.out.printf("Page attribute \"%s\" not recognized.\n", pageFieldToUpdate);
                        System.out.println("Recognized page attributes: tabname, classname, fragname, pagenum");
                        break;
                }
                break;
                
            case "unset":
                util.curWorkingPage = null;
                System.out.println("Unset current working page.");
                return true;
                
            default:
                System.out.printf("Mode not recognized: %s\n", mode);
                break;
        }
        
        return false;
    }

}
