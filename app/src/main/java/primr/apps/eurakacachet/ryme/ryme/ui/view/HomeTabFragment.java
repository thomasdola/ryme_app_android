package primr.apps.eurakacachet.ryme.ryme.ui.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import icepick.State;

import primr.apps.eurakacachet.ryme.ryme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeTabFragment extends Fragment {

    private static final String POSITION = "current_tab";
    public static TabLayout sTablayout;
    @State public int mCurrentTab = 1;
    public static ViewPager sViewPager;
    public static int sItemCount = 3;


    public HomeTabFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout, null);

        sTablayout = (TabLayout) view.findViewById(R.id.tabs);

        sViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        sViewPager.setAdapter(new HomeTabsAdapter(getChildFragmentManager()));

        sViewPager.setCurrentItem(mCurrentTab);

        sTablayout.post(new Runnable() {
            @Override
            public void run() {
                sTablayout.setupWithViewPager(sViewPager);
                setTabsIcon();
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, sTablayout.getSelectedTabPosition());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            sViewPager.setCurrentItem(savedInstanceState.getInt(POSITION, 1));
        }
    }


    private void setTabsIcon() {
        TabLayout.Tab firstTab = sTablayout.getTabAt(0);
        if (firstTab != null) {
            firstTab.setIcon(R.drawable.new_release);
        }

        TabLayout.Tab secondTab = sTablayout.getTabAt(1);
        if (secondTab != null) {
            secondTab.setIcon(R.drawable.trending);
        }

        TabLayout.Tab thirdTab = sTablayout.getTabAt(2);
        if (thirdTab != null) {
            thirdTab.setIcon(R.drawable.event);
        }
    }


    class HomeTabsAdapter extends FragmentPagerAdapter{

        public HomeTabsAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position){
            switch (position){
                case 0:
                    return new NewReleasedTracksFragment();
                case 1:
                    return new TrendingTracksFragment();
                case 2:
                    return new EventsFragment();
            }

            return null;
        }

        @Override
        public int getCount(){
            return sItemCount;
        }

        @Override
        public CharSequence getPageTitle(int position){
//            switch (position){
//                case 0:
//                    return "New Releases";
//                case 1:
//                    return "Trending";
//                case 2:
//                    return "Events";
//            }
            return null;
        }
    }
}
