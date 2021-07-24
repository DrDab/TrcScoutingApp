package trc3543.trcscoutingapp.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.travijuu.numberpicker.library.NumberPicker;

import org.json.JSONException;
import org.json.JSONObject;

import trc3543.trcscoutingapp.R;
import trc3543.trcscoutingapp.uiutil.UIUtils;

public class AutonomousFragment extends AbstractPageFragment
{
    private EditText matchNumET;
    private EditText teamNumET;
    private Spinner matchTypeSpinner;
    private Spinner allianceSpinner;
    private CheckBox initLineCrossedCB;
    private NumberPicker lowerCellsPicker;
    private NumberPicker outerCellsPicker;
    private NumberPicker innerCellsPicker;
    private NumberPicker missedCellsPicker;

    @Override
    public void instantiateViews(LayoutInflater inflater, ViewGroup container)
    {
        view = inflater.inflate(R.layout.fragment_autonomous_page, container, false);
        matchNumET = (EditText) view.findViewById(R.id.matchNum);
        teamNumET = (EditText) view.findViewById(R.id.teamNum);
        matchTypeSpinner = (Spinner) view.findViewById(R.id.matchTypeSpinner);
        allianceSpinner = (Spinner) view.findViewById(R.id.spectatingTeamSpinner);
        initLineCrossedCB = (CheckBox) view.findViewById(R.id.initLineCrossedCB);
        lowerCellsPicker = (NumberPicker) view.findViewById(R.id.lowerCellsCounter);
        outerCellsPicker = (NumberPicker) view.findViewById(R.id.outerCellsCounter);
        innerCellsPicker = (NumberPicker) view.findViewById(R.id.innerCellsCounter);
        missedCellsPicker = (NumberPicker) view.findViewById(R.id.missedCellsCounter);
    }

    @Override
    public void setFields(JSONObject fieldData) throws JSONException
    {
        UIUtils.setEditTextValue(matchNumET, fieldData.getInt("matchNumber"));
        UIUtils.setSpinnerByTextValue(matchTypeSpinner, fieldData.getString("matchType"));
        UIUtils.setSpinnerByTextValue(allianceSpinner, fieldData.getString("alliance"));
        UIUtils.setEditTextValue(teamNumET, fieldData.getInt("teamNumber"));
        UIUtils.setCheckbox(initLineCrossedCB, fieldData.getBoolean("initLineCrossed"));
        UIUtils.setNumberPickerVal(lowerCellsPicker, fieldData.getInt("autonomousLower"));
        UIUtils.setNumberPickerVal(outerCellsPicker, fieldData.getInt("autonomousOuter"));
        UIUtils.setNumberPickerVal(innerCellsPicker, fieldData.getInt("autonomousInner"));
        UIUtils.setNumberPickerVal(missedCellsPicker, fieldData.getInt("autonomousMissed"));
    }

    @Override
    public JSONObject getFields()
    {
        try
        {
            JSONObject data = new JSONObject();
            data.put("matchNumber", Integer.parseInt(UIUtils.getEditTextValue(matchNumET)));
            data.put("teamNumber", Integer.parseInt(UIUtils.getEditTextValue(teamNumET)));
            data.put("matchType", matchTypeSpinner.getSelectedItem().toString());
            data.put("alliance", allianceSpinner.getSelectedItem().toString());
            data.put("initLineCrossed", initLineCrossedCB.isChecked());
            data.put("autonomousLower", lowerCellsPicker.getValue());
            data.put("autonomousOuter", outerCellsPicker.getValue());
            data.put("autonomousInner", innerCellsPicker.getValue());
            data.put("autonomousMissed", missedCellsPicker.getValue());
            return data;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}