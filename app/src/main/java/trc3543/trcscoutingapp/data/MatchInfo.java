package trc3543.trcscoutingapp.data;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class MatchInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    @SerializedName("teamNumber")
    public Integer teamNumber;

    @SerializedName("driveTrain")
    public String driveTrain;

    @SerializedName("programmingLanguage")
    public String programmingLanguage;

    @SerializedName("shootingAccuracy")
    public String shootingAccuracy;

    @SerializedName("powerCellsChamberCapacity")
    public Integer powerCellsChamberCapacity;

    @SerializedName("cycleTime")
    public Integer cycleTime;

    @SerializedName("cyclesPerMatch")
    public Integer cyclesPerMatch;

    // autonomous
    @SerializedName("startNearAudience")
    public Boolean startNearAudience;

    @SerializedName("startMidPos")
    public Boolean startMidPos;

    @SerializedName("startAwayFromAudience")
    public Boolean startAwayFromAudience;

    @SerializedName("canCrossInitLine")
    public Boolean canCrossInitLine;

    @SerializedName("autoShootingLow")
    public Boolean autoShootingLow;

    @SerializedName("autoShootingHigh")
    public Boolean autoShootingHigh;

    @SerializedName("autoShootingInner")
    public Boolean autoShootingInner;

    @SerializedName("autoShootingNumber")
    public Integer autoShootingNumber;

    @SerializedName("controlPanelRotation")
    public Boolean controlPanelRotation;

    @SerializedName("controlPanelPosition")
    public Boolean controlPanelPosition;

    // teleop
    @SerializedName("shootingFromNearField")
    public Boolean shootingFromNearField;

    @SerializedName("shootingFromMidField")
    public Boolean shootingFromMidField;

    @SerializedName("shootingFromFarField")
    public Boolean shootingFromFarField;

    @SerializedName("teleopShootingLow")
    public Boolean teleopShootingLow;

    @SerializedName("teleopShootingHigh")
    public Boolean teleopShootingHigh;

    @SerializedName("teleopShootingInner")
    public Boolean teleopShootingInner;

    @SerializedName("pickupGround")
    public Boolean pickupGround;

    @SerializedName("pickupFeederStation")
    public Boolean pickupFeederStation;

    @SerializedName("pickupType")
    public String pickupType;

    @SerializedName("strategyType")
    public String strategyType;

    @SerializedName("driveUnderTrench")
    public Boolean driveUnderTrench;

    @SerializedName("climbing")
    public Boolean climbing;

    @SerializedName("balancing")
    public Boolean balancing;

    @SerializedName("notes")
    public String notes;

    public String getDisplayString()
    {
        return String.format("Team %d", teamNumber);
    }

    public String getCsvString()
    {
        CsvOrder csvOrder = new CsvOrder(teamNumber,
                driveTrain,
                programmingLanguage,
                shootingAccuracy,
                powerCellsChamberCapacity,
                cycleTime,
                cyclesPerMatch,
                startNearAudience,
                startMidPos,
                startAwayFromAudience,
                canCrossInitLine,
                autoShootingLow,
                autoShootingHigh,
                autoShootingInner,
                autoShootingNumber,
                controlPanelRotation,
                controlPanelPosition,
                shootingFromNearField,
                shootingFromMidField,
                shootingFromFarField,
                teleopShootingLow,
                teleopShootingHigh,
                teleopShootingInner,
                pickupGround,
                pickupFeederStation,
                pickupType,
                strategyType,
                driveUnderTrench,
                climbing,
                balancing,
                notes);
        return csvOrder.csvString;
    }

    @Override
    public String toString()
    {
        return getDisplayString();
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

    public boolean allFieldsPopulated()
    {
        return !checkAnyNull(teamNumber, powerCellsChamberCapacity, cycleTime, cyclesPerMatch);
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
