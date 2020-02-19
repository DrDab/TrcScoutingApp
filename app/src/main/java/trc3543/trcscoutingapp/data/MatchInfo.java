package trc3543.trcscoutingapp.data;

import java.io.Serializable;

import org.json.JSONException;

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

    @SerializedName("matchType")
    public String matchType;

    @SerializedName("notes")
    public String notes;

    // autonomous variables.
    @SerializedName("initLineCrossed")
    public Boolean initLineCrossed;

    @SerializedName("autonomousLower")
    public Integer autonomousLower;

    @SerializedName("autonomousOuter")
    public Integer autonomousOuter;

    @SerializedName("autonomousInner")
    public Integer autonomousInner;

    @SerializedName("autonomousMissed")
    public Integer autonomousMissed;

    // teleop variables.
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

    // endgame variables.
    @SerializedName("generatorSwitchParked")
    public Boolean generatorSwitchParked;

    @SerializedName("generatorSwitchHanging")
    public Boolean generatorSwitchHanging;

    @SerializedName("generatorSwitchSupportingMechanism")
    public Boolean generatorSwitchSupportingMechanism;

    @SerializedName("generatorSwitchLevel")
    public Boolean generatorSwitchLevel;

    public boolean allFieldsPopulated() throws JSONException
    {
        if (matchNumber == null ||
                teamNumber == null ||
                alliance == null ||
                matchType == null)
        {
            return false;
        }
        return true;
    }

    public String getDisplayString()
    {
        return String.format("Match # %d (%s) Team: %d", matchNumber, matchType, teamNumber);
    }

    public String getCsvString()
    {
        CsvOrder csvOrder = new CsvOrder(matchNumber,
                teamNumber,
                matchType,
                alliance,
                initLineCrossed,
                autonomousLower,
                autonomousOuter,
                autonomousInner,
                autonomousMissed,
                teleopLower,
                teleopOuter,
                teleopInner,
                teleopMissed,
                shieldStage1,
                shieldStage2,
                shieldStage3,
                controlPanelRotated,
                controlPanelPositioned,
                generatorSwitchParked,
                generatorSwitchHanging,
                generatorSwitchSupportingMechanism,
                generatorSwitchLevel,
                notes);
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
