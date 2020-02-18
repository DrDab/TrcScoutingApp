package trc3543.trcscoutingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import trc3543.trcscoutingapp.R;
import trc3543.trcscoutingapp.fragmentcommunication.CollectorClient;

import org.json.JSONException;
import org.json.JSONObject;

// In this case, the fragment displays simple text based on the page
public class SamplePageFragment extends Fragment
{
    public static final String ARG_PAGE = "ARG_PAGE";

    private View view;

    private int mPage;
    private TextView textView;
    private Button button;

    private Thread sendThread;

    public static SamplePageFragment newInstance(int page)
    {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        SamplePageFragment fragment = new SamplePageFragment();
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
            view = inflater.inflate(R.layout.fragment_sample_page, container, false);
        }
        textView = (TextView) view.findViewById(R.id.tvTitle);
        button = (Button) view.findViewById(R.id.button);
        textView.setText("Fragment #" + mPage);


        if (sendThread == null)
        {
            CollectorClient collectorClient = new CollectorClient(mPage)
            {
                @Override
                public JSONObject onRequestFields() throws JSONException
                {
                    JSONObject data = new JSONObject();
                    data.put("sample", "LoremIpsum");
                    data.put("tvTitleText", textView.getText());
                    data.put("buttonText", button.getText());
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
                                textView.setText(fieldData.get("textView").toString());
                                button.setText(fieldData.get("buttonText").toString());
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
}