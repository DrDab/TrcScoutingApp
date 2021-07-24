package trc3543.trcscoutingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import trc3543.trcscoutingapp.fragutil.FragmentsDataViewModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This abstract class extending Fragment is used as a template
 * from which the fragment code for pages within the SetMatchInfo
 * activity can be easily created, with the code for communication
 * between the SetMatchInfo host activity and its child Fragment
 * already included. To create your own fragment code based on
 * AbstractPageFragment, create a class extending AbstractPageFragment,
 * and implement the following methods: instantiateViews, setFields, and
 * getFields. instantiateViews should assign the AbstractPageFragment
 * instance-variable, view, according to the corresponding layout,
 * and initialize any elements (EditTexts, checkboxes, etc.) in that
 * view. setFields should populate the elements in the Fragment
 * with data values given by the JSONObject parameter. getFields should
 * return a JSONObject with the contents of each element.
 *
 * @author DrDab
 */
public abstract class AbstractPageFragment extends Fragment
{
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_INIT_JSON_FIELDS = "ARG_INIT_JSON_FIELDS";

    View view;
    private FragmentsDataViewModel viewModel;
    private int pageNum;

    /**
     * This autonomously-called method instantiates the Views contained in the Fragment by first
     * instantiating the View hierarchy corresponding to the Fragment from which the child
     * Views are stored.
     *
     * @param inflater,  the LayoutInflater used to inflate the View hierarchy.
     * @param container, the ViewGroup containing the View hierarchy of the Fragment
     *                   that inflater inflates.
     */
    public abstract void instantiateViews(LayoutInflater inflater, ViewGroup container);

    /**
     * Method which is autonomously called on creation of this Fragment, which can restore itself
     * to a previous state represented by the Bundle, savedInstanceState.
     *
     * @param savedInstanceState, the Bundle from which the previous state is restored from.
     *                            null if there is no previous state to restore the Fragment from.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    /**
     * Method which is autonomously called after the View hierarchy corresponding to the Fragment is created.
     * In this method, the View hierarchy is to be inflated by the inheriting Fragment in the
     * method instantiateViews, so the child views in the hierarchy, such as EditTexts
     * and CheckBoxes, can be accessed. After that, communication between this Fragment and the
     * host Activity, SetMatchInfo, is established. If Views in the hierarchy have data to be
     * populated, as with loading an existing match to be edited, the JSON data from which
     * to populate the Views is loaded from the Fragment's arguments and then setFields, which
     * populates Views based on given JSON data, is called. Finally, the ViewModel used by the Fragment's
     * parent activity, SetMatchInfo, to request data entered in this Fragment's Views is
     * instantiated.
     *
     * @param inflater,          the LayoutInflater used to inflate the View hierarchy.
     * @param container,         the ViewGroup containing the View hierarchy of the Fragment that
     *                           inflater inflates.
     * @param savedInstanceState the Bundle from which the previous state is restored from. null
     *                           if there is no previous state to restore the Fragment from.
     * @return
     */
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

    /**
     * Method that is autonomously called which populates this Fragment's child Views
     * with data specified by the JSONObject given, fieldData.
     *
     * @param fieldData, the data to populate this Fragment's child Views with.
     * @throws JSONException
     */
    public abstract void setFields(JSONObject fieldData) throws JSONException;

    /**
     * Method that is autonomously called by this Fragment's ViewModel instance
     * which returns a JSONObject containing the data this Fragment's child Views are
     * populated with.
     *
     * @return a JSONObject containing the data this Fragment's child Views are populated with.
     */
    public abstract JSONObject getFields();

    /**
     * Returns the page number of this Fragment.
     *
     * @return the page number of this Fragment.
     */
    public int getPage()
    {
        return pageNum;
    }
}