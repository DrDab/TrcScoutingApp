package trc3543.trcscoutingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.travijuu.numberpicker.library.NumberPicker;

import androidx.fragment.app.Fragment;
import trc3543.trcscoutingapp.R;
import trc3543.trcscoutingapp.fragmentcommunication.CollectorClient;

import org.json.JSONException;
import org.json.JSONObject;

public class AutonomousFragment extends Fragment
{
    public static final String ARG_PAGE = "ARG_PAGE";

    private View view;
    private Thread sendThread;
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
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        AutonomousFragment fragment = new AutonomousFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
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

        if (sendThread == null)
        {
            CollectorClient collectorClient = new CollectorClient(mPage)
            {
                @Override
                public JSONObject onRequestFields()
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
                        return new JSONObject();
                    }
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
                                // hacky fix cause i'm lazy: load the fields that get auto-populated before the ones that don't,
                                // so the exception will be thrown after everything needed loads, so that everything needed loads.
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
}