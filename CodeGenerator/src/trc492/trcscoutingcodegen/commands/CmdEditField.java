package trc492.trcscoutingcodegen.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import trc492.trcscoutingcodegen.CurSessionHandlerUtil;
import trc492.trcscoutingcodegen.StrUtil;
import trc492.trcscoutingcodegen.data.Element;
import trc492.trcscoutingcodegen.data.Field;
import trc492.trcscoutingcodegen.data.FieldFlag;
import trc492.trcscoutingcodegen.data.FieldType;
import trc492.trcscoutingcodegen.data.Page;

public class CmdEditField extends Command
{
    private CurSessionHandlerUtil util;

    public CmdEditField(CurSessionHandlerUtil util)
    {
        super("field",
            "Creates, edits or removes a field, consisting of a name and type.\n\tTypes include: int, double, bool(ean), str(ing)",
            "Usage: field add <name> <type>", "field rename <oldname> <newname>", "field settype <name> <type>",
            "field del <name>", "field flag {add|del} <name> <flag>", "field flag list <name>");
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

            case "flag":
                if (size < 3)
                {
                    System.out.println("Usage: " + super.getSyntax()[4]);
                    System.out.println(super.getSyntax()[5]);
                    return false;
                }

                return handleFieldFlagOp(args, size);

            case "del":
                if (size < 3)
                {
                    System.out.println("Usage: " + super.getSyntax()[3]);
                    return false;
                }

                String delName = args.get(2);

                for (int i = 0; i < util.sessionData.fields.size(); i++)
                {
                    Field field = util.sessionData.fields.get(i);
                    if (field.fieldName.equals(delName))
                    {
                        util.sessionData.fields.remove(i);
                        
                        // TODO remove elements mapped to field as well
                        int cnt = 0;
                        for (Page page : util.sessionData.pages)
                        {
                            List<Element> toRemove = new ArrayList<>();
                            for (Element element : page.elements)
                            {
                                if (element.field == field)
                                {
                                    toRemove.add(element);
                                }
                            }
                            
                            for (Element element : toRemove)
                            {
                                page.elements.remove(element);
                                cnt++;
                            }
                        }
                        
                        util.writeSessionData();
                        System.out.printf("Removed field %s and %d mapped elements.\n", delName, cnt);
                        
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

    private boolean handleFieldFlagOp(List<String> args, int size) throws IOException
    {
        String flagOp = args.get(2);

        switch (flagOp)
        {
            case "list":
                if (size < 4)
                {
                    System.out.println("Usage: " + super.getSyntax()[5]);
                    return false;
                }

                String fieldName = args.get(3);
                Field tgtField = null;

                for (Field field : util.sessionData.fields)
                {
                    if (field.fieldName.equals(fieldName))
                    {
                        tgtField = field;
                        break;
                    }
                }

                if (tgtField == null)
                {
                    System.out.printf("Field %s doesn't exist\n", fieldName);
                    return false;
                }
                
                System.out.printf("Field %s - %d flags:\n", fieldName, tgtField.fieldFlags.size());
                for (FieldFlag flag : tgtField.fieldFlags)
                {
                    System.out.println(flag);
                }
                System.out.println("---");
                return true;

            case "add":
                if (size < 5)
                {
                    System.out.println("Usage: " + super.getSyntax()[4]);
                    return false;
                }

                fieldName = args.get(3);
                String flagStr = args.get(4);
                FieldFlag flag = StrUtil.fieldFlagFromStr(flagStr);

                if (flag == null)
                {
                    System.out.println("flag must be one of the following: must_be_filled, match_num, alliance_type");
                    return false;
                }

                Field editField = null;

                for (Field field : util.sessionData.fields)
                {
                    if (field.fieldName.equals(fieldName))
                    {
                        editField = field;
                        break;
                    }
                }

                if (editField == null)
                {
                    System.out.printf("Field %s doesn't exist\n", fieldName);
                    return false;
                }

                for (FieldFlag check : editField.fieldFlags)
                {
                    if (check == flag)
                    {
                        System.out.printf("Field %s already contains flag %s!\n", fieldName, flagStr);
                        return false;
                    }
                }

                editField.fieldFlags.add(flag);
                util.writeSessionData();
                System.out.printf("Added flag %s to field %s.\n", flagStr, fieldName);
                return true;

            case "del":
                if (size < 5)
                {
                    System.out.println("Usage: " + super.getSyntax()[4]);
                    return false;
                }

                fieldName = args.get(3);
                flagStr = args.get(4);
                flag = StrUtil.fieldFlagFromStr(flagStr);

                if (flag == null)
                {
                    System.out.println("flag must be one of the following: must_be_filled, match_num, alliance_type");
                    return false;
                }

                editField = null;

                for (Field field : util.sessionData.fields)
                {
                    if (field.fieldName.equals(fieldName))
                    {
                        editField = field;
                        break;
                    }
                }

                if (editField == null)
                {
                    System.out.printf("Field %s doesn't exist\n", fieldName);
                    return false;
                }

                for (FieldFlag check : editField.fieldFlags)
                {
                    if (check == flag)
                    {
                        editField.fieldFlags.remove(check);
                        util.writeSessionData();
                        System.out.printf("Removed flag %s from field %s.\n", flagStr, fieldName);
                        return true;
                    }
                }

                System.out.printf("Field %s does not contain flag %s.\n", fieldName, flagStr);
                break;
        }

        return false;
    }

}
