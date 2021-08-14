package trc3543.trcscoutingapp.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.*;

import com.travijuu.numberpicker.library.*;

import org.json.JSONException;
import org.json.JSONObject;

import trc3543.trcscoutingapp.R;

public class EndgameFragment extends AbstractPageFragment
{
    private CheckBox endgameparkedCB;
    private CheckBox endgamehangingCB;
    private CheckBox endgameSupportingMechanismCB;
    private CheckBox endgameLevelCB;
    private EditText gameNotes;

    @Override
    public void instantiateViews(LayoutInflater inflater, ViewGroup container)
    {
        view = inflater.inflate(R.layout.fragment_endgame_page, container, false);
        endgameparkedCB = (CheckBox) findViewById(R.id.endgameparkedCB);
        endgamehangingCB = (CheckBox) findViewById(R.id.endgamehangingCB);
        endgameSupportingMechanismCB = (CheckBox) findViewById(R.id.endgameSupportingMechanismCB);
        endgameLevelCB = (CheckBox) findViewById(R.id.endgameLevelCB);
        gameNotes = (EditText) findViewById(R.id.gameNotes);
    }

    @Override
    public void setFields(JSONObject fieldData) throws JSONException
    {
        if (fieldData.has("generatorSwitchParked") {
            UIUtils.setCheckbox(endgameparkedCB, fieldData.getBoolean("generatorSwitchParked"));
        }
        if (fieldData.has("generatorSwitchHanging") {
            UIUtils.setCheckbox(endgamehangingCB, fieldData.getBoolean("generatorSwitchHanging"));
        }
        if (fieldData.has("generatorSwitchSupportingMechanism") {
            UIUtils.setCheckbox(endgameSupportingMechanismCB, fieldData.getBoolean("generatorSwitchSupportingMechanism"));
        }
        if (fieldData.has("generatorSwitchLevel") {
            UIUtils.setCheckbox(endgameLevelCB, fieldData.getBoolean("generatorSwitchLevel"));
        }
        if (fieldData.has("notes") {
            UIUtils.setEditTextValue(gameNotes, fieldData.getString("notes"));
        }
    }

    @Override
    public JSONObject getFields()
    {
        try
        {
            JSONObject data = new JSONObject();
            data.put("generatorSwitchParked", endgameparkedCB.isChecked());
            data.put("generatorSwitchHanging", endgamehangingCB.isChecked());
            data.put("generatorSwitchSupportingMechanism", endgameSupportingMechanismCB.isChecked());
            data.put("generatorSwitchLevel", endgameLevelCB.isChecked());
            data.put("notes", UIUtils.getEditTextValue(gameNotes));
            return data;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
