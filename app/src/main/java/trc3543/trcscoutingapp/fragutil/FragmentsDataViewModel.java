package trc3543.trcscoutingapp.fragutil;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.ViewModel;
import trc3543.trcscoutingapp.fragments.AbstractPageFragment;

public class FragmentsDataViewModel extends ViewModel
{
    public static final String MODULE_NAME = "FragmentsDataViewModel";
    private Map<Integer, AbstractPageFragment> pageFragmentMap;

    public FragmentsDataViewModel()
    {
        init();
    }

    public void init()
    {
        pageFragmentMap = new HashMap<>();
        Log.d(MODULE_NAME, MODULE_NAME + " initialized.");
    }

    public int getNumRegistered()
    {
        return pageFragmentMap.size();
    }

    public void register(AbstractPageFragment fragment)
    {
        pageFragmentMap.put(fragment.getPage(), fragment);
        Log.d(MODULE_NAME, "Registered fragment " + fragment.getPage());
    }

    public void deregister(int page)
    {
        pageFragmentMap.remove(page);
        Log.d(MODULE_NAME, "Deregistered fragment " + page);
    }

    public void deregisterAll()
    {
        pageFragmentMap.clear();
        Log.d(MODULE_NAME, "Cleared all fragments.");
    }

    public boolean pageRegistered(int page)
    {
        return pageFragmentMap.containsKey(page);
    }

    public JSONObject getInfoFromPage(int page)
    {
        Log.d(MODULE_NAME, "Getting info from page " + page + "...");
        if (!pageRegistered(page))
        {
            Log.d(MODULE_NAME, page + " isn't registered, no info to return.");
            return null;
        }
        JSONObject jsonObject = pageFragmentMap.get(page).getFields();
        Log.d(MODULE_NAME, "Page " + page + " info=" + (jsonObject == null ? "NULL" : jsonObject.toString()));
        return jsonObject;
    }
}
