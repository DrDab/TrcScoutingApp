package trc3543.trcscoutingapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import trc3543.trcscoutingapp.fragments.AbstractPageFragment;
import trc3543.trcscoutingapp.fragments.AutonomousFragment;
import trc3543.trcscoutingapp.fragments.EndgameFragment;
import trc3543.trcscoutingapp.fragments.TeleOpFragment;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter
{
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[PAGE_COUNT];
    private AbstractPageFragment[] fragments = new AbstractPageFragment[PAGE_COUNT];

    public CustomFragmentPagerAdapter(FragmentManager fm, String initJsonFields)
    {
        super(fm);
        initAbsPageFragment(AutonomousFragment.class, 0, "Autonomous", initJsonFields);
        initAbsPageFragment(TeleOpFragment.class, 1, "Teleoperated", initJsonFields);
        initAbsPageFragment(EndgameFragment.class, 2, "Endgame", initJsonFields);
    }

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