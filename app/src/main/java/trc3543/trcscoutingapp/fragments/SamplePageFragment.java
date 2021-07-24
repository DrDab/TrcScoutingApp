package trc3543.trcscoutingapp.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import trc3543.trcscoutingapp.R;

public class SamplePageFragment extends AbstractPageFragment
{
    private TextView textView;

    @Override
    public void instantiateViews(LayoutInflater inflater, ViewGroup container)
    {
        view = inflater.inflate(R.layout.fragment_sample_page, container, false);
        textView = (TextView) view.findViewById(R.id.tvTitle);
    }

    @Override
    public void setFields(JSONObject fieldData) throws JSONException
    {
        textView.setText(fieldData.getString("sampleText"));
    }

    @Override
    public JSONObject getFields()
    {
        return null;
    }
}