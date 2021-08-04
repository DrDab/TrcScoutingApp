package trc492.trcscoutingcodegen;

import trc492.trcscoutingcodegen.data.ElementType;
import trc492.trcscoutingcodegen.data.FieldType;

public class StrUtil
{
    public static FieldType fieldTypeFromStr(String str)
    {
        if (str == null)
            return null;
        
        switch (str.toLowerCase().trim())
        {
            case "int":
                return FieldType.Integer;
                
            case "double":
                return FieldType.Double;
                
            case "bool":
            case "boolean":
                return FieldType.Boolean;
                
            case "str":
            case "string":
                return FieldType.String;
        }
        
        return null;
    }
    
    public static ElementType elementTypeFromStr(String str)
    {
        if (str == null)
            return null;
        
        switch (str.toLowerCase().trim())
        {
            case "edittext":
                return ElementType.EditText;
                
            case "checkbox":
                return ElementType.CheckBox;
                
            case "numberpicker":
                return ElementType.NumberPicker;
                
            case "spinner":
                return ElementType.Spinner;
                
            case "switch":
                return ElementType.Switch;
        }
        
        return null;
    }
}
