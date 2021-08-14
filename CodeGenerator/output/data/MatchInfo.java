package trc3543.trcscoutingapp.data;

import java.io.Serializable;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class MatchInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    // misc. variables.
    @SerializedName("uuid")
    public String uuid;

    @SerializedName("matchNumber")
    public Integer matchNumber;

    @SerializedName("teamNumber")
    public Integer teamNumber;

    @SerializedName("alliance")
    public String alliance;

    @SerializedName("matchtype")
    public String matchtype;

    @SerializedName("notes")
    public String notes;

    @SerializedName("initLineCrossed")
    public Boolean initLineCrossed;

    @SerializedName("autonomousLower")
    public Integer autonomousLower;

    @SerializedName("autonomousMissed")
    public Integer autonomousMissed;

    @SerializedName("teleopLower")
    public Integer teleopLower;

    @SerializedName("teleopOuter")
    public Integer teleopOuter;

    @SerializedName("teleopInner")
    public Integer teleopInner;

    @SerializedName("teleopMissed")
    public Integer teleopMissed;

    @SerializedName("shieldStage1")
    public Boolean shieldStage1;

    @SerializedName("shieldStage2")
    public Boolean shieldStage2;

    @SerializedName("shieldStage3")
    public Boolean shieldStage3;

    @SerializedName("controlPanelRotated")
    public Boolean controlPanelRotated;

    @SerializedName("controlPanelPositioned")
    public Boolean controlPanelPositioned;

    @SerializedName("generatorSwitchParked")
    public Boolean generatorSwitchParked;

    @SerializedName("generatorSwitchHanging")
    public Boolean generatorSwitchHanging;

    @SerializedName("generatorSwitchSupportingMechanism")
    public Boolean generatorSwitchSupportingMechanism;

    @SerializedName("generatorSwitchLevel")
    public Boolean generatorSwitchLevel;

    @SerializedName("autonomousOuter")
    public Integer autonomousOuter;

    @SerializedName("autonomousInner")
    public Integer autonomousInner;



    public JSONObject toJSONObject() throws JSONException
    {
        return new JSONObject(new Gson().toJson(this));
    }

    public static MatchInfo fromMultipleJSONObjects(JSONObject... fragmentJSONObjects)
            throws JSONException
    {
        JSONObject head = new JSONObject();

        for (JSONObject fragmentJSONObject : fragmentJSONObjects)
        {
            if (fragmentJSONObject == null)
                continue;
            Iterator<String> keyIterator = fragmentJSONObject.keys();
            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                Object value = fragmentJSONObject.get(key);
                head.put(key, value);
            }
        }

        Gson gson = new Gson();
        MatchInfo toReturn = gson.fromJson(head.toString(), MatchInfo.class);
        return toReturn;
    }

    private boolean checkAnyNull(Object... args)
    {
        for (Object cur : args)
        {
            if (cur == null)
            {
                return true;
            }
        }
        return false;
    }

    public boolean allNeededFieldsPopulated()
    {
        return !checkAnyNull(matchNumber, teamNumber);
    }

    public String getDisplayString()
    {
        return String.format("Match # %d (%s) Team: %d", matchNumber, matchtype, teamNumber);
    }

    public String getCsvString()
    {
        CsvOrder csvOrder = new CsvOrder(matchNumber,teamNumber,matchtype,alliance,initLineCrossed,autonomousLower,autonomousOuter,autonomousInner,autonomousMissed,teleopLower,teleopOuter,teleopInner,teleopMissed,shieldStage1,shieldStage2,shieldStage3,controlPanelRotated,controlPanelPositioned,generatorSwitchParked,generatorSwitchHanging,generatorSwitchSupportingMechanism,generatorSwitchLevel,notes);
        return csvOrder.csvString;
    }

    @Override
    public String toString()
    {
        return getDisplayString();
    }

    private class CsvOrder
    {
        public String csvString;
        public CsvOrder(Object... params)
        {
            this.csvString = "";
            for (Object s : params)
            {
                if (s != null)
                {
                    boolean isStr = s instanceof String;
                    if (isStr)
                    {
                        this.csvString += "\"";
                    }
                    this.csvString += s.toString();
                    if (isStr)
                    {
                        this.csvString += "\"";
                    }
                }
                this.csvString += ",";
            }
        }
    }
}

