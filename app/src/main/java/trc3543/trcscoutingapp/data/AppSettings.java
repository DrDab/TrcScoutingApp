package trc3543.trcscoutingapp.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Iterator;

public class AppSettings implements Serializable
{
    private static final long serialVersionUID = 1L;

    @SerializedName("useAutosave")
    public Boolean useAutosave;

    @SerializedName("autosaveSeconds")
    public Integer autosaveSeconds;

    @SerializedName("selfTeamNumber")
    public Integer selfTeamNumber;

    @SerializedName("firstName")
    public String firstName;

    @SerializedName("lastName")
    public String lastName;

    @SerializedName("serverIP")
    public String serverIP;

    @SerializedName("serverPort")
    public Integer serverPort;

    @SerializedName("serverUsername")
    public String serverUsername;

    @SerializedName("serverPassword")
    public String serverPassword;

    public AppSettings()
    {
        this(false, 3600, 492, "", "", "", 3621, "", "");
    }

    public AppSettings(Boolean useAutosave, Integer autosaveSeconds, Integer selfTeamNumber,
                       String firstName, String lastName, String serverIP, Integer serverPort,
                       String serverUsername, String serverPassword)
    {
        this.useAutosave = useAutosave;
        this.autosaveSeconds = autosaveSeconds;
        this.selfTeamNumber = selfTeamNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.serverUsername = serverUsername;
        this.serverPassword = serverPassword;
    }

    public static AppSettings fromJSONObject(JSONObject jsonObject)
    {
        return new Gson().fromJson(jsonObject.toString(), AppSettings.class);
    }

    public JSONObject toJSONObject() throws JSONException
    {
        return new JSONObject(new Gson().toJson(this));
    }

    public void copyFieldsFromOtherSettings(AppSettings other)
    {
        this.useAutosave = other.useAutosave;
        this.autosaveSeconds = other.autosaveSeconds;
        this.selfTeamNumber = other.selfTeamNumber;
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.serverIP = other.serverIP;
        this.serverPort = other.serverPort;
        this.serverUsername = other.serverUsername;
        this.serverPassword = other.serverPassword;
    }
}
