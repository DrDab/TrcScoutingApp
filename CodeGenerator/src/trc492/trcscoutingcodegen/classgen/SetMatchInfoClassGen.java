package trc492.trcscoutingcodegen.classgen;

import trc492.trcscoutingcodegen.data.CodeTemplates;
import trc492.trcscoutingcodegen.data.Field;
import trc492.trcscoutingcodegen.data.FieldFlag;
import trc492.trcscoutingcodegen.data.SessionData;

public class SetMatchInfoClassGen extends ClassGenerator
{
    private SessionData sessionData;

    public SetMatchInfoClassGen(SessionData sessionData)
    {
        super("output", "activities", "SetMatchInfo.java");
        this.sessionData = sessionData;
    }

    @Override
    public String generateCode()
    {
        // TODO Auto-generated method stub
        String autoPopCode = "";
        for (Field field : sessionData.fields)
        {
            for (FieldFlag flag : field.fieldFlags)
            {
                String extraName = null;
                String prePopVal = null;
                boolean isPrePopFlag = false;

                switch (flag)
                {
                    case MATCH_TYPE:
                        extraName = "PrevMatchType";
                        prePopVal = "myIntent.getStringExtra(\"PrevMatchType\")";
                        isPrePopFlag = true;
                        break;

                    case ALLIANCE_TYPE:
                        extraName = "PrevAlliance";
                        prePopVal = "myIntent.getStringExtra(\"PrevAlliance\")";
                        isPrePopFlag = true;
                        break;

                    case MATCH_NUM:
                        extraName = "PrevMatch";
                        prePopVal = "1 + myIntent.getIntExtra(\"PrevMatch\", -1)";
                        isPrePopFlag = true;
                        break;

                    default:
                        break;
                }

                if (isPrePopFlag)
                {
                    String line1 = String.format("                if (myIntent.hasExtra(\"%s\"))\r\n", extraName);
                    String line2 = String.format("                    prePopObj.put(\"%s\", %s);\r\n", field.fieldName,
                        prePopVal);
                    autoPopCode += line1;
                    autoPopCode += line2;
                }
            }
        }
        return String.format(CodeTemplates.SETMATCHINFO_CLASS_TEMPLATE, autoPopCode);
    }

}
