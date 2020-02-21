package trc3543.trcscoutingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.travijuu.numberpicker.library.NumberPicker;

import androidx.fragment.app.Fragment;
import trc3543.trcscoutingapp.R;
import trc3543.trcscoutingapp.fragmentcommunication.CollectorClient;

import org.json.JSONException;
import org.json.JSONObject;

public class EndgameFragment extends Fragment
{
    public static final String ARG_PAGE = "ARG_PAGE";

    private View view;
    private Thread sendThread;
    private int mPage;

    private CheckBox parkedCB;
    private CheckBox hangingCB;
    private CheckBox supportingCB;
    private CheckBox levelCB;
    private EditText notesET;

    public static EndgameFragment newInstance(int page)
    {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        EndgameFragment fragment = new EndgameFragment();
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
            view = inflater.inflate(R.layout.fragment_endgame_page, container, false);
            parkedCB = (CheckBox) view.findViewById(R.id.endgameParkedCB);
            hangingCB = (CheckBox) view.findViewById(R.id.endgameHangingCB);
            supportingCB = (CheckBox) view.findViewById(R.id.endgameSupportingMechanismCB);
            levelCB = (CheckBox) view.findViewById(R.id.endgameLevelCB);
            notesET = (EditText) view.findViewById(R.id.gameNotes);
        }

        if (sendThread == null)
        {
            CollectorClient collectorClient = new CollectorClient(mPage)
            {
                @Override
                public JSONObject onRequestFields() throws JSONException
                {
                    JSONObject data = new JSONObject();
                    data.put("generatorSwitchParked", parkedCB.isChecked());
                    data.put("generatorSwitchHanging", hangingCB.isChecked());
                    data.put("generatorSwitchSupportingMechanism", supportingCB.isChecked());
                    data.put("generatorSwitchLevel", levelCB.isChecked());
                    data.put("notes", getEditTextValue(notesET));
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
                                setCheckbox(parkedCB, fieldData.getBoolean("generatorSwitchParked"));
                                setCheckbox(hangingCB, fieldData.getBoolean("generatorSwitchHanging"));
                                setCheckbox(supportingCB, fieldData.getBoolean("generatorSwitchSupportingMechanism"));
                                setCheckbox(levelCB, fieldData.getBoolean("generatorSwitchLevel"));
                                setEditTextValue(notesET, fieldData.getString("notes"));
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
}