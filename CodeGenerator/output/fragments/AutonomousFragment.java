package trc3543.trcscoutingapp.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.*;

import com.travijuu.numberpicker.library.*;

import org.json.JSONException;
import org.json.JSONObject;

import trc3543.trcscoutingapp.R;

public class AutonomousFragment extends AbstractPageFragment
{
    private EditText matchNum;
    private EditText teamNum;
    private Spinner matchTypeSpinner;
    private Spinner spectatingTeamSpinner;
    private CheckBox initLineCrossedCB;
    private NumberPicker lowerCellsCounter;
    private NumberPicker outerCellsCounter;
    private NumberPicker innerCellsCounter;
    private NumberPicker missedCellsCounter;

    @Override
    public void instantiateViews(LayoutInflater inflater, ViewGroup container)
    {
        view = inflater.inflate(R.layout.fragment_autonomous_page, container, false);
        matchNum = (EditText) findViewById(R.id.matchNum);
        teamNum = (EditText) findViewById(R.id.teamNum);
        matchTypeSpinner = (Spinner) findViewById(R.id.matchTypeSpinner);
        spectatingTeamSpinner = (Spinner) findViewById(R.id.spectatingTeamSpinner);
        initLineCrossedCB = (CheckBox) findViewById(R.id.initLineCrossedCB);
        lowerCellsCounter = (NumberPicker) findViewById(R.id.lowerCellsCounter);
        outerCellsCounter = (NumberPicker) findViewById(R.id.outerCellsCounter);
        innerCellsCounter = (NumberPicker) findViewById(R.id.innerCellsCounter);
        missedCellsCounter = (NumberPicker) findViewById(R.id.missedCellsCounter);
    }

    @Override
    public void setFields(JSONObject fieldData) throws JSONException
    {
        if (fieldData.has("matchNumber") {
            UIUtils.setEditTextValue(matchNum, fieldData.getInt("matchNumber"));
        }
        if (fieldData.has("teamNumber") {
            UIUtils.setEditTextValue(teamNum, fieldData.getInt("teamNumber"));
        }
        if (fieldData.has("matchtype") {
            UIUtils.setSpinnerByTextValue(matchTypeSpinner, fieldData.getString("matchtype"));
        }
        if (fieldData.has("alliance") {
            UIUtils.setSpinnerByTextValue(spectatingTeamSpinner, fieldData.getString("alliance"));
        }
        if (fieldData.has("initLineCrossed") {
            UIUtils.setCheckbox(initLineCrossedCB, fieldData.getBoolean("initLineCrossed"));
        }
        if (fieldData.has("autonomousLower") {
            UIUtils.setNumberPickerVal(lowerCellsCounter, fieldData.getInt("autonomousLower"));
        }
        if (fieldData.has("autonomousOuter") {
            UIUtils.setNumberPickerVal(outerCellsCounter, fieldData.getInt("autonomousOuter"));
        }
        if (fieldData.has("autonomousInner") {
            UIUtils.setNumberPickerVal(innerCellsCounter, fieldData.getInt("autonomousInner"));
        }
        if (fieldData.has("autonomousMissed") {
            UIUtils.setNumberPickerVal(missedCellsCounter, fieldData.getInt("autonomousMissed"));
        }
    }

    @Override
    public JSONObject getFields()
    {
        try
        {
            JSONObject data = new JSONObject();
            if (!UIUtils.isEditTextEmpty(matchNum)) {
                data.put("matchNumber", Integer.parseInt(UIUtils.getEditTextValue(matchNum)));
            } else {
                UIUtils.launchPopUpMessage(getContext(), "Error", "matchNum cannot be empty!");
                return null;
            }
            if (!UIUtils.isEditTextEmpty(teamNum)) {
                data.put("teamNumber", Integer.parseInt(UIUtils.getEditTextValue(teamNum)));
            } else {
                UIUtils.launchPopUpMessage(getContext(), "Error", "teamNum cannot be empty!");
                return null;
            }
            data.put("matchtype", matchTypeSpinner.getSelectedItem().toString());
            data.put("alliance", spectatingTeamSpinner.getSelectedItem().toString());
            data.put("initLineCrossed", initLineCrossedCB.isChecked());
            data.put("autonomousLower", lowerCellsCounter.getValue());
            data.put("autonomousOuter", outerCellsCounter.getValue());
            data.put("autonomousInner", innerCellsCounter.getValue());
            data.put("autonomousMissed", missedCellsCounter.getValue());
            return data;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
