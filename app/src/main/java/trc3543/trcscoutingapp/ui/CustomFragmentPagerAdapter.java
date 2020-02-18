package trc3543.trcscoutingapp.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import trc3543.trcscoutingapp.fragments.AutonomousFragment;
import trc3543.trcscoutingapp.fragments.EndgameFragment;
import trc3543.trcscoutingapp.fragments.TeleOpFragment;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Autonomous", "Teleoperated", "Endgame" };
    private Fragment[] fragments = new Fragment[3];

    public CustomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments[0] = AutonomousFragment.newInstance(0);
        fragments[1] = TeleOpFragment.newInstance(1);
        fragments[2] = EndgameFragment.newInstance(2);
    }

    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        // Generate title based on item position
        return tabTitles[position];
    }
}