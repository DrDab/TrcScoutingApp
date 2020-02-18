package trc3543.trcscoutingapp.data;

import java.io.Serializable;
import java.util.UUID;

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


    public MatchInfo(Integer matchNumber, Integer teamNumber, String alliance, String matchType, String notes)
    {
        this.uuid = UUID.randomUUID().toString();
        this.matchNumber = matchNumber;
        this.teamNumber = teamNumber;
        this.alliance = alliance;
        this.matchType = matchType;
        this.notes = notes;
    }

    public MatchInfo()
    {
        this(null, null, null, null, null);
    }

    public boolean allFieldsPopulated() throws JSONException
    {
        if (matchNumber == null ||
                teamNumber == null ||
                alliance == null ||
                matchNumber == null)
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
        String toReturn = "";
        toReturn += matchNumber;
        toReturn += ",";
        toReturn += teamNumber;
        toReturn += ",";
        toReturn += matchType;
        toReturn += ",";
        toReturn += alliance;
        toReturn += ",\"";
        toReturn += notes;
        toReturn += "\",";
        return toReturn;
    }

    @Override
    public String toString()
    {
        return getDisplayString();
    }

}
