package trc492.trcscoutingcodegen.commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import trc492.trcscoutingcodegen.CurSessionHandlerUtil;
import trc492.trcscoutingcodegen.StrUtil;
import trc492.trcscoutingcodegen.data.Element;
import trc492.trcscoutingcodegen.data.ElementType;
import trc492.trcscoutingcodegen.data.Field;
import trc492.trcscoutingcodegen.data.Page;

public class CmdManageElements extends Command
{
    private CurSessionHandlerUtil util;

    public CmdManageElements(CurSessionHandlerUtil util)
    {
        super("elements", "Adds, remaps, deletes or lists elements on the current working page.",
            "Usage: elements list", "elements add <Android R-id> <type> <fieldname>",
            "elements remap <Android R-id> <fieldname>", "elements maptypes <Android R-id>",
            "elements del <Android R-id>");
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
            case "list":
                Page curWorkingPage = util.curWorkingPage;
                if (curWorkingPage == null)
                {
                    System.out.println("No current working page!");
                    return false;
                }

                System.out.printf("%d elements in working page:\n", curWorkingPage.elements.size());

                for (Element element : curWorkingPage.elements)
                {
                    System.out.println(element);
                }

                System.out.println("---");
                return true;

            case "add":
                if (size < 5)
                {
                    System.out.println("Usage: " + super.getSyntax()[1]);
                    return false;
                }

                curWorkingPage = util.curWorkingPage;

                if (curWorkingPage == null)
                {
                    System.out.println("No current working page!");
                    return false;
                }

                String id = args.get(2);
                ElementType elementType = StrUtil.elementTypeFromStr(args.get(3));
                String mapFieldName = args.get(4);

                if (elementType == null)
                {
                    System.out.println(
                        "element type must be one of the following: edittext, checkbox, numberpicker, spinner, switch");
                    return false;
                }

                // get the field corresponding to fieldName and check if element
                // supports field data type
                Field mapField = null;
                for (Field field : util.sessionData.fields)
                {
                    if (field.fieldName.equals(mapFieldName))
                    {
                        mapField = field;
                        break;
                    }
                }

                if (mapField == null)
                {
                    System.out.printf("Field %s doesn't exist!\n", mapFieldName);
                    return false;
                }

                if (!mapField.supportedByElementType(elementType))
                {
                    System.out.printf("Element type %s does not support field type %s.\n", elementType,
                        mapField.fieldType);
                    System.out.printf("Supported field types for element type %s are: %s\n", elementType,
                        Arrays.toString(elementType.eligibleFields));
                    return false;
                }

                Element newElement = new Element(id, elementType, mapField);
                curWorkingPage.elements.add(newElement);
                util.writeSessionData();
                System.out.printf("Added element %s.\n", newElement);
                return true;

            case "remap":
                if (size < 4)
                {
                    System.out.println("Usage: " + super.getSyntax()[2]);
                    return false;
                }

                curWorkingPage = util.curWorkingPage;

                if (curWorkingPage == null)
                {
                    System.out.println("No current working page!");
                    return false;
                }

                id = args.get(2);
                mapFieldName = args.get(3);
                Element remapElement = null;

                for (Element element : curWorkingPage.elements)
                {
                    if (element.elementId.equals(id))
                    {
                        remapElement = element;
                        break;
                    }
                }

                if (remapElement == null)
                {
                    System.out.printf("Element id %s doesn't exist!\n", id);
                    return false;
                }

                mapField = null;
                for (Field field : util.sessionData.fields)
                {
                    if (field.fieldName.equals(mapFieldName))
                    {
                        mapField = field;
                        break;
                    }
                }

                if (mapField == null)
                {
                    System.out.printf("Field %s doesn't exist!\n", mapFieldName);
                    return false;
                }

                elementType = remapElement.elementType;

                if (!mapField.supportedByElementType(elementType))
                {
                    System.out.printf("Element type %s does not support field type %s.\n", elementType,
                        mapField.fieldType);
                    System.out.printf("Supported field types for element type %s are: %s\n", elementType,
                        Arrays.toString(elementType.eligibleFields));
                    return false;
                }

                remapElement.field = mapField;
                util.writeSessionData();
                System.out.printf("Remapped element with id %s to field %s.\n", id, mapFieldName);
                return true;

            case "maptypes":
                if (size < 3)
                {
                    System.out.println("Usage: " + super.getSyntax()[3]);
                    return false;
                }

                curWorkingPage = util.curWorkingPage;

                if (curWorkingPage == null)
                {
                    System.out.println("No current working page!");
                }

                id = args.get(2);

                for (Element element : curWorkingPage.elements)
                {
                    if (element.elementId.equals(id))
                    {
                        System.out.printf("Valid field types for element %s: %s\n", id,
                            Arrays.toString(element.elementType.eligibleFields));
                        return true;
                    }
                }

                System.out.printf("No element with id %s exists!\n", id);
                return false;

            case "del":
                if (size < 3)
                {
                    System.out.println("Usage: " + super.getSyntax()[4]);
                    return false;
                }
                
                curWorkingPage = util.curWorkingPage;

                if (curWorkingPage == null)
                {
                    System.out.println("No current working page!");
                }
                
                id = args.get(2);
                
                for (Element element : curWorkingPage.elements)
                {
                    if (element.elementId.equals(id))
                    {
                        curWorkingPage.elements.remove(element);
                        util.writeSessionData();
                        System.out.printf("Removed element with id %s.\n", id);
                        return true;
                    }
                }
                
                System.out.printf("Could not find an element with id %s.\n", id);
                break;
                
            default:
                System.out.printf("Mode not recognized: %s\n", mode);
                break;
        }

        return false;
    }

}
