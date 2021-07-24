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
import trc3543.trcscoutingapp.uiutil.UIUtils;

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

    @Override
    public int getPage()
    {
        return mPage;
    }
}