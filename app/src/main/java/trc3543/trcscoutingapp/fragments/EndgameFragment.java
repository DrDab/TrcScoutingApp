package trc3543.trcscoutingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import trc3543.trcscoutingapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class EndgameFragment extends Fragment implements ActivityCommunicableFragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_INIT_JSON_FIELDS = "ARG_INIT_JSON_FIELDS";

    private View view;
    private FragmentsDataViewModel viewModel;
    private int mPage;

    private CheckBox parkedCB;
    private CheckBox hangingCB;
    private CheckBox supportingCB;
    private CheckBox levelCB;
    private EditText notesET;
    private Button confirmButton;

    public static EndgameFragment newInstance(int page)
    {
        return newInstance(page, null);
    }

    public static EndgameFragment newInstance(int page, String initJsonFields)
    {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_INIT_JSON_FIELDS, initJsonFields);
        EndgameFragment fragment = new EndgameFragment();
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
            view = inflater.inflate(R.layout.fragment_endgame_page, container, false);
            parkedCB = (CheckBox) view.findViewById(R.id.endgameParkedCB);
            hangingCB = (CheckBox) view.findViewById(R.id.endgameHangingCB);
            supportingCB = (CheckBox) view.findViewById(R.id.endgameSupportingMechanismCB);
            levelCB = (CheckBox) view.findViewById(R.id.endgameLevelCB);
            notesET = (EditText) view.findViewById(R.id.gameNotes);
            confirmButton = (Button) view.findViewById(R.id.btnConfirm);
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

    private String getEditTextValue(EditText editText)
    {
        return editText.getText().toString();
    }

    public void setFields(JSONObject fieldData) throws JSONException
    {
        setCheckbox(parkedCB, fieldData.getBoolean("generatorSwitchParked"));
        setCheckbox(hangingCB, fieldData.getBoolean("generatorSwitchHanging"));
        setCheckbox(supportingCB, fieldData.getBoolean("generatorSwitchSupportingMechanism"));
        setCheckbox(levelCB, fieldData.getBoolean("generatorSwitchLevel"));
        setEditTextValue(notesET, fieldData.getString("notes"));
    }

    @Override
    public JSONObject getFields()
    {
        try
        {
            JSONObject data = new JSONObject();
            data.put("generatorSwitchParked", parkedCB.isChecked());
            data.put("generatorSwitchHanging", hangingCB.isChecked());
            data.put("generatorSwitchSupportingMechanism", supportingCB.isChecked());
            data.put("generatorSwitchLevel", levelCB.isChecked());
            data.put("notes", getEditTextValue(notesET));
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