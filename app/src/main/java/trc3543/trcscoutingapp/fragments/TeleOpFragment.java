package trc3543.trcscoutingapp.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.travijuu.numberpicker.library.NumberPicker;

import trc3543.trcscoutingapp.R;
import trc3543.trcscoutingapp.uiutil.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class TeleOpFragment extends AbstractPageFragment
{
    private NumberPicker lowerCellsPicker;
    private NumberPicker outerCellsPicker;
    private NumberPicker innerCellsPicker;
    private NumberPicker missedCellsPicker;
    private CheckBox stage1CB;
    private CheckBox stage2CB;
    private CheckBox stage3CB;
    private CheckBox rotationCB;
    private CheckBox positionCB;

    @Override
    public void instantiateViews(LayoutInflater inflater, ViewGroup container)
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

    @Override
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
}