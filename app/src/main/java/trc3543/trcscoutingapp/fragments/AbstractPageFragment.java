package trc3543.trcscoutingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractPageFragment extends Fragment
{
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_INIT_JSON_FIELDS = "ARG_INIT_JSON_FIELDS";

    View view;
    private FragmentsDataViewModel viewModel;
    private int pageNum;


    public abstract void instantiateViews(LayoutInflater inflater, ViewGroup container);

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
            instantiateViews(inflater, container);
        }

        try
        {
            String jsonFieldsStr = getArguments().getString(ARG_INIT_JSON_FIELDS);
            JSONObject initFieldData = jsonFieldsStr == null ? null : new JSONObject(jsonFieldsStr);
            if (initFieldData != null)
                setFields(initFieldData);
        }
        catch (JSONException jsonException)
        {
            jsonException.printStackTrace();
        }

        pageNum = getArguments().getInt(ARG_PAGE);
        viewModel = new ViewModelProvider(requireActivity()).get(FragmentsDataViewModel.class);
        viewModel.register(this);

        return view;
    }

    public abstract void setFields(JSONObject fieldData) throws JSONException;

    public abstract JSONObject getFields();

    public int getPage()
    {
        return pageNum;
    }
}