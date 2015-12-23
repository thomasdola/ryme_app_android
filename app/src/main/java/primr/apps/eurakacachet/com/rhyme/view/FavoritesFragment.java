package primr.apps.eurakacachet.com.rhyme.view;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import primr.apps.eurakacachet.com.rhyme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    private static final String CURRENT_TAB_KEY = "current_tab";
    public static TabLayout sTablayout;
    public static ViewPager sViewPager;
    public static int sItemCount = 2;


    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorites, null);

        sTablayout = (TabLayout) rootView.findViewById(R.id.favorites_tabs);
        
        sViewPager = (ViewPager) rootView.findViewById(R.id.favorites_viewpager);
        sViewPager.setAdapter(new FavoritesTabsAdapter(getChildFragmentManager()));

        sTablayout.post(new Runnable() {
            @Override
            public void run() {
                sTablayout.setupWithViewPager(sViewPager);
            }
        });

        sViewPager.setCurrentItem(1);

        return rootView;
    }

    private class FavoritesTabsAdapter extends FragmentPagerAdapter{

        public FavoritesTabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new FavoriteTracksFragment();
                case 1:
                    return new FavoriteArtistsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return sItemCount;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return "Favorite Tracks";
                case 1:
                    return "Favorite Artists";
            }
            return null;
        }
    }

}
