package trc492.trcscoutingcodegen.classgen;

import trc492.trcscoutingcodegen.data.Field;
import trc492.trcscoutingcodegen.data.FieldFlag;
import trc492.trcscoutingcodegen.data.SessionData;

public class AddMatchesClassGen
{
    private SessionData sessionData;

    public AddMatchesClassGen(SessionData sessionData)
    {
        this.sessionData = sessionData;
    }

    public String generateCode()
    {
        String codeTemp = CodeTemplates.ADDMATCHES_CLASS_TEMPLATE;

        String pt1 = "";

        for (Field field : sessionData.fields)
        {
            if (field.fieldFlags == null)
                continue;
            for (FieldFlag flag : field.fieldFlags)
            {
                String intentName = null;
                
                switch (flag)
                {
                    case MATCH_NUM:
                        intentName = "PrevMatch";
                        break;

                    case ALLIANCE_TYPE:
                        intentName = "PrevAlliance";
                        break;

                    case MATCH_TYPE:
                        intentName = "PrevMatchType";
                        break;

                    default:
                        continue;
                }
                
                pt1 += String.format("intent.putExtra(\"%s\", prev.%s);\n\t\t", intentName, field.fieldName);
            }
        }

        String codeFormatted = String.format(codeTemp, pt1);
        return codeFormatted;
    }

}
