package trc3543.trcscoutingapp.ui;

import android.os.Bundle;

import org.json.JSONObject;

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
    private String tabTitles[] = new String[] { "Autonomous", "Teleoperated", "Endgame" };
    private AbstractPageFragment[] fragments = new AbstractPageFragment[PAGE_COUNT];

    public CustomFragmentPagerAdapter(FragmentManager fm, String initJsonFields)
    {
        super(fm);
        fragments[0] = new AutonomousFragment();
        fragments[0].setArguments(getArgsBundle(0, initJsonFields));
        fragments[1] = new TeleOpFragment();
        fragments[1].setArguments(getArgsBundle(1, initJsonFields));
        fragments[2] = new EndgameFragment();
        fragments[2].setArguments(getArgsBundle(2, initJsonFields));
    }

    public CustomFragmentPagerAdapter(FragmentManager fm)
    {
       this(fm, null);
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