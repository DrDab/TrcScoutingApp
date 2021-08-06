package trc492.trcscoutingcodegen.commands;

import java.io.IOException;
import java.util.List;

import trc492.trcscoutingcodegen.CurSessionHandlerUtil;
import trc492.trcscoutingcodegen.data.AppInfoSettings;

public class CmdEditAppInfo extends Command
{
    private CurSessionHandlerUtil util;

    public CmdEditAppInfo(CurSessionHandlerUtil util)
    {
        super("appinfo",
            "Edits the AppInfo variables of TrcScoutingApp code.\n\tThe AppInfo variables are: csv_header (a string), and year_number (an integer).",
            "Usage: appinfo list", "appinfo set <variable> <value>");
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
        AppInfoSettings info = util.sessionData.appInfoSettings;

        switch (mode)
        {
            case "list":
                System.out.printf("csv_header: %s\n, year_number:%s\n",
                    info.csvHeader == null ? "NULL" : info.csvHeader,
                    info.yearNumber == null ? "NULL" : info.yearNumber);
                return true;

            case "set":
                if (size < 4)
                {
                    System.out.println("Usage: " + super.getSyntax()[2]);
                    return false;
                }

                String variableStr = args.get(2);
                String valueStr = args.get(3);

                switch (variableStr.toLowerCase().toString())
                {
                    case "csv_header":
                        info.csvHeader = valueStr;
                        util.writeSessionData();
                        return true;

                    case "year_number":
                        try
                        {
                            info.yearNumber = Integer.parseInt(valueStr);
                            util.writeSessionData();
                            return true;
                        }
                        catch (NumberFormatException uwu)
                        {
                            System.out.println("year_number value must be an integer.");
                        }
                        return false;
                }

                System.out.printf(
                    "AppInfo variable %s not recognized.\nThe AppInfo variables are: csv_header (a string), and year_number (an integer).\n",
                    variableStr);
                break;

            default:
                System.out.printf("Mode not recognized: %s\n", mode);
                break;
        }

        return false;
    }

}
