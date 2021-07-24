package trc3543.trcscoutingapp.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
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
    final int PAGE_COUNT = 3;
    private String[] tabTitles = new String[PAGE_COUNT];
    private AbstractPageFragment[] fragments = new AbstractPageFragment[PAGE_COUNT];

    public CustomFragmentPagerAdapter(FragmentManager fm, String initJsonFields)
    {
        super(fm);
        instantiatePageFragments(initJsonFields);
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
        initAbsPageFragment(AutonomousFragment.class, 0, "Autonomous", initJsonFields);
        initAbsPageFragment(TeleOpFragment.class, 1, "Teleoperated", initJsonFields);
        initAbsPageFragment(EndgameFragment.class, 2, "Endgame", initJsonFields);
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