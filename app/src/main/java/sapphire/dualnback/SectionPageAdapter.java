package sapphire.dualnback;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skyler on 12/2/2017.
 */

class SectionPageAdapter extends FragmentStatePagerAdapter {
    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public SectionPageAdapter(FragmentManager fm){
        super(fm);
    }

    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    public void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
