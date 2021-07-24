package trc3543.trcscoutingapp.fragutil;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import trc3543.trcscoutingapp.data.AppInfo;
import trc3543.trcscoutingapp.fragments.AbstractPageFragment;
import trc3543.trcscoutingapp.fragments.AutonomousFragment;
import trc3543.trcscoutingapp.fragments.EndgameFragment;
import trc3543.trcscoutingapp.fragments.TeleOpFragment;

/**
 * This class is a custom Fragment pager adapter that is used
 * to manage the paged Fragment navigation offered by the SetMatchInfo
 * activity. It holds an array of AbstractPageFragments containing implementations
 * of the abstract class from which child Fragments of the SetMatchInfo
 * activity are instantiated.
 */
public class CustomFragmentPagerAdapter extends FragmentPagerAdapter
{
    public static final String MODULE_NAME = "CustomFragmentPagerAdap";
    static final int PAGE_COUNT = 3;
    private String[] tabTitles = new String[PAGE_COUNT];
    private AbstractPageFragment[] fragments = new AbstractPageFragment[PAGE_COUNT];

    public CustomFragmentPagerAdapter(FragmentManager fm, String initJsonFields)
    {
        super(fm);
        Log.d(MODULE_NAME, "Instantiating " + PAGE_COUNT + " pages...");
        instantiatePageFragments(initJsonFields);
        Log.d(MODULE_NAME, PAGE_COUNT + " pages initialized.");
    }

    /**
     * Instantiates all the AbstractPageFragment instances to be contained by this
     * custom fragment pager adapter, given a JSON string containing data to populate
     * the child Views of the AbstractPageFragment instances to be instantiated.
     *
     * @param initJsonFields, a JSON string containing data to populate the child Views
     *                        of the AbstractPageFragment instances to be instantiated.
     */
    public void instantiatePageFragments(String initJsonFields)
    {
        for (int i = 0; i < AppInfo.NUM_PAGES; i++)
        {
            initAbsPageFragment(AppInfo.FRAGMENT_CLASSES[i], i, AppInfo.TAB_NAMES[i], initJsonFields);
        }
    }

    /**
     * Instantiates an AbstractPageFragment instance given an implementation of the
     * AbstractPageFragment abstract class, the page number of the instance, the title of
     * the tab the Fragment is bound to, and a string containing data to populate the child Views
     * of the AbstractPageFragment to be instantiated.
     *
     * @param abstractPageFragmentClass, the class extending AbstractPageFragment to be instantiated.
     * @param page, the page number of the AbstractPageFragment to be instantiated.
     * @param tabTitle, the title of the tab the AbstractPageFragment instance will be bound to.
     * @param initJsonFields, a JSON string containing data to populate the child Views of the
     *                        AbstractPageFragment to be instantiated.
     */
    public void initAbsPageFragment(Class<?> abstractPageFragmentClass, int page, String tabTitle, String initJsonFields)
    {
        Log.d(MODULE_NAME, "Instantiating page fragment " + page + " (title=" +
                (tabTitle == null ? "NULL" : tabTitle) + ", initJsonFields=" +
                (initJsonFields == null ? "NULL" : initJsonFields) + ").");
        try
        {
            fragments[page] = (AbstractPageFragment) abstractPageFragmentClass.newInstance();
            fragments[page].setArguments(getArgsBundle(page, initJsonFields));
            tabTitles[page] = tabTitle;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.d(MODULE_NAME, "Instantiated page fragment " + page + ".");
    }

    /**
     * Returns an argument Bundle from which an AbstractPageFragment can be instantiated,
     * containing the page number and data to populate the child Views of the AbstractPageFragment
     * to be instantiated.
     *
     * @param page, the page number of the AbstractPageFragment to be instantiated.
     * @param initJsonFields, a JSON string with the data to populate the child Views of the
     *                       AbstractPage to be instantiated.
     * @return
     */
    public Bundle getArgsBundle(int page, String initJsonFields)
    {
        Bundle args = new Bundle();
        args.putInt(AbstractPageFragment.ARG_PAGE, page);
        args.putString(AbstractPageFragment.ARG_INIT_JSON_FIELDS, initJsonFields);
        return args;
    }

    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position)
    {
        return (Fragment) fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        // Generate title based on item position
        return tabTitles[position];
    }
}