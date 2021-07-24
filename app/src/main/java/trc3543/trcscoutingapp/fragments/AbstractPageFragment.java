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
import androidx.lifecycle.ViewModelProvider;
import trc3543.trcscoutingapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractPageFragment extends Fragment
{
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_INIT_JSON_FIELDS = "ARG_INIT_JSON_FIELDS";

    View view;
    private FragmentsDataViewModel viewModel;
    private int pageNum;



    /*
    public static AbstractPageFragment newInstance(int page, String initJsonFields)
    {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_INIT_JSON_FIELDS, initJsonFields);
        AbstractPageFragment fragment = new AbstractPageFragment();
        fragment.setArguments(args);
        return fragment;
    }


     */

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
            {
                setFields(initFieldData);
            }
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