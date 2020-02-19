package trc3543.trcscoutingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.travijuu.numberpicker.library.NumberPicker;

import androidx.fragment.app.Fragment;
import trc3543.trcscoutingapp.R;
import trc3543.trcscoutingapp.fragmentcommunication.CollectorClient;

import org.json.JSONException;
import org.json.JSONObject;

public class TeleOpFragment extends Fragment
{
    public static final String ARG_PAGE = "ARG_PAGE";

    private View view;
    private Thread sendThread;
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
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TeleOpFragment fragment = new TeleOpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
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

        if (sendThread == null)
        {
            CollectorClient collectorClient = new CollectorClient(mPage)
            {
                @Override
                public JSONObject onRequestFields() throws JSONException
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

                @Override
                public void onSettingFields(final JSONObject fieldData) throws JSONException
                {
                    // your code here!
                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                // ui actions here
                                setNumberPickerVal(lowerCellsPicker, fieldData.getInt("teleopLower"));
                                setNumberPickerVal(outerCellsPicker, fieldData.getInt("teleopOuter"));
                                setNumberPickerVal(innerCellsPicker, fieldData.getInt("teleopInner"));
                                setNumberPickerVal(missedCellsPicker, fieldData.getInt("teleopMissed"));
                                setCheckbox(stage1CB, fieldData.getBoolean("shieldStage1"));
                                setCheckbox(stage2CB, fieldData.getBoolean("shieldStage2"));
                                setCheckbox(stage3CB, fieldData.getBoolean("shieldStage3"));
                                setCheckbox(rotationCB, fieldData.getBoolean("controlPanelRotated"));
                                setCheckbox(positionCB, fieldData.getBoolean("controlPanelPositioned"));
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            };
            sendThread = new Thread(collectorClient);
            sendThread.start();
        }
        return view;
    }

    private void setCheckbox(CheckBox checkbox, Boolean toSet)
    {
        checkbox.setChecked(toSet);
    }

    private void setNumberPickerVal(NumberPicker numberPicker, Integer toSet)
    {
        numberPicker.setValue(toSet);
    }
}