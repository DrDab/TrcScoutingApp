package trc492.trcscoutingcodegen.commands;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import trc492.trcscoutingcodegen.GeneratorTempData;
import trc492.trcscoutingcodegen.data.Field;

public class CmdMapCsvColumn extends Command
{
    private GeneratorTempData util;

    public CmdMapCsvColumn(GeneratorTempData util)
    {
        super("csv", "Binds or unbinds a field to a column, or lists CSV bindings.", "Usage: csv map <col#> <field name>",
            "csv unmap <col#>", "csv list");
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
            case "map":
                if (size < 4)
                {
                    System.out.println(super.getSyntax()[0]);
                    return false;
                }
                
                int col = -1;
                try
                {
                    col = Integer.parseInt(args.get(2));
                }
                catch (NumberFormatException uwu)
                {
                    System.out.println("col# must be an integer.");
                    return false;
                }
                
                String mapFieldName = args.get(3);
                
                for (Field field : util.sessionData.fields)
                {
                    if (field.fieldName.equals(mapFieldName))
                    {
                        util.sessionData.csvBindings.put(col, field);
                        util.writeSessionData();
                        System.out.printf("Bound field %s to CSV column %d.\n", mapFieldName, col);
                        return true;
                    }
                }
                
                System.out.printf("Field \"%s\" doesn't exist!\n", mapFieldName);
                return false;

            case "unmap":
                if (size < 3)
                {
                    System.out.println(super.getSyntax()[1]);
                    return false;
                }
                
                try
                {
                    col = Integer.parseInt(args.get(2));
                }
                catch (NumberFormatException uwu)
                {
                    System.out.println("col# must be an integer.");
                    return false;
                }
                
                boolean success = util.sessionData.csvBindings.remove(col) != null;
                util.writeSessionData();
                System.out.printf(success ? "Unbound CSV column %d.\n" : "CSV column %d isn't bound.\n", col);
                return success;
                
            case "list":
                Set<Integer> cols = util.sessionData.csvBindings.keySet();
                System.out.printf("%d CSV bindings:\n", util.sessionData.csvBindings.size());
                for (Integer curCol : cols)
                {
                    System.out.printf("Col %d: %s\n", curCol, util.sessionData.csvBindings.get(curCol));
                }
                System.out.println("---");
                
                return true;

            default:
                System.out.printf("Mode not recognized: %s\n", mode);
                break;
        }

        return false;
    }

}
