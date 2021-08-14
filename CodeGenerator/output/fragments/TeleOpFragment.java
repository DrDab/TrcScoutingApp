package trc3543.trcscoutingapp.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.*;

import com.travijuu.numberpicker.library.*;

import org.json.JSONException;
import org.json.JSONObject;

import trc3543.trcscoutingapp.R;

public class TeleOpFragment extends AbstractPageFragment
{
    private NumberPicker lowerCellsCounter;
    private NumberPicker outerCellsCounter;
    private NumberPicker innerCellsCounter;
    private NumberPicker missedCellsCounter;
    private CheckBox shieldGeneratorStage1;
    private CheckBox shieldGeneratorStage2;
    private CheckBox shieldGeneratorStage3;
    private CheckBox ctrlPanelRotationCheckBox;
    private CheckBox ctrlPanelPositionCheckBox;

    @Override
    public void instantiateViews(LayoutInflater inflater, ViewGroup container)
    {
        view = inflater.inflate(R.layout.fragment_teleop_page, container, false);
        lowerCellsCounter = (NumberPicker) findViewById(R.id.lowerCellsCounter);
        outerCellsCounter = (NumberPicker) findViewById(R.id.outerCellsCounter);
        innerCellsCounter = (NumberPicker) findViewById(R.id.innerCellsCounter);
        missedCellsCounter = (NumberPicker) findViewById(R.id.missedCellsCounter);
        shieldGeneratorStage1 = (CheckBox) findViewById(R.id.shieldGeneratorStage1);
        shieldGeneratorStage2 = (CheckBox) findViewById(R.id.shieldGeneratorStage2);
        shieldGeneratorStage3 = (CheckBox) findViewById(R.id.shieldGeneratorStage3);
        ctrlPanelRotationCheckBox = (CheckBox) findViewById(R.id.ctrlPanelRotationCheckBox);
        ctrlPanelPositionCheckBox = (CheckBox) findViewById(R.id.ctrlPanelPositionCheckBox);
    }

    @Override
    public void setFields(JSONObject fieldData) throws JSONException
    {
        if (fieldData.has("teleopLower") {
            UIUtils.setNumberPickerVal(lowerCellsCounter, fieldData.getInt("teleopLower"));
        }
        if (fieldData.has("teleopOuter") {
            UIUtils.setNumberPickerVal(outerCellsCounter, fieldData.getInt("teleopOuter"));
        }
        if (fieldData.has("teleopInner") {
            UIUtils.setNumberPickerVal(innerCellsCounter, fieldData.getInt("teleopInner"));
        }
        if (fieldData.has("teleopMissed") {
            UIUtils.setNumberPickerVal(missedCellsCounter, fieldData.getInt("teleopMissed"));
        }
        if (fieldData.has("shieldStage1") {
            UIUtils.setCheckbox(shieldGeneratorStage1, fieldData.getBoolean("shieldStage1"));
        }
        if (fieldData.has("shieldStage2") {
            UIUtils.setCheckbox(shieldGeneratorStage2, fieldData.getBoolean("shieldStage2"));
        }
        if (fieldData.has("shieldStage3") {
            UIUtils.setCheckbox(shieldGeneratorStage3, fieldData.getBoolean("shieldStage3"));
        }
        if (fieldData.has("controlPanelRotated") {
            UIUtils.setCheckbox(ctrlPanelRotationCheckBox, fieldData.getBoolean("controlPanelRotated"));
        }
        if (fieldData.has("controlPanelPositioned") {
            UIUtils.setCheckbox(ctrlPanelPositionCheckBox, fieldData.getBoolean("controlPanelPositioned"));
        }
    }

    @Override
    public JSONObject getFields()
    {
        try
        {
            JSONObject data = new JSONObject();
            data.put("teleopLower", lowerCellsCounter.getValue());
            data.put("teleopOuter", outerCellsCounter.getValue());
            data.put("teleopInner", innerCellsCounter.getValue());
            data.put("teleopMissed", missedCellsCounter.getValue());
            data.put("shieldStage1", shieldGeneratorStage1.isChecked());
            data.put("shieldStage2", shieldGeneratorStage2.isChecked());
            data.put("shieldStage3", shieldGeneratorStage3.isChecked());
            data.put("controlPanelRotated", ctrlPanelRotationCheckBox.isChecked());
            data.put("controlPanelPositioned", ctrlPanelPositionCheckBox.isChecked());
            return data;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
