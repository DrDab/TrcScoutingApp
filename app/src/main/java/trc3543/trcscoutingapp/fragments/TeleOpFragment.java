package trc3543.trcscoutingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.travijuu.numberpicker.library.NumberPicker;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import trc3543.trcscoutingapp.R;
import trc3543.trcscoutingapp.uiutil.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class TeleOpFragment extends Fragment implements ActivityCommunicableFragment
{
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_INIT_JSON_FIELDS = "ARG_INIT_JSON_FIELDS";

    private View view;
    private FragmentsDataViewModel viewModel;
    private int mPage;

    private NumberPicker lowerCellsPicker;
    private NumberPicker outerCellsPicker;
    private NumberPicker innerCellsPicker;
    private NumberPicker missedCellsPicker;
    private CheckBox stage1CB;
    private CheckBox stage2CB;
    private CheckBox stage3CB;
    private CheckBox rotationCB;
    private CheckBox positionCB;

    public static TeleOpFragment newInstance(int page)
    {
        return newInstance(page, null);
    }

    public static TeleOpFragment newInstance(int page, String initJsonFields)
    {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_INIT_JSON_FIELDS, initJsonFields);
        TeleOpFragment fragment = new TeleOpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (view == null)
        {
            view = inflater.inflate(R.layout.fragment_teleop_page, container, false);
            lowerCellsPicker = (NumberPicker) view.findViewById(R.id.lowerCellsCounter);
            outerCellsPicker = (NumberPicker) view.findViewById(R.id.outerCellsCounter);
            innerCellsPicker = (NumberPicker) view.findViewById(R.id.innerCellsCounter);
            missedCellsPicker = (NumberPicker) view.findViewById(R.id.missedCellsCounter);
            stage1CB = (CheckBox) view.findViewById(R.id.shieldGeneratorStage1);
            stage2CB = (CheckBox) view.findViewById(R.id.shieldGeneratorStage2);
            stage3CB = (CheckBox) view.findViewById(R.id.shieldGeneratorStage3);
            rotationCB = (CheckBox) view.findViewById(R.id.ctrlPanelRotationCheckBox);
            positionCB = (CheckBox) view.findViewById(R.id.ctrlPanelPositionCheckBox);
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
        UIUtils.setNumberPickerVal(lowerCellsPicker, fieldData.getInt("teleopLower"));
        UIUtils.setNumberPickerVal(outerCellsPicker, fieldData.getInt("teleopOuter"));
        UIUtils.setNumberPickerVal(innerCellsPicker, fieldData.getInt("teleopInner"));
        UIUtils.setNumberPickerVal(missedCellsPicker, fieldData.getInt("teleopMissed"));
        UIUtils.setCheckbox(stage1CB, fieldData.getBoolean("shieldStage1"));
        UIUtils.setCheckbox(stage2CB, fieldData.getBoolean("shieldStage2"));
        UIUtils.setCheckbox(stage3CB, fieldData.getBoolean("shieldStage3"));
        UIUtils.setCheckbox(rotationCB, fieldData.getBoolean("controlPanelRotated"));
        UIUtils.setCheckbox(positionCB, fieldData.getBoolean("controlPanelPositioned"));
    }

    @Override
    public JSONObject getFields()
    {
        try
        {
            JSONObject data = new JSONObject();
            data.put("teleopLower", lowerCellsPicker.getValue());
            data.put("teleopOuter", outerCellsPicker.getValue());
            data.put("teleopInner", innerCellsPicker.getValue());
            data.put("teleopMissed", missedCellsPicker.getValue());
            data.put("shieldStage1", stage1CB.isChecked());
            data.put("shieldStage2", stage2CB.isChecked());
            data.put("shieldStage3", stage3CB.isChecked());
            data.put("controlPanelRotated", rotationCB.isChecked());
            data.put("controlPanelPositioned", positionCB.isChecked());
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