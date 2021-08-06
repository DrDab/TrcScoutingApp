package trc492.trcscoutingcodegen.data;

import java.io.Serializable;
import java.util.Calendar;

import com.google.gson.annotations.SerializedName;

public class AppInfoSettings implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @SerializedName("csvHeader")
    public String csvHeader;
    
    @SerializedName("yearNumber")
    public Integer yearNumber;
    
    public AppInfoSettings(String csvHeader, Integer yearNumber)
    {
        this.csvHeader = csvHeader;
        this.yearNumber = yearNumber;
    }

    public AppInfoSettings()
    {
        Calendar rightNow = Calendar.getInstance();
        yearNumber = rightNow.get(Calendar.YEAR);
    }

}
