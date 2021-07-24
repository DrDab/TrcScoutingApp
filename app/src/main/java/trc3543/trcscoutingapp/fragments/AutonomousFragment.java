package trc3543.trcscoutingapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.travijuu.numberpicker.library.NumberPicker;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import trc3543.trcscoutingapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AutonomousFragment extends Fragment implements ActivityCommunicableFragment
{
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_INIT_JSON_FIELDS = "ARG_INIT_JSON_FIELDS";

    private View view;
    private FragmentsDataViewModel viewModel;
    private int mPage;

    private EditText matchNumET;
    private EditText teamNumET;
    private Spinner matchTypeSpinner;
    private Spinner allianceSpinner;
    private CheckBox initLineCrossedCB;
    private NumberPicker lowerCellsPicker;
    private NumberPicker outerCellsPicker;
    private NumberPicker innerCellsPicker;
    private NumberPicker missedCellsPicker;

    public static AutonomousFragment newInstance(int page)
    {
        return newInstance(page, null);
    }

    public static AutonomousFragment newInstance(int page, String initJsonFields)
    {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_INIT_JSON_FIELDS, initJsonFields);
        AutonomousFragment fragment = new AutonomousFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (view == null)
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


        try
        {
            String jsonFieldsStr = getArguments().getString(ARG_INIT_JSON_FIELDS);
            JSONObject initFieldData = jsonFieldsStr == null ? null : new JSONObject(jsonFieldsStr);
            if (initFieldData != null)
            {
                setFields(initFieldData);
            }
        }
        catch (JSONException jsonException)
        {
            jsonException.printStackTrace();
        }

        mPage = getArguments().getInt(ARG_PAGE);
        viewModel = new ViewModelProvider(requireActivity()).get(FragmentsDataViewModel.class);
        viewModel.register(this);

        return view;
    }

    private void setSpinnerByTextValue(Spinner spinner, String toSelect)
    {
        if (toSelect == null)
        {
            return;
        }
        for (int i = 0; i < spinner.getCount(); i++)
        {
            if (spinner.getItemAtPosition(i).equals(toSelect))
            {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void setEditTextValue(EditText editText, Object toSet)
    {
        if (toSet == null)
        {
            return;
        }
        editText.setText(toSet.toString() + "");
    }

    private void setCheckbox(CheckBox checkbox, Boolean toSet)
    {
        if (toSet == null)
        {
            return;
        }
        checkbox.setChecked(toSet);
    }

    private void setNumberPickerVal(NumberPicker numberPicker, Integer toSet)
    {
        if (toSet == null)
        {
            return;
        }
        numberPicker.setValue(toSet);
    }

    private String getEditTextValue(EditText editText)
    {
        return editText.getText().toString();
    }

    public void setFields(JSONObject fieldData) throws JSONException
    {
        setEditTextValue(matchNumET, fieldData.getInt("matchNumber"));
        setSpinnerByTextValue(matchTypeSpinner, fieldData.getString("matchType"));
        setSpinnerByTextValue(allianceSpinner, fieldData.getString("alliance"));
        setEditTextValue(teamNumET, fieldData.getInt("teamNumber"));
        setCheckbox(initLineCrossedCB, fieldData.getBoolean("initLineCrossed"));
        setNumberPickerVal(lowerCellsPicker, fieldData.getInt("autonomousLower"));
        setNumberPickerVal(outerCellsPicker, fieldData.getInt("autonomousOuter"));
        setNumberPickerVal(innerCellsPicker, fieldData.getInt("autonomousInner"));
        setNumberPickerVal(missedCellsPicker, fieldData.getInt("autonomousMissed"));
    }

    @Override
    public JSONObject getFields()
    {
        try
        {
            JSONObject data = new JSONObject();
            data.put("matchNumber", Integer.parseInt(getEditTextValue(matchNumET)));
            data.put("teamNumber", Integer.parseInt(getEditTextValue(teamNumET)));
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

    @Override
    public int getPage()
    {
        return mPage;
    }
}