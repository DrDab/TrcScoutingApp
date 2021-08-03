package trc492.trcscoutingcodegen.commands.fields;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import trc492.trcscoutingcodegen.GeneratorTempData;
import trc492.trcscoutingcodegen.StrUtil;
import trc492.trcscoutingcodegen.commands.Command;
import trc492.trcscoutingcodegen.data.Field;
import trc492.trcscoutingcodegen.data.FieldType;

public class CmdEditField extends Command
{
    private GeneratorTempData util;

    public CmdEditField(GeneratorTempData util)
    {
        super("field",
            "Creates, edits or removes a field, consisting of a name and type.\n\tTypes include: int, double, bool(ean), str(ing)",
            "Usage: field add <name> <type>", "field rename <oldname> <newname>", "field settype <name> <type>",
            "field del <name>");
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
                if (size < 4)
                {
                    System.out.println(super.getSyntax()[0]);
                    return false;
                }
                String createName = args.get(2);
                String createTypeStr = args.get(3);

                for (Field field : util.sessionData.fields)
                {
                    if (field.fieldName.equals(createName))
                    {
                        System.out.printf("A field with name \"%s\" already exists!\n", field.fieldName);
                        return false;
                    }
                }

                FieldType createType = StrUtil.fieldTypeFromStr(createTypeStr);
                if (createType == null)
                {
                    System.out.println("Type must be one of the following: int, double, bool, str");
                    return false;
                }

                util.sessionData.fields.add(new Field(createType, createName, new ArrayList<>()));
                util.writeSessionData();
                System.out.printf("Created new field %s, type %s\n", createName, createType);
                return true;

            case "rename":
                if (size < 4)
                {
                    System.out.println("Usage: " + super.getSyntax()[1]);
                    return false;
                }

                String oldName = args.get(2);
                String newName = args.get(3);

                for (Field field : util.sessionData.fields)
                {
                    if (field.fieldName.equals(newName))
                    {
                        System.out.printf("A field with name \"%s\" already exists!\n", field.fieldName);
                        return false;
                    }
                }

                for (Field field : util.sessionData.fields)
                {
                    if (field.fieldName.equals(oldName))
                    {
                        field.fieldName = newName;
                        util.writeSessionData();
                        System.out.printf("Renamed field %s to %s\n", oldName, newName);
                        return true;
                    }
                }

                System.out.printf("Field %s doesn't exist\n", oldName);
                return false;

            case "settype":
                if (size < 4)
                {
                    System.out.println("Usage: " + super.getSyntax()[2]);
                    return false;
                }

                String setName = args.get(2);
                String newTypeStr = args.get(3);

                FieldType newType = StrUtil.fieldTypeFromStr(newTypeStr);
                if (newType == null)
                {
                    System.out.println("Type must be one of the following: int, double, bool, str");
                    return false;
                }

                for (Field field : util.sessionData.fields)
                {
                    if (field.fieldName.equals(setName))
                    {
                        field.fieldType = newType;
                        util.writeSessionData();
                        System.out.printf("Set type of field %s to %s\n", setName, newType);
                        return true;
                    }
                }

                System.out.printf("Field %s doesn't exist\n", setName);
                return false;

            case "del":
                if (size < 3)
                {
                    System.out.println("Usage: " + super.getSyntax()[3]);
                    return false;
                }

                String delName = args.get(2);

                for (int i = 0; i < util.sessionData.fields.size(); i++)
                {
                    if (util.sessionData.fields.get(i).fieldName.equals(delName))
                    {
                        util.sessionData.fields.remove(i);
                        util.writeSessionData();
                        System.out.printf("Removed field %s\n", delName);
                        return true;
                    }
                }

                System.out.printf("Field %s doesn't exist\n", delName);
                return false;

            default:
                System.out.printf("Mode not recognized: %s\n", mode);
                break;
        }

        // TODO Auto-generated method stub
        return false;
    }

}
