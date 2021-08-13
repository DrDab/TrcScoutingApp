package trc492.trcscoutingcodegen.classgen;

import trc492.trcscoutingcodegen.data.CodeTemplates;
import trc492.trcscoutingcodegen.data.Field;
import trc492.trcscoutingcodegen.data.FieldFlag;
import trc492.trcscoutingcodegen.data.SessionData;

public class MatchInfoClassGen extends ClassGenerator
{
    private SessionData sessionData;

    public MatchInfoClassGen(SessionData sessionData)
    {
        super("output", "data", "MatchInfo.java");
        this.sessionData = sessionData;
    }

    @Override
    public String generateCode()
    {
        String codeTemp = CodeTemplates.MATCHINFO_CLASS_TEMPLATE;
        String pt1 = "";
        String pt2 = "";
        String pt3 = "CsvOrder csvOrder = new CsvOrder(";

        Field matchNumField = null;
        Field matchTypeField = null;
        Field teamNumField = null;

        for (Field field : sessionData.fields)
        {
            pt1 += String.format("    @SerializedName(\"%s\")\n", field.fieldName);
            pt1 += String.format("    public %s %s;\n\n", field.fieldType, field.fieldName);

            for (FieldFlag flag : field.fieldFlags)
            {
                switch (flag)
                {
                    case MATCH_NUM:
                        matchNumField = field;
                        break;

                    case MATCH_TYPE:
                        matchTypeField = field;
                        break;

                    case TEAM_NUM:
                        teamNumField = field;
                        break;

                    default:
                        break;
                }
            }
        }

        if (matchNumField != null && matchTypeField != null && teamNumField != null)
        {
            pt2 = String.format("String.format(\"Match # %%d (%%s) Team: %%d\", %s, %s, %s);", matchNumField.fieldName,
                matchTypeField.fieldName, teamNumField.fieldName);
        }
        else
        {
            pt2 = "this.toJSONObject().toString();";
        }

        int i = 0;
        for (Integer idx : sessionData.csvBindings.keySet())
        {
            pt3 += sessionData.csvBindings.get(idx).fieldName;
            if (i != sessionData.csvBindings.size() - 1)
            {
                pt3 += ",";
            }

            i++;
        }
        pt3 += ");\n";

        return String.format(codeTemp, pt1, pt2, pt3);
    }

}
