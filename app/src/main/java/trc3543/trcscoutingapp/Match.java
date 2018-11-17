package trc3543.trcscoutingapp;

public class Match
{
    private String dispString;
    private String csvString;

    public Match(String dispString, String csvString)
    {
        this.dispString = dispString;
        this.csvString = csvString;
    }

    public String getDispString()
    {
        return dispString;
    }

    public String getCsvString()
    {
        return csvString;
    }

    public void setDispString(String dispString)
    {
        this.dispString = dispString;
    }

    public void setCsvString(String csvString)
    {
        this.csvString = csvString;
    }

    public String toString()
    {
        return dispString;
    }
}
