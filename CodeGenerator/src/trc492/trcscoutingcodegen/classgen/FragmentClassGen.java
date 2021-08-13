package trc492.trcscoutingcodegen.classgen;

import java.util.List;

import trc492.trcscoutingcodegen.data.Element;
import trc492.trcscoutingcodegen.data.Field;
import trc492.trcscoutingcodegen.data.FieldFlag;
import trc492.trcscoutingcodegen.data.Page;

public class FragmentClassGen
{
    private Page page;

    public FragmentClassGen(Page page)
    {
        this.page = page;
    }

    public String generateCode()
    {
        String codeTemp = CodeTemplates.FRAGMENT_CLASS_TEMPLATE;
        String className = page.className;
        String views = "";
        String fragmentName = page.fragmentName;
        String instantiateViewsCode = "";
        String setFieldsCode = "";
        String getFieldsCode = "";

        for (Element element : page.elements)
        {
            Field field = element.field;
            views += String.format("\t%s %s;\n", element.elementType, element.elementId);
            instantiateViewsCode += String.format("\t\t%s = (%s) findViewById(R.id.%s);\n", element.elementId,
                element.elementType, element.elementId);
            switch (element.elementType)
            {
                case EditText:
                    String jsonFieldData = "";
                    String parseStr = "";

                    switch (field.fieldType)
                    {
                        case Integer:
                            jsonFieldData = String.format("getInt(\"%s\")", field.fieldName);
                            parseStr = String.format("Integer.parseInt(UIUtils.getEditTextValue(%s))",
                                element.elementId);
                            break;

                        case Double:
                            jsonFieldData = String.format("getDouble(\"%s\")", field.fieldName);
                            parseStr = String.format("Double.parseDouble(UIUtils.getEditTextValue(%s))",
                                element.elementId);
                            break;

                        case String:
                            jsonFieldData = String.format("getString(\"%s\")", field.fieldName);
                            parseStr = String.format("UIUtils.getEditTextValue(%s)", element.elementId);
                            break;

                        default:
                            break;
                    }

                    setFieldsCode += String.format("\t\tUIUtils.setEditTextValue(%s, fieldData.%s);\n",
                        element.elementId, jsonFieldData);

                    List<FieldFlag> fieldFlags = field.fieldFlags;
                    if (fieldFlags.contains(FieldFlag.MUST_BE_FILLED) || fieldFlags.contains(FieldFlag.MATCH_NUM)
                        || fieldFlags.contains(FieldFlag.TEAM_NUM))
                    {
                        // must be filled
                        getFieldsCode += String.format("\t\tif (!UIUtils.isEditTextEmpty(%s)) {\n", element.elementId);
                        getFieldsCode += String.format("\t\t\tdata.put(\"%s\", %s);\n", field.fieldName, parseStr);
                        getFieldsCode += "\t\t}\n";
                    }
                    else
                    {
                        getFieldsCode += String.format("\t\tdata.put(\"%s\", %s);\n", field.fieldName, parseStr);
                    }
                    break;

                case CheckBox:
                    setFieldsCode += String.format("\t\tUIUtils.setCheckbox(%s, fieldData.getBoolean(\"%s\"));\n",
                        element.elementId, field.fieldName);
                    getFieldsCode += String.format("\t\tdata.put(\"%s\", %s.isChecked());\n", field.fieldName,
                        element.elementId);
                    break;

                case NumberPicker:
                    setFieldsCode += String.format("\t\tUIUtils.setNumberPickerVal(%s, fieldData.getInt(\"%s\"));\n",
                        element.elementId, field.fieldName);
                    getFieldsCode += String.format("\t\tdata.put(\"%s\", %s.getValue());\n", field.fieldName,
                        element.elementId);
                    break;

                case Spinner:
                    setFieldsCode += String.format(
                        "\t\tUIUtils.setSpinnerByTextValue(%s, fieldData.getString(\"%s\"));\n", element.elementId,
                        field.fieldName);
                    getFieldsCode += String.format("\t\tdata.put(\"%s\", %s.getSelectedItem().toString());\n",
                        field.fieldName, element.elementId);
                    break;

                case Switch:
                    setFieldsCode += String.format("\t\t%s.setChecked(fieldData.getBoolean(\"%s\"));\n\n",
                        element.elementId, field.fieldName);
                    getFieldsCode += String.format("\t\tdata.put(\"%s\", %s.isChecked());\n", field.fieldName,
                        element.elementId);
                    break;

                default:
                    break;

            }
        }

        return String.format(codeTemp, className, views, fragmentName, instantiateViewsCode, setFieldsCode,
            getFieldsCode);
    }

}
